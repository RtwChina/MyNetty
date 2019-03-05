
import com.config.SpringConfig;
import com.controller.TestController;
import com.dao.model.Role;
import com.service.IRoleService;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.service.impl.RoleServiceImpl;

public class main {
    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
//        IRoleService roleService = context.getBean(IRoleService.class);
//        Role role = new Role();
//        role.setRoleName("rtw2");
//        role.setNote("shuaige");
//        roleService.insertRole(role);

        List list = new ArrayList<String>();
        for (int i = 0; i<100; i++) {
            list.add("didi" + i);
        }


        list.add(2, "shuai");



    }
}
