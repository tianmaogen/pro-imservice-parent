package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.pros.imservice.api.FindAndVerifySendMsgService;
import cn.ibabygroup.pros.imservice.api.IMServiceService;
import cn.ibabygroup.pros.imservice.api.SendMsgService;
import cn.ibabygroup.pros.imservice.utils.JSONUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imservice.model.rest.RESTResponse;
import cn.ibabygroup.pros.imservice.service.RESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tianmaogen on 2016/9/1.
 */
@Service
public class FindAndVerifySendMsgServiceImpl implements FindAndVerifySendMsgService {
    @Autowired
    private SendGMsgServiceImpl sendGMsgService;
    @Autowired
    private SendMsgServiceImpl sendMsgService;
    @Autowired
    private IMServiceService imServiceService;
    @Autowired
    private SendOtherMsgServiceImpl sendOtherMsgService;
    @Autowired
    private RESTService restService;

    @Override
    public SendMsgService findSendMsgService(ChatMessageReq chatMessageReq) {
        int elemType = chatMessageReq.getElemType().intValue();
        //群消息
        if(elemType>1000 && elemType<2000) {
            return sendGMsgService;
        }
        else if(elemType>2000 && elemType<3000) {
            return sendMsgService;
        }
        else if(elemType>3000 && elemType<4000) {
            return sendOtherMsgService;
        }
        throw new IMException("elemType为"+elemType+"不合法。", JSONUtil.toCamelCaseJSONString(chatMessageReq));
    }

    @Override
    public boolean verifyMsg(ChatMessageReq chatMessageReq, String toUserId) {
        int elemType = chatMessageReq.getElemType().intValue();
        //群消息
        if(elemType == 1001) {
            RESTResponse restResponse = restService.checkChat(chatMessageReq.getSenderId(), toUserId);
            if(restResponse.getCode().intValue() == restResponse.okCode)
                return true;
            return false;
        }
        //问诊类消息
        else if(elemType>2000 && elemType<3000)
            return imServiceService.verificationOMgs(chatMessageReq, toUserId);
        return true;
    }
}
