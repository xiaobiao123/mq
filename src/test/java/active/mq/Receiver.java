package active.mq;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import org.springframework.jms.core.JmsTemplate;


/**
 * <b>function:</b> Spring JMSTemplate 消息接收者
 *
 * @author hoojo
 * @version 1.0
 * @createDate 2013-6-24 下午02:22:32
 * @file Receiver.java
 * @package com.hoo.mq.spring.support
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 */

public class Receiver {


    @SuppressWarnings("unchecked")

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-active-mq.xml");


        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");

        while (true) {
//            Map<String, Object> map = (Map<String, Object>) jmsTemplate.receiveAndConvert();
            System.out.println("收到消息：" + JSON.toJSONString(jmsTemplate.receiveAndConvert()));

        }

    }

}