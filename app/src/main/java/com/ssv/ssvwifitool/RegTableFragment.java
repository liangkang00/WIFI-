package com.ssv.ssvwifitool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import junit.framework.Test;
//import org.junit.Test;

import com.ssv.ssvwifitool.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor; 
import android.os.Bundle;  
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;  
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ssv.ssvwifitool.DumpInfo;
import com.ssv.ssvwifitool.chip.Chip;
import com.ssv.ssvwifitool.chip.ChipModule;

public class RegTableFragment extends Fragment{


    private static final String TAG = "SSV";
	List<Model> lstItem ;
    List<ChipModule> lstChip ;
    private View RegTableFragmentView = null;
    private View TempView = null;
    ///SimpleAdapter adapter ;
  	
    private DeviceUtil device ;
   
    ArrayAdapter<Model> adapter;
    
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("=====>", "GoogleFragment onAttach");
		
		MainActivity mainActivity = (MainActivity)activity;
		lstChip = readXmlFile("reg.xml");
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RegTableFragmentView= inflater.inflate(R.layout.frg_reg_main, container, false);
		TempView = inflater.inflate(R.layout.frg_reg_view, container, false);
		Context context = RegTableFragmentView.getContext();
		
		setHasOptionsMenu(true); //enable option menu
		device = new DeviceUtil();
		//
		List<String> lst = lstChip.get(0).getAddress();
		lstItem = new ArrayList<Model>();
	  	  for(int i=0;i<lst.size();i++)
	  	  {
	      	  String temp = lst.get(i);
		      IndexedEditText a = (IndexedEditText) new IndexedEditText(RegTableFragmentView.getContext()); 
		      a.setText(device.ReadCmd(temp));
		      
		      a.setListIndex(i);

		      lstItem.add(i,new Model(lst.get(i),a));
	  	  }
		//
		return RegTableFragmentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		final ListView listView = (ListView) RegTableFragmentView.findViewById(R.id.ListView);
		
	    adapter = new MyAdapter(RegTableFragmentView.getContext(),lstItem,device);
	    listView.setAdapter(adapter);
	   
	    Button readButton = (Button)RegTableFragmentView.findViewById(R.id.button1);
	    
	    //Read button
	    readButton.setOnClickListener(new Button.OnClickListener(){ 
	    	
            @Override
            public void onClick(View v) {
            	int i =0;
            	for(i=0;i<lstItem.size();i++)
            	{
            		Model temp = (Model)lstItem.get(i);
            		
            		
            	    IndexedEditText a = temp.getValue();
           	        a.setText(device.ReadCmd(temp.getAddress()));

           	        temp.setValue(a);
            	}            	
            	adapter.notifyDataSetChanged();	
            }         

        });    
	    
	    
		} 
		
	@SuppressWarnings("deprecation")
	public void updateList(int location)
	{
		
	   	/*if(isFile.exists() ==false)
	   	{
	   		final AlertDialog alertDialog = new AlertDialog.Builder(RegTableFragmentView.getContext()).create();
	   		alertDialog.setTitle("Title");
	   		alertDialog.setMessage("file is not exists");
	   		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	   		   public void onClick(DialogInterface dialog, int which) {
	   			alertDialog.cancel();
	   		   }
	   		});
	   		alertDialog.show();
	   	}*/
	     
		  
	  	  //int i = 0 ;
	  	  lstItem.clear();
	  	  List<String> lst = lstChip.get(location).getAddress();
	  	  for(int i=0;i<lst.size();i++)
	  	  {
	      	  String temp = lst.get(i);
		      IndexedEditText a = (IndexedEditText) new IndexedEditText(RegTableFragmentView.getContext()); 
		      a.setText(device.ReadCmd(temp));

		      a.setListIndex(i);
		      lstItem.add(i,new Model(lst.get(i),a));
	  	  }
	  	  
	  	//listView.setAdapter().notifyDataSetChanged();
	  	adapter.notifyDataSetChanged();	      
	}
	
	    @Override
	    public void onStop()
	    {
	    	super.onStop();
	    	
	    }
	    
	    @Override
	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    	 super.onCreateOptionsMenu(menu, inflater);
	    	    inflater.inflate(R.menu.menu_reg, menu);
	    	    menu.clear();
	    	    for(int i=0;i<lstChip.size();i++)
	    	    {	
	    	    	menu.add(0,i,i,lstChip.get(i).getName());
	    	    }
	    	}

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        /*String fileName ="rf_reg.txt";
	        switch(id)
	        {
			case R.id.action_sram:
				fileName="sram_reg.txt";
				break;
			
			case R.id.action_mcu:
				fileName="sram_reg.txt";
				break;
			case R.id.action_timer:
				fileName="mcu_reg.txt";
				break;
			case R.id.action_interrupt:
				fileName="interrupt_reg.txt";
				break;
			case R.id.action_sdio:
				fileName="sdio_reg.txt";
				break;
			case R.id.action_hci:
				fileName="sram_reg.txt";
				break;
			case R.id.action_rx:
				fileName="rx_reg.txt";
				break;
			case R.id.action_tx:
				fileName="tx_reg.txt";
				break;
			case R.id.action_mac_general:
				fileName="mac_reg.txt";
				break;
			case R.id.action_id_mangement:
				fileName="id_reg.txt";
				break;	
			case R.id.action_rf:
				fileName="rf_reg.txt";
				break;
			case R.id.action_phy:
				fileName="phy_reg.txt";
				break;
			default:
				
				break;
			
			}	*/       
	        updateList(id);
	          
	        return super.onOptionsItemSelected(item);
	    }
	    
	    public List<ChipModule> readXmlFile(String fileName)
	    {
	    	List<ChipModule> lst = new ArrayList<ChipModule>();
	    	File SDCardpath = Environment.getExternalStorageDirectory();
			   
			   boolean can_write = SDCardpath.canWrite(); 
			   boolean can_read = SDCardpath.canRead(); 
			   Log.d(TAG, "SDCARD PATH "+SDCardpath);
			   String path = "/data/"+ fileName; 
	           File myDataPath = new File("/data/"+ fileName);
               Chip chip;
               InputStream inputStream;
               //FileReader fr = new FileReader(myDataPath);
               if( myDataPath.exists())
               {   
                   chip= new Chip(myDataPath, null);
               }   
               else
               {   
                   inputStream = getResources().openRawResource(R.raw.regxml);
                   chip= new Chip(null, inputStream);
               }   
               lst= chip.getModules();
               return lst; 

	    }
	    
	   
}


