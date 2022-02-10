package fr.epita.project.external.dataservice.impl;

import fr.epita.project.external.dataservice.GenericService;
import fr.epita.project.external.dtos.UserDTO;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.external.exceptions.LinkingException;
import fr.epita.project.internal.entities.Role;
import fr.epita.project.internal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements GenericService<fr.epita.project.internal.entities.User>, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactServiceImpl contactService;

    @Autowired
    AddressServiceImpl addressService;

    @Autowired
    RoleServiceImpl roleService;

    @Override
    public void add(fr.epita.project.internal.entities.User user) throws AlreadyExistingException, LinkingException {
        if (hasDuplicate(user)) {
            throw new AlreadyExistingException("Username already exists");

        } else {
            if (contactService.hasDuplicate(user.getContact())) {

                throw new LinkingException("Contact already has a username");
            }
            contactService.add(user.getContact());

            logger.info("linking contact to user");
            user.setContact(contactService.search(user.getContact()).get(0));
            if (!roleService.hasDuplicate(user.getRole())) {
                roleService.add(user.getRole());
            }
            logger.info("linking role to user");
            user.setRole(roleService.searchRole(user.getRole().getName()));
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
            userRepository.save(user);
            logger.info("User Saved");
        }
    }


    @Override
    public List<fr.epita.project.internal.entities.User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean hasDuplicate(fr.epita.project.internal.entities.User user) {
        return userRepository.existsById(user.getUsername());
    }

    public fr.epita.project.internal.entities.User getUserByUsername(String username) throws NoSuchElementException{
        
        return userRepository.findById(username).orElseThrow();
    }

    public boolean Authenticate(fr.epita.project.internal.entities.User user) {
        fr.epita.project.internal.entities.User dbUser = userRepository.getById(user.getUsername());
        if (dbUser != null) {
            // If the passwords match it will return true, otherwise false
            return BCrypt.checkpw(user.getPassword(), dbUser.getPassword());
        } else
            return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        fr.epita.project.internal.entities.User dbUser = userRepository.getById(username);
        // TODO -- Fetch Roles as Well
        return new User(dbUser.getUsername(), dbUser.getPassword(), new ArrayList<>());
    }
}
