package weframe.com.weframeandroidclient.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import weframe.com.weframeandroidclient.AsyncOperationRequestListener;
import weframe.com.weframeandroidclient.MainActivity;
import weframe.com.weframeandroidclient.R;

public class LoginActivity extends AppCompatActivity implements AsyncOperationRequestListener {
    private static final String EMPTY_STRING = "";

    private LoginAuthenticator loginAuthenticator;

    private EditText emailField;
    private EditText passwordField;

    private Boolean exit = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginAuthenticator = new RestLoginAuthenticator();

        emailField = (EditText) findViewById(R.id.loginEmailField);
        passwordField = (EditText) findViewById(R.id.loginPasswordField);
    }

    @Override
    public void onBackPressed() {
        if(exit) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }
    }

    public void logIn(final View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

            Log.v(
                    "LoginAuthenticator",
                    String.format(
                            "Attempting login with email [%s].",
                            email
                    )
            );
            loginAuthenticator.attemptAuthentication(email, password, this);
    }

    @Override
    public void onComplete(boolean success) {
        if(success) {
            goToMainMenu();
        } else {
            displayErrorToast("Login invalido.");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        displayErrorToast(throwable.getMessage());
        resetFields();
    }

    private void displayErrorToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goToMainMenu() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void resetFields() {
        emailField.setText(EMPTY_STRING);
        passwordField.setText(EMPTY_STRING);
    }

}
