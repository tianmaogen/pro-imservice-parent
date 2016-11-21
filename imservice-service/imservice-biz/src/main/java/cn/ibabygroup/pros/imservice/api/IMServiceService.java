package cn.ibabygroup.pros.imservice.api;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.SendSpeedMsgReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.SyncReq;
import cn.ibabygroup.pros.imserviceapi.dto.resp.SyncResp;

/**
 * User: tianmaogen
 * Date: 2016/8/10
 */
//@Api(value = "imServiceService", description = "根据elemType找到对应的sendMsgService，并且根据来源(app或者service)做验证", protocols = "application/json")
//@FeignClient(name = "imservice-server-${spring.profiles.active}")
//@RequestMapping("/imServiceService")
public interface IMServiceService {

//    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    SyncResp sync(SyncReq syncReq);

//    @RequestMapping(value = "/getDesc", method = RequestMethod.POST)
    String getDesc(ChatMessageReq chatMessageReq);

//    @RequestMapping(value = "/getExt", method = RequestMethod.POST)
    String getExt(Integer elemType, String chatId);

    /**
     * 验证 elemType为2000~3000的1对1消息
     */
//    @RequestMapping(value = "/verificationOMgs", method = RequestMethod.POST)
    Boolean verificationOMgs(ChatMessageReq chatMessageReq, String toUserId);
}
