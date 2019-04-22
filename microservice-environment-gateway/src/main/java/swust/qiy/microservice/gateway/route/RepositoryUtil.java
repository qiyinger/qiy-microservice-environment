package swust.qiy.microservice.gateway.route;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author qiying
 * @create 2019/4/8
 */
@Component
public class RepositoryUtil implements ApplicationEventPublisherAware {

  private static final Log log = LogFactory.getLog(RepositoryUtil.class);

  private ApplicationEventPublisher publisher;
  @Autowired
  private RouteLocator cachedCompositeRouteLocator;

  @Autowired
  private LocalRouteDefinitionRepository repository;



  public void save(RouteDefinition route, boolean refresh) {
    if (null == route || null == route.getId() || route.getId().trim().isEmpty()) {
      return;
    }
    repository.save(Mono.just(route));
    if (refresh) {
      refresh();
    }
    log.info("Save route: " + route);
  }

  public void save(List<RouteDefinition> list, boolean refresh) {
    RouteDefinition[] routes = list.toArray(new RouteDefinition[list.size()]);
    Flux.just(routes).subscribe(routeDefinition -> save(routeDefinition, false));
    if (refresh) {
      refresh();
    }
  }

  public void delete(String routeId, boolean refresh) {
    if (null == routeId || routeId.trim().isEmpty()) {
      return;
    }
    Map<String, RouteDefinition> routes = getRouteDefinition();
    if (routes.containsKey(routeId)) {
      routes.remove(routeId);
      log.info("Delete route: " + routeId);
      if (refresh) {
        refresh();
      }
    }
  }

  public void delete(String[] routes, boolean refresh) {
    if (null == routes || 0 == routes.length) {
      return;
    }
    for (String routeId : routes) {
      delete(routeId, false);
    }
    if (refresh) {
      refresh();
    }
  }

  public void clear(boolean refresh) {
    getRouteDefinition().clear();
    log.info("Clear route.");
    if (refresh) {
      refresh();
    }
  }

  public void refresh() {
    publisher.publishEvent(new RefreshRoutesEvent(this));
    log.info("Refresh route.");
  }

  public boolean contain(String code) {
    return repository.contain(code);
  }

  public RouteDefinition getRouteDefinition(String code) {
    return repository.getRouteDefinition(code);
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  protected Map<String, RouteDefinition> getRouteDefinition() {
    return repository.getRouteDefinitionMap();
  }

  public void reloadBeanDefinition(List<RouteDefinition> routeDefinitions) {
    repository.clear();
    repository.save(Flux.fromIterable(routeDefinitions));
    publisher.publishEvent(new RefreshRoutesEvent(this));
  }

}
