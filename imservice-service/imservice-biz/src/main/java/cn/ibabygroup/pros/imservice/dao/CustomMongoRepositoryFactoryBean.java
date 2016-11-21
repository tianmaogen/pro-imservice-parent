package cn.ibabygroup.pros.imservice.dao;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

/**
 * Description: 用于生成自扩展的Repository方法
 * Created by gumutianqi@gmail.com on 16/8/26.
 * since 1.0.0
 */
public class CustomMongoRepositoryFactoryBean<T extends MongoRepository<S, ID>, S, ID extends Serializable>
        extends MongoRepositoryFactoryBean<T, S, ID> {

    @Override
    protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
        return new CustomMongoRepository(operations);
    }

    private static class CustomMongoRepository<T, ID extends Serializable> extends MongoRepositoryFactory {
        private final MongoOperations operations;

        public CustomMongoRepository(MongoOperations operations) {
            super(operations);
            this.operations = operations;
        }

        @Override
        protected Object getTargetRepository(RepositoryInformation metadata) {
            Class<?> repositoryInterface = metadata.getRepositoryInterface();
            MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
            if (isQueryDslRepository(repositoryInterface)) {
                return new QueryDslMongoRepository(entityInformation, operations);
            } else {
                return new CustomMongoRepositoryImpl<>((MongoEntityInformation<T, ID>) entityInformation, this.operations);
            }
        }

        private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
            return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return isQueryDslRepository(metadata.getRepositoryInterface()) ? QueryDslMongoRepository.class
                    : CustomMongoRepositoryImpl.class;
        }
    }
}
