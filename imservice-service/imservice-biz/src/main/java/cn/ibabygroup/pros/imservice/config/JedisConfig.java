package cn.ibabygroup.pros.imservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * User: tianmaogen
 * Date: 2016/10/20
 */
@Configuration
public class JedisConfig {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${redis.pool.min-idle}")
    private Integer minIdle;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        return config;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig config) {
        JedisPool jedisPool = new JedisPool(config, host, port, 5000, password);
        return jedisPool;
    }
}
