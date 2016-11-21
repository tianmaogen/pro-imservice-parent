package cn.ibabygroup.pros.imservice.model.IM;

import cn.ibabygroup.pros.imservice.utils.SignUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by tianmaogen on 2016/8/17.
 */
@Component
@Data
public class IMURL {
    @Value("${spring.imURL.usersig}")
    private String usersig;
    @Value("${spring.imURL.identifier}")
    private String identifier;
    @Value("${spring.imURL.sdkappid}")
    private Long sdkappid;
    @Value("${spring.imURL.privStr}")
    private String privStr;
    @Value("${spring.imURL.pubStr}")
    private String pubStr;

    private String apn = "1";
    private String contenttype = "json";

    private String sendmsgURL = "https://console.tim.qq.com/v4/openim/sendmsg";
    private String batchsendmsgURL = "https://console.tim.qq.com/v4/openim/batchsendmsg";
    private String pushURL = "https://console.tim.qq.com/v4/openim/im_push";

    private String createRoomURL = "https://console.tim.qq.com/v4/group_open_http_svc/create_group";
    private String delRoomURL = "https://console.tim.qq.com/v4/group_open_http_svc/destroy_group";
    private String addGroupMemberURL="https://console.tim.qq.com/v4/group_open_http_svc/add_group_member";

    private String delGroupMemberURL="https://console.tim.qq.com/v4/group_open_http_svc/delete_group_member";
    private String sendGroupMsgURL="https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg";
    private String sendGroupNotificationURL="https://console.tim.qq.com/v4/group_open_http_svc/send_group_system_notification";
    //设置推送属性
    private String setPushAttrURL="https://console.tim.qq.com/v4/openim/im_set_attr";
    //推送
    private String imPushURL = "https://console.tim.qq.com/v4/openim/im_push";
    //批量导入账号
    private String multiImportAccountURL = "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import";
    //获取用户在线状态
    private String queryStateURL = "https://console.tim.qq.com/v4/openim/querystate";

    public String getQueryStateURL() {
        return queryStateURL + getCommonStr();
    }

    public String getSendGroupNotificationURL() {
        return sendGroupNotificationURL + getCommonStr();
    }

    public String getMultiImportAccountURL() {
        return multiImportAccountURL + getCommonStr();
    }

    public String getImPushURL() {
        return imPushURL + getCommonStr();
    }

    public String getSetPushAttrURL() {
        return setPushAttrURL + getCommonStr();
    }

    public String getSendGroupMsgURL() {
        return sendGroupMsgURL + getCommonStr();
    }

    public String getDelGroupMemberURL() {
        return delGroupMemberURL + getCommonStr();
    }

    public String getAddGroupMemberURL() {
        return addGroupMemberURL + getCommonStr();
    }

    public String getCreateRoomURL() {
        return createRoomURL + getCommonStr();
    }
    public String getDelRoomURL() {
        return delRoomURL + getCommonStr();
    }

    public String getSendmsgURL() {
        return sendmsgURL + getCommonStr();
    }

    public String getBatchSendmsgURL() {
        return batchsendmsgURL + getCommonStr();
    }

    public String getPushURL() {
        return pushURL + getCommonStr();
    }

    private String getCommonStr() {
        return  "?usersig="+usersig +
                "&identifier="+identifier +
                "&sdkappid="+sdkappid +
                "&apn="+apn+
                "&contenttype="+contenttype;
    }

    public synchronized void reSetSign() {
        String sign = SignUtil.getSign(this.getSdkappid(), this.getIdentifier(), this.getPrivStr(), this.getPubStr());
        this.getIdentifier();
    }
}
