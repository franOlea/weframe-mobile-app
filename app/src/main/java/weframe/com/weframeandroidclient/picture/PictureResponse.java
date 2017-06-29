package weframe.com.weframeandroidclient.picture;

public class PictureResponse {

    private Long id;
    private String imageKey;
    private String imageUrl;

    public PictureResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(final String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
