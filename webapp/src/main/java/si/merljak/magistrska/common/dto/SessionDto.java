package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.Date;

public class SessionDto implements Serializable {

	private static final long serialVersionUID = -3760441446058253929L;

	private String sessionId;
	private Date expires;
	private UserDto user;

	SessionDto() {}

	public SessionDto(String sessionId, Date expires, UserDto user) {
		this.sessionId = sessionId;
		this.expires = expires;
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Date getExpires() {
		return expires;
	}

	public UserDto getUser() {
		return user;
	}
}
