package com.cettco.buycar.entity;

import java.util.ArrayList;

public class DealerListEntity {
	ArrayList<DealerEntity> dealer;
	ArrayList<String> status;

	public ArrayList<String> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<String> status) {
		this.status = status;
	}

	public ArrayList<DealerEntity> getDealer() {
		return dealer;
	}

	public void setDealer(ArrayList<DealerEntity> dealer) {
		this.dealer = dealer;
	}
}
