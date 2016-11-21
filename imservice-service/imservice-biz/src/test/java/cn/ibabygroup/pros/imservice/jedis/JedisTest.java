package cn.ibabygroup.pros.imservice.jedis;

import cn.ibabygroup.pros.imservice.TestApplication;
import cn.ibabygroup.pros.imservice.service.JedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class JedisTest {

    @Autowired
    private JedisService jedisService;

    @Test
    public void testJedis() throws InterruptedException {
        jedisService.set("VERSION351667f23a2d42048f64366df330305a", "5555");
        String testVal = jedisService.get("test");
        System.out.println(testVal);
    }

}
