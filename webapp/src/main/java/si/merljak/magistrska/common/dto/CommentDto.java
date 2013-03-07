package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.Date;

public class CommentDto implements Serializable {

	private static final long serialVersionUID = -6686847920153154747L;

	private String user;
	private Date date;
	private String content;

	CommentDto() {}

	public CommentDto(String user, Date date, String content) {
		this.user = user;
		this.date = date;
		this.content = content;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}

	public String getContent() {
		return content;
	}

}
