package cn.ibabygroup.pros.imservice.api;

import cn.ibabygroup.pros.imserviceapi.dto.req.AddGroupMemberReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.CreateRoomReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.DelGroupMemberReq;

/**
 * User: tianmaogen
 * Date: 2016/8/10
 */

//@FeignClient(name = "imservice-server-${spring.profiles.active}")
//@RequestMapping("/roomService")
public interface RoomService {

    //    @RequestMapping(value = "/createRoom", method = RequestMethod.POST)
    Boolean createRoom(CreateRoomReq createRoomReq);

    //    @RequestMapping(value = "/delRoom", method = RequestMethod.POST)
    Boolean delRoom(String groupId);

    //    @RequestMapping(value = "/addGroupMember", method = RequestMethod.POST)
    Boolean addGroupMember(AddGroupMemberReq addGroupMemberReq);

    //    @RequestMapping(value = "/delGroupMember", method = RequestMethod.POST)
    Boolean delGroupMember(DelGroupMemberReq delGroupMemberReq);

    Boolean importMember(String groupId, String memberId);

    Boolean removeMember(String groupId, String memberId);
}
