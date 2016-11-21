package cn.ibabygroup.pros.imservice.web.controller;

import cn.ibabygroup.pros.imservice.api.IMServiceService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imserviceapi.api.AppConstants;
import cn.ibabygroup.pros.imserviceapi.dto.req.SyncReq;
import cn.ibabygroup.pros.imserviceapi.dto.resp.SyncResp;
import cn.ibabygroup.pros.imservice.web.resp.IMJsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tianmaogen on 2016/9/5.
 * 数据同步接口
 */
@RestController
@RequestMapping(AppConstants.API_WEBAPI_PRIFEX+"/v1")
@Slf4j
public class SyncController {

    @Autowired
    private IMServiceService imServiceService;
    
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public SyncResp sync(@RequestBody SyncReq syncReq) {
        log.info("sync  Request=====>同步请求体为:{}", JSONUtil.toCamelCaseJSONString(syncReq));
        SyncResp sync = imServiceService.sync(syncReq);
        log.info("sync  Response=====>同步响应体为:{}", JSONUtil.toCamelCaseJSONString(sync));
        return sync;
    }
}
