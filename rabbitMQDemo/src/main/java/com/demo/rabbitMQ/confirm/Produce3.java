package com.demo.rabbitMQ.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;


import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

/**
 * 消息队列生产者_confirm异步发送
 * @author Administrator
 *
 */
public class Produce3 {
	
	private static final String QUEUE_NAME="test_queue_confirm3";  
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		//1.获取一个连接
		Connection connection = ConnectionUtils.getConnection();
		//2.从连接中获取一个通道
		Channel channel = connection.createChannel();
		//3.声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//4.生产者调用confirmSelect,将队列设置成confirm模式
		//注意：若队列已经被设置为事务模式，不能再设置为confirm模式
		channel.confirmSelect();
		//未确认的消息标识
		final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
		//通道添加监听
		channel.addConfirmListener(new ConfirmListener() {
			//有问题
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) 
				{
					System.out.println("==handleNack==success");
					confirmSet.headSet(deliveryTag+1).clear();
				}else 
				{
					System.out.println("==handleNack==false");
					confirmSet.remove(deliveryTag);
				}
			}
			//没问题
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if(multiple) 
				{
					System.out.println("==handleAck==success");
					confirmSet.headSet(deliveryTag+1).clear();
				}else 
				{
					System.out.println("==handleAck==false");
					confirmSet.remove(deliveryTag);
				}
			}
		});
		
		//5.发布消息
		String msg="confirm模式发出";
		//发布消息
		for(int i=0;i<10;i++) 
		{
			long nextPublishSeqNo = channel.getNextPublishSeqNo();
			channel.basicPublish("", QUEUE_NAME, null,(msg+i).getBytes());
			confirmSet.add(nextPublishSeqNo);
		}
		if(channel.waitForConfirms()) 
		{
			System.out.println("=============消息已发出===============");
		}else 
		{
			System.out.println("=============消息失败===============");
		}
		//关闭通道和连接
		channel.close();
		connection.close();
	}
}
