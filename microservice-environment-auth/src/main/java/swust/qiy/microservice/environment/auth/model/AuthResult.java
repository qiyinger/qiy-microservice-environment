package swust.qiy.microservice.environment.auth.model;

/**
 * Copy to gateway project.
 * 
 * @author lizw@primeton.com
 */
public class AuthResult {
	
	public static enum Type {
		Auth, BlackList, WhiteList, Unkown;
	}
	
	public static final AuthResult INVALIDA_ARGUMENT = new AuthResult(false, "Error, invalid argument.", //$NON-NLS-1$
			Type.Unkown.name());
	
	public static final AuthResult SERVER_INTERNAL_ERROR = new AuthResult(false, "Error, server internal error.", //$NON-NLS-1$
			Type.Unkown.name());
	
	public static final AuthResult MATCH_BLACK_LIST = new AuthResult(false, "Forbidden, match black list.", //$NON-NLS-1$
			Type.BlackList.name());
	
	public static final AuthResult MATCH_WHITE_LIST = new AuthResult(true, "Allowed, match white list.", //$NON-NLS-1$
			Type.WhiteList.name());
	
	public static final AuthResult AUTH_PASS = new AuthResult(true, "Allowed, api authentication passed.", //$NON-NLS-1$
			Type.Auth.name());
	
	public static final AuthResult AUTH_FAILED = new AuthResult(false, "Denied, api authentication failed.", //$NON-NLS-1$
			Type.Auth.name());

	public static final AuthResult INVALID_CLIENT_ID =  new AuthResult(false, "Denied, invalid clientId.", //$NON-NLS-1$
			Type.Auth.name());;

	private boolean ok;

	private String cause;

	private String type;
	
	public AuthResult() {
		super();
	}

	public AuthResult(boolean ok, String cause, String type) {
		super();
		this.ok = ok;
		this.cause = cause;
		this.type = type;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AuthResult [ok=" + ok + ", cause=" + cause + ", type=" + type + "]";
	}

}
