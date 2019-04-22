package swust.qiy.microservice.gateway.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import swust.qiy.microservice.core.dao.BaseDao;
import swust.qiy.microservice.gateway.entity.RouteInfo;

/**
 * @author qiying
 * @create 2019/4/8
 */
@Mapper
public interface RouteDao extends BaseDao<RouteInfo> {

  @Update("update route set status = #{status} where gateway_id = #{gatewayId}")
  void updateAllStatus(int gatewayId, int status);

}
