package cn.ibabygroup.pros.imservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 扩展的MongoRepository的复杂查询实现
 * Created by gumutianqi@gmail.com on 16/8/25.
 * since 1.0.0
 */
@NoRepositoryBean
public interface CustomMongoRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

    /**
     * 序列化地执行，若Bulk中的某个操作写入失败，不再执行后面的操作
     */
    BulkOperations initializeOrderedBulkOp();

    /**
     * 并行化地执行，若Bulk中的某个操作写入失败，不影响其它的操作
     */
    BulkOperations initializeUnorderedBulkOp();

    Integer upsert(Query query, Update update);

    Long count(Query query);

    Boolean exists(Query query);

    T findOne(Query query);

    List<T> findAll(Query query);

    Page<T> findPage(Query query, Pageable pageable);

    List<?> aggregate(Aggregation aggregation, Class<T> inputType, Class<?> outputType);

    List<?> aggregate(TypedAggregation<?> aggregation, Class<?> outputType);

    Integer updateFirst(Query query, Update update);

    Integer updateMulti(Query query, Update update);

    Integer remove(Query query);

    T findAndModify(Query query, Update update);

    List<T> findAllAndModify(Query query, Update update);

    T findAndRemove(Query query);

    List<T> findAllAndRemove(Query query);

    GroupByResults<T> group(String inputCollectionName, GroupBy groupBy);

    GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy);

    MapReduceResults mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction);

    MapReduceResults mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions);

    GeoResults<T> geoNear(NearQuery near);
}
