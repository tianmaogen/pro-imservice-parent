package cn.ibabygroup.pros.imservice;

import cn.ibabygroup.pros.imservice.utils.RESTUtil;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by tianmaogen on 2016/9/7.
 */
public class IOSArrivalRateTest {
    @Test
    public void testIOS() throws InterruptedException {
        String url = "http://localhost:8080/imservice/sendMsgFromApp";
        String userId = "e3df480295d54463aadf2420879257bd";
//        String userId = "test123";
        SendMsgReq sendMessageReq = new SendMsgReq();
        cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq msgBody = new cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq();
        msgBody.setElemType(2001);
        msgBody.setChatId("666");
        sendMessageReq.setMsgBody(msgBody);
        sendMessageReq.setToUserId(userId);

        int failCount = 0;
        int internetFailCount = 0;
        int sucCount = 0;
        StringBuilder failCountStr = new StringBuilder();
        StringBuilder internetFailCountStr = new StringBuilder();
        //每分钟发100条，执行100分钟
        for(int i=0;i<1;i++) {
            for(int j=0;j<50;j++) {
                //设置消息体的值
                int val = 100 * i + j + 1;
                msgBody.setContentJson("{"+String.valueOf(val)+"}");
                String uuid = UUID.randomUUID().toString();
                uuid = uuid.replace("-","");
                msgBody.setMsgId(uuid);
                String data;
                try {
                    IMJsonResponse response = RESTUtil.postForObject(url, sendMessageReq, IMJsonResponse.class, true);
                    data = response.getData().toString();
                }catch (Exception e) {
                    data = "0";
                    internetFailCount++;
                    internetFailCountStr.append(val).append("-");
                }

                if(data.equals("0") || data.equals("0L")) {
                    failCount++;
                    failCountStr.append(val).append("-");
                }
                else
                    sucCount++;
            }
//            Thread.sleep(60000);
            Thread.sleep(60);
        }
        System.out.println("成功执行了"+sucCount+"次");
        System.out.println("失败执行了"+failCount+"次");
        System.out.println("失败执行了"+internetFailCount+"次");
        System.out.println("internetFailCountStr========"+internetFailCountStr);
        System.out.println("failCountStr==========="+failCountStr);
    }
}
