package si.merljak.magistrska.server.model.mock;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author Jakob Merljak
 */
@Entity
public class WOzAction implements Serializable {

	private static final long serialVersionUID = 4192839212317584848L;

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	private String action;

	protected WOzAction() {}

	public WOzAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}
