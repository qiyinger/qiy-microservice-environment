package swust.qiy.microservice.environment.auth.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizw@primeton.com
 */
public class Permission {
	
	private BizSystem client;
	
	private List<Api> permissions;

	public Permission() {
		super();
	}

	public Permission(BizSystem client, List<Api> permissions) {
		super();
		this.client = client;
		this.permissions = permissions;
	}

	public BizSystem getClient() {
		return client;
	}

	public void setClient(BizSystem client) {
		this.client = client;
	}

	public List<Api> getPermissions() {
		if (null == permissions) {
			permissions = new ArrayList<>();
		}
		return permissions;
	}

	public void setPermissions(List<Api> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Permission [client=" + client + ", permissions=" + permissions + "]";
	}

}
