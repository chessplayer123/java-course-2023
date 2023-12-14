package edu.hw10.task1;

public class UnsupportedObjectException extends Exception {
    public UnsupportedObjectException(Object object) {
        super("Object <%s> can't be generated".formatted(object.toString()));
    }

    public UnsupportedObjectException(Object object, Exception cause) {
        super("Object <%s> can't be generated due to '%s'".formatted(object.toString(), cause.getMessage()), cause);
    }
}
