package utils;

import java.util.Optional;
import java.util.OptionalInt;

public class Utils {
    /**
     * Return an empty optional if the string is null or empty.
     * Return an optional containing the strings value otherwise.
     * @param string The string you want to box into the Optional.
     * @return Optional containing the String or empty.
     */
    public static Optional<String> optionalOfString(String string){
        if (string==null||string.isEmpty())return Optional.empty();
        return Optional.of(string);
    }

    /**
     * Try to parse a string into an integer. Returns an OptionalInt containing the value if successful.
     * An empty OptionalInt otherwise.
     * @param string The string you want to parse.
     * @return An OptionalInt containing the int if the parse was successful. An empty OptionalInt otherwise.
     */
    public static OptionalInt tryParseInt(String string){
        try {
            return OptionalInt.of(Integer.parseInt(string));
        }catch (NumberFormatException n){
            return OptionalInt.empty();
        }
    }
}
