package cn.ibabygroup.pros.imservice.model.IM;

import lombok.Data;

import java.util.List;

/**
 * Created by tianmaogen on 2016/11/2.
 */
@Data
public class QueryStateResponse extends IMResponse{

    public static final String OnlineState = "Online";
    public static final String OfflineState = "Offline";

    //导入失败的帐号列表
    private List<QueryResult> queryResult;

    @Data
    public static class QueryResult {
        private String to_Account;
        private String state="Online";
    }
}
