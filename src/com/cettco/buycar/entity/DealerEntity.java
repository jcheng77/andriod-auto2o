package com.cettco.buycar.entity;

public class DealerEntity {
	private String id;
	private String address;
	private double nakedPrice;
	private double specialPrice;
	private double totalPrice;
	private String detail1;
	private String detail2;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getNakedPrice() {
		return nakedPrice;
	}
	public void setNakedPrice(double nakedPrice) {
		this.nakedPrice = nakedPrice;
	}
	public double getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(double specialPrice) {
		this.specialPrice = specialPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDetail1() {
		return detail1;
	}
	public void setDetail1(String detail1) {
		this.detail1 = detail1;
	}
	public String getDetail2() {
		return detail2;
	}
	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}

}
