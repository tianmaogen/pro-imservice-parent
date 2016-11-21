package cn.ibabygroup.pros.imservice.model.IM;

import lombok.Data;

import java.util.List;

/**
 * Created by tianmaogen on 2016/9/19.
 */
@Data
public class ImprotAccountsResponse extends IMResponse{
    //导入失败的帐号列表
    private List<String> failAccounts;
}
