package com.cettco.buycar.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "trims")
public class CarTrimEntity {
	@DatabaseField
	private String model_id;
	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	@DatabaseField
	private String name;
	@DatabaseField
	private String guide_price;
	@DatabaseField
	private String lowest_price;
	public String getLowest_price() {
		return lowest_price;
	}
	public void setLowest_price(String lowest_price) {
		this.lowest_price = lowest_price;
	}
	@DatabaseField(id=true)
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGuide_price() {
		return guide_price;
	}
	public void setGuide_price(String guide_price) {
		this.guide_price = guide_price;
	}
}
