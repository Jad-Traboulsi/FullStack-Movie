package fr.epita.project.internal.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "contact")
public class Contact {


    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Id
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "BirthDate")
    private Date birthDate;

    public Contact() {
    }

    public Contact(String name, String gender, String email, Address address, Date birthDate) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Contact{" +
                " name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address.toString() +
                ", birthDate=" + birthDate +
                '}';
    }
}