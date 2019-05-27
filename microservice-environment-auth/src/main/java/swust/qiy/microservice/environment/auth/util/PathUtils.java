package swust.qiy.microservice.environment.auth.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * @author lizw@primeton.com
 */
public class PathUtils {
	
	public static final String PATH_PARAM_PATTERN_DEFAULT = "[0-9a-zA-Z_-]+";
	public static final String PATH_PARAM_PATTERN_NUMBER = "[0-9]+";
	public static final String PATH_PARAM_PATTERN_WORD = "[a-zA-Z_-]+";
	
	
	private static final String separatorBegin = "{";
	private static final String separatorEnd = "}";

	private PathUtils() {
	}
	
	public static boolean match(String pathPattern, String targetPath) {
		return match(pathPattern, targetPath, PATH_PARAM_PATTERN_DEFAULT);
	}
	
	public static boolean match(String pathPattern, String targetPath, String paramPattern) {
		if (StringUtils.equals(pathPattern, targetPath)) {
			return true;
		}
		if (StringUtils.isBlank(pathPattern) || StringUtils.isBlank(targetPath)) {
			return false;
		}
		int index = targetPath.indexOf("?"); //$NON-NLS-1$
		if (index > 0) {
			targetPath = targetPath.substring(0, index);
		}
		
		paramPattern = StringUtils.isBlank(paramPattern) ? PATH_PARAM_PATTERN_DEFAULT : paramPattern;
		
		List<String> pathParts = new ArrayList<>();
		String str = pathPattern;
		int indexEnd = -1;
		while (str.contains(separatorBegin) && str.contains(separatorEnd)) {
			int indexBegin = str.indexOf(separatorBegin);
			indexEnd = str.indexOf(separatorEnd);
			if (indexBegin > indexEnd) {
				return false;
			}
			pathParts.add(str.substring(0, indexBegin));
			pathParts.add(paramPattern);
			if (indexEnd == str.length() - 1) {
				str = null;
				indexEnd = -1;
				indexBegin = -1;
				break;
			}
			str = str.substring(indexEnd + 1);
		}
		
		if (null != str) {
			pathParts.add(str);
		}
		
		if (pathParts.isEmpty()) {
			return false;
		}
		
		StringBuffer regex = new StringBuffer();
		pathParts.forEach(e -> regex.append(e));
		
		return Pattern.matches(regex.toString(), targetPath);
	}
	
}
