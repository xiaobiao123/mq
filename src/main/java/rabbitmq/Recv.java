package rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {

    private final static String QUEUE_NAME = "hello123";

    public static void main(String[] argv) throws Exception {
    /*这里怎么打开连接和信道，以及声明用于接收消息的队列，这些步骤与发送端基本上是一样的*/
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("springcloud");
        factory.setPassword("123456");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        /*确保这里的队列是存在的*/
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    
    /*这里用到了额外的类QueueingConsumer来缓存服务器将要推过来的消息。我们通知服务器向接收端推送消息，
　　　然后服务器将会向客户端异步推送消息，这里提供了一个可以回调的对象来缓存消息，直到我们做好准备来使用
　　  它，这个类就是QueueingConsumer*/
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}