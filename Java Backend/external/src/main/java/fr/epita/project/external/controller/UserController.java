package fr.epita.project.external.controller;

import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.dtos.JWTRequest;
import fr.epita.project.external.dtos.JWTResponse;
import fr.epita.project.external.dtos.UpdateDTO;
import fr.epita.project.external.dtos.UserDTO;
import fr.epita.project.external.utils.JWTUtility;
import fr.epita.project.internal.entities.*;
import fr.epita.project.internal.repository.UserRepository;

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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST,RequestMethod.PATCH })
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
                            new Address(user.getCountry(), user.getArea(), user.getCity(), user.getStreet(),
                                    user.getNumber()),
                            Date.valueOf(user.getBirthDate())));
            userService.add(user1);
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
        return ResponseEntity.ok().body("User Added");

    }

    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception {

        User user =  userService.getUser(jwtRequest.getUsername());
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

        return new JWTResponse(token, jwtRequest.getUsername(),user.getContact().getEmail());
    }

    @GetMapping("/getInfo/{username}")
    public ResponseEntity getUserDetails(@PathVariable String username) {
        try {
            User user = userService.getUser(username);
            UserDTO userDTO = new UserDTO();
            
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setRoleName(user.getRole().getName());
            userDTO.setName(user.getContact().getName());
            userDTO.setGender(user.getContact().getGender());
            userDTO.setEmail(user.getContact().getEmail());
            userDTO.setCountry(user.getContact().getAddress().getCountry());
            userDTO.setArea(user.getContact().getAddress().getArea());
            userDTO.setCity(user.getContact().getAddress().getCity());
            userDTO.setStreet(user.getContact().getAddress().getStreet());
            userDTO.setNumber(user.getContact().getAddress().getNumber());
            userDTO.setBirthDate(user.getContact().getBirthDate().toString());
            return ResponseEntity.status(200).body(userDTO);

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }
    @PatchMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody UpdateDTO updateDTO)
    {
        try{
            User user = new User(updateDTO.getUsername(), updateDTO.getPassword(),
                    new Role(updateDTO.getRoleName()),
                    new Contact(updateDTO.getName(), updateDTO.getGender(), updateDTO.getEmail(),
                            new Address(updateDTO.getCountry(), updateDTO.getArea(), updateDTO.getCity(), updateDTO.getStreet(),
                                    updateDTO.getNumber()),
                            Date.valueOf(updateDTO.getBirthDate())));
            userService.updateUser(updateDTO.getOldUsername(),updateDTO.getOldEmail(), user);
            return ResponseEntity.status(200).body("User Updated");

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error " + e.getMessage());
        }
    }
}
