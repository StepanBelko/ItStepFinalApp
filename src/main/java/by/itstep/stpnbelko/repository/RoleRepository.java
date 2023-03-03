package by.itstep.stpnbelko.repository;

import by.itstep.stpnbelko.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
