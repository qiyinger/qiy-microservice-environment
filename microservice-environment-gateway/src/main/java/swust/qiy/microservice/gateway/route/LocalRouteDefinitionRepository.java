package swust.qiy.microservice.gateway.route;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author qiying
 * @create 2019/4/8
 */
@Component
public class LocalRouteDefinitionRepository implements RouteDefinitionRepository {

  private final Map<String, RouteDefinition> routes = Collections.synchronizedMap(new LinkedHashMap());

  @Override
  public Mono<Void> save(Mono<RouteDefinition> route) {
    route.subscribe(routeDefinition -> this.routes.put(routeDefinition.getId(), routeDefinition));
    return Mono.empty();
  }

  public Mono<Void> save(Flux<RouteDefinition> route) {
    route.collectMap(RouteDefinition::getId).subscribe(this.routes::putAll);
    return Mono.empty();
  }

  @Override
  public Mono<Void> delete(Mono<String> routeId) {
    return routeId.flatMap((id) -> {
      if (this.routes.containsKey(id)) {
        this.routes.remove(id);
        return Mono.empty();
      } else {
        return Mono.defer(() -> {
          return Mono.error(new NotFoundException("RouteDefinition not found: " + routeId));
        });
      }
    });
  }

  @Override
  public Flux<RouteDefinition> getRouteDefinitions() {
    return Flux.fromIterable(this.routes.values());
  }

  public boolean contain(String code) {
    return routes.containsKey(code);
  }

  public RouteDefinition getRouteDefinition(String code) {
    return routes.get(code);
  }

  public Map<String, RouteDefinition> getRouteDefinitionMap() {
    return this.routes;
  }

  public void clear() {
    this.routes.clear();
  }

}
