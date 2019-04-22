package swust.qiy.microservice.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swust.qiy.microservice.gateway.dao.RouteDao;
import swust.qiy.microservice.gateway.entity.RouteInfo;
import swust.qiy.microservice.gateway.route.RepositoryUtil;
import swust.qiy.microservice.gateway.util.RouteConvertUtil;

/**
 * @author qiying
 * @create 2019/4/4
 */
@RestController
public class Test {

  @Autowired
  private RepositoryUtil repositoryUtil;

  @Autowired
  private RouteDao routeDao;

  @RequestMapping("/route")
  public void test() {
    RouteInfo routeInfo = routeDao.selectById(1);
    RouteDefinition routeDefinition = RouteConvertUtil.getRouteDefinition(routeInfo);
    System.out.println(routeDefinition);
    repositoryUtil.save(routeDefinition, true);
  }

}
