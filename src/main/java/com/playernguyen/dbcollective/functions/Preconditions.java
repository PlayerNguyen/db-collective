package com.playernguyen.dbcollective.functions;

/**
 * Preconditions contains API to pre-check a fields, methods,...
 * 
 */
public class Preconditions {

    /**
     * Check not null object with message. If the object is null, throws
     * NullPointerException with a message
     * 
     * @param <T> type of object
     * @param object  an object
     * @param message a message
     * @return current object that dispatched
     */
    public static <T> T shouldNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * Check not null with empty message. If the object is null, throws
     * NullPointerException.
     * 
     * @param <T>    type of object
     * @param object an object
     * @return current object that dispatched
     */
    public static <T> T shouldNotNull(T object) {
        return shouldNotNull(object, "");
    }
}
