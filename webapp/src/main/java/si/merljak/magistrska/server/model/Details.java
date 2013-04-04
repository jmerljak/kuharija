package si.merljak.magistrska.server.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import si.merljak.magistrska.common.enumeration.Language;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="dtype", discriminatorType=DiscriminatorType.STRING, length=1)
public abstract class Details {

	@Id
	@GeneratedValue
	protected long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected Language language;

	@NotNull
	@Size(max = 50)
	protected String heading;

	@Size(max = 100)
	protected String subHeading;

	@Column(insertable=false, updatable=false)
	protected String dtype;

	public Language getLanguage() {
		return language;
	}

	public String getHeading() {
		return heading;
	}

	public String getSubHeading() {
		return subHeading;
	}

}
