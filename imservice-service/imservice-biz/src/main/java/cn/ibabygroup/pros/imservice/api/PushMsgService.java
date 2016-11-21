package cn.ibabygroup.pros.imservice.api;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;

/**
 * Created by tianmaogen on 2016/9/1.
 * 群发送消息service
 */
public interface PushMsgService {
    /**
     * 推送消息接口
     * @param chatMessageReq 消息体
     * @param toIds 可能是用户id数组
     * @return
     */
    Long pushMsg(ChatMessageReq chatMessageReq, String[] toIds);
}
