package fr.epsi.mspr.msprapi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Request {

	@Id
	private int id;
	
	@ManyToOne()
    @JoinColumn(name = "pharmacy", referencedColumnName = "id")
	private Pharmacy pharmacy;
	
	@ManyToOne()
    @JoinColumn(name = "type", referencedColumnName = "id")
	private RequestType requestType;
	
	@ManyToOne()
    @JoinColumn(name = "user", referencedColumnName = "id")
	private User user;
	
	private String content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Pharmacy getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	public RequestType getRequestType() {
		return requestType;
	}
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Request [id=").append(id).append(", pharmacy=").append(pharmacy).append(", requestType=")
				.append(requestType).append(", user=").append(user).append(", content=").append(content).append("]");
		return builder.toString();
	}
}