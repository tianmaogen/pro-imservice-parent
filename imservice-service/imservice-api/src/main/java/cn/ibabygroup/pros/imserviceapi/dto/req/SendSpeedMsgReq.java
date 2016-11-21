package cn.ibabygroup.pros.imserviceapi.dto.req;

import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/18.
 */
@Data
public class SendSpeedMsgReq {
    private ChatMessageReq msgBody;
    private String[] toUserIds;
}
