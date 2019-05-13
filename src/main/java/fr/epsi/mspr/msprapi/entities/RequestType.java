package fr.epsi.mspr.msprapi.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="REQUEST_TYPE")
public class RequestType {

	@Id
	private int id;
	private String name;
	
	@OneToMany(mappedBy="requestType")
	Set<Request> request;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Request> getRequest() {
		return request;
	}

	public void setRequest(Set<Request> request) {
		this.request = request;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestType [id=").append(id).append(", name=").append(name).append(", request=")
				.append(request).append("]");
		return builder.toString();
	}
}
