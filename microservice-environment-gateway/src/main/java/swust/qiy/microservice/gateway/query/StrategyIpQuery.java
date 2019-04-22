package swust.qiy.microservice.gateway.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import swust.qiy.microservice.core.util.CommonUtil;
import swust.qiy.microservice.gateway.entity.StrategyIp;

/**
 * @author qiying
 */
@Data
@Accessors(chain = true)
public class StrategyIpQuery {

  private Integer id;
  private List<Integer> ids;
  private String name;
  private Byte type;
  private String description;
  private Integer gatewayId;

  public QueryWrapper<StrategyIp> toQueryWrapper() {
    QueryWrapper<StrategyIp> queryWrapper = new QueryWrapper<>();
    if (!CommonUtil.isEmpty(id)) {
      queryWrapper.eq("id", id);
    }
    if (!CommonUtil.isEmpty(ids)) {
      queryWrapper.in("id", ids);
    }
    if (!CommonUtil.isEmpty(name)) {
      queryWrapper.eq("name", name);
    }
    if (!CommonUtil.isEmpty(type)) {
      queryWrapper.eq("type", type);
    }
    if (!CommonUtil.isEmpty(description)) {
      queryWrapper.eq("description", description);
    }
    if (!CommonUtil.isEmpty(gatewayId)) {
      queryWrapper.eq("gateway_id", gatewayId);
    }
    return queryWrapper;
  }

  public static StrategyIpQuery getInstance() {
    return new StrategyIpQuery();
  }

}