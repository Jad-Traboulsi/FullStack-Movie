package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.Contact;
import fr.epita.project.internal.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) = lower(?1)  AND  m.added = ?2 AND  lower(m.external_id) = lower(?3)")
    List<Movie> searchMovie(String title, Date date, String externalId);

    @Query("SELECT m FROM Movie m WHERE lower(m.external_id) = lower(?1)")
    List<Movie> searchMovieByExtrnalId( String externalId);

}
