package cn.ibabygroup.pros.imservice.api;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by tianmaogen on 2016/9/25.
 * 根据elemType找到对应的pushMsgService
 */
public interface FindPushMsgService {
    PushMsgService find(ChatMessageReq chatMessageReq);
}
