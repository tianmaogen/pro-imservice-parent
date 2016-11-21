package cn.ibabygroup.pros.imservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
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
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * Description: MongoRepositoryImpl 抽象实现
 * Created by gumutianqi@gmail.com on 16/8/25.
 * since 1.0.0
 */
@NoRepositoryBean
public class CustomMongoRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements CustomMongoRepository<T, ID> {

    protected final MongoOperations operations;

    protected final MongoEntityInformation<T, ID> entityInformation;

    public CustomMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations operations) {
        super(metadata, operations);

        Assert.notNull(operations);
        Assert.notNull(metadata);

        this.operations = operations;
        this.entityInformation = metadata;
    }

    protected Class<T> getEntityClass() {
        return entityInformation.getJavaType();
    }

    @Override
    public BulkOperations initializeOrderedBulkOp() {
        return operations.bulkOps(BulkOperations.BulkMode.ORDERED, getEntityClass());
    }

    @Override
    public BulkOperations initializeUnorderedBulkOp() {
        return operations.bulkOps(BulkOperations.BulkMode.UNORDERED, getEntityClass());
    }

    @Override
    public Integer upsert(Query query, Update update) {
        return operations.upsert(query, update, getEntityClass()).getN();
    }

    @Override
    public Long count(Query query) {
        return operations.count(query, getEntityClass());
    }

    @Override
    public Boolean exists(Query query) {
        return operations.exists(query, getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return operations.findOne(query, getEntityClass());
    }

    @Override
    public List<T> findAll(Query query) {
        return operations.find(query, getEntityClass());
    }

    @Override
    public Page<T> findPage(Query query, Pageable pageable) {
        final long total = operations.count(query, getEntityClass());
        final List<T> list = operations.find(query.with(pageable), getEntityClass());
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public List<?> aggregate(Aggregation aggregation, Class<T> inputType, Class<?> outputType) {
        return operations.aggregate(aggregation, inputType, outputType).getMappedResults();
    }

    @Override
    public List<?> aggregate(TypedAggregation<?> aggregation, Class<?> outputType) {
        return operations.aggregate(aggregation, outputType).getMappedResults();
    }

    @Override
    public Integer updateFirst(Query query, Update update) {
        return operations.updateFirst(query, update, getEntityClass()).getN();
    }

    @Override
    public Integer updateMulti(Query query, Update update) {
        return operations.updateMulti(query, update, getEntityClass()).getN();
    }

    @Override
    public Integer remove(Query query) {
        return operations.remove(query, getEntityClass()).getN();
    }

    @Override
    public T findAndModify(Query query, Update update) {
        return operations.findAndModify(query, update, getEntityClass());
    }

    @Override
    public List<T> findAllAndModify(Query query, Update update) {
        List<T> datas = operations.find(query, getEntityClass());
        operations.updateMulti(query, update, getEntityClass());
        return datas;
    }

    @Override
    public T findAndRemove(Query query) {
        return operations.findAndRemove(query, getEntityClass());
    }

    @Override
    public List<T> findAllAndRemove(Query query) {
        return operations.findAllAndRemove(query, getEntityClass());
    }

    @Override
    public GroupByResults<T> group(String inputCollectionName, GroupBy groupBy) {
        return operations.group(inputCollectionName, groupBy, getEntityClass());
    }

    @Override
    public GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy) {
        return operations.group(criteria, inputCollectionName, groupBy, getEntityClass());
    }

//    @Override
//    public Integer bultInsert(List<T> entity) {
//        Integer count = 0;
//        BulkOperations bulk = this.initializeUnorderedBulkOp();
//        bulk.insert(entity);
//        try {
//            count = bulk.execute().getInsertedCount();
//        } catch (MongoException me) {
//            errorItems = getErrorItem(me.getMessage());
//            bulk = this.initializeUnorderedBulkOp();
//            bulk.insert(errorItems);
//            retry(bulk.execute());
//        }
//        return count;
//    }
//
//    private BulkWriteResult retry(BulkWriteResult result) {
//        if (result.getInsertedCount()) {
//
//        }
//    }

    @Override
    public MapReduceResults mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction) {
        return operations.mapReduce(query, inputCollectionName, mapFunction, reduceFunction, getEntityClass());
    }

    @Override
    public MapReduceResults mapReduce(Query query, String inputCollectionName, String mapFunction, String reduceFunction, MapReduceOptions mapReduceOptions) {
        return operations.mapReduce(query, inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, getEntityClass());
    }

    @Override
    public GeoResults<T> geoNear(NearQuery near) {
        return operations.geoNear(near, getEntityClass());
    }

}
