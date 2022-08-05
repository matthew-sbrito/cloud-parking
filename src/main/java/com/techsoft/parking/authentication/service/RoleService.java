package com.techsoft.parking.authentication.service;

import com.techsoft.parking.authentication.domain.Role;
import com.techsoft.parking.authentication.repository.RoleRepository;
import com.techsoft.parking.common.service.impl.AbstractCrudServiceImpl;
import com.techsoft.parking.authentication.dto.form.RoleCreateDTO;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends AbstractCrudServiceImpl<Role, Long, RoleRepository, RoleCreateDTO> {

    RoleService(RoleRepository roleRepository) {
        super(roleRepository, Role.class);
    }
}
