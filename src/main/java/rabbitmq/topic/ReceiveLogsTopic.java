package rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsTopic {
    private static final String EXCHANGE_NAME = "topic_logs";
    public static void main(String[] argv)
                  throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("springcloud");
        factory.setPassword("123456");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //指定一个topic类型的exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
//
//        if (argv.length < 1){
//            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
//            System.exit(1);
//        }

        //绑定binding key
//        for(String bindingKey : argv){
            channel.queueBind(queueName, EXCHANGE_NAME, "*.orange.*");
//        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
        }
    }
}