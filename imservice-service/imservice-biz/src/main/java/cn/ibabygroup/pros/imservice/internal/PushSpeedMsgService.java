package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.pros.imservice.model.IM.IMPushMsg;
import cn.ibabygroup.pros.imservice.model.enums.ContentTypeEnum;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianmaogen on 2016/9/23.
 */
@Service
public class PushSpeedMsgService extends PushMsgServiceBase {

    @Override
    public String getDesc(ChatMessageReq chatMessageReq) {
        StringBuilder desc = new StringBuilder(chatMessageReq.getSenderName()).append("：");
        Integer contentType = chatMessageReq.getContentType();
        if (contentType.intValue() == ContentTypeEnum.Event.getCode()
                && chatMessageReq.getElemType().intValue() == 2002) {
            JSONObject jSONObject = JSONUtil.parseObject(chatMessageReq.getContentJson());
            String text = (String) jSONObject.get("value");
            if(text.length() > 100)
                text = text.substring(0,99);
            desc.append(text);
        }
        return desc.toString();
    }

    @Override
    public String getExt(ChatMessageReq chatMessageReq) {
        Map<String,Object> map = new HashMap();
        map.put("elemType", chatMessageReq.getElemType());
        map.put("chatId", chatMessageReq.getChatId());
        return JSONUtil.toCamelCaseJSONString(map);
    }

    @Override
    public String getAttrName() {
        return IMPushMsg.SpeedAsk;
    }

}
