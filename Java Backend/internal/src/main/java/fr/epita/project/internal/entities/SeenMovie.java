package fr.epita.project.internal.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "SeenMovie")
public class SeenMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Users")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Movie")
    private Movie movie;

    @Column(name = "Date")
    private Date date;

    public SeenMovie(User user, Movie movie, Date date) {
        this.user = user;
        this.movie = movie;
        this.date = date;
    }

    public SeenMovie() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "SeenMovie{" +
                ", user=" + user.toString() +
                ", movie=" + movie.toString() +
                ", date=" + date +
                '}';
    }
}