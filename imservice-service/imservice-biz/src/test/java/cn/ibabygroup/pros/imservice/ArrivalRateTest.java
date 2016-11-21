package cn.ibabygroup.pros.imservice;

import cn.ibabygroup.pros.imserviceapi.dto.req.ChatMessageReq;
import cn.ibabygroup.pros.imservice.utils.RESTUtil;
import org.junit.Test;

import java.util.UUID;

public class ArrivalRateTest {

    @Test
    public void test() throws InterruptedException {
        String url = "http://localhost:4021/v1/imservice/sendMsgFromApp";
        String userId = "test123";
        SendMsgReq sendMessageReq = new SendMsgReq();
        ChatMessageReq msgBody = new ChatMessageReq();
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
        for(int i=0;i<100;i++) {
            for(int j=0;j<1000;j++) {
                //设置消息体的值
                int val = 100 * i + j + 1;
                msgBody.setContentJson("{"+String.valueOf(val)+"}");
                msgBody.setMsgId(String.valueOf(val));
                String data;
                try {
                    data = RESTUtil.postForObject(url, sendMessageReq, String.class, true);
                }catch (Exception e) {
                    e.printStackTrace();
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
//            Thread.sleep(60);
        }
        System.out.println("成功执行了"+sucCount+"次");
        System.out.println("失败执行了"+failCount+"次");
        System.out.println("失败执行了"+internetFailCount+"次");
        System.out.println("internetFailCountStr========"+internetFailCountStr);
        System.out.println("failCountStr==========="+failCountStr);
    }

}
