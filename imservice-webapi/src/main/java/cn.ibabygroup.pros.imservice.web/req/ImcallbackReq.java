package cn.ibabygroup.pros.imservice.web.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 腾讯IM云回调请求实体
 * 2016/8/24.
 */
@Data
public class ImcallbackReq {
    //回调命令。目前固定为State.StateChange
    @JsonProperty(value = "CallbackCommand")
    private String CallbackCommand;
    @JsonProperty(value = "Info")
    private Info Info;
    @Data
    public class Info {
        //App上线或者下线的动作， Login：上线（TCP建立），Logout下线（TCP断开）。
        @JsonProperty(value = "Action")
        private String Action;
        //用户的ID。
        @JsonProperty(value = "To_Account")
        private String To_Account;
        /**
         * App上下线触发的原因。上线的原因有
         * Register:App TCP连接建立。
         * 下线的原因有
         * Unregister:App用户注销账号导致TCP断开;
         * LinkClose:云通信检测到App TCP连接断开;
         * Timeout：云通信检测到App心跳包超时，认为TCP已断开（客户端杀后台或Crash）。
         */
        @JsonProperty(value = "Reason")
        private String Reason;
    }
}

