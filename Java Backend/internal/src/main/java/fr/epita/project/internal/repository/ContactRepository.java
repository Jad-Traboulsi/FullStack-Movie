package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.Address;
import fr.epita.project.internal.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

public interface ContactRepository extends JpaRepository<Contact,String> {
    @Query("SELECT m FROM Contact m WHERE m.birthDate = ?1 AND  lower(m.email) = lower(?2) AND  lower(m.gender) = lower(?3) AND  lower(m.name) = lower(?4)")
    List<Contact> searchContact(Date birth_date, String email, String gender, String name);

    @Modifying
    @Transactional
    @Query("Update Contact u set u.name = ?2, u.gender = ?3, u.email = ?4, u.birthDate = ?5, u.address.id=?6 where u.email = ?1")
    public void updateContact(String oldEmail, String newName, String newGender, String newEmail, Date newDate, Integer addressId);


}
