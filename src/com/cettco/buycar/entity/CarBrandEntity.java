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
	private List<CarManufactorEntity> maker;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarManufactorEntity> getMaker() {
		return maker;
	}
	public void setMaker(List<CarManufactorEntity> maker) {
		this.maker = maker;
	}
	
}
