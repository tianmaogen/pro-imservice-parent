package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.apps.uac.api.UserService;
import cn.ibabygroup.apps.uac.api.dto.UserInfo;
import cn.ibabygroup.pros.imservice.api.IMServiceService;
import cn.ibabygroup.pros.imservice.api.IMUserService;
import cn.ibabygroup.pros.imservice.api.SendMsgService;
import cn.ibabygroup.pros.imservice.config.RabbitMQConfig;
import cn.ibabygroup.pros.imservice.dao.MessageDao;
import cn.ibabygroup.pros.imservice.model.Constants;
import cn.ibabygroup.pros.imservice.utils.MQUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imservice.model.IM.IMMsg;
import cn.ibabygroup.pros.imservice.model.IM.IMResponse;
import cn.ibabygroup.pros.imservice.model.enums.SenderTypeEnum;
import cn.ibabygroup.pros.imservice.model.mongodb.IMMessage;
import cn.ibabygroup.pros.imservice.service.NetService;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianmaogen on 2016/9/1.
 * 发送其他消息消息service,
 * 包括
  评论消息，系统消息等
 */
@Service("sendOtherMsgServiceImpl")
public class SendOtherMsgServiceImpl implements SendMsgService {
    @Autowired
    private TXService tXService;
    @Autowired
    private IMServiceService imServiceService;
    @Autowired
    private NetService netService;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RESTService restService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IMUserService imUserService;

    @Override
    public Long sendMsg(ChatMessageReq chatMessageReq, String toUserId) {
        Long now = System.currentTimeMillis();
        chatMessageReq.setCreateTime(now);
        //新版本
        if(imUserService.isNewVersion(toUserId))
            senMsgNewVersion(chatMessageReq, toUserId , now);
        //老版本
        else
            restService.sendMessage(chatMessageReq,toUserId);
        return now;
    }

    private void senMsgNewVersion(ChatMessageReq chatMessageReq, String toUserId, Long now) {
        String desc = getDesc(chatMessageReq);
        String ext = getExt(chatMessageReq, now);
        IMMsg iMMsg = IMMsg.getIMMsg(toUserId, chatMessageReq, desc, ext);
        rabbitTemplate.send(RabbitMQConfig.queueDName, MQUtil.getMessage(iMMsg));
//        IMResponse response = tXService.sendDMsg(iMMsg);
//        if(response.getActionStatus().equals(IMResponse.actionStatusFAIL))
//            throw new IMException("调用腾讯云发送失败!");
    }

    private String getExt(ChatMessageReq chatMessageReq, Long now) {
        Map<String,Object> map = new HashMap();
        map.put("elemType", chatMessageReq.getElemType());
        if(chatMessageReq.getElemType().intValue() == 3102)
            return JSONUtil.toCamelCaseJSONString(map);

        map.put("msgId", chatMessageReq.getMsgId());
        map.put("createTime", now);
        map.put("senderId", chatMessageReq.getSenderId());
        map.put("contentJson", chatMessageReq.getContentJson());
        //评论类的
        if(chatMessageReq.getElemType().intValue() > 3100
                && chatMessageReq.getElemType().intValue() < 3200) {
            map.put("senderName", chatMessageReq.getSenderName());
            map.put("senderHead", chatMessageReq.getSenderHead());
            map.put("senderType", chatMessageReq.getSenderType());
        }
        return JSONUtil.toCamelCaseJSONString(map);
    }

    private String getDesc(ChatMessageReq chatMessageReq) {
        int elemType = chatMessageReq.getElemType().intValue();
        //关注提醒, 主管医生, 建卡医生
        if(elemType == 3002 || elemType == 3003 || elemType == 3005)
            return "";
        //加好友
        else if(elemType == 3004)
            return chatMessageReq.getSenderName() + "请求加您为好友 【添加】【拒绝】";
        //评论类的
        else if(elemType > 3100 && elemType < 3200) {
            String val = getContentJsonVal(chatMessageReq);
            if(val.length() > 100)
                return  val.substring(0,99);
            return val;
        }
        return "";
    }

    private String getContentJsonVal(ChatMessageReq chatMessageReq) {
        JSONObject jsonObject = JSONUtil.parseObject(chatMessageReq.getContentJson());
        return jsonObject.get("value").toString();
    }
}
