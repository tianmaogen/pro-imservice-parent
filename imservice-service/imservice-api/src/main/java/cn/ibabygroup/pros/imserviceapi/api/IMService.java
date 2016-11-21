package cn.ibabygroup.pros.imserviceapi.api;

import cn.ibabygroup.pros.imserviceapi.dto.req.SendMsgReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by tianmaogen on 18/9/11.
 */
@FeignClient(name = AppConstants.APP_SERVICE_NAME)
@RequestMapping(AppConstants.API_SERVER_PRIFEX+"/imservice")
@Api(value = "/imservice", description = "发送消息API", protocols = "application/json")
public interface IMService {
    @ApiOperation(value = "从cloud业务系统发送消息", notes = "目前只有医声系统调用.")
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    Long sendMsg(@ApiParam(value = "聊天详细实体", required = true) @RequestBody SendMsgReq sendMsgReq);
}
