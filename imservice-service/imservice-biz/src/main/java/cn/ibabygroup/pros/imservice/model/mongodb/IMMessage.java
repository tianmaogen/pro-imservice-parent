package cn.ibabygroup.pros.imservice.model.mongodb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Description:
 * Created by gumutianqi@gmail.com on 16/8/25.
 * since 1.0.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "im_message")
public class IMMessage {

    @Id
    private String id;
    /**
     * 标识元素类型
     */
    @Field("elem_type")
    private Integer elemType;

    /**
     * 会话id
     */
    @Field("chat_id")
    private String chatId;

    /**
     * 会话类型
     */
    @Field("chat_type")
    private Integer chatType;

    /**
     * 消息id
     */
    @Field("msg_id")
    private String msgId;

    /**
     * 消息内容的类型(图片消息，文字消息等)
     */
    @Field("content_type")
    private Integer contentType;

    /**
     * 消息内容的Json
     */
    @Field("content_json")
    private String contentJson;

    /**
     * 消息创建时间
     */
    @Field("create_time")
    private Long createTime;

    /**
     * 发送者类型
     */
    @Field("sender_type")
    private Integer senderType;

    /**
     * 发送者id
     */
    @Field("sender_id")
    private String senderId;

    /**
     * 发送者名称
     */
    @Field("sender_name")
    private String senderName;

    /**
     * 发送者头像
     */
    @Field("sender_head")
    private String senderHead;

    /**
     * 是否读取
     */
    @Field("sync_flag")
    @JsonIgnore
    private Boolean syncFlag=false;

    /**
     * 接收者Id
     */
    @Field("to_userId")
    @JsonIgnore
    private String toUserId;

}
