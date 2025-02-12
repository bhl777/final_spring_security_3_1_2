package spb.alex.security_3_1_2.service;

import org.springframework.transaction.annotation.Transactional;
import spb.alex.security_3_1_2.model.Role;

import java.util.List;

public interface RoleService {

    Role findRoleById(Long id);

    List<Role> getAllRoles();
}
