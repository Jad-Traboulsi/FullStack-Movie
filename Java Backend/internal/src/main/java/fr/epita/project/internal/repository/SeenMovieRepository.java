package fr.epita.project.internal.repository;

import fr.epita.project.internal.entities.Movie;
import fr.epita.project.internal.entities.SeenMovie;
import fr.epita.project.internal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;


public interface SeenMovieRepository extends JpaRepository<SeenMovie,Integer> {
    @Query("SELECT m FROM SeenMovie m WHERE m.movie = ?1  AND  m.user = ?2")
    List<SeenMovie> searchMovieSeenByUser(Movie movie, User user);

    @Query("SELECT m FROM SeenMovie m WHERE m.user = ?1")
    List<SeenMovie> getMoviesSeenByUser(User user);

}
