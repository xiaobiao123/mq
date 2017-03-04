package rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("springcloud");
        factory.setPassword("123456");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明direct类型的exchange
        channel.queueDeclare("errortest", true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

//        String queueName = channel.queueDeclare().getQueue();

//        if (argv.length < 1) {
//            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
//            System.exit(1);
//        }

        //绑定我们需要接收的日志级别
//        for (String severity : argv) {
            channel.queueBind("errortest", EXCHANGE_NAME, "error");
//        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume("errortest", false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
        }
    }
}