package cn.ibabygroup.pros.imserviceapi.api;

/**
 * Created by tianmaogen on 2016/9/22.
 */
public interface AppConstants {

    String APP_SERVICE_NAME = "pro-imservice-server-${spring.profiles.active}";

    String API_SERVER_PRIFEX = "/server/im";

    String API_WEBAPI_PRIFEX = "/cloud/im";

}
