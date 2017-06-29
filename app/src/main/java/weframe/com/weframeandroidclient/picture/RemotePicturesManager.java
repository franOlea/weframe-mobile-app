package weframe.com.weframeandroidclient.picture;

import android.util.Log;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import weframe.com.weframeandroidclient.picture.local.model.PictureViewHolder;
import weframe.com.weframeandroidclient.service.RestService;

public class RemotePicturesManager {

    public boolean uploadPicture(final PictureViewHolder pictureViewHolder) {
        Log.i("IMAGE UPLOAD", "Image upload requested");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(pictureViewHolder.getFile().getAbsoluteFile()));
        map.add("uniqueName", UUID.randomUUID().toString());
        map.add("formatName", "jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", RestService.getInstance().getAuthToken());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<>(map, headers);

        Log.i("IMAGE UPLOAD", "Uploading image...");
        boolean success = RestService.getInstance().getRestTemplate().exchange(
                RestService.SERVER_URL + "/user-pictures",
                HttpMethod.POST,
                imageEntity,
                String.class
        ).getStatusCode() == HttpStatus.OK;
        Log.i("IMAGE UPLOAD", "Image uploaded " + success);
        return success;
    }

    public List<URL> downloadPictureUrls() throws MalformedURLException {
        List<URL> urls = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", RestService.getInstance().getAuthToken());

        PictureResponse[] responses = RestService.getInstance().getRestTemplate().getForObject(
                RestService.SERVER_URL + "/user-pictures",
                PictureResponse[].class,
                headers
        );

        for(PictureResponse response : responses) {
            urls.add(new URL(response.getImageUrl()));
        }

        return urls;
    }
}
