package cn.ibabygroup.pros.imserviceapi.dto.req;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by tianmaogen on 2016/8/18.
 */
@Data
public class SendMsgReq {
    @ApiModelProperty(value = "消息体")
    @JsonProperty("msgBody")
    private ChatMessageReq msgBody;
    @ApiModelProperty(value = "接受者id")
    @JsonProperty("toUserId")
    private String toUserId;
}
