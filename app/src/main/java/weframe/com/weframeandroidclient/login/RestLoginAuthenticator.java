package weframe.com.weframeandroidclient.login;

import android.os.AsyncTask;

import org.json.JSONException;

import weframe.com.weframeandroidclient.AsyncOperationRequestListener;
import weframe.com.weframeandroidclient.service.RestService;

public class RestLoginAuthenticator implements LoginAuthenticator {
    @Override
    public void attemptAuthentication(final String email,
                                      final String password,
                                      final AsyncOperationRequestListener listener) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    listener.onComplete(RestService.getInstance().login(email, password));
                } catch (JSONException e) {
                    listener.onError(new InvalidLoginException(e));
                }
                return null;
            }
        }.execute();
    }
}
