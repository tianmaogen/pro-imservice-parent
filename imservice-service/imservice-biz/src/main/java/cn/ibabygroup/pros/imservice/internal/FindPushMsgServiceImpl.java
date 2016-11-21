package cn.ibabygroup.pros.imservice.internal;

import cn.ibabygroup.pros.imservice.api.FindPushMsgService;
import cn.ibabygroup.pros.imservice.api.PushMsgService;
import cn.ibabygroup.pros.imservice.api.SendMsgService;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tianmaogen on 2016/9/25.
 */
@Service
public class FindPushMsgServiceImpl implements FindPushMsgService {

    @Autowired
    private PushSpeedMsgService pushSpeedMsgService;
    @Autowired
    private PushNoticeMsgService pushNoticeMsgService;

    @Override
    public PushMsgService find(ChatMessageReq chatMessageReq) {
        int elemType = chatMessageReq.getElemType().intValue();
        if(elemType == 2002) {
            return pushSpeedMsgService;
        }
        else if(elemType == 1011 || elemType == 1012) {
            return pushNoticeMsgService;
        }

        throw new IMException("非法的elemType"+elemType, chatMessageReq);
    }
}
