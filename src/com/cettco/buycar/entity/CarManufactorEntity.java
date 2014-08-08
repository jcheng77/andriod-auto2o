package com.cettco.buycar.entity;

import java.util.List;

public class CarManufactorEntity {
	private String name;
	private List<CarTypeEntity> list;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarTypeEntity> getList() {
		return list;
	}
	public void setList(List<CarTypeEntity> list) {
		this.list = list;
	}
}
