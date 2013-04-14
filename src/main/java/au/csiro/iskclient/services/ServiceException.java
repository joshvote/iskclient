package au.csiro.iskclient.services;


public class ServiceException extends Exception {
    private static final long serialVersionUID = -4100569134217416025L;

    /**
     * Creates a new exception with the specified message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Creates a new exception
     * @param message Descriptive message
     * @param cause Root cause of this exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
