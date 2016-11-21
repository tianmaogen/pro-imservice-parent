package cn.ibabygroup.pros.imservice.model.IM;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 创建讲坛实体
 * 2016/8/30.
 * tianmaogen
 */
@Data
public class IMRoom {

    //群名称，最长30字节。
    private String name;

    //群简介，最长240字节。
    private String introduction;

    //最大群成员数量，最大为10000，不填默认为2000个。
    private Integer maxMemberCount;

    //自定义群组ID。
    private String groupId;

//    //群主id
//    private String owner_Account;

    //群组类型：Private/Public/ChatRoom/AVChatRoom
    private String type="ChatRoom";

    private Member[] memberList;

    @Data
    @AllArgsConstructor
    public static class Member {
        public static final String ADMIN_ROLE="Admin";
        private String member_Account;
        private String role;
    }
}

