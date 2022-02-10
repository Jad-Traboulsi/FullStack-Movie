package fr.epita.project.external.dataservice.impl;

import fr.epita.project.external.dataservice.GenericService;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.internal.entities.Movie;
import fr.epita.project.internal.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements GenericService<Movie> {


    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    MovieRepository movieRep;

    @Override
    public List<Movie> getAll() {
        return movieRep.findAll();
    }

    public void addMovies(List<Movie> movies){
        movieRep.saveAll(movies);
        logger.info("Movies Created");
    }
    @Override
    public void add(Movie movie) throws AlreadyExistingException {
        if(hasDuplicate(movie))
            throw new AlreadyExistingException("Movie already exists");
        logger.info("Movie Created");
        movieRep.save(movie);
    }
    @Override
    public boolean hasDuplicate(Movie movie){
        return !movieRep.searchMovie(movie.getTitle(),movie.getAdded(), movie.getExternal_id()).isEmpty();
    }

    public List<Movie> getMovieByExternalId(String id) {
        return movieRep.searchMovieByExtrnalId(id);
    }

}
