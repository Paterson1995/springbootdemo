package com.demo.springbootdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringbootdemoApplication.class)
@RunWith(SpringRunner.class)
public class RabbitMQTest {

    //注入rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //topic 动态路由  订阅模式
    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("topics","product.save.add","produce.save.add 路由消息");
    }

    //route 路由模式
    @Test
    public void testRoute(){
        rabbitTemplate.convertAndSend("directs","error","发送info的key的路由信息");
    }

    //fanout 广播
    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("logs","","Fanout的模型发送的消息");
    }

    //work
    @Test
    public void testWork(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work","work模型"+i);
        }
    }

    //hello world
    @Test
    //生产端没有指定交换机只有routingKey和Object，也就是说这个消费方产生hello队列，放在默认的交换机(AMQP default)上。
    // 而默认的交换机有一个特点，只要你的routerKey与这个交换机中有同名的队列，就会自动路由上
    public void testHello(){
        rabbitTemplate.convertAndSend("hello","hello world");
    }

}
