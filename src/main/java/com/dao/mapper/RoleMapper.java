package com.dao.mapper;

import com.dao.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
    public int insertRole(Role role);
    public Role getRole(Long roleId);
}
