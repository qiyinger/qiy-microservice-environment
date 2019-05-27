package swust.qiy.microservice.environment.auth.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * @author lizw@primeton.com
 */
@Data
@ToString
@TableName("system_info")
public class BizSystem {

	private Integer id;
	
	private String code;
	
	private String name;
	
	private String userId;
	
	private String status;
	
	private Date createTime;
	
	private String description;

	private String rsskey;
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 逻辑删除标识, 0:未删除;1:已删除
	 */
	@TableLogic
	private Boolean isDeleted;

}
