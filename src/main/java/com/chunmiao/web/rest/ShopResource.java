package com.chunmiao.web.rest;

import com.chunmiao.config.KafkaProperties;
import com.chunmiao.service.GoodService;
import com.chunmiao.service.dto.GoodDTO;
import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import javax.websocket.SendResult;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Future;

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

    /**
     * POST good
     */
    @PostMapping("/good")
    public String good() {
        return "good";
    }

    /**
     * POST goodWithActivity
     */
    @PostMapping("/good-with-activity")
    public String goodWithActivity(Long goodId, Long activityId) {
        try (
            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProperties.getProducerProps())
        ) {
            Future<RecordMetadata> shop = producer.send(new ProducerRecord<>("shop", "hello!"));
        }


        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        RedissonClient redissonClient = Redisson.create();
        RAtomicLong atomicLong = redissonClient.getAtomicLong("stock_" + goodId);
        atomicLong.compareAndSet(0, goodService.findOne(goodId).map(GoodDTO::getStock).get());
        Long stock = atomicLong.decrementAndGet();
        if (stock >= 0) {
//            template.send("test","123");
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

// 消费者
    @GetMapping("/test")
    public void consumer(){
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties.getConsumerProps());
        LinkedList<String> topics = new LinkedList<>();
        topics.add("shop");
        consumer.subscribe(topics);
        ConsumerRecords<String, String> poll = consumer.poll(Duration.ZERO);

    }

}
