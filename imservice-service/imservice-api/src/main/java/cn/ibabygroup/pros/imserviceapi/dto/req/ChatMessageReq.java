package cn.ibabygroup.pros.imserviceapi.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 消息体
 */
@Data
@ApiModel(value = "消息体")
//@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-15 22:52:43.562Z")
public class ChatMessageReq {

    @ApiModelProperty(value = "标识元素类型", required = true)
    @JsonProperty("elemType")
    private Integer elemType;
    @ApiModelProperty(value = "会话id", required = true)
    @JsonProperty("chatId")
    private String chatId;
    @ApiModelProperty(value = "消息id", required = true)
    @JsonProperty("msgId")
    private String msgId;
    @ApiModelProperty(value = "消息内容的类型(图片消息，文字消息等)", required = true)
    @JsonProperty("contentType")
    private Integer contentType;
    @ApiModelProperty(value = "消息内容的Json", required = true)
    @JsonProperty("contentJson")
    private String contentJson;
    @ApiModelProperty(value = "消息的内容", required = true)
    @JsonProperty("createTime")
    private Long createTime;
    @ApiModelProperty(value = "发送者类型", required = true)
    @JsonProperty("senderType")
    private Integer senderType;
    @ApiModelProperty(value = "发送者id", required = true)
    @JsonProperty("senderId")
    private String senderId;
    @ApiModelProperty(value = "发送者名称", required = true)
    @JsonProperty("senderName")
    private String senderName="";
    @ApiModelProperty(value = "发送者头像", required = true)
    @JsonProperty("senderHead")
    private String senderHead;

}
