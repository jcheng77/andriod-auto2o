package com.cettco.buycar.entity;

public class MyOrderEntity {
	private String id;
	private String model;
	private String price;
	private String description;
	private String state;
	private String first_round_bids;
	private String second_round_bids;
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFirst_round_bids() {
		return first_round_bids;
	}
	public void setFirst_round_bids(String first_round_bids) {
		this.first_round_bids = first_round_bids;
	}
	public String getSecond_round_bids() {
		return second_round_bids;
	}
	public void setSecond_round_bids(String second_round_bids) {
		this.second_round_bids = second_round_bids;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
