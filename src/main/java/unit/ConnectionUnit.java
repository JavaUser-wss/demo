package unit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUnit {
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义工厂连接
        ConnectionFactory factory= new ConnectionFactory();
        //设置连接属性
        factory.setHost("localhost");
        // 设置服务端口
        factory.setPort(5672);
        // 设置账号信息：用户名，密码，vhost
        factory.setVirtualHost("testhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection  connection=factory.newConnection();
        return connection;
    }
}
