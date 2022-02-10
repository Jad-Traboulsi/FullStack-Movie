package fr.epita.project.internal.entities;

import javax.persistence.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "Movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Added")
    private Date added;

    @Column(name = "ExternalID")
    private String external_id;

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    private Set<SeenMovie> users;


    public void addSeenMovie(SeenMovie seenMovie) {
        this.users.add(seenMovie);
    }
    public void setSeenMovie(Set<SeenMovie> users) {
        this.users = users;
    }

    public Movie() {
    }

    public Movie(String title, Date added, String external_id) {
        this.title = title;
        this.added = added;
        this.external_id = external_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", added=" + added +
                ", external_id='" + external_id + '\'' +
                '}';
    }
}
