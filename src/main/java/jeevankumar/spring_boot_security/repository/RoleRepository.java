package jeevankumar.spring_boot_security.repository;

import jeevankumar.spring_boot_security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(Role.RoleType name);
}
