package cn.ibabygroup.pros.imservice.mq;

import cn.ibabygroup.pros.imservice.config.RabbitMQConfig;
import cn.ibabygroup.pros.imservice.model.IM.*;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.MQUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tianmaogen on 2016/9/20.
 */
@Component
public class ConsumerService {

    @Autowired
    private TXService txService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.queueAName, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.exchangeName, ignoreDeclarationExceptions = "true"),
            key = RabbitMQConfig.queueAName)
    )
    public void processQueueA(Message message) {
        IMMsg imMsg = MQUtil.parseMessage(message, IMMsg.class);
        txService.sendAMsg(imMsg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.queueBatchAName, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.exchangeName, ignoreDeclarationExceptions = "true"),
            key = RabbitMQConfig.queueBatchAName)
    )
    public void processQueueBatchA(Message message) {
        IMMsgBatch iMMsgBatch = MQUtil.parseMessage(message, IMMsgBatch.class);
        txService.batchSendAMsg(iMMsgBatch);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.queueBName, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.exchangeName, ignoreDeclarationExceptions = "true"),
            key = RabbitMQConfig.queueBName)
    )
    public void processQueueB(Message message) {
        GroupIMMsg groupIMMsg = MQUtil.parseMessage(message, GroupIMMsg.class);
        txService.sendBMsg(groupIMMsg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.queueCName, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.exchangeName, ignoreDeclarationExceptions = "true"),
            key = RabbitMQConfig.queueCName)
    )
    public void processQueueC(Message message) {
        IMPushMsg imPushMsg = MQUtil.parseMessage(message, IMPushMsg.class);
        txService.sendCMsg(imPushMsg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.queueDName, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.exchangeName, ignoreDeclarationExceptions = "true"),
            key = RabbitMQConfig.queueDName)
    )
    public void processQueueD(Message message) {
        IMMsg iMMsg = MQUtil.parseMessage(message, IMMsg.class);
        txService.sendDMsg(iMMsg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.queueEName, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.exchangeName, ignoreDeclarationExceptions = "true"),
            key = RabbitMQConfig.queueEName)
    )
    public void processQueueE(Message message) {
        QueueEModel queueEModel = MQUtil.parseMessage(message, QueueEModel.class);
        txService.batchImportAccounts(queueEModel.getAccountList());
        txService.addGroupMember(queueEModel.getAddGroupMemberReq());

//        MessageProperties messageProperties = message.getMessageProperties();
//        messageProperties.setHeader();
    }
}
