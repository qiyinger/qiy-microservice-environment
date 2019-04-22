package swust.qiy.microservice.gateway.entity;

import lombok.Data;

@Data
public class StrategyCall {

  /**
   * 唯一标识
   */
  private Integer id;

  /**
   * 策略名称
   */
  private String name;

  /**
   * 描述
   */
  private String description;

  /**
   * 时间单位，参考JAVA TimeUnit枚举类型
   */
  private Byte timeUnit;

  /**
   * 时间长度
   */
  private Integer timePeriod;

  /**
   * 访问次数限制
   */
  private Integer rateLimit;

  /**
   * 网关id
   */
  private Integer gatewayId;

}