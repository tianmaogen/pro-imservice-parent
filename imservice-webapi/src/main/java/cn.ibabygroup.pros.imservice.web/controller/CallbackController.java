package cn.ibabygroup.pros.imservice.web.controller;

import cn.ibabygroup.pros.imservice.web.req.ImcallbackReq;
import cn.ibabygroup.pros.imservice.web.resp.IMCallbackResp;
import cn.ibabygroup.pros.imserviceapi.api.AppConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tianmaogen on 2016/8/25.
 */
@RestController
@RequestMapping(AppConstants.API_WEBAPI_PRIFEX+"/v1")
public class CallbackController {
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "腾讯IM云回调接口", notes = "腾讯IM云回调接口")
    @ResponseBody
    public IMCallbackResp imcallback(@ApiParam(value = "回调参数", required = true)
                                     @RequestBody ImcallbackReq imcallbackReq) {
        ImcallbackReq.Info info = imcallbackReq.getInfo();
//        System.out.println("开始回调，回调时间为："+new Date() + "回调内容为:" + JSONUtil.toPascalCaseJSONString(imcallbackReq));
//        imServiceService.imcallback(info.getTo_Account(), info.getAction(), info.getReason());
        return IMCallbackResp.ok();
    }
}
