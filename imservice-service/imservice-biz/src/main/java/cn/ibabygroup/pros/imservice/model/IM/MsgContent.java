package cn.ibabygroup.pros.imservice.model.IM;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/17.
 */
@Data
public class MsgContent {
    private String Data;
    private String Desc;
    private String Ext;
    private String Sound;
}
