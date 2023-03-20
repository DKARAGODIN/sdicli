package pro.karagodin.exceptions;

public class CLIException extends Exception {

    /**
     * Used by {@Interpreter as a signal to finish the program}
     */
    private boolean exit = false;

    /**
     * Can be used to specify exit status of a program
     */
    private int statusCode = 0;

    /**
     * Flag that makes {@Interpreter} print stack trace upon catching
     */
    private boolean needToPrintStackTrace = true;

    public CLIException(String message) {
        super(message);
    }

    public CLIException(String message, Throwable cause) {
        super(message, cause);
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isNeedToPrintStackTrace() {
        return needToPrintStackTrace;
    }

    public void setNeedToPrintStackTrace(boolean needToPrintStackTrace) {
        this.needToPrintStackTrace = needToPrintStackTrace;
    }
}
