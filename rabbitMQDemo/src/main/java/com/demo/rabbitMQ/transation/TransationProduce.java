package com.demo.rabbitMQ.transation;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TransationProduce {
	private static final String QUEUE_NAME="test_queue_transation";
	
	public static void main(String[] args) throws IOException, TimeoutException  {
		Connection connection=null;
		Channel channel=null;
		try {
			//获取连接
			connection = ConnectionUtils.getConnection();
			//创建通道
			channel = connection.createChannel();
			//声明队列
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			String msg="hello tx";
			//开启事务模式
			channel.txSelect();
			//发布消息
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			//提交事务
			int i=1/0;
			channel.txCommit();
		} catch (Exception e) {
			channel.txRollback();
			System.out.println("send message rollback");
			throw e;
		}finally 
		{
			channel.close();
			connection.close();
		}
		
	}
}
