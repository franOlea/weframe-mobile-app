package weframe.com.weframeandroidclient.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

import weframe.com.weframeandroidclient.login.AccountCredentials;

public class RestService {
    public static final String SERVER_URL = "http://weframers-franolea.rhcloud.com";
    private static final RestService ourInstance = new RestService();
    private final RestTemplate restTemplate;
    private String authToken;

    public static RestService getInstance() {
        return ourInstance;
    }

    private RestService() {
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setCharset(Charset.forName("UTF8"));

        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(formHttpMessageConverter);
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.authToken = "";
    }

    public boolean login(final String email, final String password) throws JSONException {
        Log.i("LOGIN ATTEMPT", "Sending login request...");
        AccountCredentials credentials = new AccountCredentials(email, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AccountCredentials> entity = new HttpEntity<>(credentials, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(SERVER_URL + "/login", HttpMethod.POST, entity, String.class);
            Log.i("LOGIN ATTEMPT", String.format("Response [%s]", response.getStatusCode()));
            Log.i("LOGIN ATTEMPT", String.format("Authorization [%s]", response.getHeaders().getAuthorization()));
            if(response.getStatusCode() != HttpStatus.OK) {
                return false;
            }
            authToken = response.getHeaders().getAuthorization();
            return true;
        } catch(HttpServerErrorException e) {
            Log.e("REST ERROR", e.getResponseBodyAsString(), e);
            return false;
        }
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getAuthToken() {
        return authToken;
    }

}
