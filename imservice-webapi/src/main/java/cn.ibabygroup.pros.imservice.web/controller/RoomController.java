package cn.ibabygroup.pros.imservice.web.controller;

import cn.ibabygroup.commons.context.RequestContextHolder;
import cn.ibabygroup.pros.imservice.api.RoomService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imserviceapi.api.AppConstants;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.AddGroupMemberReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.CreateRoomReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.DelGroupMemberReq;
import cn.ibabygroup.pros.imservice.web.resp.IMJsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tianmaogen on 16/8/11.
 */
@RestController
@RequestMapping(AppConstants.API_WEBAPI_PRIFEX+"/v1/room")
@Api(value = "群聊操作", description = "对群聊房间的操作，包括创建房间，删除房间，添加成员，删除成员", position = 1)
@Slf4j
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/createRoom", method = RequestMethod.POST)
    @ApiOperation(value = "创建房间", notes = "创建房间")
    @ResponseBody
    public Boolean createRoom(@ApiParam(value = "创建房间实体", required = true)
                                     @RequestBody CreateRoomReq createRoomReq) {
        log.info("createRoom=====>请求实体为:{}", JSONUtil.toCamelCaseJSONString(createRoomReq));
        return roomService.createRoom(createRoomReq);
    }

    @RequestMapping(value="/{groupId}/delRoom", method = RequestMethod.GET)
    @ApiOperation(value = "删除房间", notes = "删除房间")
    @ResponseBody
    public Boolean delRoom(@ApiParam(value = "房间号", required = true)
                                     @PathVariable String groupId) {
        log.info("delRoom=====>房间号为:{}", groupId);
        return roomService.delRoom(groupId);
    }

    @RequestMapping(value="/{groupId}/addGroupMember", method = RequestMethod.POST)
    @ApiOperation(value = "添加群成员", notes = "添加群成员")
    @ResponseBody
    public Boolean addGroupMember(@ApiParam(value = "房间号", required = true)
                                         @PathVariable String groupId,
                                        @ApiParam(value = "群成员数组", required = true)
                                        @RequestBody AddGroupMemberReq.Member[] memberList) {
        log.info("addGroupMember=====>房间号为:{}，成员数组参数为:{}", groupId, JSONUtil.toCamelCaseJSONString(memberList));
        AddGroupMemberReq addGroupMemberReq = new AddGroupMemberReq();
        addGroupMemberReq.setGroupId(groupId);
        addGroupMemberReq.setMemberList(memberList);
        return roomService.addGroupMember(addGroupMemberReq);
    }

    @RequestMapping(value="/importMember/{groupId}/{memberId}", method = RequestMethod.POST)
    @ApiOperation(value = "导入单个群成员", notes = "导入单个群成员")
    @ResponseBody
    public Boolean importMember(@ApiParam(value = "房间号", required = true)
                                  @PathVariable String groupId,
                                @ApiParam(value = "用户id", required = true)
                                @PathVariable String memberId) {
        log.info("importMember=====>房间号为:{}，memberId为:{}", groupId, memberId);
        return roomService.importMember(groupId, memberId);
    }

    @RequestMapping(value="/removeMember/{groupId}/{memberId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "在缓存中删除单个群成员", notes = "当群成员退出房间时，删除该成员")
    @ResponseBody
    public Boolean removeMember(@ApiParam(value = "房间号", required = true)
                                @PathVariable String groupId,
                                @ApiParam(value = "用户id", required = true)
                                @PathVariable String memberId) {
        memberId = RequestContextHolder.getUserId();
        log.info("removeMember=====>房间号为:{}，memberId为:{}", groupId, memberId);
        return roomService.removeMember(groupId, memberId);
//        return roomService.importMember(groupId, memberId);
    }

//    @RequestMapping(value="/{groupId}/delGroupMember", method = RequestMethod.POST)
//    @ApiOperation(value = "删除群成员", notes = "删除群成员")
//    @ResponseBody
//    public Boolean delGroupMember(@ApiParam(value = "房间号", required = true)
//                                         @PathVariable String groupId,
//                                         @ApiParam(value = "群成员数组", required = true)
//                                         @RequestBody String[] delMemberArr) {
//        log.info("delGroupMember=====>房间号为:{}，成员数组参数为", groupId, JSONUtil.toCamelCaseJSONString(delMemberArr));
//        DelGroupMemberReq delGroupMemberReq = new DelGroupMemberReq();
//        delGroupMemberReq.setGroupId(groupId);
//        delGroupMemberReq.setMemberToDel_Account(delMemberArr);
//        return roomService.delGroupMember(delGroupMemberReq);
//    }

}
