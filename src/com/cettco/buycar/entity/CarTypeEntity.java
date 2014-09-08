package com.cettco.buycar.entity;

import java.util.ArrayList;

public class CarTypeEntity {
	private String name;
	private String pic_url;
	private ArrayList<TrimEntity> trims;
	private ArrayList<CarColorEntity> colors;


	public ArrayList<TrimEntity> getTrims() {
		return trims;
	}

	public void setTrims(ArrayList<TrimEntity> trims) {
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
	

}
