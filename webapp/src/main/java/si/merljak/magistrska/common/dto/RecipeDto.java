package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import si.merljak.magistrska.common.enumeration.Difficulty;

import com.mysema.query.annotations.QueryProjection;

public class RecipeDto implements Serializable {

	private static final long serialVersionUID = 4556223060117165325L;

	private long id;
	private String heading;
	private String imageUrl;
	private Difficulty difficulty;
	private int timeOverall;

	RecipeDto() {}

	@QueryProjection
	public RecipeDto(long id, String heading, String imageUrl, 
			Difficulty difficulty, int timeOverall) {
		this.id = id;
		this.heading = heading;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.timeOverall = timeOverall;
	}

	public long getId() {
		return id;
	}

	public String getHeading() {
		return heading;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public int getTimeOverall() {
		return timeOverall;
	}

}
