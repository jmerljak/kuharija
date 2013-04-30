package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1898355529967987383L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private User user;

	@NotNull
	@ManyToOne
	private Recipe recipe;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@NotNull
	@Size(max = 500)
	private String content;

	protected Comment() {}

	public Comment(User user, Recipe recipe, String content) {
		this.user = user;
		this.recipe = recipe;
		this.date = new Date();
		this.content = content;
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
