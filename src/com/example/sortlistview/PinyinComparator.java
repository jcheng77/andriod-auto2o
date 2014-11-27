package com.example.sortlistview;

import java.util.Comparator;

import com.cettco.buycar.entity.CarBrandEntity;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<CarBrandEntity> {

	public int compare(CarBrandEntity o1, CarBrandEntity o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
