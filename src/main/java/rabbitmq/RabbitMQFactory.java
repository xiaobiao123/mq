package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by Administrator on 2017/2/28.
 */
public class RabbitMQFactory {

    private final static String QUEUE_NAME = "hello123";

    public static void main(String[] args) throws Exception {

        /*连接可以抽象为socket连接，为我们维护协议版本信息和协议证书等。这里我们连接
          上了本机的消息服务器实体（localhost）。如果我们想连接其它主机上的RabbitMQ服务，只需要修改一下主机名或是IP就可以了*/
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("springcloud");
        factory.setPassword("123456");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /*接下创建channel（信道），这是绝大多数API都能用到的。为了发送消息，你必须要声明一个消息消息队列，然后向该队列里推送消息*/
        //durable：是否持久化
        //exclusive：是否排外，what？ 这又是什么呢。设置了排外为true的队列只可以在本次的连接中被访问，
        // 也就是说在当前连接创建多少个channel访问都没有关系，但是如果是一个新的连接来访问，对不起，不可以，
        // 下面是我尝试访问了一个排外的queue报的错。还有一个需要说一下的是，排外的queue在当前连接被断开的时候会自动消失（清除）无论是否设置了持久化
        //autoDelete：这个就很简单了，是否自动删除。也就是说queue会清理自己。但是是在最后一个connection断开的时候
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
