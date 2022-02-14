package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Transactional
    @Query("update User u set u.username = ?2, u.password = ?3 where u.username = ?1")
    public void updateUser(String oldUsername, String newUsername, String newPassword);
}
