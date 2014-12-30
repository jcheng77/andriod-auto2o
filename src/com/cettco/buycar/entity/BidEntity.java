package com.cettco.buycar.entity;

public class BidEntity {
	private String insurance;
	private String purchase_tax;
	private String license_fee;
	private String misc_fee;
	private String description;
	private String price;
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getPurchase_tax() {
		return purchase_tax;
	}
	public void setPurchase_tax(String purchase_tax) {
		this.purchase_tax = purchase_tax;
	}
	public String getLicense_fee() {
		return license_fee;
	}
	public void setLicense_fee(String license_fee) {
		this.license_fee = license_fee;
	}
	public String getMisc_fee() {
		return misc_fee;
	}
	public void setMisc_fee(String misc_fee) {
		this.misc_fee = misc_fee;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
