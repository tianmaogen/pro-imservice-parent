package cn.ibabygroup.pros.imservice.model.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by tianmaogen on 2016/8/22.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(collection = "user_info")
public class UserInfo {
    @Id
    private String id;
    /**
     * 用户Id
     */
    @Field("user_id")
    private String userId;
    /**
     * 用户签名
     */
    @Field("sign")
    private String sign;
}
