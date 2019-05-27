package swust.qiy.microservice.environment.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author qiying
 * @create 2018/11/9
 */
@EnableEurekaServer
@SpringBootApplication
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class);
    }
}
