package pro.karagodin.exceptions;

import java.io.IOException;

public class CLIException extends Exception {

    public CLIException() {
    }

    public CLIException(String message) {
        super(message);
    }

    public CLIException(String message, Throwable cause) {
        super(message, cause);
    }

    public CLIException(Throwable cause) {
        super(cause);
    }
}
