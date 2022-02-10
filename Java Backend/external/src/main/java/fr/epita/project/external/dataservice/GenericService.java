package fr.epita.project.external.dataservice;


import fr.epita.project.external.exceptions.AlreadyExistingException;
import fr.epita.project.external.exceptions.LinkingException;

import java.util.List;

public interface GenericService <T>{
    void add(T obj) throws AlreadyExistingException, LinkingException;
    List<T> getAll();
    boolean hasDuplicate(T obj);
}
