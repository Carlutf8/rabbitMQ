package com.demo.rabbitMQ.workQueues;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;
import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Consumer1 {
	
	private static final String QUEUE_NAME="work_queues_test";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//获取通道
		Channel channel = connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//定义一个消费者
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) 
		{
			//一旦有消息就会触发该方法
			@Override
			public void handleDelivery(String consumerTag,Envelope envelope,BasicProperties basicProperties,byte[] body) throws UnsupportedEncodingException 
			{
				String msg=new String(body,"utf-8");
				System.out.println("===========:"+msg);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally 
				{
					System.out.println("=================consumer1 over================");
				}
				
			}
		};
		//监听队列
		boolean autoAck=true;
		channel.basicConsume(QUEUE_NAME, autoAck, defaultConsumer);
	}
}
