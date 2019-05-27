package swust.qiy.microservice.environment.auth.util;

import java.util.UUID;

/**
 * @author lizw@primeton.com
 */
public class IDUtils {
	
	private IDUtils() {
	}
	
	public static String newId(String tableName) {
		return newId();
	}

	public static String newId() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
