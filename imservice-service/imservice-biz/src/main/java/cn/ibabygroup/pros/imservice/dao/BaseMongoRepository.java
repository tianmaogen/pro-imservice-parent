package cn.ibabygroup.pros.imservice.dao;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.Set;

/**
 * Description: 不需要自己实现的Mongo调用
 * Created by gumutianqi@gmail.com on 16/8/25.
 * since 1.0.0
 */
@NoRepositoryBean
public interface BaseMongoRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>, CustomMongoRepository<T, ID> {
    /**
     * 按ID批量查询
     *
     * @param ids
     * @return
     */
    Iterable<T> findByIdIn(Set<ObjectId> ids);

    Iterable<T> findByIdIn(Set<ObjectId> ids, Sort sort);
}
