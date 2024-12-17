package learn.qzy.searchbackend.model.vo;

public class ContentPictureVO {
    private String pictureUrl;

    public ContentPictureVO() {
    }

    public ContentPictureVO(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "ContentPictureVO{" +
                "pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
