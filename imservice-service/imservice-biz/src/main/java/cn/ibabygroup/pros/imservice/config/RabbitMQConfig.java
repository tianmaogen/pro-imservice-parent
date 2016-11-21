package cn.ibabygroup.pros.imservice.config;

import cn.ibabygroup.pros.imservice.service.TXService;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tianmaogen on 2016/9/20.
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String queueAName="cn.ibabygroup.pros.imservice.aMsg";
    public static final String queueBatchAName="cn.ibabygroup.pros.imservice.batchAMsg";
    public static final String queueBName="cn.ibabygroup.pros.imservice.bMsg";
    public static final String queueCName="cn.ibabygroup.pros.imservice.cMsg";
    public static final String queueDName="cn.ibabygroup.pros.imservice.dMsg";
    public static final String queueEName="cn.ibabygroup.pros.imservice.addMember";
    public static final String exchangeName="cn.ibabygroup.pros.imservice";
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String userName;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.password}")
    private String password;
//    @Bean
//    Queue queue(){
//        return new Queue(queueAName,true);
//    }
//    @Bean
//    TopicExchange exchange(){
//        return new TopicExchange(exchangeName);
//    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(4);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setChannelCacheSize(50);
//        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        //cpu核心数
//        int coreNum = Runtime.getRuntime().availableProcessors();
//        connectionFactory.setExecutor(Executors.newFixedThreadPool(coreNum * 2));
        connectionFactory.setPublisherConfirms(true); // enable confirm mode
//        connectionFactory.setPublisherReturns(true);
//        connectionFactory.setVirtualHost("/im");
        connectionFactory.getRabbitConnectionFactory().getClientProperties().put("name", "im-connection");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate r = new RabbitTemplate(connectionFactory());
        r.setExchange(exchangeName);
        r.setRoutingKey(queueAName);
//        r.setUserIdExpressionString("123");
//        r.setMandatory(true);
//        r.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Autowired
//            private TXService txService;
//
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//
//            }
//        });
        return r;
    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, ConsumerApiService service){
//        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueAName, queueBName, queueCName, queueDName);
//        container.setExposeListenerChannel(true);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                log.error("onMessage=>"+new String(message.getBody()));
//                Msg msg= JSON.parseObject(new String(message.getBody()),Msg.class);
//                service.receiveMessage(msg);
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//            }
//        });
//        container.setPrefetchCount(10);
//        container.setConcurrentConsumers(100);
//        return container;
//    }
}
