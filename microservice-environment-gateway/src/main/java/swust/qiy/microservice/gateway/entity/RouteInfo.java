package swust.qiy.microservice.gateway.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;
import swust.qiy.microservice.core.constant.StatusConstant;

@Data
@TableName("route")
public class RouteInfo {

  /**
   * 唯一标识
   */
  private Integer id;

  /**
   * 路由名称
   */
  private String name;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  /**
   * 描述
   */
  private String description;

  /**
   * 发布状态: 0:未发布, 1:已发布, 2:正在发布, 3: 正在停止
   */
  private Byte status = StatusConstant.PUBLISH_PENDING;

  /**
   * 所属网关标识
   */
  private Integer gatewayId;

  /**
   * 绑定类型
   */
  private Byte bindType;

  /**
   * 绑定id
   */
  private Integer bindId;

  /**
   * 路由编码
   */
  private String code;

  /**
   * 路由地址
   */
  private String uri;

  /**
   * 过滤器
   */
  private String filters;

  /**
   * 断言工厂
   */
  private String predicates;

  /**
   * 优先级
   */
  private Integer priority;

}