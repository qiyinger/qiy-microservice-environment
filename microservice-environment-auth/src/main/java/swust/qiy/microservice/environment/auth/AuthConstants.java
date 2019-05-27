package swust.qiy.microservice.environment.auth;

/**
 * 
 * @author lizw@primeton.com
 */
public interface AuthConstants {

	String API_PREFIX = "/api/";
	
	String TABLE_PEFIX = "T_CMSP_";

	enum StrategyType {
		
		BLACK_LIST, WHITE_LIST;
		
	}
	
	enum ApiStatus {
		
		STATUS_OK("0"),
		STATUS_INVALID("1")
		;
		
		private String status;

		private ApiStatus(String status) {
			this.status = status;
		}

		public String getStatus() {
			return status;
		}

	}
	
}
