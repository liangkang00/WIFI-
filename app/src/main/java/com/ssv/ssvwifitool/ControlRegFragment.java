package com.ssv.ssvwifitool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.RadioGroup;
import com.ssv.ssvwifitool.WlanAttributes;
import android.os.Handler;

import android.os.HandlerThread;

public class ControlRegFragment extends Fragment{
	private final String TAG = "SSV";
    private View ControlRegFragmentView = null;
    private WlanAttributes attr = new WlanAttributes(this.getContext());
    
    //private View TempView = null;
    private Spinner spinnerChLst;
    private Spinner spinnerModeLst;
    private Spinner spinnerRateLst;

    private Button resetBtn;
    
    //private Spinner spinnerClk;
    
    private ArrayAdapter<CharSequence> adapterTRX;
    private ArrayAdapter<CharSequence> adapterChLst;
    private ArrayAdapter<CharSequence> adapterModeLst;
    private ArrayAdapter<CharSequence> adapterRateLst;
	private ArrayAdapter<CharSequence> adapterBwLst;
    private ArrayAdapter<CharSequence> adapterDACLst;
    
    private String[] mChannelArray;
    private String[] mRateArray;
    private String[] mModeArray;
    private String[] mDACArray;
    
    private Button btnIQPhaseUp, btnIQPhaseDown;
    private Button btnIQAmpUp, btnIQAmpDown;
    private Button btnFreqOffsetUp, btnFreqOffsetDown;
    
    private EditText etIQPhase, etIQAmp, etFreqOffset;

    private LinearLayout bw40_layout;
    private Spinner spBw40;
    
    HashMap<String, String> wlanMap = new HashMap<String, String>();
    
    private ToggleButton togglebuttonTX; 
    
    Context context;
    
    private Handler mHandler=new Handler();
    private Handler mThreadHandler;
    private HandlerThread mThread;
    
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("=====>", "ControlRegFragment onAttach");
		
		MainActivity mainActivity = (MainActivity)activity;

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ControlRegFragmentView = inflater.inflate(R.layout.control_panel, container, false);
		//TempView = inflater.inflate(R.layout.frg_reg_view, container, false);
		
		context = ControlRegFragmentView.getContext();

		/* tx or rx */
		//radioClk = (RadioGroup)ControlRegFragmentView.findViewById(R.id.radioClock);

		/* Channel List */
		mChannelArray = getResources().getStringArray(R.array.channel_list_of_twog);
		spinnerChLst = (Spinner)ControlRegFragmentView.findViewById(R.id.spChannel);
		adapterChLst = ArrayAdapter.createFromResource(context,
		            R.array.channel_list_of_twog, android.R.layout.simple_list_item_activated_1);
		spinnerChLst.setAdapter(adapterChLst);
        //int SpinnerPostion = adapterChLst.getPosition("");
        //spinnerChLst.setSelection(adapterChLst.getCount()-1);		
		
		/* Mode List */	
		spinnerModeLst = (Spinner)ControlRegFragmentView.findViewById(R.id.spMode);
		adapterModeLst = ArrayAdapter.createFromResource(context,
		            R.array.mode_list, android.R.layout.simple_list_item_activated_1);
		spinnerModeLst.setAdapter(adapterModeLst);
		
		/* Rate List */
		mRateArray = getResources().getStringArray(R.array.rate_list_of_b);
		spinnerRateLst = (Spinner)ControlRegFragmentView.findViewById(R.id.spRate);
		adapterRateLst = ArrayAdapter.createFromResource(context,
		            R.array.rate_list_of_b, android.R.layout.simple_list_item_activated_1);
		spinnerRateLst.setAdapter(adapterRateLst);

		bw40_layout = (LinearLayout) ControlRegFragmentView.findViewById(R.id.layout_bw40);


		spBw40 = (Spinner)ControlRegFragmentView.findViewById(R.id.spBw);
		adapterBwLst = ArrayAdapter.createFromResource(context,
				R.array.bw_list, android.R.layout.simple_list_item_activated_1);
		spBw40.setAdapter(adapterBwLst);

		
		/* TX on/off btn */
		togglebuttonTX = (ToggleButton)ControlRegFragmentView.findViewById(R.id.toggleTx);

		resetBtn = (Button)ControlRegFragmentView.findViewById(R.id.btnRest);
		resetBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attr.writeCmd("rx");
			}
		});

		constructingWlanMap();
		
		return ControlRegFragmentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//radioClk.setOnCheckedChangeListener(new addRadioOnCheckedChangeListener());
		spinnerChLst.setOnItemSelectedListener(new addSpOnItemSelectedListener());
		spinnerModeLst.setOnItemSelectedListener(new addSpOnItemSelectedListener());
		spinnerRateLst.setOnItemSelectedListener(new addSpOnItemSelectedListener());
		togglebuttonTX.setOnCheckedChangeListener(new addTgOnCheckedChangeListener());
		spBw40.setOnItemSelectedListener(new addSpOnItemSelectedListener());
	}
	

	
	private void constructingWlanMap() {
		mapping(R.array.ref_clk, R.array.ref_clk_values);
		mapping(R.array.channel_list_of_twog, R.array.channel_list_of_twog_values);
		mapping(R.array.mode_list, R.array.mode_list_values);
		mapping(R.array.rate_list_of_b, R.array.rate_list_of_b_values);
		mapping(R.array.rate_list_of_ag, R.array.rate_list_of_ag_values);
		mapping(R.array.rate_list_of_n_ht20, R.array.rate_list_of_n_ht20_values);
		mapping(R.array.rate_list_of_n_ht40, R.array.rate_list_of_n_ht40_values);
		mapping(R.array.channel_list_of_fiveg, R.array.channel_list_of_fiveg_values);
		mapping(R.array.bw_list, R.array.bw_list_values);
	}
	
	private void mapping(int id, int idValue) {
		String[] mStringArray;
		String[] mStringArrayValue;
		mStringArray = getResources().getStringArray(id);
		mStringArrayValue =	getResources().getStringArray(idValue);
		for (int i = 0; i < mStringArray.length; i++) {
			wlanMap.put(mStringArray[i], mStringArrayValue[i]);
		}
	}
	
	private void dumpWlanMap() {
        for (Object key : wlanMap.keySet()) {
            Log.d(TAG, "HashMap: " + key + "\t==>\t" + wlanMap.get(key));
        }
	}
	
	/*
	public class addRadioOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radio24M:
				Log.d(TAG, "Radio 24 MHz");
				attr.setRefClk(24);
				break;
			case R.id.radio26M:
				Log.d(TAG, "Radio 26 MHz");
				attr.setRefClk(26);
				break;
			}
	    }
	}
	*/
	
	public class addTgOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    	attr.startTX(isChecked);
	    }
	}
	
	public class addSpOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            
            String selectedItem = parent.getItemAtPosition(pos).toString();
            int wlanKeyValue = Integer.parseInt(wlanMap.get(selectedItem));
            
            //check which spinner triggered the listener
            switch (parent.getId()) {
            case R.id.spChannel:
            	Log.d(TAG, "Channel: pos "+pos+", selectedItem "+selectedItem+", key "+wlanKeyValue);
            	
//            	attr.setChannel(wlanKeyValue);

                break;
  
            case R.id.spMode:
            	int prev_mode = attr.getMode();
            	Log.d(TAG, "Mode: pos "+pos+" prev_mode "+prev_mode);
                attr.setMode(pos);

                if (pos == WlanAttributes.B_MODE) {
					bw40_layout.setVisibility(View.INVISIBLE);
                	mRateArray = getResources().getStringArray(R.array.rate_list_of_b);
            		adapterRateLst = ArrayAdapter.createFromResource(context,
            		            R.array.rate_list_of_b, android.R.layout.simple_list_item_activated_1);        
                }
                else if (pos == WlanAttributes.G_MODE || pos == WlanAttributes.A_MODE) {
					bw40_layout.setVisibility(View.INVISIBLE);
                	mRateArray = getResources().getStringArray(R.array.rate_list_of_ag);
            		adapterRateLst = ArrayAdapter.createFromResource(context,
            		            R.array.rate_list_of_ag, android.R.layout.simple_list_item_activated_1);                  	
                }
                else {
					bw40_layout.setVisibility(View.VISIBLE);

					int bw_pos = spBw40.getSelectedItemPosition();

					if(bw_pos == WlanAttributes.BW_20){
						mRateArray = getResources().getStringArray(R.array.rate_list_of_n_ht20);
						adapterRateLst = ArrayAdapter.createFromResource(context,
								R.array.rate_list_of_n_ht20, android.R.layout.simple_list_item_activated_1);
					}else{
						mRateArray = getResources().getStringArray(R.array.rate_list_of_n_ht40);
						adapterRateLst = ArrayAdapter.createFromResource(context,
								R.array.rate_list_of_n_ht40, android.R.layout.simple_list_item_activated_1);
					}

                }

        		spinnerRateLst.setAdapter(adapterRateLst);  	
                
        		if (pos <= WlanAttributes.N_MODE) {
        			if (prev_mode >= WlanAttributes.A_MODE) {
	            		mChannelArray = getResources().getStringArray(R.array.channel_list_of_twog);
	        			adapterChLst = ArrayAdapter.createFromResource(context,
	        		            R.array.channel_list_of_twog, android.R.layout.simple_list_item_activated_1);
	            		spinnerChLst.setAdapter(adapterChLst);
        			}
        		}
        		else {
        			if (prev_mode <= WlanAttributes.N_MODE) {
	            		mChannelArray = getResources().getStringArray(R.array.channel_list_of_fiveg);
	        			adapterChLst = ArrayAdapter.createFromResource(context,
	        		            R.array.channel_list_of_fiveg, android.R.layout.simple_list_item_activated_1);
	            		spinnerChLst.setAdapter(adapterChLst);
        			}
        		}
 
        		break;
			case R.id.spBw:
				Log.d(TAG, "Band width: pos "+pos);
				if (pos == WlanAttributes.BW_20) {
					mRateArray = getResources().getStringArray(R.array.rate_list_of_n_ht20);
					adapterRateLst = ArrayAdapter.createFromResource(context,
							R.array.rate_list_of_n_ht20, android.R.layout.simple_list_item_activated_1);
				}else{
					mRateArray = getResources().getStringArray(R.array.rate_list_of_n_ht40);
					adapterRateLst = ArrayAdapter.createFromResource(context,
							R.array.rate_list_of_n_ht40, android.R.layout.simple_list_item_activated_1);

				}
				spinnerRateLst.setAdapter(adapterRateLst);
				break;

                
            case R.id.spRate:
            	Log.d(TAG, "Rate: pos "+pos);
            	attr.setDataRate(wlanKeyValue);
                break;
            }         
        }
 
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }	
	}
	
	@Override
	public void onDestroy(){
       	super.onDestroy();
       	Log.d(TAG, "onDestroy");

		WifiStatusControl.setWifiUnload(this.getContext());
       
    }
	@Override
	public void onStop(){
       super.onStop();
       Log.d(TAG, "onStop");
    }
}
