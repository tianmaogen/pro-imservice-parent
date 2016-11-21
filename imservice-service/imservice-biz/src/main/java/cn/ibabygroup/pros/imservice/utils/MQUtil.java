package cn.ibabygroup.pros.imservice.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;

/**
 * Created by tianmaogen on 2016/9/22.
 */
public class MQUtil {
//    private static MessageProperties props = MessagePropertiesBuilder.newInstance()
//            .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
//            .setMessageId("123")
//            .setHeader("bar", "baz")
//            .build();
    public static Message getMessage(Object o) {
        String jsonStr = JSONUtil.toCamelCaseJSONString(o);
        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message(jsonStr.getBytes(), messageProperties);
        return message;
    }
    public static<T>  T parseMessage(Message message, Class<T> type) {
        String data = new String(message.getBody());
        T t = JSON.parseObject(data ,type);
        return t;
    }
}
