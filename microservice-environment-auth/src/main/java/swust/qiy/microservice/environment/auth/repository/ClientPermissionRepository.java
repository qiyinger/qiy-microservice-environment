package swust.qiy.microservice.environment.auth.repository;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import swust.qiy.microservice.environment.auth.AuthConstants;
import swust.qiy.microservice.environment.auth.model.ApiApply;
import swust.qiy.microservice.environment.auth.model.BizSystem;
import swust.qiy.microservice.environment.auth.model.Permission;

/**
 * @author lizw@primeton.com
 */
@Repository
public class ClientPermissionRepository {

	// @PersistenceContext
	// private EntityManager entityManager;
	
	@Resource
	private BizSystemRepository bizSystemRepository;

	@Resource
	private ApiRepository apiRepository;
	@Resource
	private ApiApplyRepository apiApplyRepository;
	
	public Permission getPermission(String rsskey) {
		if (StringUtils.isBlank(rsskey)) {
			return null;
		}
		BizSystem client = bizSystemRepository.findRsskey(rsskey);
		if (null == client) {
			throw new RuntimeException(String.format("Invalid RSSKEY %s.", rsskey));
		}
		
		Permission permission = new Permission();
		permission.setClient(client);

		List<ApiApply> apiApplies = apiApplyRepository.findBySystemId(client.getId());
		if (CollectionUtils.isEmpty(apiApplies)) {
			permission.setPermissions(new ArrayList<>());
		} else {
			permission.setPermissions(apiRepository.selectBatchIds());
		}



		permission.setPermissions(apiRepository.findByClientId(client.getId(), AuthConstants.ApiStatus.STATUS_OK.getStatus()));
		return permission;
	}
	
}
