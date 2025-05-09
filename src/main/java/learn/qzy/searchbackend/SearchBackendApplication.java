package learn.qzy.searchbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("learn.qzy.searchbackend.mapper")
@EnableScheduling
public class SearchBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchBackendApplication.class, args);
    }

}
