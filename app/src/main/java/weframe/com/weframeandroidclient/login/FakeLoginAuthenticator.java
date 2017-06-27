package weframe.com.weframeandroidclient.login;

public class FakeLoginAuthenticator implements LoginAuthenticator {
    @Override
    public String attemptAuthentication(String email, String password) throws InvalidLoginException {
        return (email.equals("email@gmail.com") && password.equals("password")) ? "Bearer ######" : null;
    }
}
