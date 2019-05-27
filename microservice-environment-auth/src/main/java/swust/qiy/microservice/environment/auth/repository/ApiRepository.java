package swust.qiy.microservice.environment.auth.repository;

import org.apache.ibatis.annotations.Mapper;
import swust.qiy.microservice.core.dao.BaseDao;
import swust.qiy.microservice.environment.auth.model.Api;

/**
 * @author qiying
 * @create 2019/5/11
 */
@Mapper
public interface ApiRepository extends BaseDao<Api> {

}
