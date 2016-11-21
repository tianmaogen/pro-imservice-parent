package cn.ibabygroup.pros.imservice.api;

import cn.ibabygroup.apps.uac.api.dto.UserInfo;
import cn.ibabygroup.pros.imserviceapi.dto.req.MMLoginReq;
import cn.ibabygroup.pros.imserviceapi.dto.resp.UserInfoResp;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: tianmaogen
 * Date: 2016/8/10
 */
//@FeignClient(name = "imservice-server-${spring.profiles.active}")
//@RequestMapping("/imServiceService")
public interface IMUserService {


    /**
     * 获取用户签名
     * @param userId 用户Id
     * @return
     */
//    @RequestMapping(value = "/getNewUserSign", method = RequestMethod.POST)
    String getNewUserSign(String userId);

    /**
     * 设置用户远程推送的属性
     * @param userId 用户Id
     * @return
     */
//    @RequestMapping(value = "/setPushAttr", method = RequestMethod.POST)
    void setIMAttr(String userId);

    /**
     * 判断版本信息是否是新的
     * @param version
     * @return
     */
    boolean isNewVersion(Integer version);

    /**
     * 根据userId判断版本信息是否是新的
     * @param userId
     * @return
     */
    boolean isNewVersion(String userId);

    /**
     * 更新缓存中的版本信息
     */
    boolean updateClientVersion(String userId, Integer version);

    /**
     * 获取用户的版本信息
     * @param userId
     * @return
     */
    Integer getClientVersion(String userId);

    /**
     * 获取某一课堂在线用户id集合
     * @param groupId
     * @return
     */
    List<String> getGroupOnlineUserList(String groupId);

}
