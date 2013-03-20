package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import si.merljak.magistrska.common.enumeration.Language;

@Entity
@DiscriminatorValue("R")
public class RecipeVideo extends Video implements Serializable {

	private static final long serialVersionUID = 4397439168508935989L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	protected RecipeVideo() {}

	public RecipeVideo(Recipe recipe, Language language, String urls, Set<Subtitle> subtitles) {
		this.recipe = recipe;
		this.language = language;
		this.urls = urls;
		this.subtitles = subtitles;
	}
}
