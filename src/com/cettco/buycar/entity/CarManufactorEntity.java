package com.cettco.buycar.entity;

import java.util.List;

public class CarManufactorEntity {
	private String name;
	private List<CarTypeEntity> models;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarTypeEntity> getModels() {
		return models;
	}
	public void setModels(List<CarTypeEntity> models) {
		this.models = models;
	}
}
