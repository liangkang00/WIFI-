package com.ssv.ssvwifitool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import android.util.Log;



public class DeviceUtil
{
	String TAG ="SSV";
	
	boolean ssvwifi = true;
	//boolean ssvwifi = false;
	public String ReadCmd(String address)
	{
		if(ssvwifi)
		{
				Process p;
			    try {
			    	//p = Runtime.getRuntime().exec("data/cli tool r "+address);
			    	p = Runtime.getRuntime().exec("/data/cli tool r "+address);
			    	
			    	DataOutputStream os = new DataOutputStream(p.getOutputStream());
			        BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			        os.flush();
			        String data;
			        while((data = bf.readLine()) != null)
		            {
		            	Log.d(TAG,data);
				        os.close();
				        bf.close();
		            	return data;
		            }
			        
			        os.close();
			        bf.close();
			        
			    } catch (IOException e) {
			        e.printStackTrace();
			        return null;
			    }
			    return null;
		}
		else
		{
			return "12345678";
		}
		
	}
	
	public void WriteCmd(String address,String data)
	{
		if(ssvwifi)
		{
			Process p;
		    try {
		        //p = Runtime.getRuntime().exec("data/cli tool w "+address+" "+data);
		    	Log.d("Test", "write addr: 0x"+address+", value: "+data);
		    	p = Runtime.getRuntime().exec("/data/cli tool w "+address+" "+data);
		    } catch (IOException e) {
		       e.printStackTrace();
		    }
		}
	}

} 
