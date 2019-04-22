package swust.qiy.microservice.gateway.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import swust.qiy.microservice.core.util.CommonUtil;
import swust.qiy.microservice.gateway.entity.RouteStrategy;

/**
 * @author qiying
 */
@Data
@Accessors(chain = true)
public class RouteStrategyQuery {

  private Integer id;
  private List<Integer> ids;
  private Integer routeId;
  private List<Integer> routeIds;
  private Integer strategyId;
  private List<Integer> strategyIds;
  private Byte type;

  public QueryWrapper<RouteStrategy> toQueryWrapper() {
    QueryWrapper<RouteStrategy> queryWrapper = new QueryWrapper<>();
    if (!CommonUtil.isEmpty(id)) {
      queryWrapper.eq("id", id);
    }
    if (!CommonUtil.isEmpty(ids)) {
      queryWrapper.in("id", ids);
    }
    if (!CommonUtil.isEmpty(routeId)) {
      queryWrapper.eq("route_id", routeId);
    }
    if (!CommonUtil.isEmpty(routeIds)) {
      queryWrapper.in("route_id", routeIds);
    }
    if (!CommonUtil.isEmpty(strategyId)) {
      queryWrapper.eq("strategy_id", strategyId);
    }
    if (!CommonUtil.isEmpty(strategyIds)) {
      queryWrapper.in("strategy_id", strategyIds);
    }
    if (!CommonUtil.isEmpty(type)) {
      queryWrapper.eq("type", type);
    }
    return queryWrapper;
  }

  public static RouteStrategyQuery getInstance() {
    return new RouteStrategyQuery();
  }
}