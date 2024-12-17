package learn.qzy.searchbackend.model.vo;

public class ContentUserVO {
    private String title;
    private String profile;
    private String avatarUrl;

    public ContentUserVO() {
    }

    public ContentUserVO(String title, String profile, String avatarUrl) {
        this.title = title;
        this.profile = profile;
        this.avatarUrl = avatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "ContentUserVO{" +
                "title='" + title + '\'' +
                ", profile='" + profile + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
