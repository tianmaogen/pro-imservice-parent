package cn.ibabygroup.pros.imservice.web.req;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.CmdMsgReq;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/18.
 * 透传消息请求实体
 */
@Data
public class SendCmdMsgReq {
    private CmdMsgReq cmdMsgReq;
    private String[] toUserId;
//    /**
//     * 1.1对1聊天消息
//     * 2.讲坛聊天消息
//     * 3.1对1透传消息
//     * 4.讲坛透传消息
//     */
//    private Integer messageType;

}
