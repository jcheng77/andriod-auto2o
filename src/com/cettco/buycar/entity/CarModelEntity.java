package com.cettco.buycar.entity;

import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "models")
public class CarModelEntity {
	@DatabaseField
	private String maker_id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String pic_url;
	private ArrayList<CarTrimEntity> trims;
	private ArrayList<CarColorEntity> colors;
	@DatabaseField(id=true)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<CarTrimEntity> getTrims() {
		return trims;
	}

	public void setTrims(ArrayList<CarTrimEntity> trims) {
		this.trims = trims;
	}

	public ArrayList<CarColorEntity> getColors() {
		return colors;
	}

	public void setColors(ArrayList<CarColorEntity> colors) {
		this.colors = colors;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaker_id() {
		return maker_id;
	}

	public void setMaker_id(String maker_id) {
		this.maker_id = maker_id;
	}

}
