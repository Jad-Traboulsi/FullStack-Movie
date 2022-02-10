package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
