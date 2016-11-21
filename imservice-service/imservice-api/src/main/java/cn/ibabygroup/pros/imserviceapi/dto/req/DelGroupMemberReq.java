package cn.ibabygroup.pros.imserviceapi.dto.req;

import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/30.
 * 删除群成员请求实体
 */
@Data
public class DelGroupMemberReq {
    private String groupId;
    private String[] memberToDel_Account;
}
