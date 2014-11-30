package com.cettco.buycar.receiver;

import com.cettco.buycar.utils.UpdateManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class CheckUpdateReceiver extends BroadcastReceiver{

	private Context context;
	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		this.context = context;
		System.out.println("received");
		//new CheckAsynTask().execute();
	}
	private class CheckAsynTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			UpdateManager manager = new UpdateManager(context);  
			// 检查软件更新  
			manager.checkUpdate();
			return null;
		}
		
	}

}
