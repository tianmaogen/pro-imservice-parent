package cn.ibabygroup.pros.imservice.model.net;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by tianmaogen on 2016/8/22.
 * .net URL常量
 */
@Component
public class NetURL {
    @Value("${ibabygroup.net.host}")
    private String host;
    private String checkChatUrl = "api/system/Chat/CheckChat";
    private String syncMsgUrl = "api/system/Chat/SyncIMChatLog";

    public String getCheckChatUrl() {
        return host + checkChatUrl;
    }

    public String getSyncMsgUrl() {
        return host + syncMsgUrl;
    }
}
