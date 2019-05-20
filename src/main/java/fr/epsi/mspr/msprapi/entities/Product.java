package fr.epsi.mspr.msprapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	private int id;
	private String name;
	private float price;
	private String logoBase64;
	@Column(name = "logo_base_64")
	private String logo;
	
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getLogoBase64() {
		return logoBase64;
	}
	public void setLogoBase64(String logoBase64) {
		this.logoBase64 = logoBase64;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}	
}
