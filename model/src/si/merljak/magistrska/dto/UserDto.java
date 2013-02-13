package si.merljak.magistrska.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 644407345016869943L;

	private String username;
	private String name;
	private String email;
	private String metadata;

	UserDto() {}

	public UserDto(String username, String name, String email, String metadata) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.metadata = metadata;
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

	public String getMetadata() {
		return metadata;
	}
}
