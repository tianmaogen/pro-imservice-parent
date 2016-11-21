package cn.ibabygroup.pros.imservice.model.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by tianmaogen on 2016/8/22.
 * .net URL常量
 */
@Component
@Slf4j
public class RESTURL {
    @Value("${ibabygroup.rest.host}")
    private String host;
    @Value("${ibabygroup.rest.token}")
    private String token;
    private String sendMessageUrl = "REST/imservice/im/{token}/sendMessage/{to}";
    private String pushMessageUrl = "REST/imservice/im/{token}/pushMessage";
    private String checkChatUrl = "pulpitServer/pulpitSystem/getPulpitUserStatus";
    //搜索讲坛列表(后台管理员使用)
    private String searchPulpitbUrl = "pulpitServer/pulpitMember/searchPulpitbg";
    //获取讲坛的成员列表
    private String getMemberListUrl = "pulpitServer/pulpitMember/getMemberList";
    //同步群的聊天记录
    private String syncPulpitImMsgUrl = "pulpitServer/pulpitSystem/syncPulpitImMessage";
    //群上限
    public static final int maxPulpitNum = 9990;

    public String getSyncPulpitImMsgUrl() {
        return host + syncPulpitImMsgUrl;
    }
    public String getMemberListUrl(String groupId, Integer count) {
        return host + getMemberListUrl + "?pulpitId="+groupId+"&page=1&count="+count+"&&&&&&&&&";
    }

    public String getPushMessageUrl() {
        return host + pushMessageUrl.replace("{token}", token);
    }

    public String getSendMessageUrl(String toUserId) {
        return host + sendMessageUrl.replace("{token}", token).replace("{to}",toUserId);
    }

    public String getSearchPulpitbUrl() {
        return host + searchPulpitbUrl + "?page=1&count="+maxPulpitNum+"&&&&&&&&&";
    }

    public String getCheckChatUrl() {
        return host + checkChatUrl;
    }
}
