package cn.ibabygroup.pros.imservice.web.controller;

import cn.ibabygroup.pros.imservice.api.*;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imserviceapi.api.AppConstants;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.SendMsgReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.PushMsgReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.SendSpeedMsgReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by tianmaogen on 16/8/11.
 */
@RestController
@RequestMapping(AppConstants.API_WEBAPI_PRIFEX+"/v1/imservice")
@Api(value = "/imservice", description = "发送消息API", protocols = "application/json")
@Slf4j
public class IMServiceController {
    @Autowired
    private FindAndVerifySendMsgService findAndVerifySendMsgService;
    @Autowired
    private FindPushMsgService findPushMsgService;

    @RequestMapping(value = "/pushMsg", method = RequestMethod.POST)
    @ApiOperation(value = "推送消息接口", notes = "从业务系统推送消息，比如开课提醒，发极速问诊类消息")
    @ResponseBody
    public Long pushMsg(@ApiParam(value = "聊天详细实体", required = true)
                              @RequestBody PushMsgReq pushMsgReq) {
        log.info("pushMsg=====>请求实体为:{}", JSONUtil.toCamelCaseJSONString(pushMsgReq));
        PushMsgService pushMsgService = findPushMsgService.find(pushMsgReq.getMsgBody());
        Long resp = pushMsgService.pushMsg(pushMsgReq.getMsgBody(), pushMsgReq.getToUserIds());
        return resp;
    }

    @RequestMapping(value = "/sendMsgFromOpenFire", method = RequestMethod.POST)
    @ApiOperation(value = "从openfire发送消息", notes = "从openfire发送消息")
    @ResponseBody
    public Long sendMsgFromOpenFire(@ApiParam(value = "聊天详细实体", required = true)
                                            @RequestBody SendMsgReq sendMsgReq) {
        log.info("sendMsgFromOpenFire=====>请求实体为:{}", JSONUtil.toCamelCaseJSONString(sendMsgReq));
        return sendMsg(sendMsgReq);
    }

    private Long sendMsg(SendMsgReq sendMsgReq) {
        SendMsgService sendMsgFromApp = findAndVerifySendMsgService.findSendMsgService(sendMsgReq.getMsgBody());
        boolean verifyFlag = findAndVerifySendMsgService.verifyMsg(sendMsgReq.getMsgBody(), sendMsgReq.getToUserId());
        if(!verifyFlag)
            throw new IMException("消息验证不合法.", JSONUtil.toCamelCaseJSONString(sendMsgReq));
        Long aLong = sendMsgFromApp.sendMsg(sendMsgReq.getMsgBody(), sendMsgReq.getToUserId());
        return aLong;
    }
    @RequestMapping(value = "/sendMsgFromApp", method = RequestMethod.POST)
    @ApiOperation(value = "从App端发送消息", notes = "从App端发送消息")
    public Long sendMsgFromApp(@ApiParam(value = "聊天详细实体")
                                  @RequestBody SendMsgReq sendMsgReq) {
        log.info("sendMsgFromApp=====>请求实体为:{}", JSONUtil.toCamelCaseJSONString(sendMsgReq));
        return sendMsg(sendMsgReq);
    }

    @RequestMapping(value = "/sendMsgFromServer", method = RequestMethod.POST)
    @ApiOperation(value = "从业务系统发送消息", notes = "从业务系统发送消息,包括 讲坛，医声，讲坛系统...")
    public Long sendMsgFromServer(@ApiParam(value = "聊天详细实体", required = true)
                                         @RequestBody SendMsgReq sendMsgReq) {
        log.info("sendMsgFromServer=====>请求实体为:{}", JSONUtil.toCamelCaseJSONString(sendMsgReq));
        SendMsgService sendMsgFromApp = findAndVerifySendMsgService.findSendMsgService(sendMsgReq.getMsgBody());
        Long aLong = sendMsgFromApp.sendMsg(sendMsgReq.getMsgBody(), sendMsgReq.getToUserId());
        return aLong;
    }
}
