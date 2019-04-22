package swust.qiy.microservice.gateway.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import swust.qiy.microservice.core.util.CommonUtil;
import swust.qiy.microservice.gateway.entity.RouteInfo;

/**
 * @author qiying
 */
@Data
@Accessors(chain = true)
public class RouteInfoQuery {

  private Integer id;
  private List<Integer> ids;
  private String name;
  private Byte status;
  private Integer gatewayId;
  private Byte bindType;
  private Integer bindId;
  private List<Integer> bindIds;
  private String code;

  public QueryWrapper<RouteInfo> toQueryWrapper() {
    QueryWrapper<RouteInfo> queryWrapper = new QueryWrapper<>();
    if (!CommonUtil.isEmpty(id)) {
      queryWrapper.eq("id", id);
    }
    if (!CommonUtil.isEmpty(ids)) {
      queryWrapper.in("id", ids);
    }
    if (!CommonUtil.isEmpty(name)) {
      queryWrapper.eq("name", name);
    }
    if (!CommonUtil.isEmpty(status)) {
      queryWrapper.eq("status", status);
    }
    if (!CommonUtil.isEmpty(gatewayId)) {
      queryWrapper.eq("gateway_id", gatewayId);
    }
    if (!CommonUtil.isEmpty(bindType)) {
      queryWrapper.eq("bind_type", bindType);
    }
    if (!CommonUtil.isEmpty(bindId)) {
      queryWrapper.eq("bind_id", bindId);
    }
    if (!CommonUtil.isEmpty(bindIds)) {
      queryWrapper.in("bind_id", bindIds);
    }
    if (!CommonUtil.isEmpty(code)) {
      queryWrapper.eq("code", code);
    }
    return queryWrapper;
  }

  public static RouteInfoQuery getInstance() {
    return new RouteInfoQuery();
  }
}