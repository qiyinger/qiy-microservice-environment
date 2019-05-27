package swust.qiy.microservice.environment.auth.util;

import java.util.UUID;
import org.springframework.util.Base64Utils;

/**
 * @author lizw@primeton.com
 */
public class RsskeyUtils {

	private RsskeyUtils() {
	}
	
	public static String newRsskey(String clientId) {
		String key = newRsskey() + System.currentTimeMillis(); // + clientId;
		return Base64Utils.encodeToString(key.getBytes());
	}
	
	public static String newRsskey() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
