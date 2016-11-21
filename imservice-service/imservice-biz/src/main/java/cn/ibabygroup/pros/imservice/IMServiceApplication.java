package cn.ibabygroup.pros.imservice;

import cn.ibabygroup.pros.imservice.dao.CustomMongoRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created with IntelliJ IDEA. User: Crowhyc Date: 2016/8/8 Time: 16:07
 */
//@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableMongoRepositories(repositoryFactoryBeanClass = CustomMongoRepositoryFactoryBean.class)
public class IMServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMServiceApplication.class, args);
    }
}
