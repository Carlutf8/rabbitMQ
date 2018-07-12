package com.demo.rabbitMQ.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
public class Produces {
	
	private static final String EXCHANGE_NAME="test_exchange_topic";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//创建队列
		Channel channel = connection.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String msg="goods";
		//发送消息
		channel.basicPublish(EXCHANGE_NAME, "good.delete", null, msg.getBytes());
		System.out.println("============消息已发送！=============");
		channel.close();
		connection.close();
		
	}
}
