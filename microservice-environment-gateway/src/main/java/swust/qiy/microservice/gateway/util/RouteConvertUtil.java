package swust.qiy.microservice.gateway.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import swust.qiy.microservice.core.enums.TimeUnitEnum;
import swust.qiy.microservice.gateway.entity.RouteInfo;
import swust.qiy.microservice.gateway.entity.StrategyCall;

/**
 * @author qiying
 * @create 2019/4/9
 */
@Slf4j
public class RouteConvertUtil {

  public static final String ARG_SPLIT = ",";
  public static final String ITEM_SPLIT = ";";

  public static RouteDefinition getRouteDefinition(RouteInfo routeInfo) {
    return new RouteDefinition() {
      {
        setId(routeInfo.getCode());
        setUri(URI.create(routeInfo.getUri()));
        setFilters(string2FilterDefinition(routeInfo.getFilters()));
        setPredicates(string2PredicateDefinition(routeInfo.getPredicates()));
        if (routeInfo.getPriority() != null) {
          setOrder(routeInfo.getPriority());
        }
      }
    };
  }

  public static List<FilterDefinition> string2FilterDefinition(String filterStr) {
    List<FilterDefinition> list = new ArrayList<>();
    if (StringUtils.isBlank(filterStr)) {
      return list;
    }
    String[] items = filterStr.split(";");
    for (String item : items) {
      list.add(getFilterDefinition(filterStr));
    }
    return list;
  }

  public static FilterDefinition getFilterDefinition(String text) {
    int eqIdx = text.indexOf("=");
    if (eqIdx <= 0) {
      return new FilterDefinition(text);
    }
    String name = text.substring(0, eqIdx);
    if ("RequestRateLimiter".equalsIgnoreCase(name)) {
      FilterDefinition filterDefinition = new FilterDefinition();
      filterDefinition.setName(name);
      String[] args = org.springframework.util.StringUtils
          .tokenizeToStringArray(text.substring(eqIdx + 1), ",");
      for (String arg : args) {
        String[] kv = arg.split(":");
        if (kv.length != 2) {
          log.error("RateLimitFilter arg: {} is invalid, skip", arg);
          continue;
        }
        if (StringUtils.isBlank(kv[0]) || StringUtils.isBlank(kv[1])) {
          log.error("RateLimitFilter arg: {} is invalid, skip", arg);
          continue;
        }
        filterDefinition.getArgs().put(kv[0], kv[1]);
      }
      return filterDefinition;
    } else {
      return new FilterDefinition(text);
    }
  }

  public static List<PredicateDefinition> string2PredicateDefinition(String predicateStr) {
    List<PredicateDefinition> list = new ArrayList<>();
    if (StringUtils.isBlank(predicateStr)) {
      return list;
    }
    String[] items = predicateStr.split(";");
    for (String item : items) {
      list.add(new PredicateDefinition(item));
    }
    return list;
  }

  public static FilterDefinition getRedidsLimitFilterDefinition(StrategyCall strategyCall) {
    FilterDefinition filter = new FilterDefinition("RequestRateLimiter");
    filter.addArg("key-resolver", "#{@routeKeyResolver}");
    filter.addArg("rate-limiter", "#{@tokenBucketRateLimiter}");
    filter.addArg("tokenbucket-rate-limiter.capacity", String.valueOf(strategyCall.getRateLimit()));
    filter.addArg("tokenbucket-rate-limiter.refillTokens", String.valueOf(strategyCall.getRateLimit()));
    filter.addArg("tokenbucket-rate-limiter.refillPeriod", String.valueOf(strategyCall.getTimePeriod()));
    filter.addArg("tokenbucket-rate-limiter.refillUnit",
        (TimeUnitEnum.valueOf(strategyCall.getTimeUnit()).name()));
    return filter;
  }

  public static void addRateLimiter4RouteInfo(RouteInfo routeInfo, StrategyCall strategyCall) {
    String filters = routeInfo.getFilters();
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(filters).append("RequestRateLimiter=")
        .append("key-resolver:").append("#{@routeKeyResolver},")
        .append("rate-limiter").append("#{@tokenBucketRateLimiter},")
        .append("tokenbucket-rate-limiter.capacity").append(String.valueOf(strategyCall.getRateLimit())).append(",")
        .append("tokenbucket-rate-limiter.refillTokens").append(String.valueOf(strategyCall.getRateLimit())).append(",")
        .append("tokenbucket-rate-limiter.refillPeriod").append(String.valueOf(strategyCall.getTimePeriod())).append(",")
        .append("tokenbucket-rate-limiter.refillUnit").append(TimeUnitEnum.valueOf(strategyCall.getTimeUnit()).name())
        .append(";");
    routeInfo.setFilters(stringBuffer.toString());
  }

  public static void main(String[] args) {
    System.out.println(new FilterDefinition("RequestRateLimiter=routeKeyResolver,tokenBucketRateLimiter"));
  }

}
