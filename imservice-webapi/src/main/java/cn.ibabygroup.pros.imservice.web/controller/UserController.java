package cn.ibabygroup.pros.imservice.web.controller;

import cn.ibabygroup.commons.context.RequestContextHolder;
import cn.ibabygroup.pros.imservice.api.IMUserService;
import cn.ibabygroup.pros.imserviceapi.api.AppConstants;
import cn.ibabygroup.pros.imserviceapi.dto.req.MMLoginReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.UserLoginReq;
import cn.ibabygroup.pros.imserviceapi.dto.resp.UserInfoResp;
import cn.ibabygroup.pros.imservice.web.resp.IMJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tianmaogen on 2016/9/7.
 *
 */
@RestController
@RequestMapping(AppConstants.API_WEBAPI_PRIFEX+"/v1/user")
@Api(value = "user", description = "与用户相关的controller，包括登录，设置用户属性等", position = 1)
@Slf4j
public class UserController {
    @Autowired
    private IMUserService imUserService;

    @RequestMapping(value = "/getNewUserSign", method = RequestMethod.GET)
    @ApiOperation(value = "获取新的用户签名", notes = "app在快要过期的时候获取新的用户签名")
    @ResponseBody
    public String getNewUserSign() {
        String userId = RequestContextHolder.getUserId();
        String newUserSign = imUserService.getNewUserSign(userId);
        return newUserSign;
    }

    @RequestMapping(value = "/{userId}/{version}", method = RequestMethod.PUT)
    @ApiOperation(value = "更新用户版本信息", notes = "更新缓存中的用户版本信息")
    @ResponseBody
    public boolean updateVersion(@ApiParam(value = "用户Id", required = true)
                            @PathVariable String userId,
                            @ApiParam(value = "用户版本信息", required = true)
                            @PathVariable Integer version) {
        return imUserService.updateClientVersion(userId, version);
    }

    @RequestMapping(value = "/getGroupOnlineUserList/{groupId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某一课堂在线用户id集合", notes = "获取某一课堂在线用户id集合")
    @ResponseBody
    public List<String> getGroupOnlineUserList(@ApiParam(value = "房间号", required = true)
                                                    @PathVariable String groupId) {
        log.info("getGroupOnlineUserList=====>房间号为:{}", groupId);
        return imUserService.getGroupOnlineUserList(groupId);
    }
}
