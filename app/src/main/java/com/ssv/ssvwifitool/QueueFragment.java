package com.ssv.ssvwifitool;

import com.ssv.ssvwifitool.R;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ListAdapter; 
import android.widget.ListView;


public class QueueFragment extends Fragment {

	private String value = "";
	private String ADR_RD_FFOUT_CNT1 = "CD000048";
	private String ADR_RD_FFOUT_CNT2 = "CD00004C";
	private String ADR_RD_FFOUT_CNT3 = "CD000050";
	
	private String ADR_RD_IN_FFCNT1 = "CD00001C";
	private String ADR_RD_IN_FFCNT2 = "CD000020";
	
	private String ADR_ID_LEN_THREADSHOLD2 = "CD01003C";
	private String ADR_TAG_STATUS = "CF000030";
	
	TextView mcuQInput;
	TextView mcuQOutput;
	TextView hciQInput;
	TextView hciQOutput;
	TextView securityQInput;
	TextView securityQOutput;
	TextView rxQInput;
	TextView rxQOutput;
	TextView micQInput;
	TextView micQOutput;
	TextView tx0QInput;
	TextView tx0QOutput;
	TextView tx1QInput;
	TextView tx1QOutput;
	TextView tx2QInput;
	TextView tx2QOutput;
	TextView tx3QInput;
	TextView tx3QOutput;
	TextView tx4QInput;
	TextView tx4QOutput;
	TextView security2QInput;
	TextView security2QOutput;
	TextView mic2QInput;
	TextView mic2QOutput;
	TextView trashCanQInput;
	TextView trashCanQOutput;
	
	TextView tx;
	TextView rx2;
	TextView avaliable;
	
	View QueueFragmentView;
	Context mContext;
	MainActivity mainActivity;
	private int m_nTime = 0;
	private Handler mHandlerTime = new Handler();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("=====>", "QueueFragment onAttach");
		mainActivity = (MainActivity)activity;
		//value = mainActivity.getFacebookData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("=====>", "QueueFragment onCreateView");
		
		QueueFragmentView = inflater.inflate(R.layout.frg_queue, container, false);
		mContext = QueueFragmentView.getContext();
		setHasOptionsMenu(true);

		
		return QueueFragmentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("=====>", "QueueFragment onActivityCreated");
		
		mcuQInput = (TextView )QueueFragmentView.findViewById(R.id.textMcuIn);
		mcuQOutput = (TextView )QueueFragmentView.findViewById(R.id.textMcuOut);
		hciQInput = (TextView )QueueFragmentView.findViewById(R.id.textHciIn);
		hciQOutput = (TextView )QueueFragmentView.findViewById(R.id.textHciOut);
		securityQInput = (TextView )QueueFragmentView.findViewById(R.id.textSecurityIn);
		securityQOutput = (TextView )QueueFragmentView.findViewById(R.id.textSecurityOut);
		rxQInput = (TextView )QueueFragmentView.findViewById(R.id.textRxIn);
		rxQOutput = (TextView )QueueFragmentView.findViewById(R.id.textRxOut);
		micQInput = (TextView)QueueFragmentView.findViewById(R.id.textMicIn);
		micQOutput = (TextView )QueueFragmentView.findViewById(R.id.textMicOut);
		tx0QInput = (TextView )QueueFragmentView.findViewById(R.id.textTX0In);
		tx0QOutput = (TextView )QueueFragmentView.findViewById(R.id.textTX0Out);
		tx1QInput = (TextView )QueueFragmentView.findViewById(R.id.textTX1In);
		tx1QOutput = (TextView )QueueFragmentView.findViewById(R.id.textTX1Out);;
		tx2QInput = (TextView )QueueFragmentView.findViewById(R.id.textTX2In);
		tx2QOutput = (TextView )QueueFragmentView.findViewById(R.id.textTX2Out);;
		tx3QInput = (TextView )QueueFragmentView.findViewById(R.id.textTX3In);
		tx3QOutput = (TextView )QueueFragmentView.findViewById(R.id.textTX3Out);;
		tx4QInput = (TextView )QueueFragmentView.findViewById(R.id.textTX4In);
		tx4QOutput = (TextView )QueueFragmentView.findViewById(R.id.textTX4Out);;
		security2QInput = (TextView)QueueFragmentView.findViewById(R.id.textSecurity2In);
		security2QOutput = (TextView)QueueFragmentView.findViewById(R.id.textSecurity2Out);
		mic2QInput = (TextView )QueueFragmentView.findViewById(R.id.textMic2In);
		mic2QOutput = (TextView)QueueFragmentView.findViewById(R.id.textMic2Out);
		trashCanQInput = (TextView)QueueFragmentView.findViewById(R.id.textTrashCanIn);
		trashCanQOutput = (TextView)QueueFragmentView.findViewById(R.id.textTrashCanOut);
		
		tx = (TextView)QueueFragmentView.findViewById(R.id.textTxValue);
		rx2 = (TextView)QueueFragmentView.findViewById(R.id.textRx2Value);
		avaliable = (TextView)QueueFragmentView.findViewById(R.id.textAvaliableValue);
		updateValue();
		mHandlerTime.postDelayed(timerRun, 1000);
		
	}
	@Override
	public void onStop()
	{
		super.onStop();
		mHandlerTime.removeCallbacks(timerRun);
	}
	
	 private final Runnable timerRun = new Runnable()
	  {
	    public void run()
	    {
	      updateValue();
	      mHandlerTime.postDelayed(this, 1000);
	      // 若要取消可以寫一個判斷在這決定是否啟動下一次即可
	    }
	    
	  };
	void updateValue()
	{
		final WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		WifiStatusControl.checkWifiStateAndOn(mainActivity);
		if (wifi.getWifiState() != WifiManager.WIFI_STATE_DISABLED)
			return;
		
		DeviceUtil dev= new DeviceUtil();
		String value = dev.ReadCmd(ADR_RD_FFOUT_CNT1);
		long Lvalue = Long.parseLong(value,16);
	    int  mcuO = (int)(Lvalue & 0x0000001f);
		mcuQOutput.setText(String.valueOf(mcuO));
		
		int  hciO = (int)((Lvalue & 0x000003e0)>>5);
		hciQOutput.setText(String.valueOf(hciO)); 
		
		int  securityO = (int)((Lvalue & 0x00000c00)>>10);
		securityQOutput.setText(String.valueOf(securityO));
		
		int  rxO = (int)((Lvalue & 0x000f8000)>>15);
		rxQOutput.setText(String.valueOf(rxO));
		
		int  micO = (int)((Lvalue & 0x00f00000)>>20);
		micQOutput.setText(String.valueOf(micO));
		
		int tx0O = (int)((Lvalue & 0x0e000000)>>25);
		tx0QOutput.setText(String.valueOf(tx0O));
		
		value = dev.ReadCmd(ADR_RD_FFOUT_CNT2);
		Lvalue = Long.parseLong(value,16);
	    int  tx1O = (int)(Lvalue & 0x0000001f);
		tx1QOutput.setText(String.valueOf(tx1O));
		
		int  tx2O = (int)((Lvalue & 0x000003e0)>>5);
		tx2QOutput.setText(String.valueOf(tx2O)); 
		
		int  tx3O = (int)((Lvalue & 0x00000c00)>>10);
		tx3QOutput.setText(String.valueOf(tx3O));
		
		int  tx4O = (int)((Lvalue & 0x000f8000)>>15);
		tx4QOutput.setText(String.valueOf(tx4O));
		
		int  security2O = (int)((Lvalue & 0x00f00000)>>20);
		security2QOutput.setText(String.valueOf(security2O));
		
		int mic2O = (int)((Lvalue & 0x0e000000)>>25);
		mic2QOutput.setText(String.valueOf(mic2O));
		
		value = dev.ReadCmd(ADR_RD_FFOUT_CNT3);
		Lvalue = Long.parseLong(value,16);
	    int  trashO = (int)((Lvalue & 0x001f8000)>>15);
	    trashCanQOutput.setText(String.valueOf(trashO));
	    
	    //input
	    
	    value = dev.ReadCmd(ADR_RD_IN_FFCNT1);
		Lvalue = Long.parseLong(value,16);
	    int  mcuI = (int)(Lvalue & 0x0000001f);
		mcuQInput.setText(String.valueOf(mcuI));
		
		int  hciI = (int)((Lvalue & 0x000001e0)>>5);
		hciQInput.setText(String.valueOf(hciI)); 
		
		int  securityI = (int)((Lvalue & 0x00003800)>>11);
		securityQInput.setText(String.valueOf(securityI));
		
		int  rxI = (int)((Lvalue & 0x000e0000)>>17);
		rxQInput.setText(String.valueOf(rxI));
		
		int  micI = (int)((Lvalue & 0x00700000)>>20);
		micQInput.setText(String.valueOf(micI));
		
		int tx0I = (int)((Lvalue & 0x03800000)>>23);
		tx0QInput.setText(String.valueOf(tx0I));
		
	    int  tx1I = (int)((Lvalue & 0x01c00000)>>26);
		tx1QInput.setText(String.valueOf(tx1I));
		
		int  tx2I = (int)((Lvalue & 0xe0000000)>>29);
		tx2QInput.setText(String.valueOf(tx2I)); 
		
		value = dev.ReadCmd(ADR_RD_IN_FFCNT2);
		Lvalue = Long.parseLong(value,16);
		
		int  tx3I = (int)((Lvalue & 0x00000007)>>0);
		tx3QInput.setText(String.valueOf(tx3I));
		
		int  tx4I = (int)((Lvalue & 0x00000038)>>3);
		tx4QInput.setText(String.valueOf(tx4I));
		
		int  security2I = (int)((Lvalue & 0x000001c0)>>6);
		security2QInput.setText(String.valueOf(security2I));
		
		int mic2I = (int)((Lvalue & 0x00000600)>>9);
		mic2QInput.setText(String.valueOf(mic2I));
		
		
	    int  trashI = (int)((Lvalue & 0x00006000)>>13);
	    trashCanQInput.setText(String.valueOf(trashI));
	    
	    value =dev.ReadCmd(ADR_ID_LEN_THREADSHOLD2);
		Lvalue = Long.parseLong(value,16);
	    int  txId = (int)((Lvalue & 0x0003fe00)>>9);
	    tx.setText(String.valueOf(txId));
	    
	    int  rxId = (int)((Lvalue & 0x07fc0000)>>18);
		rx2.setText(String.valueOf(rxId));
		
		value = dev.ReadCmd(ADR_TAG_STATUS);
		Lvalue = Long.parseLong(value,16);
		int  avalible = (int)((Lvalue & 0x01ff0000)>>16);
		avaliable.setText(String.valueOf(avalible));
	}
	
	
	
	
}
