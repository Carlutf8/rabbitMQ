package com.demo.rabbitMQ.util;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class ConnectionUtils {
	/**
	 * 获取rabbitMQ的连接
	 * @return hechuanbin
	 */
	public static Connection getConnection() throws IOException, TimeoutException 
	{
		//1.定义连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//2.设置服务地址
		connectionFactory.setHost("localhost");
		//3.设置端口 5672
		connectionFactory.setPort(5672);
		//4.设置虚拟主机
		connectionFactory.setVirtualHost("/sleuthZipkin");
		//5.设置用户名
		connectionFactory.setUsername("zipkin");
		//6.设置密码
		connectionFactory.setPassword("zipkin");
		return connectionFactory.newConnection();
	}
}
