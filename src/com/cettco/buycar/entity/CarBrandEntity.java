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
	private List<CarManufactorEntity> manufactorlist;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarManufactorEntity> getManufactorlist() {
		return manufactorlist;
	}
	public void setManufactorlist(List<CarManufactorEntity> manufactorlist) {
		this.manufactorlist = manufactorlist;
	}
	
}
