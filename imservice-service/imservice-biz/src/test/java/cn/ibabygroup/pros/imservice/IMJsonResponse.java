package cn.ibabygroup.pros.imservice;

/**
 * Created by tianmaogen on 2016/8/24.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class IMJsonResponse implements Serializable {

    private static final long serialVersionUID = -8601352503902193959L;
    @JsonProperty(value = "Code")
    private int code;
    @JsonProperty(value = "Success")
    private boolean success=true;
    @JsonProperty(value = "Msg")
    private String msg;
    @JsonProperty(value = "Data")
    private Object data;

    public IMJsonResponse(){
    }
    public IMJsonResponse(Object data) {
        this.data = data;
    }

    public IMJsonResponse(int code, String msg) {
        this.data = code;
        this.msg = msg;
    }

    public static IMJsonResponse ok(Object data) {
//        String str = JSONUtil.toJSONString(new IMJsonResponse(data));
//        return str;
        return new IMJsonResponse(data);
    }

    public static IMJsonResponse error(int Code, String Msg) {
        return new IMJsonResponse(Code, Msg);
    }


    public String toString() {
        return "IMJsonResponse{Code=" + this.code + ", Msg=\'" + this.msg + '\'' + ", Data=" + this.data + '}';
    }

//    static {
//        SUCCESS_CODE = HttpStatus.OK.value();
//        ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
//        BADREQUEST_CODE = HttpStatus.BAD_REQUEST.value();
//    }
}
