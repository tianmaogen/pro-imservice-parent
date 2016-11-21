package cn.ibabygroup.pros.imservice.web.resp;

import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/24.
 * 腾讯云回调响应对象
 */
@Data
public class IMCallbackResp {
    //请求处理的结果，OK表示处理成功，FAIL表示失败。
    private String ActionStatus;
    //错误码，0表示APP后台处理成功，1表示APP后台处理失败。
    private Integer ErrorCode;
    //错误信息。
    private String ErrorInfo;

    public static IMCallbackResp ok() {
        IMCallbackResp iMCallbackResp = new IMCallbackResp();
        iMCallbackResp.setActionStatus("OK");
        iMCallbackResp.setErrorCode(0);
        return iMCallbackResp;
    }

    public static IMCallbackResp error(String errorInfo) {
        IMCallbackResp iMCallbackResp = new IMCallbackResp();
        iMCallbackResp.setActionStatus("FAIL");
        iMCallbackResp.setErrorCode(1);
        iMCallbackResp.setErrorInfo(errorInfo);
        return iMCallbackResp;
    }
}
