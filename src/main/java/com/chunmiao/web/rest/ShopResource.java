package com.chunmiao.web.rest;

import com.chunmiao.service.GoodService;
import com.chunmiao.service.dto.GoodDTO;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ShopResource controller
 */
@RestController
@RequestMapping("/api/shop")
public class ShopResource {

    private final Logger log = LoggerFactory.getLogger(ShopResource.class);

    @Autowired
    private GoodService goodService;
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
    public String goodWithActivity(Long goodId,Long activityId) {
        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        RedissonClient redissonClient = Redisson.create();
        RAtomicLong atomicLong = redissonClient.getAtomicLong("stock_" + goodId);
        atomicLong.compareAndSet(0, goodService.findOne(goodId).map(GoodDTO::getStock).get());
        Long stock = atomicLong.decrementAndGet();
        if (stock >= 0) {
//            template.send("test","123");
            return buyer + "购买" + goodId + "成功";
        }
        else {
            return "没库存";
        }
    }

    /**
    * DELETE good
     * @return
     */
    @DeleteMapping("/good")
    public void refund() {

    }

}
