package com.demo.rabbitMQ.workPublishSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式
 * @author Administrator
 *
 */
public class Produce
{
	private static final String EXCHANGE_NAME="test_exchange_fanout";
	public static void main(String[] args) throws IOException, TimeoutException 
	{
		//获取连接
		Connection connection = ConnectionUtils.getConnection();
		//获取通道
		Channel channel = connection.createChannel();
		//声明交换机   fanout一种分发模式
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String msg="测试一下啊";
		//向交换机发送消息
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		
		System.out.println("消息已经发送："+msg);
		channel.close();
		connection.close();
	}
}
