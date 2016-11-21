package cn.ibabygroup.pros.imservice.model.IM;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by tianmaogen on 2016/8/17.
 */
@Data
public class IMResponse implements Serializable {
    public static final String actionStatusOK = "OK";
    public static final String actionStatusFAIL = "FAIL";
    
    //请求处理的结果，OK表示处理成功，FAIL表示失败。
    private String actionStatus;
    //错误码。
    private Integer errorCode;
    //错误信息。
    private String errorInfo;
}
