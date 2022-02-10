package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    @Query("SELECT m FROM Address m WHERE lower(m.area) = lower(?1) AND  lower(m.city) = lower(?2) AND  lower(m.country) = lower(?3) AND  lower(m.number) = lower(?4) AND lower(m.street) = lower(?5)")
    List<Address> searchAddress(String area, String city, String country, String number, String street);

}
