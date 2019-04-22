package swust.qiy.microservice.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import swust.qiy.microservice.gateway.RouteEventListener;
import swust.qiy.microservice.gateway.rateLimit.TokenBucketRateLimiter;

/**
 * @author qiying
 * @create 2019/4/19
 */
@Configuration
public class GatewayAutoConfiguration {

  @Bean
  TokenBucketRateLimiter tokenBucketRateLimiter(Validator validator) {
    return new TokenBucketRateLimiter(validator);
  }

  @Bean
  RouteEventListener routeEventListener() {
    return new RouteEventListener();
  }
}
