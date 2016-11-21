package cn.ibabygroup.pros.imservice.web.req;

import cn.ibabygroup.pros.imserviceapi.dto.req.Aps;
import cn.ibabygroup.pros.imserviceapi.dto.req.Xg;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/18.
 */
@Data
public class SendRemotePushMsg {
    private Integer messagetype;
    private String messageId;
    private Integer chatType;
    private Aps aps;
    private Xg xg;
}
