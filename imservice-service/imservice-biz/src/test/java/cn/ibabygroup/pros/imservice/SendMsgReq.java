package cn.ibabygroup.pros.imservice;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/18.
 */
@Data
public class SendMsgReq {
    private ChatMessageReq msgBody;
    private String toUserId;
}
