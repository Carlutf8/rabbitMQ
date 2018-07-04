package com.demo.rabbitMQ.workFair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Produce {
	
	private static final String QUEUE_NAME="work_queues_test";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.获取连接
		Connection connection = ConnectionUtils.getConnection();
		//2.在连接中获取通道
		Channel channel = connection.createChannel();
		//3.声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		/**
		 * 每个消费者发送确认消息之前，只能收到不超过一条数据
		 */
		int prefetchCount=1;
		channel.basicQos(prefetchCount);
		//5.发布消息
		for (int i = 1; i<=50; i++) {
			String msg="生产者发送消息"+i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		}
		System.out.println("=============发送完毕=============");
		channel.close();
		connection.close();
		
	}
}
