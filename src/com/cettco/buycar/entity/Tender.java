package com.cettco.buycar.entity;

public class Tender {
	private String model;
	private String trim_id;
	private String color_id;
	private String description;
	public String getTrim_id() {
		return trim_id;
	}
	public void setTrim_id(String trim_id) {
		this.trim_id = trim_id;
	}
	public String getColor_id() {
		return color_id;
	}
	public void setColor_id(String color_id) {
		this.color_id = color_id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
