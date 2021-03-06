package com.demo.rabbitMQ.workDirect;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.demo.rabbitMQ.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Consumer2 {
	//队列
    private static final String QUEUE_NAME="test_queues_direct1";
    //交换机
  	private static final String EXCHANGE_NAME="test_exchange_direct";
  	
  	public static void main(String[] args) throws IOException, TimeoutException {
		//获取连接
  		Connection connection = ConnectionUtils.getConnection();
  		//创建对列
  		Channel channel = connection.createChannel();
  		//声明队列
  		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
  		//绑定路由和交换机
  		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
  		//保证一次只分发一个
  		channel.basicQos(1);
  		//定义一个消费者
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) 
		{
			//一旦有消息就会触发该方法
			@Override
			public void handleDelivery(String consumerTag,Envelope envelope,BasicProperties basicProperties,byte[] body) throws IOException 
			{
				String msg=new String(body,"utf-8");
				System.out.println("消费者2===========:"+msg);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally 
				{
					System.out.println("=================consumer2 over================");
					//手动应答
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		//关闭自动应答
		boolean autoAck=false;
		//监听队列
		channel.basicConsume(QUEUE_NAME, autoAck, defaultConsumer);
  		
  		
  	}

}
