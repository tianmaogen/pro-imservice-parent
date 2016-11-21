package cn.ibabygroup.pros.imservice.service;

import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imservice.utils.Utils;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.AddGroupMemberReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.DelGroupMemberReq;
import cn.ibabygroup.pros.imservice.model.IM.*;
import cn.ibabygroup.pros.imservice.utils.RESTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by tianmaogen on 2016/8/30.
 *
 * 调用腾讯云通信的service
 */

@Service
@Slf4j
public class TXService {
    @Autowired
    private IMURL iMURL;

    /**
     *创建房间
     */
    public IMResponse createRoom(IMRoom iMRoom) {
        String url = iMURL.getCreateRoomURL();
        log.info("createRoom begin===>{}",JSONUtil.toCamelCaseJSONString(iMRoom));
        IMResponse imResponse = RESTUtil.postForObject(url, iMRoom, IMResponse.class);
        log.info("createRoom end===>{}",JSONUtil.toCamelCaseJSONString(imResponse));
        return imResponse;
    }

    /**
     *删除房间
     */
    public IMResponse delRoom(String groupId) {
        String url = iMURL.getDelRoomURL();
        Map<String,String> map = new HashMap<>();
        map.put("GroupId", groupId);
        IMResponse imResponse = RESTUtil.postForObject(url, map, IMResponse.class);
        return imResponse;
    }

    /**
     *添加群成员
     */
    public IMResponse addGroupMember(AddGroupMemberReq addGroupMemberReq) {
        String url = iMURL.getAddGroupMemberURL();
        log.info("addGroupMember begin===>{}",JSONUtil.toCamelCaseJSONString(addGroupMemberReq));
        IMResponse imResponse = RESTUtil.postForObject(url, addGroupMemberReq, IMResponse.class);
        log.info("addGroupMember end===>{}",JSONUtil.toCamelCaseJSONString(imResponse));
        return imResponse;
    }

    /**
     *删除群成员
     */
    public IMResponse delGroupMember(DelGroupMemberReq delGroupMemberReq) {
        String url = iMURL.getDelGroupMemberURL();
        IMResponse imResponse = RESTUtil.postForObject(url, delGroupMemberReq, IMResponse.class);
        return imResponse;
    }

    /**
     * 设置用户远程推送的属性
     */
    public IMResponse setPushAttr(IMSetAttr iMSetAttr) {
        String url = iMURL.getSetPushAttrURL();
        log.info("setPushAttr begin===>{}",JSONUtil.toCamelCaseJSONString(iMSetAttr));
        IMResponse imResponse = RESTUtil.postForObject(url, iMSetAttr, IMResponse.class);
        log.info("setPushAttr end===>{}",JSONUtil.toCamelCaseJSONString(imResponse));
        return imResponse;
    }

    /**
     * 批量发送A类消息
     * 业务场景:开课提醒，推送新的极速问诊
     */
    public IMResponse batchSendAMsg(IMMsgBatch iMMsgBatch) {
        String url = iMURL.getBatchSendmsgURL();
        return sendMsg3Times(iMMsgBatch, 0, "batchSendAMsg", url);
    }

    private IMResponse sendMsg3Times(Msg3times msg, int sendCount, String methodName, String url) {
        if(sendCount > 2) {
            log.error(methodName+"===========>连续调用3次腾讯云服务失败,请求实体为:"+ JSONUtil.toCamelCaseJSONString(msg));
            return null;
        }
        sendCount++;

        IMResponse imResponse = null;
        try {
            imResponse = RESTUtil.postForObject(url, msg, IMResponse.class);
            if(imResponse == null || imResponse.getActionStatus().equals(imResponse.actionStatusFAIL)) {
                log.error(methodName + "调用失败========>错误信息为"+ JSONUtil.toCamelCaseJSONString(imResponse)+"请求实体为："+JSONUtil.toCamelCaseJSONString(msg));
                msg.setMsgRandom(Utils.randInt());
                sendMsg3Times(msg, sendCount, methodName, url);
            }
        }catch (Exception e) {
            log.error(methodName+"调用失败========>异常信息为"+ e.toString()+"请求实体为："+JSONUtil.toCamelCaseJSONString(msg));
            msg.setMsgRandom(Utils.randInt());
            sendMsg3Times(msg, sendCount, methodName, url);
        }
        log.info(methodName+"===========>调用成功,请求实体为:"+ JSONUtil.toCamelCaseJSONString(msg)+"请求次数为:"+sendCount);
        return imResponse;
    }

    /**
     * A类消息
     * 业务场景:1对1聊天(图文问诊，建卡问诊，好友聊天)
     */
    public IMResponse sendAMsg(IMMsg iMMsg) {
        String url = iMURL.getSendmsgURL();
        return sendMsg3Times(iMMsg, 0, "sendAMsg", url);
    }

    /**
     * B类消息
     * 业务场景:讲坛聊天，讲坛的透传消息
     */
    public IMResponse sendBMsg(GroupIMMsg groupIMMsg) {
        String url = iMURL.getSendGroupMsgURL();
        return sendMsg3Times(groupIMMsg, 0, "sendBMsg", url);
    }

    /**
     * C类消息
     * 业务场景:新的极速问诊
     */
    public IMResponse sendCMsg(IMPushMsg iMPushMsg) {
        String url = iMURL.getImPushURL();
        return sendMsg3Times(iMPushMsg, 0, "sendCMsg", url);
    }

    /**
     * D类消息
     * 业务场景:添加主管医生,关注提醒,加好友
     */
    public IMResponse sendDMsg(IMMsg iMMsg) {
        String url = iMURL.getSendmsgURL();
        return sendMsg3Times(iMMsg, 0, "sendDMsg", url);
    }

    /**
     * E类消息
     * 业务场景:讲坛中指定某些人接受的透传消息
     */
    public IMResponse sendEMsg(GroupIMMsg groupIMMsg) {
        String url = iMURL.getSendGroupNotificationURL();
        IMResponse imResponse = RESTUtil.postForObject(url, groupIMMsg, IMResponse.class);
        return imResponse;
    }

    /**
     * 批量导入账号信息
     * @param accountList
     */
    public Boolean batchImportAccounts(List<String> accountList) {
        //每次最多导50个账号信息
        int importCount = accountList.size()/50  + 1;
        List<String> subList = null;
        Map<String, List<String>> map = new HashMap();
        for(int i=0; i<importCount; i++) {
//            System.out.println("开始第"+i+"次账号导入--------------");
            if(i == importCount-1)
                subList = accountList.subList(i * 50, accountList.size());
            else
                subList = accountList.subList(i * 50, (i+1) * 50);
            map.put("Accounts", subList);
            if(!importAccounts(map, 0))
                return false;
        }
        return true;
    }

    private Boolean importAccounts(Map<String, List<String>> map, int count) {
        String url = iMURL.getMultiImportAccountURL();
        if(count > 2) {
            log.error("连续调用3次IM云导入账号信息失败，导入的账号是:{}", map);
//            throw new IMException("连续3次导入账号信息失败!");
            return false;
        }
        count++;
        try {
            ImprotAccountsResponse imResponse = RESTUtil.postForObject(url, map, ImprotAccountsResponse.class);
            if (imResponse.getActionStatus().equals(imResponse.actionStatusFAIL)){
                log.error("第"+count+"次导入账号信息失败!imResponse==={}",JSONUtil.toCamelCaseJSONString(imResponse));
                importAccounts(map, count);
            }
        }
        catch (Exception e) {
            log.error("导入账号信息失败!异常信息为:{}",e);
            throw new IMException("导入账号信息异常!", e);
        }
        return true;
    }

    /**
     * 获取用户在线状态
     * @param toAccountList
     * @return
     */
    public List<QueryStateResponse.QueryResult> queryState(List<String> toAccountList) {
        List<QueryStateResponse.QueryResult> results = new ArrayList<>();
        int queryTXMaxCount = 75;
        //每次最多查询queryTXMaxCount个账号信息
        int queryCount = toAccountList.size()/queryTXMaxCount  + 1;
        List<String> subList = null;
        for(int i=0; i<queryCount; i++) {
            if(i == queryCount-1)
                subList = toAccountList.subList(i * queryTXMaxCount, toAccountList.size());
            else
                subList = toAccountList.subList(i * queryTXMaxCount, (i+1) * queryTXMaxCount);

            QueryStateResponse response = querySubState(subList);
            results.addAll(response.getQueryResult());
        }
        return results;
    }

    private QueryStateResponse querySubState(List<String> toAccountList) {
        String url = iMURL.getQueryStateURL();
        Map<String, List<String>> map = new HashMap<>();
        map.put("To_Account",toAccountList);
        QueryStateResponse imResponse =  RESTUtil.postForObject(url, map, QueryStateResponse.class);
        if(imResponse.getActionStatus().equals(imResponse.actionStatusFAIL)) {
            log.error("queryState===>失败，失败的subList为{}",JSONUtil.toCamelCaseJSONString(toAccountList));
            throw new IMException("调用腾讯云接口queryState获取用户状态出错!");
        }
        return imResponse;
    }

}
