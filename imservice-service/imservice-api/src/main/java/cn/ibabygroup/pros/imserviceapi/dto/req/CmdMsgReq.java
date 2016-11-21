package cn.ibabygroup.pros.imserviceapi.dto.req;

import lombok.Data;

/**
 * 透传消息：
 * Created by Administrator on 2016/8/23.
 */
@Data
public class CmdMsgReq {

    /**
     * 消息类型
     * 系统消息：102
     * 评论消息:103
     * 急速问诊消息：104
     * 关闭问诊:105
     */
    private Integer CacheType;

    /**
     * 消息附加信息
     * 免费问诊消息会话ID,关闭问诊会话ID等
     */
    private String CacheValue;

    /**
     * 提示消息内容
     */
    private String CacheTitle;

}
