package pl.edu.pb.wi.mmm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pb.wi.mmm.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(pl.edu.pb.wi.mmm.enumeration.Role role);
}
