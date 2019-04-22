package swust.qiy.microservice.gateway.entity;

import lombok.Data;

@Data
public class StrategyIp {

  /**
   * 唯一标识
   */
  private Integer id;

  /**
   * 策略名称
   */
  private String name;

  /**
   * 控制类型:黑名单black/白名单white
   */
  private Byte type;

  /**
   * 描述
   */
  private String description;

  /**
   * ip列表
   */
  private String ipList;

  private Integer gatewayId;

}