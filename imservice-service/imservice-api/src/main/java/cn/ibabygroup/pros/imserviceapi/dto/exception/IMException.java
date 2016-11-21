package cn.ibabygroup.pros.imserviceapi.dto.exception;

import cn.ibabygroup.commons.exception.ProsScopeException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tianmaogen on 2016/9/2.
 */
@Slf4j
public class IMException extends ProsScopeException {
    private static final int DEFAULT_ERROR_CODE = 402000;

    public IMException(String message) {
        this( message, null);
    }

    public IMException(String message,Object data) {
        super(DEFAULT_ERROR_CODE, message, data, null);
        if(data != null)
            log.error(message+"data===={}", data.toString());
        else
            log.error(message);
    }
}
