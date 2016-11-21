package cn.ibabygroup.pro.server;

import cn.ibabygroup.pros.imservice.dao.CustomMongoRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by tianmaogen on 2016/9/6.
 */
@EnableFeignClients(basePackages = {"cn.ibabygroup.apps.uac.api"})
@SpringBootApplication(scanBasePackages = {"cn.ibabygroup.pros.imservice"})
@EnableMongoRepositories(repositoryFactoryBeanClass = CustomMongoRepositoryFactoryBean.class,
        basePackages = "cn.ibabygroup.pros.imservice.dao")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class //禁止springboot自动加载持久化bean
})
public class IMServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class, args);
    }
}
