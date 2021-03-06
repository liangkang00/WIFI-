package com.ssv.ssvwifitool;

import java.util.HashMap;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.text.method.ScrollingMovementMethod;
import android.widget.ScrollView;
import java.text.DecimalFormat;

public class ControlRegFragmentRx extends Fragment{
	private final String TAG = "SSV";
	
	private View ControlRegFragmentView = null;
	private WlanAttributes attr = new WlanAttributes(this.getContext());
	
    private Spinner spinnerClk;
    private Spinner spinnerChLst;
    private Spinner spinnerModeLst;
    private Spinner spinnerRateLst;
    
    private ArrayAdapter<CharSequence> adapterClk;  
    private ArrayAdapter<CharSequence> adapterChLst;
    private ArrayAdapter<CharSequence> adapterModeLst;
    private ArrayAdapter<CharSequence> adapterRateLst;
    
    private EditText etTimer;
    
    HashMap<String, String> wlanMap = new HashMap<String, String>();
    
    private ToggleButton togglebuttonRX; 
    private ToggleButton togglebuttonRxReset;
    
	Context context;

    private TextView textPtkShow;
    private TextView textErrShow;
    private TextView textErrRateShow;
    private TextView textRSSIShow;
    private TextView textRxHistroy;

    private ScrollView scrollRxHis;    

	private Thread timerThread;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("=====>", "ControlRegFragment onAttach");
		
		MainActivity mainActivity = (MainActivity)activity;

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ControlRegFragmentView = inflater.inflate(R.layout.control_panel_rx, container, false);
	
		context = ControlRegFragmentView.getContext();
		
		/* Reference clock */
		spinnerClk = (Spinner)ControlRegFragmentView.findViewById(R.id.spRefClk);
		adapterClk = ArrayAdapter.createFromResource(context,
		            R.array.ref_clk, android.R.layout.simple_list_item_activated_1);
		spinnerClk.setAdapter(adapterClk);
		
		/* Channel */
		spinnerChLst = (Spinner)ControlRegFragmentView.findViewById(R.id.spChannel);
		adapterChLst = ArrayAdapter.createFromResource(context,
		            R.array.channel_list_of_twog, android.R.layout.simple_list_item_activated_1);
		spinnerChLst.setAdapter(adapterChLst);
		
		/* Mode List */	
		spinnerModeLst = (Spinner)ControlRegFragmentView.findViewById(R.id.spMode);
		adapterModeLst = ArrayAdapter.createFromResource(context,
		            R.array.mode_list, android.R.layout.simple_list_item_activated_1);
		spinnerModeLst.setAdapter(adapterModeLst);
		
		/* Rate List */
		spinnerRateLst = (Spinner)ControlRegFragmentView.findViewById(R.id.spRate);
		adapterRateLst = ArrayAdapter.createFromResource(context,
		            R.array.rate_list_of_b, android.R.layout.simple_list_item_activated_1);
		spinnerRateLst.setAdapter(adapterRateLst);		
		
		/* RX on/off btn */
		togglebuttonRX = (ToggleButton)ControlRegFragmentView.findViewById(R.id.toggleRx);
		togglebuttonRxReset = (ToggleButton)ControlRegFragmentView.findViewById(R.id.toggleRxReset);
		
		/* Timer */
		etTimer = (EditText)ControlRegFragmentView.findViewById(R.id.etTimer);
		
        /* TextView show */
        textPtkShow = (TextView)ControlRegFragmentView.findViewById(R.id.textPtkShow);
        textErrShow = (TextView)ControlRegFragmentView.findViewById(R.id.textErrShow);
        textErrRateShow = (TextView)ControlRegFragmentView.findViewById(R.id.textErrRateShow);
        textRSSIShow = (TextView)ControlRegFragmentView.findViewById(R.id.textRSSIShow);

        /* Rx Histroy */
        textRxHistroy = (TextView)ControlRegFragmentView.findViewById(R.id.textRxHistroy);
        textRxHistroy.setMovementMethod(new ScrollingMovementMethod());
        scrollRxHis = (ScrollView)ControlRegFragmentView.findViewById(R.id.scrollRxHis);
        //textRxHistroy.setMovementMethod(ScrollingMovementMethod.getInstance());

		constructingWlanMap();
		
		return ControlRegFragmentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		spinnerClk.setOnItemSelectedListener(new addSpOnItemSelectedListener());
		etTimer.setOnEditorActionListener(new addEtSetOnEditorActionListener());
		spinnerChLst.setOnItemSelectedListener(new addSpOnItemSelectedListener());
		spinnerModeLst.setOnItemSelectedListener(new addSpOnItemSelectedListener());
		spinnerRateLst.setOnItemSelectedListener(new addSpOnItemSelectedListener());
		togglebuttonRX.setOnCheckedChangeListener(new addTgOnCheckedChangeListener());
		togglebuttonRxReset.setOnCheckedChangeListener(new addTgOnCheckedChangeListener());
	}
	
	private Handler timerHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            attr.getRxCounters();
	        attr.resetRxCounters();
            String rssi = attr.getRssi();
            int ptkCounts = attr.getPtkCounts();
            int errCount = attr.getErrCounts();
            double errRate = 0;
            String errRateOutput = "0.0";
            DecimalFormat df = new DecimalFormat("0.000");

            if(ptkCounts > 0) {
                errRate = (double)(errCount * 100) / (double)(ptkCounts);
                errRateOutput = df.format(errRate);
            }
            Log.d(TAG, "fetch ptk "+ptkCounts+", err "+errRate+", rssi "+rssi);

            textPtkShow.setText(String.valueOf(ptkCounts));
            textErrShow.setText(String.valueOf(errCount));
            textErrRateShow.setText(errRateOutput+"%");
            textRSSIShow.setText("-"+String.valueOf(rssi)+" dBM");
            //textRxHistroy.setMovementMethod(new ScrollingMovementMethod());
            //rxHisLog = textRxHistroy.getText().toString() + "\n" + 
            //                        "Ptk: "+ptkCounts+", Err: "+errCount+"("+errRate+"%), RSSI: -"+rssi+" dBm";
            //textRxHistroy.setText(rxHisLog);
            textRxHistroy.append("\n" +"Ptk: "+ptkCounts+"\t\tErr: "+errCount+"("+errRateOutput+"%)\t\tRSSI: -"+rssi+" dBm");
            scrollRxHis.post(new Runnable() {
                public void run() {
                    scrollRxHis.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    };
    
	private class TimerThread extends Thread { 
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                while (true) {    
                    Message msg = new Message();
                    timerHandler.sendMessage(msg);
                    Thread.sleep(Integer.parseInt(etTimer.getText().toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	public class addTgOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

	    	switch (buttonView.getId()) {
	    	case R.id.toggleRx:
	    		Log.d(TAG, "RX ischecked "+isChecked);
		    	if (isChecked) {
                    textRxHistroy.setText("");
		    		timerThread = new TimerThread();
		    		attr.startRx(true);
		    		timerThread.start();
		    	} 
		    	else {
                    timerThreadStop();
		    		attr.startRx(false);
		    	}
		    	break;
		    	
	    	case R.id.toggleRxReset:
	    		Log.d(TAG, "toggleRxReset");
	    		attr.resetRxCounters();
	    		break;
	    	}
	    }
	}
	
	private void constructingWlanMap() {
		mapping(R.array.ref_clk, R.array.ref_clk_values);
		mapping(R.array.channel_list_of_twog, R.array.channel_list_of_twog_values);
		mapping(R.array.mode_list, R.array.mode_list_values);
		mapping(R.array.rate_list_of_b, R.array.rate_list_of_b_values);
		mapping(R.array.rate_list_of_ag, R.array.rate_list_of_ag_values);
		mapping(R.array.rate_list_of_n_ht20, R.array.rate_list_of_n_ht20_values);
		mapping(R.array.channel_list_of_fiveg, R.array.channel_list_of_fiveg_values);
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
	
	public class addEtSetOnEditorActionListener implements EditText.OnEditorActionListener {
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { 
			int timer = Integer.parseInt(etTimer.getText().toString());
			switch (v.getId()) {
			case R.id.etTimer:
				Log.d(TAG, "edTimer: "+timer);
				break;
			}
			return false;
		}
	}
	
	public class addSpOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            
            String selectedItem = parent.getItemAtPosition(pos).toString();
            int wlanKeyValue = Integer.parseInt(wlanMap.get(selectedItem));
            String ret;
            
            //check which spinner triggered the listener
            switch (parent.getId()) {
            case R.id.spRefClk:
            	Log.d(TAG, "Ref Clk: pos "+pos+", selectedItem "+selectedItem+", key "+wlanKeyValue);
            	attr.setRefClk(wlanKeyValue);
            	break;
            	
            case R.id.spChannel:
            	Log.d(TAG, "Channel: pos "+pos+", selectedItem "+selectedItem+", key "+wlanKeyValue);

				ret = attr.setChannel(wlanKeyValue);

                break;
  
            case R.id.spMode:
            	int prev_mode = attr.getMode();
            	Log.d(TAG, "Mode: pos "+pos+" prev_mode "+prev_mode);
                attr.setMode(pos);

                if (pos == WlanAttributes.B_MODE) {
            		adapterRateLst = ArrayAdapter.createFromResource(context,
            		            R.array.rate_list_of_b, android.R.layout.simple_list_item_activated_1);        
                }
                else if (pos == WlanAttributes.G_MODE || pos == WlanAttributes.A_MODE) {
            		adapterRateLst = ArrayAdapter.createFromResource(context,
            		            R.array.rate_list_of_ag, android.R.layout.simple_list_item_activated_1);                  	
                }
                else {
            		adapterRateLst = ArrayAdapter.createFromResource(context,
            		            R.array.rate_list_of_n_ht20, android.R.layout.simple_list_item_activated_1);
                }

        		spinnerRateLst.setAdapter(adapterRateLst);  	
                
        		if (pos <= WlanAttributes.N_MODE) {
        			if (prev_mode >= WlanAttributes.A_MODE) {
	        			adapterChLst = ArrayAdapter.createFromResource(context,
	        		            R.array.channel_list_of_twog, android.R.layout.simple_list_item_activated_1);
	            		spinnerChLst.setAdapter(adapterChLst);
        			}
        		}
        		else {
        			if (prev_mode <= WlanAttributes.N_MODE) {
	        			adapterChLst = ArrayAdapter.createFromResource(context,
	        		            R.array.channel_list_of_fiveg, android.R.layout.simple_list_item_activated_1);
	            		spinnerChLst.setAdapter(adapterChLst);
        			}
        		}
 
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

    void timerThreadStop() {
		if (timerThread != null) {
	        if (!timerThread.isInterrupted()) {
		        timerThread.interrupt();
	        }
        }
    }

	@Override
	public void onStop(){
       	super.onStop();
       	Log.d(TAG, "onStop");
        timerThreadStop();
    }

}
