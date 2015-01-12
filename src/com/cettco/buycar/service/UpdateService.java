package com.cettco.buycar.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cettco.buycar.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

public class UpdateService extends Service{

	private String url = null;  
    // 通知栏  
    private NotificationManager updateNotificationManager = null;  
    //private Notification updateNotification = null;  
    private String appName = null;  
    private String fileName = null;  
    private String updateDir = null;  
    private NotificationCompat.Builder mBuilder;
    
      
    //通知栏跳转Intent  
      
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
        // 获取传值  
    	//System.out.println("service url:"+url);
        url = intent.getStringExtra("url");  
        appName = getApplication().getResources().getText(R.string.app_name).toString();  
        if (url != null) {  
            fileName = url.substring(url.lastIndexOf("/")+1);
            if(Environment.getExternalStorageState() != null){
            	updateDir = Environment.getExternalStorageDirectory()
                        + "/Downloads/";
            }else{
            	updateDir = Environment.getDataDirectory() + "/data/" + this.getPackageName() + "/files/";
            }  
            Intent nullIntent = new Intent();  
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, nullIntent, 0);  
            // 创建文件  
            updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
            mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setContentTitle("拍立行更新")
                .setContentText("正在下载")
                .setSmallIcon(R.drawable.ic_launcher);
//            updateNotification = new Notification();  
//            updateNotification.icon = R.drawable.ic_launcher;  
//            updateNotification.tickerText = "正在更新" + appName;  
//            updateNotification.setLatestEventInfo(getApplication(), "正在下载"+appName,"0%", null);  
//            updateNotification.defaults = Notification.DEFAULT_SOUND;  
//            updateNotification.flags = Notification.FLAG_AUTO_CANCEL;  
//            updateNotification.contentIntent = pendingIntent;  
            //updateNotificationManager.notify(101, updateNotification);  
            mBuilder.setProgress(100, 0, false);
            updateNotificationManager.notify(101, mBuilder.build());
            //开启线程现在  
            new Thread(new updateRunnable()).start();  
        }  
        return super.onStartCommand(intent, 0, 0);  
    }  
  
    private class updateRunnable implements Runnable {  
        Message message = updateHandler.obtainMessage();  
  
        public void run() {  
            message.what = 0;  
            try {  
                long downloadSize = downloadUpdateFile(url);  
                //Log.i("cai", downloadSize/1024+"");  
                if (downloadSize > 0) {  
                    // 下载成功  
                    updateHandler.sendMessage(message);  
                }  
            } catch (Exception ex) {  
                ex.printStackTrace();  
                message.what = 1;  
                // 下载失败  
                updateHandler.sendMessage(message);  
            }  
        }  
    }  
  
    private Handler updateHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case 0:  
                /*try { 
                    Runtime.getRuntime().exec("chmod 777 " + updateDir.getAbsolutePath()); 
                    Runtime.getRuntime().exec("chmod 777 " + updateFile.getAbsolutePath()); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                }*/  
                // 点击安装PendingIntent  
                Intent installIntent = new Intent(Intent.ACTION_VIEW);  
                //installIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED| Intent.FLAG_ACTIVITY_NEW_TASK);  
                installIntent.setDataAndType(Uri.fromFile(new File(updateDir,fileName)),"application/vnd.android.package-archive");  
                //getApplication().startActivity(installIntent);*/  
                PendingIntent updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installIntent, 0);  
//                updateNotification.defaults = Notification.DEFAULT_SOUND;// 铃声提醒  
//                updateNotification.flags = Notification.FLAG_AUTO_CANCEL;  
//                updateNotification.setLatestEventInfo(UpdateService.this,appName, "下载完成,点击安装", updatePendingIntent);  
//                updateNotificationManager.notify(101, updateNotification);  
                mBuilder.setContentText("下载完成").setProgress(0,0,false);
                mBuilder.setContentIntent(updatePendingIntent);
                updateNotificationManager.notify(101, mBuilder.build());
                // 停止服务  
                stopSelf();  
                break;  
            case 1:  
//                Intent nullIntent = new Intent();  
//                PendingIntent pendingIntent = PendingIntent.getActivity(UpdateService.this, 10, nullIntent, 0);  
//                // 下载失败  
//                updateNotification.setLatestEventInfo(UpdateService.this,appName, "网络连接不正常，下载失败！", pendingIntent);  
//                updateNotification.flags = Notification.FLAG_AUTO_CANCEL;  
//                updateNotificationManager.notify(101, updateNotification);  
                
                mBuilder.setContentText("网络连接不正常,下载失败!")
                // Removes the progress bar
                        .setProgress(0,0,false);
                updateNotificationManager.notify(101, mBuilder.build());
                break;  
            default:  
                stopSelf();  
            }  
        }  
    };  
    //下载 
    public long downloadUpdateFile(String downloadUrl) throws Exception {  
        int downloadCount = 0;  
        int currentSize = 0;  
        long totalSize = 0;  
        int updateTotalSize = 0;  
           
        HttpURLConnection httpConnection = null;  
        InputStream is = null;  
        FileOutputStream fos = null;  
           
        try {  
            URL url = new URL(downloadUrl);  
            httpConnection = (HttpURLConnection)url.openConnection();  
            if(currentSize > 0) {  
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");  
            }  
            httpConnection.setConnectTimeout(10000);  
            httpConnection.setReadTimeout(20000);  
            updateTotalSize = httpConnection.getContentLength();  
            if (httpConnection.getResponseCode() == 404) {  
                throw new Exception("fail!");  
            }  
            is = httpConnection.getInputStream();                     
           /* fos = new FileOutputStream(saveFile, false);*/  
            fos =  openFileOutput(fileName, MODE_WORLD_READABLE);  
              
            byte buffer[] = new byte[4096];  
            int readsize = 0;  
            while((readsize = is.read(buffer)) > 0){  
                fos.write(buffer, 0, readsize);  
                totalSize += readsize;  
                //为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次  
                if((downloadCount == 0)||(int) (totalSize*100/updateTotalSize)-1>downloadCount){   
                    downloadCount += 1;  
                    Intent nullIntent = new Intent();  
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, nullIntent, 0);  
//                    updateNotification.contentIntent = pendingIntent;  
//                    updateNotification.setLatestEventInfo(UpdateService.this, appName+"正在下载", (int)totalSize*100/updateTotalSize+"%", null);  
//                    updateNotificationManager.notify(101, updateNotification);  
                    mBuilder.setProgress(100, (int)totalSize*100/updateTotalSize, false);
                    updateNotificationManager.notify(101, mBuilder.build());
                }                          
            }  
        } finally {  
            if(httpConnection != null) {  
                httpConnection.disconnect();  
            }  
            if(is != null) {  
                is.close();  
            }  
            if(fos != null) {  
                fos.close();  
            }  
        }  
        return totalSize;  
    }  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        return null;  
    }  
} 