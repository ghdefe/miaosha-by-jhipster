package com.chunmiao.web.rest;

import com.chunmiao.config.KafkaProperties;
import com.chunmiao.domain.SecActivity;
import com.chunmiao.domain.User;
import com.chunmiao.repository.SecActivityRepository;
import com.chunmiao.repository.UserRepository;
import com.chunmiao.service.GoodOrderService;
import com.chunmiao.service.GoodService;
import com.chunmiao.service.dto.ActivityTime;
import com.chunmiao.service.dto.GoodDTO;
import com.chunmiao.service.dto.GoodOrderDTO;
import com.chunmiao.service.mapper.ActivityTimeMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.protocol.types.Field;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * ShopResource controller
 */
@RestController
@RequestMapping("/api/shop")
public class ShopResource {

    private final Logger log = LoggerFactory.getLogger(ShopResource.class);

    @Autowired
    private GoodService goodService;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecActivityRepository secActivityRepository;

    @Autowired
    private GoodOrderService goodOrderService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityTimeMapper activityTimeMapper;

    /**
     * 不安全的购买机制
     */
    @Transactional
    @PostMapping("/goodTest")
    public String goodTest(Long goodId) {
        log.debug("线程不安全的购买接口");
//        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        String buyer = "admin";
        GoodDTO goodDTO = goodService.findOne(goodId).get();
        Long stock = goodDTO.getStock();
        if (stock > 0) {
            // 扣减库存
            stock--;
            goodDTO.setStock(stock);
            goodService.save(goodDTO);
            // 增加订单
            GoodOrderDTO goodOrderDTO = new GoodOrderDTO();
            goodOrderDTO.setCreateTime(ZonedDateTime.now());
            goodOrderDTO.setPrice(goodDTO.getPrice());
            goodOrderDTO.setActivityId(0l);
            goodOrderDTO.setGoodId(goodDTO.getId());
            goodOrderDTO.setBuyerId(userRepository.findOneByLogin(buyer).get().getId());
            goodOrderService.save(goodOrderDTO);
            return buyer + "购买" + goodId + "成功";
        }
        return buyer + "购买" + goodId + "失败";
    }

    /**
     * POST good
     * 普通购买，利用乐观锁
     */
    @Transactional
    @PostMapping("/good")
    public String good(Long goodId) {
        log.debug("线程安全的购买接口，使用乐观锁实现");
//        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        String buyer = "admin";
        // 扣减库存
        try {
            GoodDTO goodDTO = goodService.decreaseStockOptimistic(goodId);
            // 增加订单
            GoodOrderDTO goodOrderDTO = new GoodOrderDTO();
            goodOrderDTO.setCreateTime(ZonedDateTime.now());
            goodOrderDTO.setPrice(goodDTO.getPrice());
            goodOrderDTO.setActivityId(0l);
            goodOrderDTO.setGoodId(goodDTO.getId());
            goodOrderDTO.setBuyerId(userRepository.findOneByLogin(buyer).get().getId());
            goodOrderService.save(goodOrderDTO);
        } catch (Exception e) {
            return "购买失败";
        }
        return buyer + "购买" + goodId + "成功";
    }

    /**
     * POST goodWithActivity
     * 秒杀购买，利用redis缓存
     */
    @Transactional
    @PostMapping("/good-with-activity")
    public String goodWithActivity(Long goodId, Long activityId) throws JsonProcessingException {
        log.debug("秒杀购买接口");
        if (!checkIsActivityTime(activityId)) {
            return "不在活动时间";
        }
//        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        String buyer = "admin";
        // 从redis中拿数据
        BoundValueOperations<String, String> boundValueOps = redisTemplate.boundValueOps("stock_" + goodId);
        if (boundValueOps.get() == null) {
            boundValueOps.set(goodService.findOne(goodId).map(GoodDTO::getStock).get().toString());
        }
        Long stock = boundValueOps.decrement();
        boundValueOps.expire(5, TimeUnit.MINUTES);
        // redis中还有库存时
        if (stock >= 0) {
            // 发送消息到kafka，对数据库进行操作
            addOrderToKafka(buyer, goodId, activityId);
            return buyer + "购买" + goodId + "成功";
        } else {
            return "没库存";
        }
    }


    /**
     * DELETE good
     *
     * @return
     */
    @DeleteMapping("/good")
    public void refund() {

    }

    // 给kafka发送消息
    void addOrderToKafka(String buyer, Long goodId, Long activityId) {
        try (
            KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProperties.getProducerProps())
        ) {
            GoodOrderDTO goodOrderDTO = new GoodOrderDTO();
            goodOrderDTO.setBuyerId(userRepository.findOneByLogin(buyer).map(User::getId).get());
            goodOrderDTO.setGoodId(goodId);
            goodOrderDTO.setActivityId(activityId);
            goodOrderDTO.setPrice(secActivityRepository.findById(activityId).map(SecActivity::getSecPrice).get());
            goodOrderDTO.setCreateTime(ZonedDateTime.now());
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(goodOrderDTO);
            Future<RecordMetadata> order = producer.send(new ProducerRecord<>("order", json));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // kafka消费者
    @Transactional
    @KafkaListener(topics = "order", groupId = "order-consumer")
    public void consumer(String json) throws JsonProcessingException {
        GoodOrderDTO goodOrderDTO = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(json, GoodOrderDTO.class);
        // 扣减库存 使用悲观锁
        goodService.decreaseStockPessimistic(goodOrderDTO.getGoodId());
        // 创建订单
        goodOrderService.save(goodOrderDTO);
    }

    boolean checkIsActivityTime(Long activityId) throws JsonProcessingException {
        log.debug("校验是否在活动时间");
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("activity_" + activityId);
        if (ops.get() == null){
            SecActivity activity = secActivityRepository.getOne(activityId);
            ActivityTime activityTime = activityTimeMapper.toDto(activity);
            String json = objectMapper.writeValueAsString(activityTime);
            ops.set(json);
        }
        ActivityTime activityTime = objectMapper.readValue(ops.get(), ActivityTime.class);
        if (activityTime.getStart().isBefore(ZonedDateTime.now()) && activityTime.getEnd().isAfter(ZonedDateTime.now())){
            return true;
        }
        return false;

    }


}
