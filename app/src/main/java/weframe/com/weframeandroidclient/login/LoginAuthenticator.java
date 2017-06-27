package weframe.com.weframeandroidclient.login;

public interface LoginAuthenticator {
    String attemptAuthentication(final String email, final String password) throws InvalidLoginException;
}
