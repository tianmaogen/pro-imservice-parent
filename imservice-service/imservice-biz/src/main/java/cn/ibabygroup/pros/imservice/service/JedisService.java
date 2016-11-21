package cn.ibabygroup.pros.imservice.service;

import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * User: tianmaogen
 * Date: 2016/10/20
 */
@Service
@Slf4j
public class JedisService {
    //版本缓存前缀
    public static final String versionKeyPre = "VERSION";
    //讲坛成员前缀
    public static final String courceMemberKeyPre = "COURCEMEMBER";
    @Autowired
    private JedisPool jedisPool;
    @Value("${redis.database}")
    private Integer database;
    //设置key的默认过期时间为2小时
    private static final int expireTime = 60 * 60 * 2;

    /**
     * 设置缓存
     * @param key
     * @param val
     */
    public boolean set(String key, String val) {
        try (Jedis jedis = getJedis()) {
            String result = jedis.set(key, val);
            if(result.equals("OK")) {
                jedis.expire(key, expireTime);
                return true;
            }
            else {
                log.error("jedis调用set方法失败:key={},val={}", key, val);
                return false;
            }

        } catch (Exception e) {
            log.error("jedis使用失败", e.getMessage(), e);
        }
        return true;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public String get(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        } catch (Exception e) {
            log.error("jedis使用失败", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 设置set类型的缓存
     * @param key
     * @param val
     * @return
     */
    public boolean sadd(String key, String val) {
        try (Jedis jedis = getJedis()) {
            jedis.sadd(key, val);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            log.error("jedis==========>sadd使用失败", e.getMessage(), e);
        }
        return true;
    }

    /**
     * 返回名称为key的set的所有元素
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        try (Jedis jedis = getJedis()) {
            Set<String> set = jedis.smembers(key);
            return set;
        } catch (Exception e) {
            log.error("jedis==========>srem使用失败", e.getMessage(), e);
        }
        return null;
    }
    /**
     * 清楚set类型某一主键key的缓存
     * @param key
     * @param val
     * @return
     */
    public boolean srem(String key, String val) {
        try (Jedis jedis = getJedis()) {
            Long srem = jedis.srem(key, val);
            System.out.println(srem);

        } catch (Exception e) {
            log.error("jedis==========>srem使用失败", e.getMessage(), e);
        }
        return true;
    }


    /**
     * 清除键下所有数据
     *
     * @param key 键
     * @return 是否成功
     */

    public boolean cleanKey(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.del(key) > 0;
        } catch (Exception e) {
            log.error("jedis使用失败", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取jedis链接
     *
     * @return jedis链接
     */

    public Jedis getJedis() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            Jedis jedis = jedisPool.getResource();
            jedis.select(database);
            return jedis;
        }
        throw new IMException("获取redis链接失败");
    }

}
