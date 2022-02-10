package fr.epita.project.external;

import fr.epita.project.external.dataservice.impl.RoleServiceImpl;
import fr.epita.project.external.dataservice.impl.UserServiceImpl;
import fr.epita.project.internal.entities.Role;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleTest {
    private static final Logger logger = LoggerFactory.getLogger(RoleTest.class);

    @Autowired
    RoleServiceImpl roleService;

    @Test
    public void getAll(){
        for (Role r:
             roleService.getAll()) {
            logger.info(r.toString());
        }
    }
}
