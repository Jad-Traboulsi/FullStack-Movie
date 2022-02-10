package fr.epita.project.external.exceptions;

public class AlreadyExistingException extends Exception{
    public AlreadyExistingException(String errorMessage){
        super(errorMessage);
    }
}
