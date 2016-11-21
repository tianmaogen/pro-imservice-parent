package cn.ibabygroup.pros.imservice.course;

import cn.ibabygroup.pros.imservice.TestApplication;
import cn.ibabygroup.pros.imservice.api.IMUserService;
import cn.ibabygroup.pros.imservice.api.RoomService;
import cn.ibabygroup.pros.imservice.service.RESTService;
import cn.ibabygroup.pros.imservice.service.TXService;
import cn.ibabygroup.pros.imservice.utils.CSVUtil;
import cn.ibabygroup.pros.imserviceapi.dto.exception.IMException;
import cn.ibabygroup.pros.imserviceapi.dto.req.AddGroupMemberReq;
import cn.ibabygroup.pros.imserviceapi.dto.req.CreateRoomReq;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ImportCource {

    @Autowired
    private RESTService restService;
    @Autowired
    private TXService txService;
    @Autowired
    private IMUserService imUserService;
    @Autowired
    private RoomService roomService;

    @Test
    public void creatCrouse() throws InterruptedException {
        JSONArray allPulpits = restService.getAllPulpits();
        int failCount = 0;
        System.out.println("总的讲坛数量为:" + allPulpits.size());
        for(int i=0; i<allPulpits.size(); i++) {
            System.out.println("开始导入第"+i+"个讲坛。。。。");
            JSONObject bean = (JSONObject) allPulpits.get(i);
            String title = bean.getString("title");
            String teacherId = bean.getString("teacherId");
            String managerId = bean.getString("managerId");
            Integer memberLimit = bean.getInteger("memberLimit");
            String id = bean.getString("id");
            CreateRoomReq createRoomReq = new CreateRoomReq();
            createRoomReq.setGroupId(id);
            createRoomReq.setAssistantId(managerId);
            createRoomReq.setDoctorId(teacherId);
            createRoomReq.setIntroduction(title);
            createRoomReq.setName(title);
            createRoomReq.setMaxMemberCount(10000);
            //在IM上创建房间
            try {
                Boolean createFlage = roomService.createRoom(createRoomReq);
                if(!createFlage)
                    failCount++;
            }catch (IMException e) {
                System.out.println(e.getMessage());
            }

            List<String> userListByGroupId = restService.getUserListByGroupId(id, 10000);
            System.out.println(userListByGroupId);
            if(userListByGroupId.size() > 0) {
                //导入房间的成员
                if(userListByGroupId != null && userListByGroupId.size()>0) {
                    AddGroupMemberReq addGroupMemberReq = AddGroupMemberReq.getAddGroupMemberReq(id, userListByGroupId);
                    try {
                        roomService.addGroupMember(addGroupMemberReq);
                    }catch (IMException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            //暂停1秒钟
            Thread.sleep(1000);
        }
        System.out.println("总的测试次数是:"+allPulpits);
        System.out.println("失败的次数是:"+failCount);
        System.out.println(allPulpits);
    }

//    @Test
    public void importDoctor() throws Exception {
//        System.getProperty("user.dir");
        List<String> stringList = CSVUtil.readIds("src\\test\\resources\\doc.csv");
        //导入医生账号
//        txService.batchImportAccounts(stringList);
        System.out.println("用户账号的数量为" + stringList.size());
        //设置医生的推送属性
        for(int i=0;i< stringList.size();i++) {
            if(i%50 == 0)
                System.out.println("开始设置第"+i+"个医生.");
            imUserService.setIMAttr(stringList.get(0));
        }
    }

//    @Test
    public void importMM() throws Exception {
        List<String> stringList = CSVUtil.readIds("src\\test\\resources\\mm.csv");
        System.out.println("用户账号的数量为" + stringList.size());
        //导入孕妈账号
        txService.batchImportAccounts(stringList);
    }

//    @Test
    public void imporAssistant() throws Exception {
        List<String> stringList = CSVUtil.readIds("src\\test\\resources\\assistant.csv");
        System.out.println("用户账号的数量为" + stringList.size());
        //导入孕妈账号
        txService.batchImportAccounts(stringList);
    }

}
