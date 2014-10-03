package com.cettco.buycar.fragment;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.cettco.buycar.R;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WelcomeFragment extends Fragment {
	private View fragmentView;
	private AutoScrollViewPager autoScrollViewPager;
	private WelcomePagerAdapter adapter;
	private ArrayList<ImageView> arrayList;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.fragment_welcome, container,
				false);
		autoScrollViewPager = (AutoScrollViewPager)fragmentView.findViewById(R.id.welcome_view_pager);
		autoScrollViewPager.setInterval(2000);
		autoScrollViewPager.setCycle(true);
		arrayList = new ArrayList<ImageView>();
		adapter = new WelcomePagerAdapter(arrayList);
		for(int i=0;i<5;i++){
			ImageView view = new ImageView(getActivity());
			view.setBackgroundResource(R.drawable.welcome);
			arrayList.add(view);
		}
		autoScrollViewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		autoScrollViewPager.startAutoScroll();
		return fragmentView;
	}
	protected class WelcomePagerAdapter extends PagerAdapter{

		private List<ImageView>list;
		public WelcomePagerAdapter(List<ImageView> list){
			this.list = list;
		}
		 @Override  
		    public void destroyItem(ViewGroup container, int position, Object object)   {     
		        container.removeView(list.get(position));
		    }  


		    @Override  
		    public Object instantiateItem(ViewGroup container, int position) {  
		         container.addView(list.get(position), 0);
		         return list.get(position);  
		    } 
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list==null?0:list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1; 
		}
		
	}
}
