package io.subHub.common.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.subHub.common.configBean.ConfigBean;

public class RabbitMQUtils {

    // Define methods for providing connection objects
    public static Connection getConnection(ConfigBean configBean) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(configBean.getMq_host());
            factory.setPort(configBean.getMq_port());
            factory.setUsername(configBean.getMq_username());
            factory.setPassword(configBean.getMq_password());
            factory.setAutomaticRecoveryEnabled(true);
            factory.setNetworkRecoveryInterval(5000);
            factory.setVirtualHost("/");
            factory.setConnectionTimeout(30 * 1000);
            factory.setHandshakeTimeout(30 * 1000);
            factory.setShutdownTimeout(0);
            return factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Define methods for closing channels and connections
    public static void closeAll(Channel chan, Connection conn) {
        try{
            if(chan != null)  chan.close();
            if(conn != null)  conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
