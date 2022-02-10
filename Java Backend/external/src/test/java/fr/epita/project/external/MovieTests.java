package fr.epita.project.external;

import fr.epita.project.external.dataservice.impl.MovieServiceImpl;
import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.internal.entities.Movie;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MovieTests {
    private static final Logger logger = LoggerFactory.getLogger(MovieTests.class);

    @Autowired
    MovieServiceImpl movieService;

    @Test
    public void readMovies() throws IOException, ParseException {
        List<Movie> movies = readAllMovies();
        for(Movie movie:movies){
            System.out.println(movie.toString());
        }
    }

    @Test
    public void addMovies() throws IOException, ParseException {
        movieService.addMovies(readAllMovies());
    }
    @Test
    public void addMovie() throws AlreadyExistingException {

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        movieService.add(new Movie("Title",date,"2"));
    }
    @Test
    public void getAllMovies(){
        List<Movie> movies = movieService.getAll();
        for(Movie movie:movies){
            logger.info(movie.toString());
        }
    }

    public List<Movie> readAllMovies() throws IOException, ParseException {
        List<Movie> out = new ArrayList<>();
        List<String> listOfLines = Files.readAllLines(new File("src/main/resources/movieCollection.csv").toPath());
        listOfLines.remove(0);

        for (String line : listOfLines) {

            String[] l = line.split(",");
            try {
                java.util.Date date = new SimpleDateFormat("dd-MMM-yy").parse(l[1]);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                out.add(new Movie(l[2], sqlDate,l[0]));
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                for (String r : l)
                    System.out.println(r);
            }

        }

        return out;
    }


}
