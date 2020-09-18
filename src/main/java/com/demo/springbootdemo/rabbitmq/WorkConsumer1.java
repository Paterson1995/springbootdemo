package com.demo.springbootdemo.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class WorkConsumer1 {

    public static void main(String[] args) throws IOException {

        //通过工具类获取连接
        Connection connection = RabbitMQUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //通道绑定队列
        channel.queueDeclare("hello",true,false,false,null);
        channel.basicQos(1);    //每次只能消费一个消息

        //消费消息
        //参数1: 消费那个队列的消息 队列名称
        //参数2: 开始消息的自动确认机制，得到消息之后，队列自动丢弃消息，但是消费者对消息的处理可能还未完成，这时若发生异常或者服务宕机，
        // 后续的消息也会丢失，不建议开启
        //参数3: 消费时的回调接口
        channel.basicConsume("hello",false,new DefaultConsumer(channel){
            @Override //最后一个参数: 消息队列中取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者1："+new String(body));
                //手动消息确认，不进行这一步消息会保留在消息队列中，参数2：不开启多条同时确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }

}
