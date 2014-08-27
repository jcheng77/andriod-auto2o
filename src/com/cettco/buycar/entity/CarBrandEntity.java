package com.cettco.buycar.entity;

import java.util.List;

public class CarBrandEntity {
	private String name;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private List<CarManufactorEntity> makers;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarManufactorEntity> getMakers() {
		return makers;
	}
	public void setMakers(List<CarManufactorEntity> makers) {
		this.makers = makers;
	}
	
}
