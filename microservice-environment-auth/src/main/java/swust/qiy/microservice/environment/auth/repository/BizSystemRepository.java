package swust.qiy.microservice.environment.auth.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import swust.qiy.microservice.core.dao.BaseDao;
import swust.qiy.microservice.environment.auth.model.BizSystem;

/**
 * @author lizw@primeton.com
 */
@Mapper
public interface BizSystemRepository extends BaseDao<BizSystem> {

	@Select(value = "select * from system_info where rsskey = #{rsskey}")
	BizSystem findRsskey(String rsskey);
	
}
