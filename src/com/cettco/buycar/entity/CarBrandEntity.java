package com.cettco.buycar.entity;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "brands")
public class CarBrandEntity {
	@DatabaseField(id=true)
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@DatabaseField
	private String name;
	private String sortLetters;
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private List<CarMakerEntity> makers;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CarMakerEntity> getMakers() {
		return makers;
	}
	public void setMakers(List<CarMakerEntity> makers) {
		this.makers = makers;
	}
	
}
