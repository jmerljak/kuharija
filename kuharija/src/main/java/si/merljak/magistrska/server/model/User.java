package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	private byte[] password;

	@NotNull
	private byte[] salt;

	@NotNull
	private String name;

	private String email;

	private String preferences;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "bookmark", inverseJoinColumns = @JoinColumn(name = "recipe_id"))
	private Set<Recipe> bookmarks;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	private Set<Session> sessions;

	@Version
	private Date updated;

	protected User() {}

	public User(String username, byte[] password, byte[] salt, String name, String email) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.name = name;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public byte[] getPassword() {
		return password;
	}

	public byte[] getSalt() {
		return salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences; 
	}

	public Set<Recipe> getBookmarks() {
		return bookmarks;
	}

	public void addBookmarks(Recipe recipe, boolean add) {
		if (add) {
			bookmarks.add(recipe);
		} else {
			bookmarks.remove(recipe);
		}
	}

	public Date getUpdated() {
		return updated;
	}
}
