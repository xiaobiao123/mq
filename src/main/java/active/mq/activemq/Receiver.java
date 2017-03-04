package active.mq.activemq;

import java.util.concurrent.TimeUnit;
 
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
 
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class Receiver {
 
    public static void aa() throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616"); 
           
        Connection connection = connectionFactory.createConnection(); 
        connection.start();
       
        final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); 
        Destination destination = session.createQueue("Test.foo");
         
        MessageConsumer consumer = session.createConsumer(destination);
        //listener 方式
        consumer.setMessageListener(new MessageListener() {
      
            public void onMessage(Message msg) {
                ObjectMessage message = (ObjectMessage) msg;
                //TODO something....
                try {
                    User user = (User) message.getObject();
                    System.out.println("收到消息："+user.getName());
                } catch (JMSException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    session.commit();
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
      
        });
        TimeUnit.MINUTES.sleep(1);
       
        session.close(); 
        connection.close();
    }
     
    public static void main(String[] args) throws Exception{
        aa();
    }
}