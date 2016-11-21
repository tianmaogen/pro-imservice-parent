package cn.ibabygroup.pros.imservice.model.IM;

import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imservice.utils.Utils;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/26.
 */
@Data
public class IMMsg implements Msg3times{
    //消息接收方账号
    private String to_Account;
    //消息随机数,由随机函数产生。（用作消息去重）
    private int msgRandom;
    //消息内容
    private MsgBody[] msgBody;

    public static IMMsg getIMMsg(String to_Account,Object data) {
        return getIMMsg(to_Account, data, null, null);
    }

    public static IMMsg getIMMsg(String to_Account,Object data, String desc, String ext) {
        IMMsg iMMsg = new IMMsg();
        iMMsg.setTo_Account(to_Account);
        iMMsg.setMsgRandom(Utils.randInt());
        MsgBody msgBody = new MsgBody();
        MsgContent msgContent = new MsgContent();
        msgContent.setData(JSONUtil.toCamelCaseJSONString(data));
        msgContent.setDesc(desc);
        msgContent.setExt(ext);
        msgBody.setMsgContent(msgContent);
        iMMsg.setMsgBody(new MsgBody[] { msgBody });
        return iMMsg;
    }

    public static void main(String[] args) {
        IMMsg iMMsg = new IMMsg();
        iMMsg.setTo_Account("11");
        iMMsg.setMsgRandom(12);
        MsgBody msgBody = new MsgBody();
        msgBody.setMsgType("123");

        MsgContent msgContent = new MsgContent();
        msgContent.setData("33");
        msgContent.setDesc("desc");
        msgContent.setExt("ext");
        msgContent.setSound("12");
        msgBody.setMsgContent(msgContent);

        iMMsg.setMsgBody(new MsgBody[] { msgBody });
        System.out.println(JSONUtil.toPascalCaseJSONString(iMMsg));
    }

}
