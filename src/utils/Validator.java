package utils;

import java.util.Objects;

public class Validator {
    /**
     * Verify a parameter by using this method. Returns the original value when it is not null.
     * Else throw an IllegalArgumentException.
     * @param value Parameter you want to verify.
     * @return Original value if it is not null.
     * @param <T> Type of the parameter.
     * @throws IllegalArgumentException If the value is null.
     */
    public static <T> T requireNonNull(T value){
        if (value==null)throw new IllegalArgumentException("Expected value to be non null");
        return value;
    }
    /**
     * Verify a parameter by using this method. Returns the original value when it is not null.
     * Else throw an IllegalArgumentException with the specified message.
     * @param value Parameter you want to verify.
     * @param message Message of the IllegalArgumentException if thrown.
     * @return Original value if it is not null.
     * @param <T> Type of the parameter.
     * @throws IllegalArgumentException If the value is null.
     */
    public static <T> T requireNonNull(T value,String message){
        if (value==null)throw new IllegalArgumentException(message);
        return value;
    }
}
