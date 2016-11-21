package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.pros.imservice.api.PushMsgService;
import cn.ibabygroup.pros.imservice.config.RabbitMQConfig;
import cn.ibabygroup.pros.imservice.model.IM.IMPushMsg;
import cn.ibabygroup.pros.imservice.model.IM.IMResponse;
import cn.ibabygroup.pros.imservice.model.IM.IMSetAttr;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imservice.utils.MQUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.PushMsgReq;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by tianmaogen on 2016/9/23.
 */
@Service
public class PushNoticeMsgService extends PushMsgServiceBase {

    @Override
    public String getExt(ChatMessageReq chatMessageReq) {
        Map<String,Object> map = new HashMap();
        map.put("elemType", chatMessageReq.getElemType());
        map.put("chatId", chatMessageReq.getChatId());
        map.put("contentType", chatMessageReq.getContentType());
        map.put("contentJson", chatMessageReq.getContentJson());
        return JSONUtil.toCamelCaseJSONString(map);
    }

    @Override
    public String getAttrName() {
        return IMPushMsg.CourceNotice;
    }

    @Override
    public String getDesc(ChatMessageReq chatMessageReq) {
        JSONObject jSONObject = JSONUtil.parseObject(chatMessageReq.getContentJson());
        String descText = (String) jSONObject.get("value");
        return descText;
    }

    public static void main(String[] args) {
        PushNoticeMsgService sendNoticeGMsgService = new PushNoticeMsgService();
        ChatMessageReq chatMessageReq = new ChatMessageReq();
        String[] toIds = new String[847];
        List<String> list = new ArrayList<>();
        for(int i=0;i<847;i++) {
            String str = "test"+i;
            list.add(str);
        }
        sendNoticeGMsgService.pushMsg(chatMessageReq, list.toArray(toIds));
    }
}
