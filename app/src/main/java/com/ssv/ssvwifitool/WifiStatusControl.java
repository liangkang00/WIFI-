package com.ssv.ssvwifitool;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
//import android.os.SystemProperties;

public class WifiStatusControl {
	private final static String TAG = "SSV-WifiStatusControl";
	public static boolean isDriverLoaded = false;
	
    private static void loadWifi(boolean on) {
    	//restart wifi

//        SystemProperties.set("sys.lights_leds", "0");
//        if (on) {
//            SystemProperties.set("sys.load_wifi", "1");
//        } else {
//            SystemProperties.set("sys.load_wifi", "0");
//        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
//	    SystemProperties.set("sys.lights_leds", "1");
    }

    public static void setWifiUnload(Context context) {
        WlanAttributes attr = new WlanAttributes(context);
        attr.startTX(false);
//        loadWifi(false);
    }

	public static void checkWifiStateAndOn(final Context mContext) {
		
	    final ProgressDialog progressDialog = ProgressDialog.show(mContext, "SSV", "Please wait wifi re-turning on...");
	    new Thread() {
	    	public void run() {

	        	try{
					final WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

					int wifiState = wifi.getWifiState();
		/*
		if (wifiState != WifiManager.WIFI_STATE_ENABLED || wifiState != WifiManager.WIFI_STATE_ENABLING) {
			wifi.setWifiEnabled(true);// Enabling WiFi
		}
		*/
					Log.d(TAG, "====Current wifi state "+ wifiState);
//					if (wifiState != WifiManager.WIFI_STATE_DISABLED || wifiState != WifiManager.WIFI_STATE_DISABLING) {
//						wifi.setWifiEnabled(false);
//					}

					WlanAttributes attr = new WlanAttributes(mContext);
					if(!attr.isWifiDriverLoad()){
//							loadWifi(true);

						wifi.setWifiEnabled(false);
						Thread.sleep(1000);
						wifi.setWifiEnabled(true);

					}

					Thread.sleep(200);
//  check wifi driver cmd file again
//					if(!attr.isWifiDriverLoad()){
//						//exit app
//						showAlerDialog(mContext);
//					}
					isDriverLoaded = true;

//	        		while (wifi.getWifiState() != WifiManager.WIFI_STATE_DISABLED) {
//	        			Log.d(TAG, "Current wifi state "+ wifi.getWifiState());
//	        			Thread.sleep(1000);
//	        		}
	        			// Intent intent = new Intent();  
	        			// intent.setAction("12345678");  
	        			// mContext.sendBroadcast(intent);
	        			//all of these functions are not working
	            		/*Process p;
		        		p = Runtime.getRuntime().exec("su -c setprop sys.lights_leds 0");
		        		p = Runtime.getRuntime().exec("su -c setprop persist.sys.abc 0");
		        		p = Runtime.getRuntime().exec("su -c /system/bin/lights_leds.sh");
	        			//Log.d("SSV", "1234567: "+SystemPropertiesProxy.get(mContext, "sys.lights_leds"));
	        			SystemPropertiesProxy.set(mContext, "sys.lights_leds", "0");
	        			SystemPropertiesProxy.set(mContext, "persist.sys.abc", "0");
	        			//Log.d("SSV", "1234567: "+SystemPropertiesProxy.get(mContext, "sys.lights_leds"));
	        			//SystemProperties.set("sys.lights_leds", "0");
	        			//SystemProperties.set("sys.lights_leds", "1");
	        			 */

	        		/*
	        		while (wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
	        			Thread.sleep(1000);
	        		}
	        		*/
	            } 
	            catch (Exception e) {
	                Log.e("SSV", e.getMessage());
	            }
	            // dismiss the progress dialog
	            progressDialog.dismiss();
	    	}
	    }.start();
	}
	
}
