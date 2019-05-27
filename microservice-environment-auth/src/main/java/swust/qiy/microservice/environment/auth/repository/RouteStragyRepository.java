package swust.qiy.microservice.environment.auth.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import swust.qiy.microservice.core.dao.BaseDao;
import swust.qiy.microservice.environment.auth.model.RouteStrategy;
import swust.qiy.microservice.environment.auth.model.StrategyIp;

/**
 * @author qiying
 * @create 2019/5/11
 */
@Mapper
public interface RouteStragyRepository extends BaseDao<RouteStrategy> {

  @Select("select * from route where route_id = #{routeId}")
  List<RouteStrategy> findByRouteId(Integer routeId);

}
