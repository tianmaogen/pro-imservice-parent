package cn.ibabygroup.pros.imservice.service;

import cn.ibabygroup.pros.imservice.api.FindAndVerifySendMsgService;
import cn.ibabygroup.pros.imservice.api.SendMsgService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imserviceapi.api.IMService;
import cn.ibabygroup.pros.imserviceapi.dto.req.SendMsgReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tianmaogen on 2016/9/18.
 */
@RestController
@Slf4j
public class IMServiceImpl implements IMService {
    @Autowired
    private FindAndVerifySendMsgService findAndVerifySendMsgService;

    @Override
    public Long sendMsg(@RequestBody SendMsgReq sendMsgReq) {
        log.info("sendMsg=====>请求实体为:{}", JSONUtil.toCamelCaseJSONString(sendMsgReq));
        SendMsgService sendMsgFromApp = findAndVerifySendMsgService.findSendMsgService(sendMsgReq.getMsgBody());
        Long aLong = sendMsgFromApp.sendMsg(sendMsgReq.getMsgBody(), sendMsgReq.getToUserId());
        return aLong;
    }
}
