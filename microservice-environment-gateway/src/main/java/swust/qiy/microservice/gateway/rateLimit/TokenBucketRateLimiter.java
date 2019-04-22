/**
 *
 */
package swust.qiy.microservice.gateway.rateLimit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;
import swust.qiy.microservice.gateway.rateLimit.TokenBucketRateLimiter.Config;

/**
 * @author lizw@primeton.com
 */
public class TokenBucketRateLimiter extends AbstractRateLimiter<Config> {

  public static final String CONFIGURATION_PROPERTY_NAME = "tokenbucket-rate-limiter";

  private Config defaultConfig = new Config(10, 10, 1, TimeUnit.SECONDS.toString());

  private Map<String, TokenBucket> tokenBuckets = new LinkedHashMap<>();

  public static final String KEY_SEPARATOR = ":";


  public TokenBucketRateLimiter(Validator validator) {
    super(Config.class, CONFIGURATION_PROPERTY_NAME, validator);
  }

  @Override
  public Mono<Response> isAllowed(String routeId, String id) {
    // 获得限流配置
    Config routeConfig = getConfig().getOrDefault(routeId, defaultConfig);

    if (routeConfig == null) {
      throw new IllegalArgumentException(
          "No Configuration found for route " + routeId);
    }

    TokenBucket tokenBucket = getTokenBucket(routeId, id, routeConfig);
    boolean allowed = tokenBucket.tryConsume();
    return Mono.just(new Response(allowed, new HashMap<>()));
  }

  protected String getKey(String routeId, String id) {
    return routeId + KEY_SEPARATOR + id;
  }

  // 获得桶容器
  protected TokenBucket getTokenBucket(String routeId, String id, Config config) {
    String key = getKey(routeId, id);
    // 最后请求时间
    //   setLastAccessTime(key);
    if (tokenBuckets.containsKey(key)) {
      return tokenBuckets.get(key);
    }
    // 新建一个桶
    TokenBucket tokenBucket = TokenBuckets.builder()
        .withCapacity(config.getCapacity())
        .withFixedIntervalRefillStrategy(config.getRefillTokens(), config.getRefillPeriod(),
            config.parsedRefillUnit())
        .build();
    // 记录这个桶
    tokenBuckets.put(key, tokenBucket);
    return tokenBucket;
  }

  public void cleanTokenBucket(Integer routeId) {
    cleanTokenBucket(new Integer[]{routeId});
  }

  protected void cleanTokenBucket(Integer[] routes) {
    if (ArrayUtils.isEmpty(routes)) {
      return;
    }
    Set<Integer> keys = new HashSet<>();
    keys.forEach(key ->
        tokenBuckets.remove(key));
  }

  protected void cleanTokenBuckets() {
    tokenBuckets.clear();
  }

  public static class Config {

    private int capacity;
    private int refillTokens;
    private int refillPeriod;
    private String refillUnit;

    public Config() {
      super();
    }

    public Config(int capacity, int refillTokens, int refillPeriod, String refillUnit) {
      super();
      this.capacity = capacity;
      this.refillTokens = refillTokens;
      this.refillPeriod = refillPeriod;
      this.refillUnit = refillUnit;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
      return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
      this.capacity = capacity;
    }

    /**
     * @return the refillTokens
     */
    public int getRefillTokens() {
      return refillTokens;
    }

    /**
     * @param refillTokens the refillTokens to set
     */
    public void setRefillTokens(int refillTokens) {
      this.refillTokens = refillTokens;
    }

    /**
     * @return the refillPeriod
     */
    public int getRefillPeriod() {
      return refillPeriod;
    }

    /**
     * @param refillPeriod the refillPeriod to set
     */
    public void setRefillPeriod(int refillPeriod) {
      this.refillPeriod = refillPeriod;
    }

    /**
     * @return the refillUnit
     */
    public String getRefillUnit() {
      return refillUnit;
    }

    /**
     * @param refillUnit the refillUnit to set
     */
    public void setRefillUnit(String refillUnit) {
      this.refillUnit = refillUnit;
    }

    public TimeUnit parsedRefillUnit() {
      if (StringUtils.isBlank(refillUnit)) {
        return TimeUnit.SECONDS;
      }
      try {
        return TimeUnit.valueOf(refillUnit.toUpperCase());
      } catch (Exception e) {
        return TimeUnit.SECONDS;
      }
    }

  }
}
