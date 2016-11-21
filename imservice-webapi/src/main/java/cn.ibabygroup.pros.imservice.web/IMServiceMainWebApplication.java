package cn.ibabygroup.pros.imservice.web;

import cn.ibabygroup.pros.imservice.dao.CustomMongoRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by tianmaogen
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"cn.ibabygroup.apps", "cn.ibabygroup.apps.uac.api"})
@EnableMongoRepositories(repositoryFactoryBeanClass = CustomMongoRepositoryFactoryBean.class, basePackages = "cn.ibabygroup.pros.imservice.dao")
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class//禁止springboot自动加载持久化bean
})
@ComponentScan(basePackages = {"cn.ibabygroup.pros.imservice"})
public class IMServiceMainWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMServiceMainWebApplication.class, args);
    }
}
