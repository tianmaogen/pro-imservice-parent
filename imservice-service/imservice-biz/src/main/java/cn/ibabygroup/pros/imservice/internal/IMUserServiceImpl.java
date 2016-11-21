package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.apps.uac.api.UserService;
import cn.ibabygroup.pros.imservice.api.IMUserService;
import cn.ibabygroup.pros.imservice.dao.MessageDao;
import cn.ibabygroup.pros.imservice.dao.UserInfoDao;
import cn.ibabygroup.pros.imservice.model.Constants;
import cn.ibabygroup.pros.imservice.service.JedisService;
import cn.ibabygroup.pros.imserviceapi.dto.req.MMLoginReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.UserLoginReq;
import cn.ibabygroup.pros.imserviceapi.dto.resp.NetResp;
import cn.ibabygroup.pros.imserviceapi.dto.resp.UserInfoResp;
import cn.ibabygroup.pros.imservice.model.IM.*;
import cn.ibabygroup.pros.imservice.model.mongodb.UserInfo;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.RESTUtil;
import cn.ibabygroup.pros.imservice.utils.SignUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA. User: Crowhyc Date: 2016/8/10 Time: 19:22
 */
@Service
public class IMUserServiceImpl implements IMUserService {

    @Autowired
    private IMURL iMURL;
    @Autowired
    private JedisService jedisService;
    @Autowired
    private UserService userService;
    @Autowired
    private TXService txService;

    @Override
    public String getNewUserSign(String userId) {
        String sign = SignUtil.getSign(iMURL.getSdkappid(),userId,iMURL.getPrivStr(), iMURL.getPubStr());
        //更新数据库中该user对于的sign
//        Criteria criteria = Criteria.where("userId").is(userId);
//        Query query = new Query(criteria);
//        Update update = Update.update("sign", sign);
//        userInfoDao.updateFirst(query, update);
        return sign;
    }

    @Override
    public void setIMAttr(String userId) {
        IMResponse imResponse = null;
        //设置远程推送属性
//        try {
//            imResponse = tXService.setPushAttr(IMSetAttr.getIMSetAttr(userId));
//            while (imResponse.getActionStatus().equals(imResponse.actionStatusFAIL)){
//                imResponse = tXService.setPushAttr(IMSetAttr.getIMSetAttr(userId));
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//            while (imResponse.getActionStatus().equals(imResponse.actionStatusFAIL)){
//                imResponse = tXService.setPushAttr(IMSetAttr.getIMSetAttr(userId));
//            }
//        }

        //设置用户签名
//        String sign = SignUtil.getSign(iMURL.getSdkappid(),userId,iMURL.getPrivStr(), iMURL.getPubStr());
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(userId);
//        userInfo.setSign(sign);
//        userInfoDao.save(userInfo);
    }

    @Override
    public boolean isNewVersion(Integer version) {
        if(version.intValue() < Constants.newVersion)
            return false;
        else
            return true;
    }

    @Override
    public boolean isNewVersion(String userId) {
        Integer version = getClientVersion(userId);
        return isNewVersion(version);
    }

    @Override
    public boolean updateClientVersion(String userId, Integer version) {
        String key = JedisService.versionKeyPre+userId ;
        return jedisService.set(key, version.toString());
    }

    @Override
    public Integer getClientVersion(String userId) {
        String version = jedisService.get(userId);
        if(StringUtils.isEmpty(version)) {
            cn.ibabygroup.apps.uac.api.dto.UserInfo userInfo = userService.getRealtimeUser(userId);
            updateClientVersion(userId, userInfo.getVersion());
            return userInfo.getVersion();
        }
        return Integer.valueOf(version);
    }

    @Override
    public List<String> getGroupOnlineUserList(String groupId) {
        String key = JedisService.courceMemberKeyPre + groupId;
        Set<String> membersSet = jedisService.smembers(key);
        if(membersSet.isEmpty())
            return new ArrayList<>();
        List<String> onLineMember = new ArrayList<>(membersSet.size());
        //获取用户的在线状态
        List<QueryStateResponse.QueryResult> results = txService.queryState(new ArrayList<>(membersSet));
        for(QueryStateResponse.QueryResult queryResult : results) {
            if(queryResult.getState().equals(QueryStateResponse.OnlineState))
                onLineMember.add(queryResult.getTo_Account());
        }
        return onLineMember;
    }
}
