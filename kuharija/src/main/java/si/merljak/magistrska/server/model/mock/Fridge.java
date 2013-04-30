package si.merljak.magistrska.server.model.mock;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import si.merljak.magistrska.server.model.Ingredient;
import si.merljak.magistrska.server.model.User;

@Entity
public class Fridge implements Serializable {

	private static final long serialVersionUID = 9162185543221983475L;

	@Id
	private long id;

	@ManyToOne(fetch=FetchType.LAZY)
	private User user;

	@ManyToOne(fetch=FetchType.LAZY)
	private Ingredient ingredient;

	protected Fridge() {}
}
