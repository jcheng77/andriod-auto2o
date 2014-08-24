package com.cettco.buycar.entity;

import java.util.List;

public class CarManufactorEntity {
	private String name;
	private List<CarTypeEntity> modelList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarTypeEntity> getModelList() {
		return modelList;
	}
	public void setModelList(List<CarTypeEntity> modelList) {
		this.modelList = modelList;
	}
}
