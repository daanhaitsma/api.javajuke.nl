package api.javajuke.exception;

import javax.persistence.PersistenceException;

public class BadRequestException extends PersistenceException {
    public BadRequestException() { }

    public BadRequestException(String message) {
        super(message);
    }
}
