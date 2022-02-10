package fr.epita.project.external.controller;

import fr.epita.project.external.dataservice.impl.MovieServiceImpl;
import fr.epita.project.external.dtos.MovieDTO;
import fr.epita.project.internal.entities.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST })
public class MovieController {
    @Autowired
    MovieServiceImpl movieServiceImpl;

    @GetMapping("/getAll")
    public ResponseEntity<List<Movie>>getAllMovies(){
        return ResponseEntity.ok().body(movieServiceImpl.getAll());
    }

    @GetMapping("/getOK")
    public ResponseEntity getOK(){return ResponseEntity.ok("OK!");}

    @PostMapping("/getByExternalID")
    public ResponseEntity getMovieByExternalId(@RequestBody MovieDTO id){
            List<Movie> result = movieServiceImpl.getMovieByExternalId(id.getExternalID());
            for (Movie movie : result) {
                System.out.println(movie);
            }
            System.out.println(result);
            if(result.isEmpty())
                return ResponseEntity.status(500).body("Movie not found");
            else
                return ResponseEntity.ok(result);

    }

}
