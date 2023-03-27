package by.itstep.stpnbelko.service;

import by.itstep.stpnbelko.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getByName(String name);
}
