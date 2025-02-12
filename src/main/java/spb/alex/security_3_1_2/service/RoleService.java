package spb.alex.security_3_1_2.service;

import org.springframework.transaction.annotation.Transactional;
import spb.alex.security_3_1_2.model.Role;

public interface RoleService {

    Role findRoleById(Long id);
}
