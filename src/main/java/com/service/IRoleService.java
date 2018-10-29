package com.service;


import com.dao.model.Role;

import java.util.List;

public interface IRoleService {
    public void insertRole(Role role);
    public Role getRole(Long roleId);

}
