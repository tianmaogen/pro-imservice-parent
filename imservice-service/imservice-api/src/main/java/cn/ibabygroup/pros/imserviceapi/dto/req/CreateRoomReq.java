package cn.ibabygroup.pros.imserviceapi.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 讲坛创建请求实体
 * 2016/8/29.
 */
@Data
public class CreateRoomReq {

    @ApiModelProperty(value = "群名称，最长30字节", required = true)
    @JsonProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "群简介，最长240字节。", required = true)
    @JsonProperty(value = "introduction")
    private String introduction;

    @ApiModelProperty(value = "最大群成员数量，最大为10000，不填默认为2000个。", required = true)
    @JsonProperty(value = "maxMemberCount")
    private Integer maxMemberCount;

    @ApiModelProperty(value = "自定义群组ID。", required = true)
    @JsonProperty(value = "groupId")
    private String groupId;

    @ApiModelProperty(value = "医生Id", required = true)
    @JsonProperty(value = "doctorId")
    private String doctorId;

    @ApiModelProperty(value = "小助手Id", required = true)
    @JsonProperty(value = "assistantId")
    private String assistantId;
}

