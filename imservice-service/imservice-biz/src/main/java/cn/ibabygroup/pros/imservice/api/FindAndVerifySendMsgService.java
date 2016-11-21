package cn.ibabygroup.pros.imservice.api;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import io.swagger.annotations.Api;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by tianmaogen on 2016/9/1.
 * 根据elemType找到对应的sendMsgService，并且根据来源(app或者service)做验证
 */
//@Api(value = "findAndVerifySendMsgService", description = "根据elemType找到对应的sendMsgService，并且根据来源(app或者service)做验证", protocols = "application/json")
//@FeignClient(name = "imservice-server-${spring.profiles.active}")
//@RequestMapping("/findAndVerifySendMsgService")
public interface FindAndVerifySendMsgService {

//    @RequestMapping(value = "/findSendMsgService", method = RequestMethod.POST)
    SendMsgService findSendMsgService(ChatMessageReq chatMessageReq);

//    @RequestMapping(value = "/verifyMsg", method = RequestMethod.POST)
    boolean verifyMsg(ChatMessageReq chatMessageReq, @RequestParam(name="toUserId") String toUserId);
}
