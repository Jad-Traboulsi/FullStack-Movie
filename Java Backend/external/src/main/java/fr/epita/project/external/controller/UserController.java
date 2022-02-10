package fr.epita.project.external.controller;

import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.dtos.JWTRequest;
import fr.epita.project.external.dtos.JWTResponse;
import fr.epita.project.external.dtos.UserDTO;
import fr.epita.project.external.utils.JWTUtility;
import fr.epita.project.internal.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*",methods = {RequestMethod.GET,RequestMethod.POST})
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    private JWTUtility jwtUtility;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllMovies() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/getOK")
    public ResponseEntity getOK() {
        return ResponseEntity.ok("OK!");
    }


    @PostMapping("/addUser")
    public ResponseEntity<String> getMovieByExternalId(@RequestBody UserDTO user) {

        try {
            User user1 = new User(user.getUsername(), user.getPassword(),
                new Role(user.getRoleName()),
                new Contact(user.getName(), user.getGender(), user.getEmail(),
                        new Address(user.getCountry(), user.getArea(), user.getCity(), user.getStreet(), user.getNumber()),
                        Date.valueOf(user.getBirthDate())));
            userService.add(user1);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error "+e.getMessage());
        }
        return ResponseEntity.ok().body("User Added");

    }

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);

        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtUtility.generateToken(userDetails);

        return new JWTResponse(token, jwtRequest.getUsername());
    }
}
