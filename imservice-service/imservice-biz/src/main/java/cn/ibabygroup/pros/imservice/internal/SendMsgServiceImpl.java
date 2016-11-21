package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.apps.uac.api.UserService;
import cn.ibabygroup.apps.uac.api.dto.UserInfo;
import cn.ibabygroup.commons.context.RequestContextHolder;
import cn.ibabygroup.pros.imservice.api.IMServiceService;
import cn.ibabygroup.pros.imservice.api.IMUserService;
import cn.ibabygroup.pros.imservice.api.SendMsgService;
import cn.ibabygroup.pros.imservice.config.RabbitMQConfig;
import cn.ibabygroup.pros.imservice.dao.MessageDao;
import cn.ibabygroup.pros.imservice.model.Constants;
import cn.ibabygroup.pros.imservice.model.enums.ContentTypeEnum;
import cn.ibabygroup.pros.imservice.utils.MQUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imservice.model.IM.AndroidExt;
import cn.ibabygroup.pros.imservice.model.IM.IMMsg;
import cn.ibabygroup.pros.imservice.model.IM.IMResponse;
import cn.ibabygroup.pros.imservice.model.mongodb.IMMessage;
import cn.ibabygroup.pros.imservice.model.net.NetCheckChat;
import cn.ibabygroup.pros.imservice.model.net.NetResponse;
import cn.ibabygroup.pros.imservice.service.NetService;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tianmaogen on 2016/9/1.
 * 发送普通消息消息service,
 * 包括
  图文问诊-----新的问诊回复                2002
  图文问诊-----问诊的结束符消息            2003
  建卡问诊聊天                             2004
  普通1对1聊天                             2005
 */
@Service("sendMsgFAppService")
public class SendMsgServiceImpl implements SendMsgService {
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
        IMMessage iMMessage = new IMMessage();
        BeanUtils.copyProperties(chatMessageReq, iMMessage);
        iMMessage.setToUserId(toUserId);
        //新版本
        if(imUserService.isNewVersion(toUserId))
            senMsgNewVersion(chatMessageReq, toUserId);
        //老版本
        else
            restService.sendMessage(chatMessageReq,toUserId);
        //不保存透传类的消息
        if(iMMessage.getContentType().intValue() != ContentTypeEnum.Event.getCode()) {
            //调用net系统同步消息
            netService.syncMsg(iMMessage);
            //mongodb保存消息
            messageDao.save(iMMessage);
        }

        return now;
    }

    private void senMsgNewVersion(ChatMessageReq chatMessageReq, String toUserId) {
        String desc = imServiceService.getDesc(chatMessageReq);
        String ext = imServiceService.getExt(chatMessageReq.getElemType(), chatMessageReq.getChatId());
        IMMsg iMMsg = IMMsg.getIMMsg(toUserId, chatMessageReq, desc, ext);
        rabbitTemplate.send(RabbitMQConfig.queueAName, MQUtil.getMessage(iMMsg));
//        IMResponse response = tXService.sendAMsg(iMMsg);
//        if(response.getActionStatus().equals(response.actionStatusFAIL))
//            throw new IMException("调用腾讯云通信接口出错，错误信息为:"+response.getErrorInfo());
    }
}
