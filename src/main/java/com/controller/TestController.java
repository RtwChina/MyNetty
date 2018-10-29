package com.controller;

import com.dao.model.Role;
import com.dao.model.RoleParams;
import com.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

//注解@Controller表示它是一个控制器
@Controller("myController")
//表明当请求的URI在/my下的时候才有该控制器响应
@RequestMapping("/my")
public class TestController {
    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/getRole",method = RequestMethod.GET)
    public ModelAndView getRole(@RequestParam("id")Long id) {
        Role role = roleService.getRole(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("roleDetail");
        mv.addObject("role", role);
        return mv;
    }

    @RequestMapping(value = "/findRoles",method = RequestMethod.GET)
    public ModelAndView findRoles(@RequestBody RoleParams roleParams) {
//        List<Role> roleList = roleService.findRoles(roleParams);
        ModelAndView mv = new ModelAndView();
//        //绑定模型
//        mv.addObject(roleList);
//        //设置为JSON视图
//        mv.setView(new MappingJackson2JsonView());
        return mv;
    }

    //表明URI是/index的时候该方法才请求
    @RequestMapping("/index")
    public ModelAndView  index() {
        //模型和视图
        ModelAndView mv = new ModelAndView();
        //视图逻辑名称为index
        mv.setViewName("params");
        //返回模型和视图
        return mv;
    }

    public void didi() {
        Role role = new Role();
        role.setRoleName("rtw2");
        role.setNote("shuaige");
        roleService.insertRole(role);
    }
}
