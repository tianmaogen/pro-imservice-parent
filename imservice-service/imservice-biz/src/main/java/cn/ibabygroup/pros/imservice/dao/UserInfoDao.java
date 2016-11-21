package cn.ibabygroup.pros.imservice.dao;

import cn.ibabygroup.pros.imservice.model.mongodb.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户信息DAO
 * Created by gumutianqi@gmail.com on 16/8/25.
 * since 1.0.0
 */
@Repository
public interface UserInfoDao extends BaseMongoRepository<UserInfo, ObjectId> {

}
