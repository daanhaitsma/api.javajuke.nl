package api.stenden.exception;

import javax.persistence.PersistenceException;

public class EntityNotFoundException extends PersistenceException {
    public EntityNotFoundException() { }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
