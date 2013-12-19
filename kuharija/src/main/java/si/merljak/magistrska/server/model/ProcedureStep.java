package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
@Table(name="step")
public class ProcedureStep implements Serializable {

	private static final long serialVersionUID = 3254802601787752128L;

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private int page;

	@NotNull
	@Size(max = 5000)
	private String content;

	private String imageUrl;

	protected ProcedureStep() {}

	public ProcedureStep(Recipe recipe, Language language, int page, String content) {
		this.recipe = recipe;
		this.language = language;
		this.page = page;
		this.content = content;
	}

	public Language getLanguage() {
		return language;
	}

	public String getContent() {
		return content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public int getPage() {
		return page;
	}
}
