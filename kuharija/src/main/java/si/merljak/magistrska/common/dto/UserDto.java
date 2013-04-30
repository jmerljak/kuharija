package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 644407345016869943L;

	private String username;
	private String name;
	private String email;
	private String preferences;

	UserDto() {}

	@QueryProjection
	public UserDto(String username, String name, String email, String preferences) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.preferences = preferences;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPreferences() {
		return preferences;
	}
}
