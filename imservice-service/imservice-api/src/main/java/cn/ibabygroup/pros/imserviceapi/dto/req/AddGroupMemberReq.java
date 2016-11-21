package cn.ibabygroup.pros.imserviceapi.dto.req;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianmaogen on 2016/8/30.
 * 添加群成员请求实体
 */
@Data
public class AddGroupMemberReq {
    private String groupId;
    private Member[] memberList;

    @Data
    public static class Member {
        private String member_Account;
    }

    public static AddGroupMemberReq getAddGroupMemberReq(String groupId, List<String> userList) {
        AddGroupMemberReq addGroupMemberReq = new AddGroupMemberReq();
        addGroupMemberReq.setGroupId(groupId);
        Member[] memberList = new Member[userList.size()];
        int i=0;
        for(String account : userList) {
            Member member = new Member();
            member.setMember_Account(account);
            memberList[i++] = member;
        }
        addGroupMemberReq.setMemberList(memberList);
        return addGroupMemberReq;
    }

    public static AddGroupMemberReq getAddGroupMemberReq(String groupId, String userId) {
        List<String> userList = new ArrayList<>();
        userList.add(userId);
        return getAddGroupMemberReq(groupId, userList);
    }
}
