package simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import unit.ConnectionUnit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    //定义队列的名称
    private static final String EXCHANGE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection conn = ConnectionUnit.getConnection();
        //在连接中创建管道
        Channel channel = conn.createChannel();
        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
        //声明一个队列
        //channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i=0;i<10;i++) {
            //定义一个信息
            String message = "hello world"+i;
            //把信息放入队列中
            channel.basicPublish( EXCHANGE_NAME, "detele",null, message.getBytes());
            // 输出到控制台,仅作测试
            System.out.println("生产者：发送消息 --> " + message);
        }
        channel.close();
        conn.close();
    }
}
