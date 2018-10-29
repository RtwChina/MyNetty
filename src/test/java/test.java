import com.config.SpringConfig;
import com.dao.model.Role;
import com.service.IRoleListService;
import com.service.IRoleService;
import com.service.impl.RoleServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        IRoleService roleService = context.getBean(IRoleService.class);
        IRoleListService roleListService= context.getBean(IRoleListService.class);
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRoleName("rtw2");
        role.setNote("shuaige2");
        Role role2 = new Role();
        role2.setRoleName("rtw3");
        role2.setNote("shuaige3");
        roles.add(role);
        roles.add(role2);
        roleListService.insertRoleList(roles);
    }
}
