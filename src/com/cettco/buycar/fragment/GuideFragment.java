package com.cettco.buycar.fragment;

import com.cettco.buycar.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GuideFragment extends Fragment{
	private View fragmentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.fragment_guide, container,false);
		return fragmentView;
	}
}
