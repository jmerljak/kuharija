package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
// TODO remove this class, bookmark is just a many-to-many recipe-user relation
public class Bookmark implements Serializable {

	private static final long serialVersionUID = 1898355529967987383L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private User user;

	@NotNull
	@ManyToOne
	private Recipe recipe;

	protected Bookmark() {}

	public Bookmark(User user, Recipe recipe) {
		this.user = user;
		this.recipe = recipe;
	}

	public User getUser() {
		return user;
	}

	public Recipe getRecipe() {
		return recipe;
	}
}
