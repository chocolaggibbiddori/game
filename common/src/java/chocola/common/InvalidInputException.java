package chocola.common;

import java.io.IOException;

public class InvalidInputException extends IOException {

    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
