package fr.epita.project.external;

import fr.epita.project.external.dataservice.impl.AddressServiceImpl;
import fr.epita.project.external.dataservice.impl.ContactServiceImpl;
import fr.epita.project.external.dataservice.impl.RoleServiceImpl;
import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.external.exceptions.LinkingException;
import fr.epita.project.internal.entities.Address;
import fr.epita.project.internal.entities.Contact;
import fr.epita.project.internal.entities.Role;
import fr.epita.project.internal.entities.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
public class UserTest {
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    ContactServiceImpl contactService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;

    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Test
    public void addCompleteContact() throws AlreadyExistingException, LinkingException {

        User user1 = new User("NightKnight",
                "Jad",
                new Role("Viewer"),
                new Contact("Jad", "Male", "jad.tr@hotmail.com",
                        new Address("France", "Ile de France", "Vitry Sur Seine", "Voie Delacroix", "44"),
                        Date.valueOf("1996-04-16")));

        User user2 = new User("NightyKnighty",
                "Password",
                new Role("Viewer"),
                new Contact("Jad", "Male", "jad.trh@hotmail.com",
                        new Address("France", "Ile de France", "Vitry Sur Seine", "Voie Delacroix", "44"),
                        Date.valueOf("1996-04-16")));

        userService.add(user2);
        userService.add(user1);

    }
@Test
    public void updateUser(){
        
        User user2 = new User("girl3",
                "Password",
                new Role("Viewer"),
                new Contact("Test", "Male", "girl1",
                        new Address("Albania", "a", "v", "2x", "33"),
                        Date.valueOf("1996-04-16")));
        try{                
        userService.updateUser("girl3","girl1",user2);
        }
        catch(Exception e){
            logger.info(e.toString());
        }
    }

    @Test
    public void getAllUsers() {
        for (User user : userService.getAll()) {
            logger.info(user.toString());
        }

    }

}
