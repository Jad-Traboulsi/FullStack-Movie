package fr.epita.project.external.controller;

import fr.epita.project.external.dataservice.impl.MovieServiceImpl;
import fr.epita.project.external.dataservice.impl.SeenMovieServiceImpl;
import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.dtos.MovieDTO;
import fr.epita.project.external.dtos.SeenMovieDTO;
import fr.epita.project.internal.entities.Movie;
import fr.epita.project.internal.entities.SeenMovie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/seenMovies")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST})
public class SeenMovieController {
    @Autowired
    SeenMovieServiceImpl seenMovieServiceImpl;

    @GetMapping("/getAll")
    public ResponseEntity getAllMovies() {
        return ResponseEntity.ok().body(seenMovieServiceImpl.getAll());
    }

    @GetMapping("/getMoviesSeenBy/{username}")
    public ResponseEntity getMoviesSeenByUser(@PathVariable String username) {
        try {
            List<SeenMovie> result = seenMovieServiceImpl.getAllMoviesWatchedByUser(username);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("User doesn't Exist");
        }
    }

    @GetMapping("/getIfMovieSeen/{username}/{externalID}")
    public ResponseEntity getIfMovieSeen(@PathVariable String username, @PathVariable String externalID) {
        try {
            boolean watched = seenMovieServiceImpl.getIfMovieWatchedByUser(username, externalID);
            return ResponseEntity.ok().body(watched);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/getWhenMovieSeen/{username}/{externalID}")
    public ResponseEntity getWhenMovieSeen(@PathVariable String username, @PathVariable String externalID) {
        try {
            Date watched = seenMovieServiceImpl.getWhenMovieWatchedByUser(username, externalID);
            return ResponseEntity.ok().body(watched);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/addMovieToUser")
    public ResponseEntity addMovieToUser(@RequestBody SeenMovieDTO seenMovieDto) {

        try {
            SeenMovie seenMovie = seenMovieServiceImpl.getSeenMovie(seenMovieDto.getUsername(),
                    seenMovieDto.getExternalId(), Date.valueOf(seenMovieDto.getDate()));
            System.out.println(seenMovie.toString());

            seenMovieServiceImpl.add(seenMovie);
            System.out.println("Added");
            return ResponseEntity.ok().body("Created");
        } catch (Exception e) {
            System.out.println("Error");
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

}
