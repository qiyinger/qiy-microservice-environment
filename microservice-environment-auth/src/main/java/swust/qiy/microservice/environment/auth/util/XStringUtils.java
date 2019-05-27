/**
 * 
 */
package swust.qiy.microservice.environment.auth.util;

import java.util.Collection;
import java.util.stream.Stream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author lizw@primeton.com
 */
public class XStringUtils {
	
	private XStringUtils() {
	}
	
	public static String[] split(String str, String regex) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(regex)) {
			return new String[0];
		}
		return Stream.of(str.split(regex)).filter(e -> StringUtils.isNotBlank(e)).map(e -> e.trim()).toArray(String[] :: new);
	}
	
	public static String toString(Collection<?> elements) {
		return toString(elements, null, true);
	}
	
	public static String toString(Collection<?> elements, String separator, boolean skipNullElement) {
		if (CollectionUtils.isEmpty(elements)) {
			return null;
		}
		separator = null == separator ? "," : separator; //$NON-NLS-1$
		StringBuffer sb = new StringBuffer();
		for (Object e : elements) {
			String snippet = null;
			if (null == e) {
				if (skipNullElement) {
					continue;
				}
				snippet = "null";
			} else {
				snippet = e instanceof String ? (String)e : e.toString();
			}
			sb.append(snippet).append(separator);
		}
		return sb.substring(0, sb.length() - separator.length());
	}

}
