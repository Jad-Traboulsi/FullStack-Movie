package fr.epita.project.external.dataservice.impl;

import fr.epita.project.external.dataservice.GenericService;
import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.internal.entities.Address;
import fr.epita.project.internal.entities.Contact;
import fr.epita.project.internal.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContactServiceImpl implements GenericService<Contact> {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AddressServiceImpl addressService;

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public void add(Contact c) throws AlreadyExistingException {
        // better to set an address in var
        if (!hasDuplicate(c)) {

            if (!addressService.hasDuplicate(c.getAddress())) {
                logger.info("Address not found, creating address");

                addressService.add(c.getAddress());
            } else {
                logger.info("Address already existing, linking to contact");

                c.setAddress(addressService.search(c.getAddress()).get(0));

            }
            contactRepository.save(c);

            logger.info("Contact saved");
        } else
            throw new AlreadyExistingException("Email already exists");
    }

    public void updateContact(Contact newContact, String email) throws AlreadyExistingException {

        if (hasDuplicate(newContact) && !newContact.getEmail().equals(email)) {
            throw new AlreadyExistingException("Email already exists");
        } else {

            if (!addressService.hasDuplicate(newContact.getAddress())) {
                logger.info("Address not found, creating address");

                addressService.add(newContact.getAddress());
            } else {
                logger.info("Address already existing, linking to contact");

            }
            newContact.setAddress(addressService.search(newContact.getAddress()).get(0));
            contactRepository.updateContact(email, newContact.getName(), newContact.getGender(), newContact.getEmail(),
                    newContact.getBirthDate(), newContact.getAddress().getId());

            logger.info("Contact updated");
        }
    }

    public List<Contact> search(Contact c) {
        return contactRepository.searchContact(c.getBirthDate(), c.getEmail(), c.getGender(), c.getName());

    }

    public Contact getContactByID(Contact c) {
        return contactRepository.findById(c.getEmail()).get();
    }

    @Override
    public boolean hasDuplicate(Contact c) {
        return contactRepository.existsById(c.getEmail());
    }

}
