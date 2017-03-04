package rabbitmq.publish.subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogs2 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv)throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("springcloud");
        factory.setPassword("123456");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明消息路由的名称和类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //声明一个随机消息队列
        String queueName = channel.queueDeclare().getQueue();
        
        //绑定消息队列和消息路由
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        
        //启动一个消费者
        channel.basicConsume(queueName, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received2 '" + message + "'");
        }
    }
}