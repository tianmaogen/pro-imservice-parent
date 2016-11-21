package cn.ibabygroup.pros.imserviceapi.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录实体
 */
@Data
@ApiModel(value = "登录实体")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-15 22:52:43.562Z")
public class UserLoginReq implements Serializable {

    @ApiModelProperty(value = "类型【1密码登录、6快捷登录】", required = true)
    @JsonProperty("Logintype")
    private Integer Logintype;

    @ApiModelProperty(value = "手机号", required = true)
    @JsonProperty("Phone")
    private String Phone;

    @ApiModelProperty(value = "密码【不加密，快捷登录时传空串】", required = true)
    @JsonProperty("pwd")
    private String pwd;

    @ApiModelProperty(value = "短信验证码【密码登录时传空串】", required = true)
    @JsonProperty("smsCode")
    private String smsCode;

    @ApiModelProperty(value = "第三方ID【目前并未使用，传空串】", required = true)
    @JsonProperty("OpenId")
    private String OpenId;

    @ApiModelProperty(value = "客户端类型【1Android、2IOS】", required = true)
    @JsonProperty("phoneType")
    private Integer phoneType;

    @ApiModelProperty(value = "城市Code", required = true)
    @JsonProperty("cityCode")
    private String cityCode;

    @ApiModelProperty(value = "用户状态【1备孕、2怀孕、3育儿】", required = true)
    @JsonProperty("dueType")
    private Integer dueType;

    @JsonProperty("mobilecode")
    private String mobilecode;

    @ApiModelProperty(value = "昵称", required = true)
    @JsonProperty("nick")
    private String nick;

    @ApiModelProperty(value = "预产期【yyyy-MM-dd】【dueType为2时必传】", required = false)
    @JsonProperty("dueDate")
    private String dueDate;

    @ApiModelProperty(value = "宝宝生日【yyyy-MM-dd】【dueType为3时必传】", required = false)
    @JsonProperty("babyBirthday")
    private String babyBirthday;

    @ApiModelProperty(value = "宝宝性别【1女、2男，dueType为3时必传】", required = false)
    @JsonProperty("babySex")
    private Integer babySex;

    @ApiModelProperty(value = "设备信息(机型,分辨率,操作系统,网络,运营商)【逗号进行分割】", required = false)
    @JsonProperty("phoneInfo")
    private String phoneInfo;
}
