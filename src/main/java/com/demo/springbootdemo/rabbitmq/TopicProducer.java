package com.demo.springbootdemo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class TopicProducer {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机以及交换机类型 topic
        channel.exchangeDeclare("topics", "topic");

        //发布消息
        String routekey = "save.user.delete";

        channel.basicPublish("topics", routekey, null, ("这里是topic动态路由模型,routekey: [" + routekey + "]").getBytes());

        //关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);

    }
}