package weframe.com.weframeandroidclient.login;

class InvalidLoginException extends Exception {
    public InvalidLoginException(final String message) {
        super(message);
    }

    public InvalidLoginException(final Throwable cause) {
        super(cause);
    }
}
