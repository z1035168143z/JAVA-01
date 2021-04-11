package io.zrzhao.mq.msgqueue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *
 *
 * @author zrzhao
 * @date 2021/4/11
 */
public class ActiveMessageQueue implements MessageQueue {

    private static final String MQ_HOST = "localhost";
    private static final String MQ_PORT = "61616";

    private Connection connection;

    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL("tcp://" + MQ_HOST + ":" + MQ_PORT);

            this.connection = connectionFactory.createConnection();
            connection.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean sendMsg(String msg) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("TEST.QUEUE");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage message = session.createTextMessage(msg);

            producer.send(message);

            producer.close();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void notify(MessageReceiveHandler handler) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("TEST.QUEUE");
            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive(1000);
            handler.handler(message);

            consumer.close();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
