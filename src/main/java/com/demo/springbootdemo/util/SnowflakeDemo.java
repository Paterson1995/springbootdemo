package com.demo.springbootdemo.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SnowflakeDemo {

    Logger logger;

    private long workId = 0;
    private long datecenterId = 1;
    private Snowflake snowflake = IdUtil.createSnowflake(workId, datecenterId);

    @PostConstruct
    public void init() {
        try {
            workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            logger.info("当前机器的workId：{}", workId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("当前机器的workId获取失败", e);
            workId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized long snowflkeId() {
        return snowflake.nextId();
    }

    public synchronized long snowflkeId(long workId, long datecenterId) {
        Snowflake snowflake = IdUtil.createSnowflake(workId, datecenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
//        System.out.println(new SnowflakeDemo().snowflkeId());
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        SnowflakeDemo snowflakeDemo = new SnowflakeDemo();
        for (int i=0; i<30; i++) {
            threadPool.submit(()->{
                System.out.println(snowflakeDemo.snowflkeId());
            });
        }
        threadPool.shutdown();
    }

}
