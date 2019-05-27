package swust.qiy.microservice.environment.auth.model;

import lombok.Data;
import lombok.ToString;

/**
 * Copy to gateway project.
 * 
 * @author lizw@primeton.com
 */
@Data
@ToString
public class AuthArgument {
	
	private Integer routeId;

	private String uri;
	
	private String method;
	
	private String remoteAddr;
	
	private String remoteHost;
	
	private String rsskey;
	
	private String clientId;

}
