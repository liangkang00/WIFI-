package com.ssv.ssvwifitool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.app.Activity;
import android.app.ActionBar;
//import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.support.v4.app.Fragment;

import com.ssv.ssvwifitool.R;

public class RegFragment extends Fragment {
	
	String address_s1;
	Button readButton;
	TextView addressText;
	EditText address1;
	EditText address2;
	EditText address3;
	EditText address4;
	EditText address5;
	EditText address6;
	EditText address7;
	EditText address8;
	EditText address9;
	EditText addressA;
	EditText addressB;
	EditText data1;
	EditText data2;
	EditText data3;
	EditText data4;
	EditText data5;
	EditText data6;
	EditText data7;
	EditText data8;
	EditText data9;
	EditText dataA;
	EditText dataB;
	String str_sram = "00000000";
	String str_mcu = "C0000000";
	String str_timer = "C0000200";
	String str_interrupt = "C0000E00";
	String str_sdio ="C0001000";
	String str_hci ="C1000000";
	String str_rx = "C6000000";
	String str_tx = "C6002000";
	String str_mac_general = "CA000000";
	String str_id_mangement ="CD010000";	
	String str_rf ="CB110000";
	String str_phy = "CE000000";
	
	int selectMenuItem = 0;
	
	Process process = null;
    Process process1 = null;   
    DataOutputStream os = null;
    DataInputStream is = null;
    String TAG ="ssvwifi";
    
	private String value = "";
	
	View RegFragmentView;
	public DeviceUtil device ;

	@Override
	public void onAttach(Activity activity) {
		
		Log.d("=====>", "RegFragment onAttach");
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("=====>", "RegFragment onCreateView");
		RegFragmentView = inflater.inflate(R.layout.frg_reg, container, false);
		Context context = RegFragmentView.getContext();
		setHasOptionsMenu(true);
		device = new DeviceUtil();
		return RegFragmentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("=====>", "RegFragment onActivityCreated");
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }   
        
        
        addressText = (TextView)RegFragmentView.findViewById(R.id.textView00);
        address1 = (EditText)RegFragmentView.findViewById(R.id.EditText10);
        address2 = (EditText)RegFragmentView.findViewById(R.id.EditText20);
        address3 = (EditText)RegFragmentView.findViewById(R.id.EditText30);
        address4 = (EditText)RegFragmentView.findViewById(R.id.EditText40);
        address5 = (EditText)RegFragmentView.findViewById(R.id.EditText50);
        address6 = (EditText)RegFragmentView.findViewById(R.id.EditText60);
        address7 = (EditText)RegFragmentView.findViewById(R.id.EditText70);
        address8 = (EditText)RegFragmentView.findViewById(R.id.EditText80);
        address9 = (EditText)RegFragmentView.findViewById(R.id.EditText90);
        addressA = (EditText)RegFragmentView.findViewById(R.id.EditTextA0);        
        addressB = (EditText)RegFragmentView.findViewById(R.id.EditTextB0);
        
        
        data1 = (EditText)RegFragmentView.findViewById(R.id.EditText11);
        data2 = (EditText)RegFragmentView.findViewById(R.id.EditText21);
        data3 = (EditText)RegFragmentView.findViewById(R.id.EditText31);
        data4 = (EditText)RegFragmentView.findViewById(R.id.EditText41);
        data5 = (EditText)RegFragmentView.findViewById(R.id.EditText51);
        data6 = (EditText)RegFragmentView.findViewById(R.id.EditText61);
        data7 = (EditText)RegFragmentView.findViewById(R.id.EditText71);
        data8 = (EditText)RegFragmentView.findViewById(R.id.EditText81);
        data9 = (EditText)RegFragmentView.findViewById(R.id.EditText91);
        dataA = (EditText)RegFragmentView.findViewById(R.id.EditTextA1);
        dataB = (EditText)RegFragmentView.findViewById(R.id.EditTextB1);
        
        readButton = (Button)RegFragmentView.findViewById(R.id.Readbutton);
        
        selectMenuItem =R.id.action_sram;
        
        data1.setOnFocusChangeListener(new OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address1.getText().toString()!=null)
    					device.WriteCmd(address1.getText().toString(),data1.getText().toString());
                }
            }
        });
        
        data1.setOnKeyListener(new OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address1,data1);
			}  
          });  
        
        
        data2.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address2.getText().toString()!=null)
    					device.WriteCmd(address2.getText().toString(),data2.getText().toString());
                }
                  
            }
        });
        data2.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address2,data2);
			}  
          });  
        
        
        data3.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address3.getText().toString()!=null)
    					device.WriteCmd(address3.getText().toString(),data3.getText().toString());
                } 
            }
        });
        data3.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address3,data3);
			}  
        });  
        
        data4.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address4.getText().toString()!=null)
    					device.WriteCmd(address4.getText().toString(),data4.getText().toString());
                }
            }
        });        
        data4.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address4,data4);
			}  
          });  
        
        
        data5.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address5.getText().toString()!=null)
    					device.WriteCmd(address5.getText().toString(),data5.getText().toString());
                }
            }
        });
        data5.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address5,data5);
			}  
          });  
        
        data6.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address6.getText().toString()!=null)
    					device.WriteCmd(address6.getText().toString(),data6.getText().toString());
                }
            }
        });
        data6.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address6,data6);
			}  
          });  
        
        data7.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address7.getText().toString()!=null)
    					device.WriteCmd(address7.getText().toString(),data7.getText().toString());
                }
            }
        });
        data7.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address7,data7);
			}  
          });  
        data8.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address8.getText().toString()!=null)
    					device.WriteCmd(address8.getText().toString(),data8.getText().toString());
                }
            }
        });
        data8.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address8,data8);
			}  
          });  
        data9.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(address9.getText().toString()!=null)
    					device.WriteCmd(address9.getText().toString(),data9.getText().toString());
                } 
            }
        });
        data9.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,address9,data9);
			}  
          });  
        
        
        dataA.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(addressA.getText().toString()!=null)
    					device.WriteCmd(addressA.getText().toString(),dataA.getText().toString());
                } 
            }
        });
        dataA.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,addressA,dataA);
			}  
          });  
        
        dataB.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {          
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                	if(addressB.getText().toString()!=null)
    					device.WriteCmd(addressB.getText().toString(),dataB.getText().toString());
                } 
            }
        });
        dataB.setOnKeyListener(new EditText.OnKeyListener()  
        {                 
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return registerKey(keyCode,addressB,dataB);
			}  
          }); 
        
        
      
		readButton.setOnClickListener(new Button.OnClickListener(){							
			@Override
			public void onClick(View view)
			{					
				String read1 = address1.getText().toString();
				if(!read1.isEmpty())
				{
					data1.setText(device.ReadCmd(read1));
					saveAddress(Integer.toString(selectMenuItem)+"ADR1",address1.getText().toString());
				}
				
				String read2= address2.getText().toString();
				if(!read2.isEmpty())
				{
					data2.setText(device.ReadCmd(read2));
					saveAddress(Integer.toString(selectMenuItem)+"ADR2",address2.getText().toString());
				}
				
				String read3 = address3.getText().toString();
				if(!read3.isEmpty())
				{
					data3.setText(device.ReadCmd(read3));
					saveAddress(Integer.toString(selectMenuItem)+"ADR3",address3.getText().toString());
				}
				
				String read4 = address4.getText().toString();
				if(!read4.isEmpty())
				{
					data4.setText(device.ReadCmd(read4));
					saveAddress(Integer.toString(selectMenuItem)+"ADR4",address4.getText().toString());
				}
				
				String read5 = address5.getText().toString();
				if(!read5.isEmpty())
				{
					data5.setText(device.ReadCmd(read5));
					saveAddress(Integer.toString(selectMenuItem)+"ADR5",address5.getText().toString());
				}
				
				String read6 = address6.getText().toString();
				if(!read6.isEmpty())
				{
					data6.setText(device.ReadCmd(read6));
					saveAddress(Integer.toString(selectMenuItem)+"ADR6",address6.getText().toString());
				}
				
				String read7 = address7.getText().toString();
				if(!read7.isEmpty())
				{
					data7.setText(device.ReadCmd(read7));
					saveAddress(Integer.toString(selectMenuItem)+"ADR7",address7.getText().toString());
				}
				
				String read8 = address8.getText().toString();
				if(!read8.isEmpty())
				{
					data8.setText(device.ReadCmd(read8));
					saveAddress(Integer.toString(selectMenuItem)+"ADR8",address8.getText().toString());
				}
				
				String read9 = address9.getText().toString();
				if(!read9.isEmpty())
				{
					data9.setText(device.ReadCmd(read9));
					saveAddress(Integer.toString(selectMenuItem)+"ADR9",address9.getText().toString());
				}
				
				
				String read10 = addressA.getText().toString();
				if(!read10.isEmpty())
				{
					dataA.setText(device.ReadCmd(read10));
					saveAddress(Integer.toString(selectMenuItem)+"ADR10",addressA.getText().toString());
				}
				
				String read11 = addressB.getText().toString();
				if(!read11.isEmpty())
				{
					dataB.setText(device.ReadCmd(read11));
					saveAddress(Integer.toString(selectMenuItem)+"ADR11",addressB.getText().toString());
				}
			}
    });
		

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(RegFragmentView.getContext());
        
		addressText.setText("address(SRAM)");
		loadFunctionAddress(R.id.action_sram);
		
    }
	
    public void check()
    {
    	checkData(address1,data1);
        checkData(address2,data2);
        checkData(address3,data3);
        checkData(address4,data4);
        checkData(address5,data5);
        checkData(address6,data6);
        checkData(address7,data7);
        checkData(address8,data8);
        checkData(address9,data9);
        checkData(addressA,dataA);
        checkData(addressB,dataB);
    }
    public void checkData(EditText address,EditText data)
    {
    	if(address.getText().toString().length() ==0)
        {
        	data.setText("");
        }
        else
        {
        	data.setText(device.ReadCmd(address.getText().toString()));
        }
    	
    	
    }
    
    
   /* public String ReadCmd(String address)
	{
		Process p;
	    try {
	        
	    	p = Runtime.getRuntime().exec("/data/cli tool r "+address);
	    	
	    	DataOutputStream os = new DataOutputStream(p.getOutputStream());
	        BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        os.flush();
	        String data;
	        while((data = bf.readLine()) != null)
            {
            	Log.d(TAG,data);
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
	
	public void writeCmd(String address,String data)
	{
		Process p;
	    try {
	        p = Runtime.getRuntime().exec("data/cli tool w "+address+" "+data);	   
	        
	    } catch (IOException e) {
	       e.printStackTrace();
	    }
	}
	*/
	private boolean addressKey(int keyCode,EditText address)
	{
		return true;
	}
	private boolean registerKey(int keyCode,EditText address,EditText data)
	{
		
		
		if(keyCode == KeyEvent.KEYCODE_ENTER)
		{
			if(address.getText().toString()!=null)
				device.WriteCmd(address.getText().toString(),data.getText().toString());
			return false;
		}
		return false;
	}
    
    public void saveAddress(String address,String StoreData)
    {    	
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(RegFragmentView.getContext());
        
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(address,StoreData);
        editor.commit();
    }
    
    public void loadFunctionAddress(int index)
    {
    	
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(RegFragmentView.getContext());
        
		switch(index)
		{
		case R.id.action_sram:
			address1.setText( str_sram);
			break;
		
		case R.id.action_mcu:
			address1.setText(str_mcu);
			break;
		case R.id.action_timer:
			address1.setText(str_timer);
			break;
		case R.id.action_interrupt:
			address1.setText(str_interrupt);
			break;
		case R.id.action_sdio:
			address1.setText(str_sdio);
			break;
		case R.id.action_hci:
			address1.setText(str_hci);
			break;
		case R.id.action_rx:
			address1.setText( str_rx);
			break;
		case R.id.action_tx:
			address1.setText(str_tx);
			break;
		case R.id.action_mac_general:
			address1.setText(str_mac_general);
			break;
		case R.id.action_id_mangement:
			address1.setText(str_id_mangement);
			break;	
		case R.id.action_rf:
			address1.setText(str_rf);
			break;
		case R.id.action_phy:
			address1.setText(str_phy);
			break;
		default:
			break;
		
		}
		
		
        address2.setText(settings.getString(Integer.toString(index)+"ADR2", ""));
        address3.setText(settings.getString(Integer.toString(index)+"ADR3", ""));
        address4.setText(settings.getString(Integer.toString(index)+"ADR4", ""));
        address5.setText(settings.getString(Integer.toString(index)+"ADR5", ""));
        address6.setText(settings.getString(Integer.toString(index)+"ADR6", ""));
        address7.setText(settings.getString(Integer.toString(index)+"ADR7", ""));
        address8.setText(settings.getString(Integer.toString(index)+"ADR8", ""));
        address9.setText(settings.getString(Integer.toString(index)+"ADR9", ""));
        addressA.setText(settings.getString(Integer.toString(index)+"ADR10", ""));
        addressB.setText(settings.getString(Integer.toString(index)+"AD11", ""));
        check();

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
    	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        selectMenuItem = id;
       
        /*if (id == R.id.action_sram) {
        	}
            */
        addressText.setText("address("+item.getTitle()+")");
        loadFunctionAddress(id);
        return super.onOptionsItemSelected(item);
    }
   
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

		
        public PlaceholderFragment() {
        }
        
       
       
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ssv_wifi_tool, container, false);
            
            
            

            
            
            /*address1.setOnKeyListener(new EditText.OnKeyListener()  
            {                 
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					return addressKey(keyCode,address1);
				}  
              });  
            
            //address1.addTextChangedListener(textWatcher); 
            address2.setOnKeyListener(new EditText.OnKeyListener()  
            {                 
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					return addressKey(keyCode,address2);
				}  
              });
            address3.setOnKeyListener(new EditText.OnKeyListener()  
            {                 
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					return addressKey(keyCode,address3);
				}  
              }); 
            
            address4.setOnKeyListener(new EditText.OnKeyListener()  
            {                 
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					return addressKey(keyCode,address4);
				}  
              }); 
            address5.setOnKeyListener(new EditText.OnKeyListener()  
            {                 
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					return addressKey(keyCode,address5);
				}  
              }); 
            address6.setOnKeyListener(new EditText.OnKeyListener()  
            {                 
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					return addressKey(keyCode,address6);
				}  
              }); 
            */
           
            
           
			
			
            return rootView;
        }
    }
    
    

}
