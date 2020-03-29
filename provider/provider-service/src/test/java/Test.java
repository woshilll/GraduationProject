import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.provider.ProviderApplication;
import com.yang.graduation.provider.api.AdminService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 李洋
 * @date 2020/3/24 10:16
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class Test {
    @Resource
    private AdminService adminService;



    @org.junit.Test
    public void RegTest() {
        Admin admin = new Admin();
        admin.setName("花");
        admin.setPassword("654321");
        int res = adminService.regAdmin(admin);
        Assert.assertEquals(res, 1);

    }
}
