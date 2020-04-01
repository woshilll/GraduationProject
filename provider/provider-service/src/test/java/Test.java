import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.provider.ProviderApplication;
import com.yang.graduation.provider.api.AdminService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 李洋
 * @date 2020/3/24 10:16
 */
@SpringBootTest(classes = ProviderApplication.class)
@RunWith(SpringRunner.class)
public class Test {
    @Resource
    private AdminService adminService;


    /**
     * 注册测试
     */
    @org.junit.Test
    public void RegTest() {
        Admin admin = new Admin();
        admin.setName("花");
        admin.setPassword("654321");
        int res = adminService.regAdmin(admin);
        Assert.assertEquals(res, 1);

    }

    /**
     * 时间测试
     */
    @org.junit.Test
    public void dateTest() {
        System.out.println(new Date());
    }

    /**
     * 查询admin集合测试
     */
    @org.junit.Test
    public void findAdmins() {
        Admin admins = new Admin();
        admins.setName("1");
        admins.setId("2979251879936");
        for (Admin admin : adminService.getAdmins(admins)) {
            System.out.println(admin.getName());
        }
    }

    /**
     * 删除测试
     */
    @org.junit.Test
    public void deleteByIdTest() {
        int res = adminService.deleteById("666");
        Assert.assertEquals(1, res);
    }

    @org.junit.Test
    public void updateTest() {
        Admin admin = new Admin();
        admin.setId("12345");
        admin.setName("yangyang");
        int res = adminService.updateById(admin);
        Assert.assertEquals(1, res);
    }
}
