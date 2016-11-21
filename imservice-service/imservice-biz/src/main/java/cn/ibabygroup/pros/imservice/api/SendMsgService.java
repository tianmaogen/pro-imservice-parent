package cn.ibabygroup.pros.imservice.api;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tianmaogen on 2016/9/1.
 * 发送消息service
 */
//@FeignClient(name = "imservice-server-${spring.profiles.active}")
//@RequestMapping("/sendMsgService")
public interface SendMsgService {
    /**
     * 发送消息接口
     * @param chatMessageReq 消息体
     * @param toId 可能是用户id，用户id数组，群id
     * @return
     */
//    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    Long sendMsg(ChatMessageReq chatMessageReq, String toId);
}
