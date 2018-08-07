package com.demo.rabbitMQ.workDirect;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模式
 * @author Administrator
 */
public class Produce
{
	private static final String EXCHANGE_NAME="test_exchange_direct";
	
	
	public static void main(String[] args) throws IOException, TimeoutException
	{
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//创建通道
		Channel channel = connection.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		String msg="路由模式";
		String routingKey="info";
		channel.basicPublish(EXCHANGE_NAME, routingKey,null, msg.getBytes());
		System.out.println("消息已发送:"+msg);
		channel.close();
		connection.close();
	}
}
