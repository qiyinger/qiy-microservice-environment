package swust.qiy.microservice.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author qiying
 * @create 2019/5/18
 */
public class XRoutePredicateHandlerMapping extends RoutePredicateHandlerMapping {

  @Autowired
  private SetOperations setOperations;

  public XRoutePredicateHandlerMapping(FilteringWebHandler webHandler, RouteLocator routeLocator) {
    super(webHandler, routeLocator);
  }

  @Override
  protected void validateRoute(Route route, ServerWebExchange exchange) {
    // 检查白名单
  }
}
