package com.service.impl;

import com.dao.model.Role;
import com.service.IRoleListService;
import com.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleListServiceImpl implements IRoleListService{
    @Autowired
    private IRoleService roleService;

    @Override
    @Transactional
    public void insertRoleList(List<Role> roleList) {
        for (Role role : roleList) {
            roleService.insertRole(role);
        }
    }
}
