package active.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

    public static void main(String[] args) throws Exception {
        aa();
    }
    public static void aa() throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
          
        Connection connection = connectionFactory.createConnection();  
        connection.start();  
        
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
        Destination destination = session.createQueue("Test.foo");  

        MessageProducer producer = session.createProducer(destination);  
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for(int i=0; i<100; i++) {  
            int id = i+1;
            ObjectMessage message = session.createObjectMessage();
            message.setObject(new User(id, "张三"+id, "123456"));
            producer.send(message);  
        }  
        System.out.println("ok...");
        session.commit();
        session.close();  
        connection.close();  
    }
}