package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    @Query("SELECT m FROM Address m WHERE lower(m.area) = lower(?1) AND  lower(m.city) = lower(?2) AND  lower(m.country) = lower(?3) AND  lower(m.number) = lower(?4) AND lower(m.street) = lower(?5)")
    List<Address> searchAddress(String area, String city, String country, String number, String street);

    @Modifying
    @Transactional
    @Query("update Address u set u.country = ?1, u.area=?2, u.city = ?3 , u.street = ?4, u.number =?5 where u.country = ?6 and u.area = ?7  and  u.city = ?8  and  u.street = ?9  and  u.number =?10")
    public void updateAddress(String newCountry,String newArea, String newCity, String newStreet, String newNumber, String oldCountry,String oldArea, String oldCity, String oldStreet, String oldNumber);
}
