package swust.qiy.microservice.environment.auth.model;

import java.time.LocalDateTime;
import lombok.Data;
import swust.qiy.microservice.core.constant.StatusConstant;

@Data
public class ApiApply {

  /**
   * 唯一标识
   */
  private Integer id;

  /**
   * 申请人
   */
  private String applicant;

  /**
   * 审核状态,0:打回;1:等待审核;2:通过
   */
  private Byte status = StatusConstant.CHECK_PENDING;

  /**
   * 申请类型:service(微服务),api(接口)
   */
  private Byte type;

  /**
   * 申请时间
   */
  private LocalDateTime applyTime;

  /**
   * 描述
   */
  private String description;

  /**
   * 审核人
   */
  private String auditorName;

  /**
   * 接口/微服务版本标识
   */
  private Integer asId;

  /**
   * 修改时间
   */
  private LocalDateTime modifyTime;

  private Integer systemId;

}