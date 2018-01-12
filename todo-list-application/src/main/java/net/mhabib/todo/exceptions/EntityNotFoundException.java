package net.mhabib.todo.exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(final String message) {
        super(message);
    }

    public EntityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
