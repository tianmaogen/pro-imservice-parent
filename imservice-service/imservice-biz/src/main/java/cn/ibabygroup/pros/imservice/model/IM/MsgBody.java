package cn.ibabygroup.pros.imservice.model.IM;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by tianmaogen on 2016/8/17.
 */
@Data
public class MsgBody {
    private String MsgType="TIMCustomElem";
    private MsgContent MsgContent;
}
