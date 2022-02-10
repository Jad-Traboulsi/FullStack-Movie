package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.Address;
import fr.epita.project.internal.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,String> {
    @Query("SELECT m FROM Contact m WHERE m.birthDate = ?1 AND  lower(m.email) = lower(?2) AND  lower(m.gender) = lower(?3) AND  lower(m.name) = lower(?4)")
    List<Contact> searchContact(Date birth_date,String email,String gender,String name);

}
