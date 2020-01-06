package com.ssv.ssvwifitool;

import android.widget.EditText;


public class Model {

	private String address;
	private IndexedEditText value;
	private int index;
	
	public String getAddress() {
	    return address;
	}
	
	public void setAddress(String address) {
	    this.address = address;
	}
	
	public void setValue(IndexedEditText value) {
	    this.value = value;
	}
	
	public Model(String address,IndexedEditText value) {
	    this.value = value;
	    this.address = address;
	}
	
	public IndexedEditText getValue() {
	    return value;
	}


}