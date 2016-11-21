package cn.ibabygroup.pros.imserviceapi.dto.req;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by tianmaogen on 2016/9/5.
 * 数据同步请求体
 */
@Data
public class SyncReq {
    //最后一次同步时间戳，第一次传0
    private Long lastSyncMarker=0L;
    //用户id
    private String userId;
}
