package pro.karagodin.exceptions;

public class CLIException extends Exception {

    private boolean exit = false;
    private int statusCode = 0;
    private boolean needToPrintStackTrace = false;

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

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public int getStatus_code() {
        return statusCode;
    }

    public void setStatus_code(int status_code) {
        this.statusCode = status_code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isNeedToPrintStackTrace() {
        return needToPrintStackTrace;
    }

    public void setNeedToPrintStackTrace(boolean needToPrintStackTrace) {
        this.needToPrintStackTrace = needToPrintStackTrace;
    }
}
