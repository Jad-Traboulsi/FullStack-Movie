package fr.epita.project.external.dataservice.impl;

import fr.epita.project.external.dataservice.GenericService;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.internal.entities.Role;
import fr.epita.project.internal.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements GenericService<Role> {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void add(Role r) throws AlreadyExistingException{
        if (hasDuplicate(r)) {
            throw new AlreadyExistingException("Role already exists");
        } else {
            roleRepository.save(r);
            logger.info("Role created");
        }
    }

    @Override
    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    @Override
    public boolean hasDuplicate(Role r) {
        return roleRepository.existsById(r.getName());
    }

    public Role searchRole(String r) throws NoSuchElementException{
        return roleRepository.findById(r).orElseThrow();
    }

}
