package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import si.merljak.magistrska.common.enumeration.AppendixType;
import si.merljak.magistrska.common.enumeration.Language;

@Entity
public class Appendix implements Serializable {

	private static final long serialVersionUID = 8561203082427891549L;

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AppendixType type;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	@Size(max = 1000)
	private String content;

	protected Appendix() {}

	public Appendix(Recipe recipe, AppendixType type, Language language, String content) {
		this.recipe = recipe;
		this.type = type;
		this.language = language;
		this.content = content;
	}

	public AppendixType getType() {
		return type;
	}

	public Language getLanguage() {
		return language;
	}

	public String getContent() {
		return content;
	}
}
