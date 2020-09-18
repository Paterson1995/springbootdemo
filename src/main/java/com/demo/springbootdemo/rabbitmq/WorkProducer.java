package com.demo.springbootdemo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * helloWorld模型：只有一个消费者，直连，点对点
 */
public class WorkProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        //通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();

        //获取连接中通道
        Channel channel = connection.createChannel();

        //通道绑定对应消息队列
        //参数1:  队列名称 如果队列不存在自动创建
        //参数2:  用来定义队列特性是否要持久化 true 持久化队列，关闭rabbitmq后队列会存储在磁盘，但是队列中的消息不会
        //参数3:  exclusive 是否独占队列  true:独占队列，只能和当前通道绑定，一般设置为false
        //参数4:  autoDelete，true：在消费者消费完队列中的消息并且关闭与队列的连接后，删除队列
        //参数5:  额外附加参数
        channel.queueDeclare("hello",true,false,false,null);

        //发布消息

        //参数1: 交换机名称 参数2:队列名称
        // 参数3:传递消息额外设置（MessageProperties.PERSISTENT_TEXT_PLAIN：消息的持久化）  参数4:消息的具体内容
        for (int i = 1; i <= 20; i++) {
            channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, (i+"---hello rabbitmq").getBytes());
        }

        channel.close();
        connection.close();

        //调用工具类
//        RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }

}
