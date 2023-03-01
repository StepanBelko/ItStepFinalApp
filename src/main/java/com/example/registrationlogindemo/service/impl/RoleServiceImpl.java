package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
