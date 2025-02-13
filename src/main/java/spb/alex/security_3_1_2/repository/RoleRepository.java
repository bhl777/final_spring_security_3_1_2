package spb.alex.security_3_1_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spb.alex.security_3_1_2.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleById(Long id);
}
