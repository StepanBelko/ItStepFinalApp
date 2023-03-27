package by.itstep.stpnbelko.service.impl;

import by.itstep.stpnbelko.entity.Role;
import by.itstep.stpnbelko.repository.RoleRepository;
import by.itstep.stpnbelko.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getByName(String name) {
        Role role = roleRepository.findByName(name);
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
