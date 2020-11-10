package com.chunmiao.web.rest;

import com.chunmiao.config.KafkaProperties;
import com.chunmiao.domain.SecActivity;
import com.chunmiao.domain.User;
import com.chunmiao.repository.SecActivityRepository;
import com.chunmiao.repository.UserRepository;
import com.chunmiao.service.GoodOrderService;
import com.chunmiao.service.GoodService;
import com.chunmiao.service.dto.GoodDTO;
import com.chunmiao.service.dto.GoodOrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * POST good
     * 普通购买，利用乐观锁
     */
    @PostMapping("/good")
    public String good(Long goodId) {
        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            GoodDTO goodDTO = goodService.decreaseStockOptimistic(goodId);
        }
        catch (RuntimeException e){
            return "库存不足，购买失败";
        }

        return buyer + "购买" + goodId + "成功";
    }

    /**
     * POST goodWithActivity
     * 秒杀购买，利用redis缓存
     */
    @Transactional
    @PostMapping("/good-with-activity")
    public String goodWithActivity(Long goodId, Long activityId) {
        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        // 从redis中拿数据
        RedissonClient redissonClient = Redisson.create();
        RAtomicLong atomicLong = redissonClient.getAtomicLong("stock_" + goodId);
        atomicLong.compareAndSet(0, goodService.findOne(goodId).map(GoodDTO::getStock).get());  // redis不存在该键值则从数据库取值
        atomicLong.expire(5, TimeUnit.MINUTES); // 设置过期时间
        Long stock = atomicLong.decrementAndGet();  // 减少redis中的库存

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


}
