package spb.alex.security_3_1_2.service;

import org.springframework.stereotype.Service;
import spb.alex.security_3_1_2.model.Role;
import spb.alex.security_3_1_2.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleById(Long id){
        return roleRepository.findRoleById(id);
    }
}
