package simple;

import com.rabbitmq.client.*;
import unit.ConnectionUnit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {
    private static final String QUEUE_NAME = "test_work_queue2";
    private static final String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUnit.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换机
        //channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "detele");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "select");
        // 同一时刻，服务器只会发一条消息给一个消费者
        channel.basicQos(1);
        //监听队列
        channel.basicConsume(QUEUE_NAME, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 获取并转换成String
                String msg = new String(body, "UTF-8");
                System.out.println("消费者，收到消息，msg --> " + msg);
                // 休眠50ms
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 设置为使用手动模式进行应答
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
