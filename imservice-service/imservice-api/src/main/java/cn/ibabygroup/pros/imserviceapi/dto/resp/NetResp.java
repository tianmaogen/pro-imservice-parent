package cn.ibabygroup.pros.imserviceapi.dto.resp;

import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/22.
 * 调用.net接口返回的实体
 */
@Data
public class NetResp {
    private String Msg;
    private String Success;
    private UserInfoResp Data;
}
