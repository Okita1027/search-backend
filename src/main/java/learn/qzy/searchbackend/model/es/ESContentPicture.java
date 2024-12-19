package learn.qzy.searchbackend.model.es;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * 这个类用不上，保留这个类用于以后复习
 */
@Document(indexName = "content_picture")
public class ESContentPicture {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    @Id
    private Long id;
    private String title;
    @Field(name = "picture_url")
    private String pictureUrl;
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN, name = "create_time")
    private LocalDateTime createTime;

    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN, name = "update_time")
    private LocalDateTime updateTime;

    @Field(name = "is_deleted")
    private Integer isDeleted;

    public ESContentPicture() {
    }

    public ESContentPicture(Long id, String title, String pictureUrl, LocalDateTime createTime, LocalDateTime updateTime, Integer isDeleted) {
        this.id = id;
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "ESContentPicture{" +
                "isDeleted=" + isDeleted +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
