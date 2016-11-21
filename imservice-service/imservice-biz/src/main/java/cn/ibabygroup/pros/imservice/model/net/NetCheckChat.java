package cn.ibabygroup.pros.imservice.model.net;

import lombok.Data;

/**
 * Created by tianmaogen on 2016/9/6.
 * 调用.net检查消息实体
 */
@Data
public class NetCheckChat {
    /**
     * 1-免费问诊 2-图文问诊 3-医患聊天 4-好友聊天
    */
    private Integer chatType;
    private String toUser;
    private String fromUser;
    private String diagnosisId;

    public Integer getChatType(Integer elemType) {
        if(elemType > 2000 && elemType < 2100)
            return 1;
        else if(elemType > 2100 && elemType < 2200)
            return 2;
        else if(elemType > 2200 && elemType < 2300)
            return 3;
        else if(elemType > 2300 && elemType < 2400)
            return 4;
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sbf = new StringBuilder("?");
        sbf.append("chatType="+this.chatType);
        sbf.append("&toUser="+this.toUser);
        sbf.append("&fromUser="+this.fromUser);
        sbf.append("&diagnosisId="+this.diagnosisId);
        return sbf.toString();
    }
}
