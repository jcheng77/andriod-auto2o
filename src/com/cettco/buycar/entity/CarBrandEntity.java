package com.cettco.buycar.entity;

import java.util.List;

public class CarBrandEntity {
	private String name;
	private List<CarManufactorEntity> list;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarManufactorEntity> getList() {
		return list;
	}
	public void setList(List<CarManufactorEntity> list) {
		this.list = list;
	}
	
}
