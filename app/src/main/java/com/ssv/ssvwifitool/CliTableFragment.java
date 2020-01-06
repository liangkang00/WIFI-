package com.ssv.ssvwifitool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ssv.ssvwifitool.chip.Chip;
import com.ssv.ssvwifitool.chip.ChipModule;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import junit.framework.Test;
//import org.junit.Test;

public class CliTableFragment extends Fragment implements View.OnClickListener{


    private static final String TAG = "SSV";
	List<Model> lstItem ;
    List<ChipModule> lstChip ;
    private View CliTableFragmentView = null;
    private Button btn_send;
    private EditText edt_cli;
    private TextView txt_content;
    ///SimpleAdapter adapter ;
  	
    private DeviceUtil device ;
   
    ArrayAdapter<Model> adapter;
	private WlanAttributes attr = new WlanAttributes(this.getContext());
    
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("=====>", "GoogleFragment onAttach");
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		CliTableFragmentView = inflater.inflate(R.layout.frg_cli, container, false);

		btn_send = (Button)CliTableFragmentView.findViewById(R.id.btnSend);
		btn_send.setOnClickListener(this);

		edt_cli = (EditText)CliTableFragmentView.findViewById(R.id.cliEdit);

		txt_content = (TextView)CliTableFragmentView.findViewById(R.id.cliContent);
		return CliTableFragmentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	    
		}


	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.btnSend){
			// call jni to send cmd
			String ret = new String();
			ret = attr.writeCmd(edt_cli.getText().toString());
			//show ack info
			txt_content.setText(ret);
		}
	}
}


