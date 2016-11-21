package cn.ibabygroup.pros.imservice.model.IM;

import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imservice.utils.Utils;
import lombok.Data;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by tianmaogen on 2016/9/7.
 * 用于推送的消息实体
 */
@Data
public class IMPushMsg implements Msg3times{
    //消息随机数,由随机函数产生。（用作消息去重）
    private int msgRandom;
    //消息离线存储时间，单位秒，最多7天。默认为0, 表示不离线存储。
    private Integer msgLifeTime=0;
    private Condition condition;
    //消息内容
    private MsgBody[] msgBody;

//    "0": "Id",
//    "1": "SpeedAsk",
//    "2": "CourceNotice"
    public static final String Id = "Id";
    public static final String SpeedAsk = "SpeedAsk";
    public static final String CourceNotice = "CourceNotice";

    public static IMPushMsg getIMPushMsg(String attrName, String attrVal, Object data, String desc, String ext) {

//        Map<String,Object> attrsOr = new IdentityHashMap<>();
        Map<String,Object> attrsOr = new HashMap<>();
//        for(String id : ids)
        attrsOr.put(attrName, attrVal);

        Condition condition = new Condition();
        condition.setAttrsOr(attrsOr);

        MsgBody msgBody = new MsgBody();
        MsgContent msgContent = new MsgContent();
        msgContent.setData(JSONUtil.toCamelCaseJSONString(data));
        msgContent.setDesc(desc);
        msgContent.setExt(ext);
        msgBody.setMsgContent(msgContent);


        IMPushMsg iMMsg = new IMPushMsg();
        iMMsg.setMsgRandom(Utils.randInt());
        iMMsg.setMsgBody(new MsgBody[] { msgBody });
        iMMsg.setCondition(condition);

        return iMMsg;
    }

    @Data
    public static class Condition {
        Map<String,Object> attrsOr;
    }
}
