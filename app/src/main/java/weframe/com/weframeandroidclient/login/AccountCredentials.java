package weframe.com.weframeandroidclient.login;

public class AccountCredentials {

    private String email;
    private String password;

    public AccountCredentials() {
    }

    public AccountCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
