package com.cettco.buycar.entity;

import java.util.List;

public class CarManufactorEntity {
	private String name;
	private List<CarTypeEntity> model;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarTypeEntity> getModel() {
		return model;
	}
	public void setModel(List<CarTypeEntity> model) {
		this.model = model;
	}
}
