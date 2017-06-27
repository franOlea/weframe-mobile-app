package weframe.com.weframeandroidclient.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import weframe.com.weframeandroidclient.MainActivity;
import weframe.com.weframeandroidclient.R;

public class LoginActivity extends AppCompatActivity {
    private static final String EMPTY_STRING = "";

    private LoginAuthenticator loginAuthenticator;

    private EditText emailField;
    private EditText passwordField;

    private Boolean exit = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginAuthenticator = new FakeLoginAuthenticator();

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

        try {
            Log.v(
                    "LoginAuthenticator",
                    String.format(
                            "Attempting login with email [%s].",
                            email
                    )
            );
            String token = loginAuthenticator.attemptAuthentication(email, password);
            storeSessionData(email, token);
            goToMainMenu();
        } catch (InvalidLoginException e) {
            displayErrorToast(e.getMessage());
            resetFields();
        }
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

    private void storeSessionData(final String email, final String token) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.session_shared_preferences_file),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.session_email), email);
        editor.putString(getString(R.string.session_token), token);
        editor.apply();
    }

}
