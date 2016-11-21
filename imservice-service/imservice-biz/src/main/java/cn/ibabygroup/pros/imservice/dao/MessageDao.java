package cn.ibabygroup.pros.imservice.dao;

import cn.ibabygroup.pros.imservice.model.mongodb.IMMessage;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

/**
 * Description: 消息DAO
 * Created by gumutianqi@gmail.com on 16/8/25.
 * since 1.0.0
 */
@Repository
public interface MessageDao extends BaseMongoRepository<IMMessage, ObjectId> {

}
