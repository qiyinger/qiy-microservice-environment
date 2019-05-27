package swust.qiy.microservice.environment.auth.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import swust.qiy.microservice.environment.auth.model.ApiApply;

/**
 * @author qiying
 * @create 2019/5/11
 */
@Mapper
public interface ApiApplyRepository {

  @Select("select * from api_apply where system_id = #{systemId}")
  List<ApiApply> findBySystemId(Integer systemId);

}
