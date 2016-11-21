package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.apps.uac.api.UserService;
import cn.ibabygroup.commons.context.RequestContextHolder;
import cn.ibabygroup.pros.imservice.api.IMServiceService;
import cn.ibabygroup.pros.imservice.api.IMUserService;
import cn.ibabygroup.pros.imservice.dao.MessageDao;
import cn.ibabygroup.pros.imservice.dao.UserInfoDao;
import cn.ibabygroup.pros.imservice.model.IM.IMURL;
import cn.ibabygroup.pros.imservice.model.IM.MsgBody;
import cn.ibabygroup.pros.imservice.model.IM.MsgContent;
import cn.ibabygroup.pros.imservice.model.enums.ContentTypeEnum;
import cn.ibabygroup.pros.imservice.model.enums.SenderTypeEnum;
import cn.ibabygroup.pros.imservice.model.mongodb.IMMessage;
import cn.ibabygroup.pros.imservice.model.net.NetCheckChat;
import cn.ibabygroup.pros.imservice.model.net.NetResponse;
import cn.ibabygroup.pros.imservice.service.NetService;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imservice.utils.Utils;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.SyncReq;
import cn.ibabygroup.pros.imserviceapi.dto.resp.SyncResp;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: Crowhyc Date: 2016/8/10 Time: 19:22
 */
@Service
public class IMServiceServiceImpl implements IMServiceService {

    @Autowired
    private IMURL iMURL;
    @Autowired
    private TXService tXService;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private NetService netService;
    @Autowired
    private UserService userService;
    @Autowired
    private IMUserService imUserService;
    @Autowired
    private RESTService restService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 每次同步的最大消息数量
    private static final Integer maxSyncCount = 200;

    @Override
    public SyncResp sync(SyncReq syncReq) {
        Criteria criteria;
        //第一次只拉取未读的消息
        if(syncReq.getLastSyncMarker().longValue() == 0L) {
            criteria = Criteria.where("syncFlag").is(Boolean.FALSE);
        }
        else {
            criteria = Criteria.where("createTime").gt(syncReq.getLastSyncMarker());
        }
        String userId = RequestContextHolder.getUserId();
        criteria.and("toUserId").is(userId);
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        Query query = new Query(criteria).with(sort);
        Update update = Update.update("syncFlag", true);
        Long count = messageDao.count(query);
        SyncResp syncResp = new SyncResp();
        //如果数据已经同步完成，设置LastSyncMarker为最初传上来的值 --- add by tmg 2016-11-11
        if(count.intValue() == 0) {
            syncResp.setLastSyncMarker(syncReq.getLastSyncMarker());
            return syncResp;
        }
        if(count.intValue() > maxSyncCount) {
            syncResp.setEndFlag(false);
            query.limit(maxSyncCount);
        }
        List<IMMessage> datas = messageDao.findAllAndModify(query, update);
        if(datas != null && datas.size()>0) {
            syncResp.setDataList(datas);
            syncResp.setLastSyncMarker(datas.get(datas.size() - 1).getCreateTime());
        }
        return syncResp;
    }

    @Override
    public String getDesc(ChatMessageReq chatMessageReq) {
        //问诊关闭消息，不要desc
        if(chatMessageReq.getElemType() == 2004 || chatMessageReq.getElemType() == 2003) {
            return "";
        }
        StringBuilder desc = new StringBuilder(chatMessageReq.getSenderName());
        Integer senderType = chatMessageReq.getSenderType();
        if(senderType.intValue() == SenderTypeEnum.Doctor.getCode())
            desc.append("医生");
        desc.append(" : ");
        Integer contentType = chatMessageReq.getContentType();
        if (contentType.intValue() == ContentTypeEnum.Text.getCode()) {
            JSONObject jSONObject = JSONUtil.parseObject(chatMessageReq.getContentJson());
            String text = (String) jSONObject.get("text");
            if(text.length() > 100 )
                text = text.substring(0,99);
            desc.append(text);
        }
        else if(contentType.intValue() == ContentTypeEnum.Pic.getCode()) {
            desc.append("发来一张图片");
        }
        else if(contentType.intValue() == ContentTypeEnum.Coordinate.getCode()) {
            desc.append("分享了一个地理位置");
        }
        else if(contentType.intValue() == ContentTypeEnum.Audio.getCode()) {
            desc.append("发来一段语音");
        }
        else if(contentType.intValue() == ContentTypeEnum.Dynamic.getCode()) {
            desc.append("[动态] 动态标题");
        }
        else if(contentType.intValue() == ContentTypeEnum.Post.getCode()) {
            desc.append("[帖子] 帖子标题");
        }
        return desc.toString();
    }

    @Override
    public String getExt(Integer elemType, String chatId) {
        Map<String,Object> map = new HashMap();
        map.put("elemType", elemType);
        map.put("chatId", chatId);
        return JSONUtil.toCamelCaseJSONString(map);
    }

    @Override
    public Boolean verificationOMgs(ChatMessageReq chatMessageReq, String toUserId) {
        //调用net系统验证消息的合理性
        NetCheckChat netCheckChat = new NetCheckChat();
        netCheckChat.setChatType(netCheckChat.getChatType(chatMessageReq.getElemType()));
        netCheckChat.setDiagnosisId(chatMessageReq.getChatId());
        netCheckChat.setFromUser(chatMessageReq.getSenderId());
        netCheckChat.setToUser(toUserId);
        NetResponse netResponse = netService.checkChat(netCheckChat);
        Boolean data = netResponse.getSuccess();
        return data;
    }

    public static void main(String[] args) {

        String[] ids = new String[] {"test123", "e3df480295d54463aadf2420879257bd" };
        Map<String, Object> map = new HashMap();
        map.put("MsgRandom", Utils.randInt());
        map.put("MsgLifeTime", 0);
        Map<String, Object> condition = new HashMap<>();
        Map<String,Object> attrsOrMap = new IdentityHashMap<>();
        for(String id : ids)
            attrsOrMap.put(new String("Id"), id);
        condition.put("AttrsOr", attrsOrMap);
        map.put("Condition", condition);

        MsgBody msgBody = new MsgBody();
        MsgContent msgContent = new MsgContent();
        ChatMessageReq chatMessageReq = new ChatMessageReq();
        chatMessageReq.setMsgId("123");
        chatMessageReq.setContentJson("{123}");
        msgContent.setData(JSONUtil.toCamelCaseJSONString(chatMessageReq));
        msgContent.setDesc("123");
        msgContent.setExt("fff");
        msgBody.setMsgContent(msgContent);
        map.put("MsgBody",new MsgBody[] { msgBody });


        String s = JSONUtil.toCamelCaseJSONString(map);
        System.out.println(s);

    }
}
