package cn.ibabygroup.pros.imservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * 配置文件config.properties
 *
 * @author tianmaogen
 */
public class ConfigUtils {
    private static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);
    private static YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
    //分别修改 profiles文件夹中的dev,prod,test来指定开发，产品，测试环境
    public static final String CONFIG_NAME = "application-dev.yml";

    static {
        try {
            yaml.setResources(new ClassPathResource(CONFIG_NAME));
        } catch (Exception e) {
            log.error("载入配置文件异常，" + e.getMessage(), e);
        }
    }

    /**
     * 获得配置文件中指定key对应的value，如果没有此key则返回空字符
     */
    public static String getString(String key) {
        return getString(key, "");
    }

    public static String appendStringByKeys(String... keys) {
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(getString(key, ""));

        }
        return sb.toString();
    }

    /**
     * 获得配置文件中指定key对应的value，如果没有此key则返回指定的默认值
     */
    public static String getString(String key, String defaultValue) {
        try {
            String val = yaml.getObject().getProperty(key);
            if(null == val){
                log.error("key={}找不到",key);
                return defaultValue;
            }
            return val;
        } catch (Exception e) {
            log.error("获取配置文件属性异常，" + e.getMessage(), e);
        }
        return defaultValue;
    }

//    /**
//     * 获得配置文件中指定key对应的long型value，如果没有此key则返回-1
//     */
//    public static long getLong(String key) {
//        return getLong(key, -1);
//    }
//
//    /**
//     * 获得配置文件中指定key对应的long型value，如果没有此key则返回指定的默认值
//     */
//    public static long getLong(String key, long defaultValue) {
//        long result = defaultValue;
//        try {
//            String value = pro.getProperty(key, result + "");
//            result = value.matches("-?\\d+") ? Long.parseLong(value) : result;
//        } catch (Exception e) {
//            log.error("获取配置文件属性异常，" + e.getMessage(), e);
//        }
//        return result;
//    }

//    /**
//     * 获得配置文件中指定key对应的int型value，如果没有此key则返回-1
//     */
//    public static int getInt(String key) {
//        return getInt(key, -1);
//    }

//    /**
//     * 获得配置文件中指定key对应的int型value，如果没有此key则返回指定的默认值
//     */
//    public static int getInt(String key, int defaultValue) {
//        int result = defaultValue;
//        try {
//            String value = pro.getProperty(key, result + "");
//            result = value.matches("-?\\d+") ? Integer.parseInt(value) : result;
//        } catch (Exception e) {
//            log.error("获取配置文件属性异常，" + e.getMessage(), e);
//        }
//        return result;
//    }

    public static void main(String[] args) {
        System.out.println("javapns.path=" + ConfigUtils.getString("spring.netURL"));
    }
}

