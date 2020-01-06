package com.ssv.ssvwifitool;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

public class WlanAttributes {
	private static final String TAG = "SSV-WlanAttributes";
	
	public static final String SSV_KO_PATH = "/data/ssv6200.ko";
	private static Context context;
	//position value
	public static final int B_MODE 		= 0;
	public static final int G_MODE 		= 1;
	public static final int N_MODE 		= 2;
	public static final int A_MODE 		= 3;
	public static final int AC_MODE 		= 4;

	public static final int BW_20 		= 0;
	public static final int BW_40 		= 1;
	
	public static final int REF_CLK_40	= 40;
	public static final int REF_CLK_24	= 24;	
	public static final int REF_CLK_26	= 26;
	
	private final int PRE_B		 		= 0;
	private final int PRE_GN				= 1;
	private final int PRE_N				= 2;
	private final int PRE_A				= 3;

	
	private boolean isTxStart = false;
	private boolean isRxStart = false;

	private int bw40		 		= 0;
	private int refClk;
	private int channel;
	private int mode;
	private int dataRate;
	private int dac;
	private int freqOffset;
	private int IQPhase;
	private int IQAmp;
	private int preamble;
	private int rssi;
	private int ptkCount;
	private int ptkErrCount;

	private native String helloString(String toWhat);
	private native void rf_calibration();
	private native void reg_rw_open();
	private native void reg_rw_close();
	private native void channel_change(int ref_clk, int channel);
	private native void data_rate_change(int data_rate);
	private native void freq_offset(int freq_offset);
	private native void start_tx_frame(int data_rate, int dac);
	private native void IQ_change(int IQphase, int IQAmp);
	private native void stop_tx_frame();
	private native void set_dac(int dac);
	private native void start_rx_frame();
	//private native void stop_rx_frame();
	private native void reset_rx_counters();
	private native void retrieve_rx_frame_statistic(int preamble);
	private native int get_rssi();
	private native int get_ptks();
	private native int get_err();

	private native int rf_write_cmd(String cmd);
    private native void rf_start_RX(int channel);
    private native void rf_start_TX(int channel,int bw40, int Data_rate);
    private native void rf_stop();
    private native char rf_set_channel(int channel,int bw40);
    private native char rf_set_rate(int index);
    private native char rf_set_count(int val);
    private native char rf_reset();

    private native char cli_write(String cmd);
    private native String cli_read();
	private native boolean check_wifi_driver_load();
	
	//load share lib, module name is defined in jni/Android.mk
	static {
		System.loadLibrary("ssvWifiTool");
	}
	
	public WlanAttributes(Context context) {
		this.context = context;
		refClk = REF_CLK_26;
		channel = 1;
		mode = 0;
		dataRate = 0;
		dac = 0;
		freqOffset = 0;
		IQPhase = 0;
		IQAmp = 0;
		preamble = 0;
		rssi = 0;
		ptkCount = 0;
		ptkErrCount = 0;
		bw40 = 0;
	}
	
	public int getRefClk() {
		return this.refClk;
	}
	
	public void setRefClk(int clk) {
		this.refClk = clk;
		Log.d(TAG, "set ref clk " + clk);
	}
	
	public int getChannel() {
		return this.channel;
	}
	
	public String setChannel(int channel) {
		this.channel = channel;
		if (!isTxStart && !isRxStart) {
			return null;
		}
		Log.i("SSV","setChannel ch = "+channel);
		String cmd = new String("ch "+channel);
		Log.i("SSV","setChannel cmd = "+cmd);
		if(bw40 == 0){
			return writeCmd(cmd);
		}
		return writeCmd(cmd + " bw40");

	}
	
	public int getMode() {
		return this.mode;
	}
	
	public void setMode(int mode) {
        Log.d(TAG, "Mode: "+mode);
		this.mode = mode;
		if (mode == B_MODE) {
			preamble = PRE_B;
		}else if(mode == G_MODE){
			preamble = PRE_GN;
		}else if(mode == N_MODE){
			preamble = PRE_N;
		}else {
			preamble = PRE_A;
		}
	}

	public boolean isWifiDriverLoad(){
		return check_wifi_driver_load();
	}
	
	public int getDataRate() {
		return this.dataRate;
	}
	
	public String setDataRate(int dataRate) {
		this.dataRate = dataRate;
		if (!isTxStart && !isRxStart)
			return null;
//		data_rate_change(dataRate);
		return writeCmd("rf rate "+dataRate);
	}

	public void setBW(int bw){
		bw40 = bw;
	}

	public int getBW(){
		return bw40;
	}
	
	public int getDAC() {
		return this.dac;
	}
	
	public void setDAC(int dac) {
		this.dac = dac;
		if (!isTxStart)
			return;
		set_dac(dac);
	}
	
	public int getIQAmp() {
		return this.IQAmp;
	}
	
	public void setIQAmp(int amp) {
		if (amp > 15 || amp < -15)
			this.IQAmp = 0;
		else
			this.IQAmp = amp;
		Log.d(TAG, "IQAMP "+IQAmp);
		if (!isTxStart)
			return;
		IQ_change(IQPhase, IQAmp);
	}
	
	public int getIQPhase() {
		return this.IQPhase;
	}
	
	public void setIQPhase(int phase) {
		if (phase > 15 || phase < -15)
			this.IQPhase = 0;
		else
			this.IQPhase = phase;
		Log.d(TAG, "IQPhase "+ IQPhase);
		if (!isTxStart)
			return;
		IQ_change(IQPhase, IQAmp);
	}	
	
	public int getFreqOffset()
	{
		return this.freqOffset;
	}
	
	public void setFreqOffset(int offset)
	{
		this.freqOffset = offset;
		if (!isTxStart)
			return;
		freq_offset(offset);		
	}

	public String writeCmd(String cmd){
        Log.d("SSV","writeCmd cmd = " + cmd);
		String respose = new String ();
        cli_write(cmd);
        String ret = cli_read();
        Log.d("SSV","结果  ret = " + ret);
        String[] result =  ret.split("\n");
        for(int i =0; i < result.length;i++){
        	if(result[i].startsWith("ALLOCATED")){
				return respose;
			}
			if(result[i].length() >1)
				respose += (result[i] + "\n");
		}

//        if(result.length > 1){
//            Log.d(TAG,"return:"+result[1]);
//            return result[1]+"\n";
//        }

        return null;
    }
	
	public String startTX(boolean on) {
		String ack = new String();
        String ret;

		if (on) {
			isTxStart = true;
            ret = writeCmd("rf unblock");
			if(ret != null){
			    ack += ret;
            }

            ret = writeCmd("rf block");
            if(ret != null){
                ack += ret;
            }

            ret = writeCmd("rf ack disable");
            if(ret != null){
                ack += ret;
            }

			ack += setChannel(channel);
			ack += setDataRate(dataRate);

			ret = writeCmd("rf phy_txgen 0xffffffff");
			if(ret != null){
				ack += ret;
			}

		} else {
			Log.d(TAG, "TX OFF");
			if (isTxStart)
				ack += writeCmd("rf unblock");
			isTxStart = false;
		}
		return ack;
	}
	
	public String startRx(boolean on) {
		String ack = new String();
		String ret;
		if (on) {
			isRxStart = true;
//			rf_calibration();
//			channel_change(refClk, channel);
//			data_rate_change(dataRate);
//			start_rx_frame();
			ret = writeCmd("rf unblock");
			if(ret != null){
				ack += ret;
			}

			ret = writeCmd("rf block");
			if(ret != null){
				ack += ret;
			}

			ret = writeCmd("rf ack disable");
			if(ret != null){
				ack += ret;
			}
			ack += setChannel(channel);
			ack += resetRxCounters();

		}
		else {
			//stop_rx_frame();
			ack = writeCmd("rf unblock");
			isRxStart = false;
		}
		return ack;
	}
	
	public String resetRxCounters()
	{
		if (isRxStart)
//			reset_rx_counters();
			return writeCmd("mib reset");
		else
			return "RX Not Start!";
	}
	
	public void getRxCounters() {
		retrieve_rx_frame_statistic(preamble);
	}

    public String getRssi() {
		return writeCmd("rf rssi 1").trim().replaceAll("\r|\n", "");
//        return get_rssi();
    }

    public int getPtkCounts() {
        return get_ptks();
    }

    public int getErrCounts() {
        return get_err();
    }

    public HashMap updateCounts(){
		HashMap<String, String> ret = new HashMap();
		String data;
		if(mode == B_MODE)
			data = writeCmd("rf count 0");
		else
			data = writeCmd("rf count 1");
		String[] val_arr = data.split("\n");
		for(int idx=0;idx<val_arr.length;idx++){
			String[] val = val_arr[idx].split("=");
			if (val.length > 1)
				ret.put(val[0].trim(),val[1].trim());
		}

		return ret;
	}
    
}
