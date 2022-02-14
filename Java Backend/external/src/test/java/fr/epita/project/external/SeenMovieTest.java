package fr.epita.project.external;

import fr.epita.project.external.dataservice.impl.MovieServiceImpl;
import fr.epita.project.external.dataservice.impl.SeenMovieServiceImpl;
import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.exceptions.LinkingException;
import fr.epita.project.external.exceptions.NotWatchedException;
import fr.epita.project.internal.entities.SeenMovie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.NoSuchElementException;

@SpringBootTest
public class SeenMovieTest {

    private static final Logger logger = LoggerFactory.getLogger(RoleTest.class);

    @Autowired
    SeenMovieServiceImpl seenMovieService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    MovieServiceImpl movieService;

    @Test
    public void addMovieToUser() throws LinkingException {
        SeenMovie seenMovie = seenMovieService.getSeenMovie("NightKnight",
                "61ccc546aa8d438faa312a12", Date.valueOf("2022-01-19"));

        logger.info(seenMovie.toString());
        seenMovieService.add(seenMovie);
    }

    @Test
    public void checkIfUserWatched() {
        boolean watched = seenMovieService.getIfMovieWatchedByUser("NightKnight", "61ccc546aa8d438faa312a12");
        if (watched)
            logger.info("Watched");
        else
            logger.info("didnt watch it");
        watched = seenMovieService.getIfMovieWatchedByUser("NightKnight", "61ccc546aa8d438faa312a0e");
        if (watched)
            logger.info("Watched");
        else
            logger.info("didnt watch it");

    }

    @Test
    public void getWhenWatched() throws NoSuchElementException, NotWatchedException {
        Date watchedOn = seenMovieService.getWhenMovieWatchedByUser("NightKnight", "61ccc546aa8d438faa312a12");
        logger.info(watchedOn.toString());
        watchedOn = seenMovieService.getWhenMovieWatchedByUser("NightKnight", "61ccc546aa8d438faa312a0e");
        logger.info(watchedOn.toString());

    }

    @Test
    public void getAll() {
        for (SeenMovie s :
                seenMovieService.getAll()) {
            logger.info(s.toString());
        }
    }
    @Test
    public void getAllMoviesWatchedByUser() {
        for (SeenMovie s :
                seenMovieService.getAllMoviesWatchedByUser("NightKnight")) {
            logger.info(s.toString());
        }
    }


}
