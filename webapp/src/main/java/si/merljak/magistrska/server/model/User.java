package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private Byte[] password;

	@NotNull
	private Byte[] salt;

	@NotNull
	private String name;

	private String email;

	private String preferences;

	@ManyToMany
	@JoinTable(name = "bookmark", inverseJoinColumns = @JoinColumn(name = "recipe_id"))
	private Set<Recipe> bookmarks;

	@Version
	private Date updated;

	protected User() {}

	public User(String username, Byte[] password, Byte[] salt, String name, String email) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.name = name;
		this.email = email;
	}

	public String getUsername() {
		return username;
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

	public Date getUpdated() {
		return updated;
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
}
