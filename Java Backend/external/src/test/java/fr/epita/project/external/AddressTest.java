package fr.epita.project.external;

import fr.epita.project.external.dataservice.impl.AddressServiceImpl;
import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.internal.entities.Address;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AddressTest {
    private static final Logger logger = LoggerFactory.getLogger(AddressTest.class);

    @Autowired
    AddressServiceImpl addressService;

    @Test
    public void addAddress() throws AlreadyExistingException {
        Address address = new Address("France","Ile de France","Vitry Sur Seine","Voie Delacroix","44");

        addressService.add(address);
    }

    @Test
    public void getAllAddresses(){
        List<Address> addresses = addressService.getAll();
        for (Address address:
             addresses) {
            logger.info(address.toString());
        }
    }
    @Test
    public void searchAddresses(){
        Address address = new Address("France","Ile de France","Vitry Sur Seine","Voie Delacroix","44");

        List<Address> addresses = addressService.search(address);
        for (Address address1:
                addresses) {
            logger.info(address1.toString());
        }
    }

}
