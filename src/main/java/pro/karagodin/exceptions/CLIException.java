package pro.karagodin.exceptions;

public class CLIException extends Exception {

    private boolean exit = false;
    private int statusCode = 0;
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
