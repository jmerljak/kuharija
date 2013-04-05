package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User implements Serializable {

	private static final long serialVersionUID = 1079761133290324147L;

	@Id
	@Size(max = 40)
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String name;

	private String email;

	private String metadata;

	@Version
	private Date updated;

	protected User() {}

	public User(String username, String password, String name, String email) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
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

	public void setMetadata(String metadata) {
		this.metadata = metadata; 
	}

	public String getMetadata() {
		return metadata;
	}

	public Date getUpdated() {
		return updated;
	}
}