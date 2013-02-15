package si.merljak.magistrska.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1898355529967987383L;

	@Id @GeneratedValue
	private long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Recipe recipe;

	@NotNull
	private Date date;

	@NotNull
	private String content;

	Comment(long id, User user, Recipe recipe, Date date, String content) {
		this.id = id;
		this.user = user;
		this.recipe = recipe;
		this.date = date;
		this.content = content;
	}

	public Comment(User user, Recipe recipe, String content) {
		this.user = user;
		this.recipe = recipe;
		this.date = new Date();
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public Date getDate() {
		return date;
	}

	public String getContent() {
		return content;
	}
}
