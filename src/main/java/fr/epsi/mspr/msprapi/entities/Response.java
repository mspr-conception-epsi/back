package fr.epsi.mspr.msprapi.entities;

import javax.persistence.EmbeddedId;

public class Response {

	private @EmbeddedId ResponseId id;
	private String content;
	private String label;
	
	public ResponseId getId() {
		return id;
	}
	public void setId(ResponseId id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
