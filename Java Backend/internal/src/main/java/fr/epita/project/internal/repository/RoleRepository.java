package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
