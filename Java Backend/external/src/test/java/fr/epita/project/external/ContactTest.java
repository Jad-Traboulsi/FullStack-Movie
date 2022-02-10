package fr.epita.project.external;

import fr.epita.project.external.dataservice.impl.ContactServiceImpl;
import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.internal.entities.Address;
import fr.epita.project.internal.entities.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;

@SpringBootTest
public class ContactTest {
    private static final Logger logger = LoggerFactory.getLogger(ContactTest.class);

    @Autowired
    ContactServiceImpl contactService;

    @Test
    public void addContact() throws AlreadyExistingException {
        Contact c = new Contact("JLdd","Male","jad.tr@hotmail.com",
                new Address("France","IIlle du France","Vitry Sur Seine","Voie Delacroix","44"), Date.valueOf("1996-04-16"));
        contactService.add(c);


        getAllContacts();
    }
    @Test
    public void getAllContacts(){
        List<Contact> contacts = contactService.getAll();
        for (Contact c: contacts) {
            logger.info(c.toString());
        }
    }
    @Test
    public void searchContact(){
        Contact c = new Contact("Jad","Male","jad.tr@hotmail.com",
                new Address("France","Ile de France","Vitry Sur Seine","Voie Delacroix","44"), Date.valueOf("1996-04-16"));

        List<Contact> contacts = contactService.search(c);
        for (Contact cd: contacts) {
            logger.info(cd.toString());
        }
    }
}
