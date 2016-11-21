package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.apps.uac.api.UserService;
import cn.ibabygroup.apps.uac.api.dto.UserInfo;
import cn.ibabygroup.pros.imservice.api.IMUserService;
import cn.ibabygroup.pros.imservice.api.PushMsgService;
import cn.ibabygroup.pros.imservice.config.RabbitMQConfig;
import cn.ibabygroup.pros.imservice.model.IM.IMMsgBatch;
import cn.ibabygroup.pros.imservice.model.IM.IMPushMsg;
import cn.ibabygroup.pros.imservice.model.IM.IMResponse;
import cn.ibabygroup.pros.imservice.model.IM.IMSetAttr;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.MQUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.PushMsgReq;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tianmaogen on 2016/10/19.
 */
public abstract class PushMsgServiceBase implements PushMsgService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TXService tXService;
    @Autowired
    private UserService userService;
    @Autowired
    private IMUserService imUserService;
    @Autowired
    private RESTService restService;

    public abstract String getDesc(ChatMessageReq chatMessageReq);
    public abstract String getExt(ChatMessageReq chatMessageReq);
    public abstract String getAttrName();

    @Override
    public Long pushMsg(ChatMessageReq chatMessageReq, String[] toUserIds) {
        //重新更新消息创建时间
        Long createdTime = System.currentTimeMillis();
        chatMessageReq.setCreateTime(createdTime);
        //3.2.0版本的客户端走push接口，其他新版本的客户端走批量群发消息接口
        List<String> newUserIds320 = new ArrayList<>();
        List<String> newUserIds = new ArrayList<>();
        List<String> oldUserIds = new ArrayList<>();
        //获取新老用户的版本信息
        for(String userId : toUserIds) {
            Integer version = imUserService.getClientVersion(userId);
            boolean newVersionFlag = imUserService.isNewVersion(version);
            if(newVersionFlag) {
                if(version.intValue() == 320)
                    newUserIds320.add(userId);
                else
                    newUserIds.add(userId);
            }
            else
                oldUserIds.add(userId);
        }

        //旧版本推送
        if(oldUserIds.size() > 0) {
            restService.pushMessage(new PushMsgReq(chatMessageReq, oldUserIds.toArray(new String[oldUserIds.size()])));
        }
        //新版本推送
        String desc = getDesc(chatMessageReq);
        String ext = getExt(chatMessageReq);
        //320版本之后的新版本推送
        if(newUserIds.size() > 0) {
            //批量发送消息的用户数量最多为200
            int iterationCount = 200;
            int iterationNum = newUserIds.size()/iterationCount + 1;
            for(int i=0;i<iterationNum;i++) {
                List<String> sendMsgList;
                if(i == iterationNum-1)
                    sendMsgList = newUserIds.subList(i*iterationCount, newUserIds.size());
                else
                    sendMsgList = newUserIds.subList(i*iterationCount, (i+1)*iterationCount);

                String[] newUserIdsArr = sendMsgList.toArray(new String[sendMsgList.size()]);
                IMMsgBatch iMMsgBatch = IMMsgBatch.getIMMsgBatch(newUserIdsArr, chatMessageReq, desc, ext);
                rabbitTemplate.send(RabbitMQConfig.queueBatchAName, MQUtil.getMessage(iMMsgBatch));
            }
        }
        //320版本推送
        if(newUserIds320.size() > 0) {
            //批量设置用户数量最多为300
            int iterationCount = 300;
            int iterationNum = newUserIds320.size()/iterationCount + 1;
            String attrName = getAttrName();
            //设置Id属性为uuidStr
            String uuidStr = UUID.randomUUID().toString();
            for(int i=0;i<iterationNum;i++) {
                List<String> importList;
                if(i == iterationNum-1)
                    importList = newUserIds320.subList(i*iterationCount, newUserIds320.size());
                else
                    importList = newUserIds320.subList(i*iterationCount, (i+1)*iterationCount);

                IMSetAttr imSetAttr = IMSetAttr.getIMSetAttr(importList, attrName, uuidStr);
                tXService.setPushAttr(imSetAttr);
            }

            IMPushMsg imPushMsg = IMPushMsg.getIMPushMsg(attrName, uuidStr, chatMessageReq, desc, ext);
            IMResponse response = tXService.sendCMsg(imPushMsg);
            if(response.getActionStatus().equals(response.actionStatusFAIL))
                throw new IMException("调用腾讯云通信接口出错，错误信息为:"+response.getErrorInfo());
        }
        return createdTime;
    }
}
