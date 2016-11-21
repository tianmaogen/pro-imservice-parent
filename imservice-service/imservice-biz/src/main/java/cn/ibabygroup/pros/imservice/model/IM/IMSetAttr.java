package cn.ibabygroup.pros.imservice.model.IM;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置用户属性实体
 * 2016/9/7.
 * tianmaogen
 */
@Data
public class IMSetAttr {

    private UserAttr[] userAttrs;

    @Data
    public static class UserAttr {
        private String to_Account;
        private Map<String, String> attrs;
    }

//    @Data
//    public static class Attr {
//        private String id;
//        private String
//    }

    public static IMSetAttr getIMSetAttr(List<String> userIds, String attrName, String val) {
        UserAttr[] userAttrs = new UserAttr[userIds.size()];
        for(int i=0;i<userIds.size();i++) {
            Map<String, String> attr = new HashMap<>();
            attr.put(attrName,val);
            UserAttr userAttr = new UserAttr();
            userAttr.setAttrs(attr);
            userAttr.setTo_Account(userIds.get(i));
            userAttrs[i] = userAttr;
        }
        IMSetAttr iMSetAttr = new IMSetAttr();
        iMSetAttr.setUserAttrs(userAttrs);
        return iMSetAttr;
    }
}

