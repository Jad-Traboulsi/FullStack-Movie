package fr.epita.project.external.dataservice.impl;

import fr.epita.project.external.dataservice.GenericService;
import fr.epita.project.external.exceptions.LinkingException;
import fr.epita.project.external.exceptions.NotWatchedException;
import fr.epita.project.internal.entities.Movie;
import fr.epita.project.internal.entities.SeenMovie;
import fr.epita.project.internal.entities.User;
import fr.epita.project.internal.repository.SeenMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SeenMovieServiceImpl implements GenericService<SeenMovie> {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    SeenMovieRepository seenMovieRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    MovieServiceImpl movieServiceImpl;

    @Override
    public void add(SeenMovie seenMovie) throws LinkingException {
        if (hasDuplicate(seenMovie)) {
            throw new LinkingException("Movie already seen by user");
        } else {
            seenMovieRepository.save(seenMovie);
            logger.info("Movie Added to user");
        }
    }

    @Override
    public List<SeenMovie> getAll() {
        return seenMovieRepository.findAll();
    }

    public List<SeenMovie> getAllMoviesWatchedByUser(String username) throws NoSuchElementException {
        User user = userServiceImpl.getUserByUsername(username);
        return seenMovieRepository.getMoviesSeenByUser(user);
    }
    
    public boolean getIfMovieWatchedByUser(String username, String externalID) throws NoSuchElementException {
        List<SeenMovie> moviesWatchedByUser = getAllMoviesWatchedByUser(username);
        for (SeenMovie seenMovie : moviesWatchedByUser) {
            if (seenMovie.getMovie().getExternal_id().equals(externalID))
                return true;
        }
        return false;
    }

    public Date getWhenMovieWatchedByUser(String username, String externalID) throws NoSuchElementException,NotWatchedException {
        List<SeenMovie> moviesWatchedByUser = getAllMoviesWatchedByUser(username);
        for (SeenMovie seenMovie : moviesWatchedByUser) {
            if (seenMovie.getMovie().getExternal_id().equals(externalID))
                return seenMovie.getDate();
        }
        throw new NotWatchedException("Movie Not Watched");
    }

    @Override
    public boolean hasDuplicate(SeenMovie seenMovie) {
        return !seenMovieRepository.searchMovieSeenByUser(seenMovie.getMovie(),seenMovie.getUser()).isEmpty();
    }

    public SeenMovie getSeenMovie(String username,String movieId,Date date){
        User user = userServiceImpl.getUserByUsername(username);
        Movie movie = movieServiceImpl.getMovieByExternalId(movieId).get(0);
        return new SeenMovie(user,movie,date);
    }

}
