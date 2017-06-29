package weframe.com.weframeandroidclient.picture;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RemotePicturesManager {
    private static final String SERVER_URL = "localhost:8080/user-pictures";

    private final String authToken;
    private final RestTemplate restTemplate;

    public RemotePicturesManager(final RestTemplate restTemplate, final String authToken) {
        this.restTemplate = restTemplate;
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setCharset(Charset.forName("UTF8"));
        this.restTemplate.getMessageConverters().add(formHttpMessageConverter);
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.authToken = authToken;
    }

    public boolean uploadPicture(final PictureViewHolder pictureViewHolder) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(pictureViewHolder.getFile().getAbsoluteFile()));
        map.add("uniqueName", UUID.randomUUID().toString());
        map.add("formatName", "jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authToken);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<>(map, headers);

        return restTemplate.exchange(
                SERVER_URL,
                HttpMethod.POST,
                imageEntity,
                Boolean.class
        ).getStatusCode() == HttpStatus.OK;
    }

    public List<URL> downloadPictureUrls() throws MalformedURLException {
        List<URL> urls = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authToken);

        PictureResponse[] responses = restTemplate.getForObject(
                SERVER_URL,
                PictureResponse[].class,
                headers
        );

        for(PictureResponse response : responses) {
            urls.add(new URL(response.getImageUrl()));
        }

        return urls;
    }
}
