package cn.ibabygroup.pros.imservice.model.IM;

import cn.ibabygroup.pros.imserviceapi.dto.req.AddGroupMemberReq;
import lombok.Data;

import java.util.List;

/**
 * Created by tianmaogen on 2016/9/26.
 */
@Data
public class QueueEModel {
    private List<String> accountList;
    private AddGroupMemberReq addGroupMemberReq;
}
