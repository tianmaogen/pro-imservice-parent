package cn.ibabygroup.pros.imservice.model.rest;

import lombok.Data;

/**
 * Created by tianmaogen on 2016/9/19.
 */
@Data
public class AllPulpitsResp {
    //总数量
    private int totalCount;
    private Datas[] dataList;

    @Data
    public static class Datas {
        private String id;
        private String title;
        private int memberLimit;
        private String teacherId;//医生id
        private String managerId;//小助手id
    }
}
