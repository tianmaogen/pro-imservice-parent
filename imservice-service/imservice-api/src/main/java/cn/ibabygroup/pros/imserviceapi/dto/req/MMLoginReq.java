package cn.ibabygroup.pros.imserviceapi.dto.req;

import lombok.Data;
import org.springframework.http.HttpHeaders;

/**
 * Created by tianmaogen on 2016/9/12.
 */
@Data
public class MMLoginReq {
    private UserLoginReq userLoginReq;
    private HttpHeaders headers;
}
