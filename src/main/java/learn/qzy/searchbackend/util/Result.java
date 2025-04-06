package learn.qzy.searchbackend.util;

import lombok.*;

import java.io.Serializable;

/**
 * @author qzy
 * @create 2024/12/12 13:12 星期四
 * @title 通用结果类
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

}
