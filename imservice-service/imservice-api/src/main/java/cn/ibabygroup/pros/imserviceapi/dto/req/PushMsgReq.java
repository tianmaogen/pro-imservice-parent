package cn.ibabygroup.pros.imserviceapi.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/18.
 */
@Data
@AllArgsConstructor
public class PushMsgReq {
    private ChatMessageReq msgBody;
    private String[] toUserIds;
}
