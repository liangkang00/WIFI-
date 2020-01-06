#include <jni.h>

#include <stdio.h>
#include <unistd.h>
#include <fcntl.h> //file open
#include <unistd.h> //file close
#include <stdlib.h> //system, strtol
#include <stdint.h> //int16_t
#include "types.h"
#include "SDIO-EXE.h"
#include "SSV6051_ATE.h"
#include "log.h"

#include "rf_tool.h"
#include "com_ssv_ssvwifitool_WlanAttributes.h"

u32 Static_Totcount = 0;
u32 Static_Errcount = 0;
u32 Static_RSSI = 0;

JNIEXPORT jstring JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_helloString(
		JNIEnv* env, jobject obj, jstring str)
{
	const char* toWhat = env->GetStringUTFChars(str, JNI_FALSE);
	char hello[80];
	u32 reg_value = 0;
	//sdio_reg_open();
	//sdio_write_reg(0x80000000, 0x1111111a);
	//reg_value = sdio_read_reg(0x80000000);
	//sdio_reg_close();
	sprintf(hello,"Hello, %s! buf %x", toWhat, reg_value);

	return env->NewStringUTF(hello);
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1calibration
  (JNIEnv *, jobject)
{
	LOGD("rf_calibation\n");
	rf_calibration();
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_reg_1rw_1open
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	sdio_reg_open();
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_reg_1rw_1close
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	sdio_reg_close();
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_channel_1change
  (JNIEnv *, jobject, jint xtal_freq, jint channel)
{
	LOGD("%s", __func__);
	sdio_drv_phy_channel_change(xtal_freq, channel);
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_data_1rate_1change
  (JNIEnv *, jobject, jint data_rate)
{
	LOGD("%s", __func__);
	sdio_drv_phy_data_rate_change(data_rate);
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_freq_1offset
  (JNIEnv *, jobject, jint freq_offset)
{
	LOGD("%s", __func__);
	sdio_Xtal_freq_offset((char)freq_offset);

}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_start_1tx_1frame
  (JNIEnv *, jobject, jint data_rate, jint power_index)
{
	LOGD("%s", __func__);

	//TODO: check txpacket
	sdio_Start_TXFrame(0xffffffff, data_rate, power_index);
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_IQ_1change
  (JNIEnv *, jobject, jint IQ_phase, jint IQ_amp)
{
	LOGD("%s", __func__);
	LOGD("p: %x, a: %x\n", (u8)IQ_phase, (u8)IQ_amp);
	sdio_IQ_change((u32)IQ_phase, (u32)IQ_amp);
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_stop_1tx_1frame
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	sdio_Stop_TXFrame();
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_set_1dac
  (JNIEnv *, jobject, jint power_idx)
{
	LOGD("%s", __func__);
	power_index(power_idx);
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_start_1rx_1frame
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	sdio_Start_RXFrame();
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_reset_1rx_1counters
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	sdio_RX_Reset_Counters();
}

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_retrieve_1rx_1frame_1statistic
  (JNIEnv *, jobject, jint preamble)
{
	LOGD("%s", __func__);
	sdio_RXFrame_Statics(preamble, &Static_Totcount, &Static_Errcount, &Static_RSSI);
	LOGD("preamble: %d, Static_Totcount: %d, Static_Errcount: %d, Static_RSSI: %u,%d\n",
			preamble,
            (int)Static_Totcount, (int)Static_Errcount,
			Static_RSSI, (int16_t)Static_RSSI);
}

JNIEXPORT jint JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_get_1rssi
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	return (int16_t)Static_RSSI;
}

JNIEXPORT jint JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_get_1ptks
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	return (int)Static_Totcount;
}

JNIEXPORT jint JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_get_1err
  (JNIEnv *, jobject)
{
	LOGD("%s", __func__);
	return (int)Static_Errcount;
}
 JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1stop
   (JNIEnv *, jobject){
        rf_stop();
   }



 JNIEXPORT jint JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1start_1TX
   (JNIEnv *, jobject, jint channel, jint bw40, jint Data_rate){
        rf_start_TX(channel,bw40,Data_rate);
        return 1;

   }


  JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1start_1RX
    (JNIEnv *, jobject, jint channel){

        rf_start_RX(channel);
    }

JNIEXPORT void JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1set_1channel(
        JNIEnv *, jobject, jint channel,jint bw40){
        rf_set_channel(channel,bw40);
    }

JNIEXPORT jchar JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1set_1rate
    (JNIEnv *, jobject, jint index){
        return rf_set_rate(index);
}


JNIEXPORT jchar JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1set_1count
 (JNIEnv *, jobject, jint val){
    return  rf_set_count(val);

}


JNIEXPORT jchar JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_rf_1reset
  (JNIEnv *, jobject){
    return rf_reset();
 }


 JNIEXPORT jchar JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_cli_1write
   (JNIEnv *env, jobject, jstring cmd){
        const char* cmd_write = env->GetStringUTFChars(cmd, JNI_FALSE);
        return cli_write(cmd_write);
   }


   JNIEXPORT jboolean JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_check_1wifi_1driver_1load
               (JNIEnv *, jobject){
        if(checkWifiDriver() < 0){
            return false;
        }else{
             return true;
        }
    }

JNIEXPORT jstring JNICALL Java_com_ssv_ssvwifitool_WlanAttributes_cli_1read
            (JNIEnv *env, jobject){

   //const char* toWhat = env->GetStringUTFChars(str, JNI_FALSE);
   	char buf[128];
   	u32 reg_value = 0;
   	//sdio_reg_open();
   	//sdio_write_reg(0x80000000, 0x1111111a);
   	//reg_value = sdio_read_reg(0x80000000);
   	//sdio_reg_close();
   	int len = cli_read(buf,128);
   	if(len > 0){
       	return env->NewStringUTF(buf);
   	}else{
   	    return NULL;
   	}


}
