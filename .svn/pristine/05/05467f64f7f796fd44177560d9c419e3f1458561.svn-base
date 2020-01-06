package com.ssv.ssvwifitool;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Model> implements TextWatcher {

private final List<Model> list;
private final Context context;
int listPosititon;
LayoutInflater inflater;

DeviceUtil dev;

ViewHolder viewHolder = null;

public MyAdapter(Context context, List<Model> list,DeviceUtil dev) {
    super(context, R.layout.frg_reg_view, list);
    this.context = context;
    this.list = list;
    this.dev = dev;
}

static class ViewHolder {
	IndexedEditText text;
    protected TextView address;
}
public void showSoftKeyboard(IndexedEditText edt) {
    InputMethodManager imm =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    //imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
}
public void disableSoftKeyboard(View v) {
    InputMethodManager imm =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    //imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
    imm.hideSoftInputFromWindow(v.getWindowToken(),   InputMethodManager.HIDE_NOT_ALWAYS);

}
@Override
public View getView(final int position, View convertView, ViewGroup parent) {
    listPosititon = position;
    if (convertView == null) {
    	
    	 if(inflater == null)
             inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        convertView = inflater.inflate(R.layout.frg_reg_view, null);
        viewHolder = new ViewHolder();
        
        viewHolder.address = (TextView) convertView
                .findViewById(R.id.EditText1);
      
        viewHolder.text = (IndexedEditText) convertView.findViewById(R.id.EditText2);
        
        convertView.setTag(viewHolder);
        convertView.setTag(R.id.EditText1, viewHolder.text);
        convertView.setTag(R.id.EditText2, viewHolder.address);
      
        
    } else {
        viewHolder = (ViewHolder) convertView.getTag();
    }
    viewHolder.address.setText(list.get(position).getAddress());
    
    viewHolder.text.setText(list.get(position).getValue().getText().toString());
    
    viewHolder.text.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View arg0, boolean arg1) {
         // TODO Auto-generated method stub
         IndexedEditText edt = (IndexedEditText)arg0.findViewById(R.id.EditText2);
         if(arg1 == false)
         {

        	 disableSoftKeyboard(arg0);
          String strEdit = edt.getText().toString();

          Model b =list.get(position);
          int index = b.getValue().getListIndex();
          edt.setListIndex(index);
          b.getValue().setText(strEdit);
          
          String c =b.getValue().getText().toString();
          String d =b.getAddress();
          list.set(index, b);
          Log.d("angel","write"+d+","+c);
          dev.WriteCmd(d,c);
          //b.getValue().setText(dev.ReadCmd(d));
         }
         else
         {
        	 Log.d("viewHolder", "on Focus");
        	 //edt.setFocusable(true); 
        	 //edt.requestFocus();
        	 //showSoftKeyboard(edt);
        	 //InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        	 
         }
       }});
    return convertView;
}

@Override
public void afterTextChanged(Editable s) {
	
}

@Override
public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
        int arg3) {
    // TODO Auto-generated method stub
	//listvew.getPositionForView((View) v.getParent())
}

@Override
public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
   

}
}