package cn.ibabygroup.pros.imserviceapi.dto.resp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianmaogen on 2016/9/5.
 * 返回的同步response
 */
@Data
public class SyncResp {
    private boolean endFlag=true;
    private List dataList=new ArrayList();
    private Long lastSyncMarker = 0L;
}
