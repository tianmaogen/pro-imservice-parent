package cn.ibabygroup.pros.imservice.service;

import cn.ibabygroup.pros.imservice.model.mongodb.IMMessage;
import cn.ibabygroup.pros.imservice.model.net.NetCheckChat;
import cn.ibabygroup.pros.imservice.model.net.NetResponse;
import cn.ibabygroup.pros.imservice.model.net.NetURL;
import cn.ibabygroup.pros.imservice.utils.RESTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tianmaogen on 2016/9/6.
 * 调用.net系统的service
 */
@Service
public class NetService {
    @Autowired
    private NetURL netURL;

    /**
     * 检查消息的合法性
     * @return
     */
    public NetResponse checkChat(NetCheckChat netCheckChat) {
        String url = netURL.getCheckChatUrl() + netCheckChat.toString();
        NetResponse netResponse = RESTUtil.postForObject(url, netCheckChat, NetResponse.class);
        return netResponse;
    }

    /**
     * 同步消息
     * @return
     */
    public NetResponse syncMsg(IMMessage imMessage) {
        String url = netURL.getSyncMsgUrl();
        NetResponse netResponse = RESTUtil.postForObject(url, imMessage, NetResponse.class);
        return netResponse;
    }

}
