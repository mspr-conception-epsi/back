package fr.epsi.mspr.msprapi.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String address;
	@Column(name="gps_lat")
	private float gpsLat;
	@Column(name="gps_long")
	private float gpsLong;
	
	@OneToMany(mappedBy="pharmacy")
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getGpsLat() {
		return gpsLat;
	}
	public void setGpsLat(float gpsLat) {
		this.gpsLat = gpsLat;
	}
	public float getGpsLong() {
		return gpsLong;
	}
	public void setGpsLong(float gpsLong) {
		this.gpsLong = gpsLong;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pharmacy [id=").append(id).append(", name=").append(name).append(", address=").append(address)
				.append(", gpsLat=").append(gpsLat).append(", gpsLong=").append(gpsLong).append("]");
		return builder.toString();
	}
}
