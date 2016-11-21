package cn.ibabygroup.pros.imservice.model.IM;

import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imservice.utils.Utils;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/30.
 */
@Data
public class GroupIMMsg implements Msg3times{
    //群Id
    private String groupId;
    //消息随机数,由随机函数产生。（用作消息去重）
    private int random;
    //消息内容
    private MsgBody[] msgBody;
    //消息来源账号
    private String from_Account;

    public static GroupIMMsg getGroupIMMsg(String groupId,Object data, String from_Account) {
        return getGroupIMMsg(groupId, data, null, null, from_Account);
    }
    public static GroupIMMsg getGroupIMMsg(String groupId,Object data, String desc, String from_Account) {
        return getGroupIMMsg(groupId, data, desc, null, from_Account);
    }

    public static GroupIMMsg getGroupIMMsg(String groupId,Object data, String desc, String ext, String from_Account) {
        GroupIMMsg groupIMMsg = new GroupIMMsg();
        groupIMMsg.setFrom_Account(from_Account);
        groupIMMsg.setGroupId(groupId);
        groupIMMsg.setRandom(Utils.randInt());
        MsgBody msgBody = new MsgBody();
        MsgContent msgContent = new MsgContent();
        msgContent.setData(JSONUtil.toCamelCaseJSONString(data));
        msgContent.setDesc(desc);
        msgContent.setExt(ext);
        msgBody.setMsgContent(msgContent);
        groupIMMsg.setMsgBody(new MsgBody[] { msgBody });
        return groupIMMsg;
    }

    @Override
    public void setMsgRandom(int randomInt) {
        this.random = randomInt;
    }
}
