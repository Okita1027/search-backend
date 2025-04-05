package learn.qzy.searchbackend.model.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * 这个类用不上，保留它用于以后复习
 */
@Getter
@Setter
@Document(indexName = "content_user")
public class ESContentUser {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Id
    private Long id;
    private String title;
    private String profile;
    @Field(name = "avatar_url")
    private String avatarUrl;

    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN, name = "create_time")
    private LocalDateTime createTime;

    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN, name = "update_time")
    private LocalDateTime updateTime;

    @Field(name = "is_deleted")
    private Integer isDeleted;

}
