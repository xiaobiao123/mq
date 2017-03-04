package active.mq;

import java.util.Date;

import javax.jms.JMSException;

import javax.jms.MapMessage;

import javax.jms.Message;

import javax.jms.Session;

import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import org.springframework.jms.core.JmsTemplate;

import org.springframework.jms.core.MessageCreator;


/**
 * <b>function:</b> Spring JMSTemplate 消息发送者
 *
 * @author hoojo
 * @version 1.0
 * @createDate 2013-6-24 下午02:18:48
 * @file Sender.java
 * @package com.hoo.mq.spring.support
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 */

public class Sender {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-active-mq.xml");
        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("message", "current system time: " + new Date().getTime());
                return message;
            }
        });
        ctx.close();

    }

}