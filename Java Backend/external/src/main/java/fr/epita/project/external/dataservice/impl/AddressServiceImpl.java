package fr.epita.project.external.dataservice.impl;

import fr.epita.project.external.dataservice.GenericService;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.internal.entities.Address;
import fr.epita.project.internal.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements GenericService<Address> {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    AddressRepository addressRep;

    @Override
    public List<Address> getAll() {
        return addressRep.findAll();
    }

    @Override
    public void add(Address address) throws AlreadyExistingException {
        if(!hasDuplicate(address)) {
            addressRep.save(address);
            logger.info("Address created");
        }
        else{
            throw new AlreadyExistingException("Address already exists");

        }
    }

    public List<Address> search(Address address){
        return addressRep.searchAddress(address.getArea(),address.getCity(), address.getCountry(), address.getNumber(), address.getStreet());
    }

    @Override
    public boolean hasDuplicate(Address a){
        return !search(a).isEmpty();
    }
}
