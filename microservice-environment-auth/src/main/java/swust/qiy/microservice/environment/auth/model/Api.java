package swust.qiy.microservice.environment.auth.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import swust.qiy.microservice.core.constant.StatusConstant;

/**
 * @author lizw@primeton.com
 */
@Data
@TableName("api_info")
public class Api {
	/**
	 * 唯一标识
	 */
	private Integer id;

	/**
	 * API名称
	 */
	private String name;

	/**
	 * 请求协议
	 */
	private String protocol;

	/**
	 * HTTP Method
	 */
	private String method;

	/**
	 * 请求路径
	 */
	private String path;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 类型:内网Intranet/外网Internet
	 */
	private Byte type;

	/**
	 * 是否发布, 0:未发布, 1:发布
	 */
	private Byte status = StatusConstant.DISABLE;

	/**
	 * 是否失效, 0:有效, 1:失效
	 */
	private Boolean invalid = false;

	/**
	 * 微服务版本标识
	 */
	private Integer serviceVersionId;

	/**
	 * 所属微服务
	 */
	private Integer microserviceId;
}
