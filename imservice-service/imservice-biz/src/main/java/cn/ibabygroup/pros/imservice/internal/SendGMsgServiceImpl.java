package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.pros.imservice.api.SendMsgService;
import cn.ibabygroup.pros.imservice.config.RabbitMQConfig;
import cn.ibabygroup.pros.imservice.utils.MQUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imservice.model.IM.GroupIMMsg;
import cn.ibabygroup.pros.imservice.model.IM.IMResponse;
import cn.ibabygroup.pros.imservice.model.rest.RESTResponse;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tianmaogen on 2016/9/1.
 * 发送群消息service
 */
@Service("sendGMsgFAppService")
public class SendGMsgServiceImpl implements SendMsgService {
    @Autowired
    private TXService tXService;
    @Autowired
    private RESTService restService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Long sendMsg(ChatMessageReq chatMessageReq, String groupId) {
        Long now = System.currentTimeMillis();
        chatMessageReq.setCreateTime(now);
        //老版本
        //3.2版本定义的新的消息类型不走rest接口
        boolean needSendRest = needSendRest(chatMessageReq.getElemType());
        if(needSendRest) {
            RESTResponse restResponse = restService.sendMessage(chatMessageReq, groupId);
            if(restResponse.getCode().intValue() != restResponse.okCode)
                throw new IMException("调用REST接口发消息报错!", restResponse);
        }
        String desc = getDesc(chatMessageReq);
        //新版本
        GroupIMMsg groupIMMsg = GroupIMMsg.getGroupIMMsg(groupId, chatMessageReq, desc, chatMessageReq.getSenderId());
        rabbitTemplate.send(RabbitMQConfig.queueBName, MQUtil.getMessage(groupIMMsg));
//        IMResponse response = tXService.sendBMsg(groupIMMsg);
//        if(response.getActionStatus().equals(response.actionStatusFAIL))
//            throw new IMException("调用腾讯云通信接口出错，错误信息为:"+response.getErrorInfo());
        //同步消息记录
        if(chatMessageReq.getElemType().intValue() == 1001)
            restService.syncMessage(chatMessageReq);
        return now;
    }

    //1013~1016
    private boolean needSendRest(Integer elemType) {
        if(elemType.intValue() > 1012 &&  elemType.intValue()<1017)
            return false;
        return true;
    }

    private String getDesc(ChatMessageReq chatMessageReq) {
        //讲坛开课提醒
        if(chatMessageReq.getElemType().intValue() == 1011) {
            JSONObject jSONObject = JSONUtil.parseObject(chatMessageReq.getContentJson());
            String value = (String) jSONObject.get("value");
            return value;
        }
        return "";
    }
}
