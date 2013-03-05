package si.merljak.magistrska.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENT")
public class Comment implements Serializable {

	private static final long serialVersionUID = 1898355529967987383L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Recipe recipe;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private String content;

	protected Comment() {}

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
