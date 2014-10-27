package com.cettco.buycar.entity;

public class OrderDetailEntity {
	private String id;
	private String model;
	private String trim_id;
	private String price;
	private String pickup_time;
	private String license_location;
	private String got_licence;
	private String loan_option;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getTrim_id() {
		return trim_id;
	}
	public void setTrim_id(String trim_id) {
		this.trim_id = trim_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPickup_time() {
		return pickup_time;
	}
	public void setPickup_time(String pickup_time) {
		this.pickup_time = pickup_time;
	}
	public String getLicense_location() {
		return license_location;
	}
	public void setLicense_location(String license_location) {
		this.license_location = license_location;
	}
	public String getGot_licence() {
		return got_licence;
	}
	public void setGot_licence(String got_licence) {
		this.got_licence = got_licence;
	}
	public String getLoan_option() {
		return loan_option;
	}
	public void setLoan_option(String loan_option) {
		this.loan_option = loan_option;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getVerfiy_code() {
		return verfiy_code;
	}
	public void setVerfiy_code(String verfiy_code) {
		this.verfiy_code = verfiy_code;
	}
	private String description;
	private String state;
	private String verfiy_code;
}
