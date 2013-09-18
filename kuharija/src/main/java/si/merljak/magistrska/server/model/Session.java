package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Session implements Serializable {

	private static final long serialVersionUID = -4961571644033577114L;

	@Id
	private String id;

	@NotNull
	@ManyToOne
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expires;

	protected Session() {}

	public Session(String id, User user, Date expires) {
		this.id = id;
		this.user = user;
		this.expires = expires;
	}
}
