package swust.qiy.microservice.gateway.entity;

import lombok.Data;

@Data
public class RouteStrategy {

  private Integer id;

  /**
   * 路由id
   */
  private Integer routeId;

  /**
   * 调用策略id
   */
  private Integer strategyId;

  /**
   * 策略类型: 0-IP, 1-限流
   */
  private Byte type;

  public RouteStrategy() {
  }

  public RouteStrategy(Integer routeId, Integer strategyId, Byte type) {
    this.routeId = routeId;
    this.strategyId = strategyId;
    this.type = type;
  }
}