package weframe.com.weframeandroidclient.login;

import weframe.com.weframeandroidclient.AsyncOperationRequestListener;

public class FakeLoginAuthenticator implements LoginAuthenticator {
    @Override
    public void attemptAuthentication(String email, String password, AsyncOperationRequestListener listener)  {
        listener.onComplete(email.equals("email@gmail.com") && password.equals("password"));
    }
}
