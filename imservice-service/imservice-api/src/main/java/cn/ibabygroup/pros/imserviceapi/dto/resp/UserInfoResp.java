package cn.ibabygroup.pros.imserviceapi.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/22.
 */
@Data
@ApiModel(value = "登录返回实体")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-15 22:52:43.562Z")
public class UserInfoResp {

    private UserInfo userInfo;

    @Data
    public class UserInfo {
        private Integer NotReadReplyCount;
        private Integer DueType;
        private String XmppServer;
        private Integer BabySex;
        private String BabyBirthday;
        private String MainDoctorId;
        private String DueDate;
        private String Area;
        private Boolean IsWarn;
        private String Integral;
        private Integer LoginDays;
        private Integer Week;
        private String Phone;
        private String Id;
        private String Pwd;
        private Integer PregnantStatus;
        private String MobileCode;
        private String City;
        private String CityCode;
        private String InviteCode;
        private String HospitalId;
        private String NickName;
        private String HeadPic;
        private Integer Age;
        private Integer YunStatus;
        private String HospitalName;
        private Integer CurrentIntegral;

        @ApiModelProperty(value = "用户签名")
        @JsonProperty("userSign")
        private String userSign;
    }
}
