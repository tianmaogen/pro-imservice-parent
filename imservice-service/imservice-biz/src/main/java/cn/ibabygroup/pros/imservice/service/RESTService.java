package cn.ibabygroup.pros.imservice.service;

import cn.ibabygroup.commons.context.RequestContextHolder;
import cn.ibabygroup.pros.imservice.model.rest.AllPulpitsResp;
import cn.ibabygroup.pros.imservice.model.rest.RESTResponse;
import cn.ibabygroup.pros.imservice.model.rest.RESTURL;
import cn.ibabygroup.pros.imservice.utils.RESTUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.PushMsgReq;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianmaogen on 2016/9/6.
 * 调用REST系统的service,用于给老版本发消息
 */
@Service
public class RESTService {
    @Autowired
    private RESTURL restURL;

    /**
     * 给老版本推送消息
     */
    public RESTResponse pushMessage(PushMsgReq pushMsgReq) {
        String url = restURL.getPushMessageUrl();
        RESTResponse restResponse = RESTUtil.postForObject(url, pushMsgReq, RESTResponse.class, true);
        return restResponse;
    }

    /**
     * 给老版本发送消息
     * @return
     */
    public RESTResponse sendMessage(ChatMessageReq chatMessageReq, String toUserId) {
        String url = restURL.getSendMessageUrl(toUserId);
        RESTResponse restResponse = RESTUtil.postForObject(url, chatMessageReq, RESTResponse.class, true);
        return restResponse;
    }

    /**
     * 同步聊天消息记录
     * @return
     */
    public RESTResponse syncMessage(ChatMessageReq chatMessageReq) {
        String url = restURL.getSyncPulpitImMsgUrl();
        RESTResponse restResponse = RESTUtil.postForObject(url, chatMessageReq, RESTResponse.class, true);
        return restResponse;
    }

    /**
     * 检查消息的合法性
     * @return
     */
    public RESTResponse checkChat(String senderId, String groupId) {
        StringBuilder url = new StringBuilder(restURL.getCheckChatUrl());
        url.append("?pulpitId=").append(groupId).append("&userId=").append(senderId);
        RESTResponse restResponse = RESTUtil.postForObject(url.toString(), null, RESTResponse.class);
        return restResponse;
    }

    /**
     * 获取所有讲坛
     */
    public JSONArray getAllPulpits() {
        String url = restURL.getSearchPulpitbUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.set("token","cNuPBH6MxQYPv2sTTUw7Fs2A10ZWGIgiEs5coCsMqDEYz6K5MDrxgFZ99YNU4e9pddrbalGf8Vzq94SWJD19qw==");
        RESTResponse restResponse = RESTUtil.getForObject(url.toString(), null, RESTResponse.class, headers, true);
        JSONObject allPulpitsResp = (JSONObject) restResponse.getData();
        Integer totalCount = (Integer) allPulpitsResp.get("TotalCount");
        if(totalCount > restURL.maxPulpitNum)
            throw new IMException("讲坛的数量超过最大设置的最大值"+restURL.maxPulpitNum);
        JSONArray datas = allPulpitsResp.getJSONArray("DataList");
        return datas;
    }

    /**
     * 获取讲坛中的用户信息
     */
    public List<String> getUserListByGroupId(String groupId, Integer count) {
        List<String> userList = new ArrayList<>();
        String url = restURL.getMemberListUrl(groupId, count);
        HttpHeaders headers = new HttpHeaders();
        headers.set("token","cNuPBH6MxQYPv2sTTUw7Fs2A10ZWGIgiEs5coCsMqDEYz6K5MDrxgFZ99YNU4e9pddrbalGf8Vzq94SWJD19qw==");
        RESTResponse restResponse = RESTUtil.getForObject(url.toString(), null, RESTResponse.class, headers, true);
        JSONObject jsonResponse = (JSONObject) restResponse.getData();
        JSONArray dataList = jsonResponse.getJSONArray("DataList");
        if(dataList == null)
            return null;
        for(int i=0; i<dataList.size(); i++) {
            JSONObject o = (JSONObject) dataList.get(i);
            userList.add(o.getString("id"));
        }
        return userList;
    }
}
