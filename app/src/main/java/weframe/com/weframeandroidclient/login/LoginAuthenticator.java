package weframe.com.weframeandroidclient.login;

import weframe.com.weframeandroidclient.AsyncOperationRequestListener;

public interface LoginAuthenticator {
    void attemptAuthentication(final String email,
                                  final String password,
                                  final AsyncOperationRequestListener listener);
}
