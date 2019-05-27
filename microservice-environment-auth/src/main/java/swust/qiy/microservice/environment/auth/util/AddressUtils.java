package swust.qiy.microservice.environment.auth.util;

import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author lizw@primeton.com
 */
public class AddressUtils {

	private AddressUtils() {
	}
	
	public static boolean matchAddress(Collection<String> patterns, String ip) {
		if (CollectionUtils.isEmpty(patterns) || StringUtils.isBlank(ip)) {
			return false;
		}
		for (String pattern : patterns) {
			boolean match = matchAddress(pattern, ip);
			if (match) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean matchAddress(String[] patterns, String ip) {
		if (ArrayUtils.isEmpty(patterns) || StringUtils.isBlank(ip)) {
			return false;
		}
		for (String pattern : patterns) {
			boolean match = matchAddress(pattern, ip);
			if (match) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean matchAddress(String pattern, String ip) {
		if (StringUtils.isBlank(pattern) || StringUtils.isBlank(ip)) {
			return false;
		}
		if (StringUtils.equals(pattern, ip)) {
			return true;
		}
		if (pattern.endsWith(".*") && pattern.length() > 2) { //$NON-NLS-1$
			int index = ip.lastIndexOf("."); //$NON-NLS-1$
			if (index > 0 && StringUtils.equals(pattern.substring(0, pattern.length() - 2), ip.substring(0, index))) {
				return true;
			}
		}
		if (pattern.charAt(pattern.length() - 1) == '*' && ip.startsWith(pattern.substring(0, pattern.length() - 1))) { //$NON-NLS-1$
			return true;
		}
		return false;
	}
	
	public static boolean matchDomain(Collection<String> patterns, String domain) {
		if (CollectionUtils.isEmpty(patterns) || StringUtils.isBlank(domain)) {
			return false;
		}
		for (String pattern : patterns) {
			boolean match = matchDomain(pattern, domain);
			if (match) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean matchDomain(String[] patterns, String domain) {
		if (ArrayUtils.isEmpty(patterns) || StringUtils.isBlank(domain)) {
			return false;
		}
		for (String pattern : patterns) {
			boolean match = matchDomain(pattern, domain);
			if (match) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean matchDomain(String pattern, String domain) {
		if (StringUtils.isBlank(pattern) || StringUtils.isBlank(domain)) {
			return false;
		}
		if (StringUtils.equals(pattern, domain)) {
			return true;
		}
		if (pattern.length() > 1 && pattern.charAt(0) == '*' && domain.endsWith(pattern.substring(1))) { //$NON-NLS-1$
			return true;
		}
		if (pattern.length() > 2 && pattern.startsWith("*.")) { //$NON-NLS-1$
			int index = domain.indexOf("."); //$NON-NLS-1$
			if (index > 0 && index < domain.length() - 2 && StringUtils.equals(pattern.substring(2), domain.substring(index + 1))) {
				return true;
			}
		}
		return false;
	}
	
}
