package com.hellyard.cuttlefish.util;

public class Validation {
    /**
     * Tests if an object is null, throws NullPointer if it is.
     *
     * @param object is a {@link Object}
     * @param msg is a {@link String}
     * @param <T> is the class of object.
     * @throws NullPointerException if object is null.
     * @return object
     */
    public static <T> T notNull(final T object, String msg) {
        if (object == null) {
            throw new NullPointerException(msg);
        }
        return object;
    }
}
