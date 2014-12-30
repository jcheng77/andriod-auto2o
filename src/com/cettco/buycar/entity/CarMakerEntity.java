package com.cettco.buycar.entity;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "makers")
public class CarMakerEntity {
	@DatabaseField
	private String name;
	@DatabaseField
	private String brand_id;
	@DatabaseField(id=true)
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	private List<CarModelEntity> models;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarModelEntity> getModels() {
		return models;
	}
	public void setModels(List<CarModelEntity> models) {
		this.models = models;
	}
}
