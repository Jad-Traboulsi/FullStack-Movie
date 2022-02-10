package fr.epita.project.internal.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "Password")
    private String password;

    @OneToOne
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "contact", nullable = false)
    private Contact contact;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<SeenMovie> movies;


    public void addSeenMovie(SeenMovie seenMovie) {
        this.movies.add(seenMovie);
    }

    public void setSeenMovie(Set<SeenMovie> movies) {
        this.movies = movies;
    }

    public User(String username, String password, Role role, Contact contact) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.contact = contact;
    }

    public User() {

    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contactID) {
        this.contact = contactID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role roleID) {
        this.role = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role.toString() +
                ", contact=" + contact.toString() +
                '}';
    }
}