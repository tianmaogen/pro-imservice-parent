package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.pros.imservice.api.RoomService;
import cn.ibabygroup.pros.imservice.config.RabbitMQConfig;
import cn.ibabygroup.pros.imservice.model.IM.IMURL;
import cn.ibabygroup.pros.imservice.model.IM.QueueEModel;
import cn.ibabygroup.pros.imservice.model.rest.RESTResponse;
import cn.ibabygroup.pros.imservice.service.JedisService;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imservice.utils.MQUtil;
import cn.ibabygroup.pros.imservice.utils.Utils;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.AddGroupMemberReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.CreateRoomReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.DelGroupMemberReq;
import cn.ibabygroup.pros.imservice.model.IM.IMResponse;
import cn.ibabygroup.pros.imservice.model.IM.IMRoom;
import cn.ibabygroup.pros.imservice.service.TXService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianmaogen
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private TXService tXService;
    @Autowired
    private IMURL imURL;
    @Autowired
    private RESTService restService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JedisService jedisService;

    @Override
    public Boolean createRoom(CreateRoomReq createRoomReq) {
        //添加群成员的时候先将所有账号导入到IM云，这是为了防止老版本的用户升级到新版本后课堂信息里不存在该成员，上课会有问题
        List<String> accountList = new ArrayList<>();
        accountList.add(createRoomReq.getDoctorId());
        accountList.add(createRoomReq.getAssistantId());
        Boolean importAccountsFlag = tXService.batchImportAccounts(accountList);
        if(!importAccountsFlag)
            return false;

        IMRoom iMRoom = new IMRoom();
        BeanUtils.copyProperties(createRoomReq, iMRoom);
        IMRoom.Member member1 = new IMRoom.Member(createRoomReq.getDoctorId(), IMRoom.Member.ADMIN_ROLE);
        IMRoom.Member member2 = new IMRoom.Member(createRoomReq.getAssistantId(), IMRoom.Member.ADMIN_ROLE);
        IMRoom.Member member3 = new IMRoom.Member(imURL.getIdentifier(), IMRoom.Member.ADMIN_ROLE);
        iMRoom.setMemberList(new IMRoom.Member[]{member1, member2, member3});
        iMRoom.setMaxMemberCount(10000);
        String name = iMRoom.getName();
        if(name.getBytes().length > 30)
            iMRoom.setName(Utils.subStringByByte(name, 28));
        IMResponse response = tXService.createRoom(iMRoom);
        if(response.getActionStatus().equals(response.actionStatusFAIL))
            throw new IMException("创建房间调用失败!错误信息为:{}"+response.getErrorInfo(), JSONUtil.toCamelCaseJSONString(response));
        return true;
    }

    @Override
    public Boolean delRoom(String groupId) {
        IMResponse response = tXService.delRoom(groupId);
        if(response.getActionStatus().equals(response.actionStatusFAIL))
            throw new IMException(response.getErrorInfo());
        return true;
    }

    @Override
    public Boolean addGroupMember(AddGroupMemberReq addGroupMemberReq) {
        //添加群成员的时候先将所有账号导入到IM云，这是为了防止老版本的用户升级到新版本后课堂信息里不存在该信息，上课会有问题
        List<String> accountList = new ArrayList<>();
        AddGroupMemberReq.Member[] memberList = addGroupMemberReq.getMemberList();
        for(AddGroupMemberReq.Member member : memberList)
            accountList.add(member.getMember_Account());

        QueueEModel queueEModel = new QueueEModel();
        queueEModel.setAccountList(accountList);
        queueEModel.setAddGroupMemberReq(addGroupMemberReq);
        rabbitTemplate.send(RabbitMQConfig.queueEName, MQUtil.getMessage(queueEModel));
//        tXService.batchImportAccounts(accountList);
//        IMResponse response = tXService.addGroupMember(addGroupMemberReq);
//        if(response.getActionStatus().equals(response.actionStatusFAIL))
//            throw new IMException("调用IM云服务添加成员失败!错误信息为:"+response.getErrorInfo(), JSONUtil.toCamelCaseJSONString(response));
        return true;
    }

    @Override
    public Boolean delGroupMember(DelGroupMemberReq delGroupMemberReq) {
        IMResponse response = tXService.delGroupMember(delGroupMemberReq);
        if(response.getActionStatus() != response.actionStatusOK)
            throw new IMException("调用IM云服务删除成员失败!错误信息为:"+response.getErrorInfo(), JSONUtil.toCamelCaseJSONString(response));
        return true;
    }

    @Override
    public Boolean importMember(String groupId, String memberId) {
        RESTResponse restResponse = restService.checkChat(memberId, groupId);
        if(restResponse.getCode().intValue() != restResponse.okCode)
            return false;
        //异步添加群成员
        AddGroupMemberReq addGroupMemberReq = AddGroupMemberReq.getAddGroupMemberReq(groupId, memberId);
        addGroupMember(addGroupMemberReq);

        //在redis中写入缓存
        String key = JedisService.courceMemberKeyPre+groupId;
        jedisService.sadd(key, memberId);
        return true;
    }

    @Override
    public Boolean removeMember(String groupId, String memberId) {
        //在redis中写入缓存
        String key = JedisService.courceMemberKeyPre+groupId;
        jedisService.srem(key, memberId);
        return true;
    }

    public static void main(String[] args) {
        System.out.println("医生版视频直播测试-IOS".getBytes().length);
        System.out.println("123456789011".substring(0,10).getBytes().length);
    }

}
