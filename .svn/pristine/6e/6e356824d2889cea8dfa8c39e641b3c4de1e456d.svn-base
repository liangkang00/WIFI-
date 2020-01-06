// SDIO-EXE.cpp : Defines the entry point for the application.
//

#include "SDIO-EXE.h"
#include "ssv6200_ate.h"
#include "types.h"
#include "sdio_rw.h"
#include "log.h"

#if 0
extern "C"
{
	#include "SDIO-EXE.h"
	#include "ssv6200_ate.h"
	#include "types.h"
}
#endif

#define MAX_LOADSTRING 100
#define SX_LCK_BIN_CNT_MAX 80000    // SX-LCK-BIN takes about 100us. SX-LCK-VT-MON takes about 12.8ms.




const u32 CabrioE_PHY_20140326[][2] = { // Table from Eden
	{0xCE000004,0x00000000},
	{0xCE000000,0x80000016},
	{0xCE000010,0x00007FFF},
	{0xCE000018,0x0055003C},
	{0xCE00001C,0x00000064},
	{0xCE000020,0x20000400},
	{0xCE000030,0x80046072},
	{0xCE000038,0x660F36D0},
	{0xCE00003C,0x106c0004},
	{0xCE000040,0x01600500},
	{0xCE00007C,0x10110003},
	{0xCE000080,0x0110000F},
	{0xCE000094,0x01012425},
	{0xCE000098,0x01010101},
	{0xCE0010B4,0x00003001},
	{0xCE0030A4,0x00001901},
	{0xCE004004,0x00750075},
	{0xCE004008,0x00000075},
	{0xCE00400c,0x10000075},
	{0xCE004010,0x3F404905},
	{0xCE004014,0x40182000},
	{0xCE004018,0x28680000},
	{0xCE00401C,0x0c010120},
	{0xCE004020,0x50505050},
	{0xCE004024,0x50000000},
	{0xCE004028,0x50505050},
	{0xCE00402c,0x506070A0},
	{0xCE004030,0xF0000000},
	{0xCE004034,0x00002424},
	{0xCE00409c,0x0000300A},
	{0xCE0040C0,0x40000280},
	{0xCE0040C4,0x30023002},
	{0xCE004130,0x40000000},
	{0xCE004164,0x009C007E},
	{0xCE004180,0x00044400},
	{0xCE004188,0x82050000},
	{0xCE004190,0x00001820},
	{0xCE0043FC,0x00010421},
	{0xCE002008,0x20400040},
	{0xCE002014,0x20304015},
	{0xCE00201C,0x02333567},
	{0xCE002030,0x04061787},
	{0xCE00209c,0x00c0000A},
	{0xCE0020A0,0x00000000},
	{0xCE0023FC,0x00000001},
	{0xCE000004,0x0000017F},
};

const u32 rg_data_rate_select[][3]={
		{0,0xCE00000C,0x00000400},       //1M
		{1,0xCE00000C,0x01000400},       //2M
	    {2,0xCE00000C,0x02000400},       //5.5M
		{3,0xCE00000C,0x03000400},       //11M
		{4,0xCE00000C,0x01400400},       //2M SP
		{5,0xCE00000C,0x02400400},       //5.5M SP
		{6,0xCE00000C,0x03400400},       //11M SP
		{7,0xCE00000C,0x00010400},       //6M
		{8,0xCE00000C,0x01010400},       //9M
		{9,0xCE00000C,0x02010400},       //12M
		{10,0xCE00000C,0x03010400},       //18M
		{11,0xCE00000C,0x04010400},       //24M
		{12,0xCE00000C,0x05010400},       //36M
		{13,0xCE00000C,0x06010400},       //48M
		{14,0xCE00000C,0x07010400},       //54M
		{15,0xCE00000C,0x00021000},       //MCS0
		{16,0xCE00000C,0x01021000},       //MCS1
		{17,0xCE00000C,0x02021000},       //MCS2
		{18,0xCE00000C,0x03021000},       //MCS3
		{19,0xCE00000C,0x04021000},       //MCS4
		{20,0xCE00000C,0x05021000},       //MCS5
		{21,0xCE00000C,0x06021000},       //MCS6
		{22,0xCE00000C,0x07021000},       //MCS7
		{23,0xCE00000C,0x00821000},       //SGI-MCS0
		{24,0xCE00000C,0x01821000},       //SGI-MCS1
		{25,0xCE00000C,0x02821000},       //SGI-MCS2
		{26,0xCE00000C,0x03821000},       //SGI-MCS3
		{27,0xCE00000C,0x04821000},       //SGI-MCS4
		{28,0xCE00000C,0x05821000},       //SGI-MCS5
		{29,0xCE00000C,0x06821000},       //SGI-MCS6
		{30,0xCE00000C,0x07821000},       //SGI-MCS7
};


                                   
const syn_channel_select VT_Tbl_40M[]={ 
		{ 1,0xf1,0x333333,3859,3859},       
		{ 2,0xf1,0xB33333,3867,3867},       
		{ 3,0xf2,0x333333,3875,3875},       
		{ 4,0xf2,0xB33333,3883,3883},       
		{ 5,0xf3,0x333333,3891,3891},       
		{ 6,0xf3,0xB33333,3899,3899},       
		{ 7,0xf4,0x333333,3907,3907},       
		{ 8,0xf4,0xB33333,3915,3915},       
		{ 9,0xf5,0x333333,3923,3923},       
		{10,0xf5,0xB33333,3931,3931},       
		{11,0xf6,0x333333,3939,3939},       
		{12,0xf6,0xB33333,3947,3947},       
		{13,0xf7,0x333333,3955,3955},       
		{14,0xf8,0x666666,3974,3974}		    
};                                      


const syn_channel_select VT_Tbl_26M[]={
		{ 1,0xB9,0x89D89E,3859,3859},
		{ 2,0xB9,0xEC4EC5,3867,3867},
		{ 3,0xBA,0x4EC4EC,3875,3875},
		{ 4,0xBA,0xB13B14,3883,3883},
		{ 5,0xBB,0x13B13B,3891,3891},
		{ 6,0xBB,0x762762,3899,3899},
		{ 7,0xBB,0xD89D8A,3907,3907},
		{ 8,0xBC,0x3B13B1,3915,3915},
		{ 9,0xBC,0x9D89D9,3923,3923},
		{10,0xBD,0x000000,3931,3931},
		{11,0xBD,0x627627,3939,3939},
		{12,0xBD,0xC4EC4F,3947,3947},
		{13,0xBE,0x276276,3955,3955},
		{14,0xBF,0x13B13B,3974,3974}		
};

                                          
const syn_channel_select VT_Tbl_24M[]={   
		{ 1,0xC9,0x000000 ,3859,3859},        
		{ 2,0xC9,0x6AAAAB ,3867,3867},        
		{ 3,0xC9,0xD55555 ,3875,3875},        
		{ 4,0xCA,0x400000 ,3883,3883},        
		{ 5,0xCA,0xAAAAAB ,3891,3891},        
		{ 6,0xCB,0x155555 ,3899,3899},        
		{ 7,0xCB,0x800000 ,3907,3907},        
		{ 8,0xCB,0xEAAAAB ,3915,3915},        
		{ 9,0xCC,0x555555 ,3923,3923},        
		{10,0xCC,0xC00000 ,3931,3931},        
		{11,0xCD,0x2AAAAB ,3939,3939},        
		{12,0xCD,0x955555 ,3947,3947},        
		{13,0xCE,0x000000 ,3955,3955},        
		{14,0xCF,0x000000 ,3974,3974}		      
};                                        
                                          


bool drv_phy_channel_change(int Xtalfreq, s32 channel_id)
{
	//calibration
        u32 phy_sx_lck_bin_cnt;
        u32 phy_sx_lck_bin_rdy;

#if 0
	SET_CBR_RG_EN_EXT_DA(0);
	//LOG_PRINTF("Change to channel:%d\n",channel_id);
	
	//Step1:SET_REFBYTWO
	//drv_phy_set_sx_refbytwo(1);OS_MsDelay(20);     /*??????this delay cause read and write occur error, it's better to find it out?????

	SET_CBR_RG_SX_REFBYTWO(1)
	//Step2:
	SET_CBR_RG_SX_RFCTRL_F(VT_Tbl_26M[channel_id-1].rf_ctrl_F);
	//LOG_PRINTF("Write Register15:=%x\n",VT_Tbl_26M[channel_id-1].rf_ctrl_F);
	//LOG_PRINTF("Read  Register15:=%x\n",drv_phy_get_sx_rfctrl_f());
	SET_RG_SX_RFCTRL_CH(VT_Tbl_26M[channel_id-1].rf_ctrl_N);
	//LOG_PRINTF("Write Register16:=%x\n",VT_Tbl_26M[channel_id-1].rf_ctrl_N);
	//LOG_PRINTF("Read  Register16:=%x\n",drv_phy_get_sx_rfctrl_ch());
	//???Hans procedur
	SET_CBR_RG_SX_SUB_SEL_CWR(1);
	SET_CBR_RG_SX_SUB_SEL_CWR(0);
	//Step3:Calibration
	SET_CBR_RG_EN_SX_VT_MON(1);

	/*(*(volatile u32 *) 0xcb110038)= 0x00006c7c;
	LOG_PRINTF("0:drv_phy_get_en_vt_mon_dg()=%d\n",drv_phy_get_en_vt_mon_dg());
	(*(volatile u32 *) 0xcb110038)= 0x00007c7c;
	LOG_PRINTF("1:drv_phy_get_en_vt_mon_dg()=%d\n",drv_phy_get_en_vt_mon_dg());*/

		
	
	SET_CBR_RG_EN_SX_VT_MON_DG(0);
	//LOG_PRINTF("0:drv_phy_get_en_vt_mon_dg()=%d\n",drv_phy_get_en_vt_mon_dg());
	SET_CBR_RG_EN_SX_VT_MON_DG(1);
	//LOG_PRINTF("1:drv_phy_get_en_vt_mon_dg()=%d\n",drv_phy_get_en_vt_mon_dg());
	//LOG_PRINTF("Waiting......\n");
	OS_MsDelay(400);
	if(GET_CBR_VT_MON_RDY)
	{
		if(!GET_CBR_AD_SX_VT_MON_Q)
		{
			
		//	LOG_PRINTF("Lock\n");
			SET_CBR_RG_EN_EXT_DA(1);
		//	LOG_PRINTF("phy on\n");
			return 0;
		}
		else
		{
			//	LOG_PRINTF("drv_mac_get_ad_sx_vt_mon_q()=%d\n",drv_phy_get_ad_sx_vt_mon_q());
			//	LOG_PRINTF("Failed\n");
				return -1;
		}

		
	}
	else
	{
		return -1;
	}
#endif
        #ifdef __LOG_DRV_PHY_CHANNEL_CHANGE__
	//LOG_PRINTF("Change to channel:%d\n",channel_id);
	;
        #endif;


	SET_RG_SX_LCK_BIN_PRECISION (1)  ;

        #ifdef __LOG_DRV_PHY_CHANNEL_CHANGE__
//	LOG_PRINTF("Write Register15:=%x\n",VT_Tbl_26M[channel_id-1].rf_ctrl_F);
//	LOG_PRINTF("Read  Register15:=%x\n",drv_phy_get_sx_rfctrl_f());
        #endif

        #ifdef __LOG_DRV_PHY_CHANNEL_CHANGE__
//	LOG_PRINTF("Write Register16:=%x\n",VT_Tbl_26M[channel_id-1].rf_ctrl_N);
//	LOG_PRINTF("Read  Register16:=%x\n",drv_phy_get_sx_rfctrl_ch());
        #endif

	if(Xtalfreq == 26)
    {
    	SET_CBR_RG_SX_RFCTRL_F(VT_Tbl_26M[channel_id-1].rf_ctrl_F);
		SET_RG_SX_RFCTRL_CH(VT_Tbl_26M[channel_id-1].rf_ctrl_N);	
    	SET_RG_SX_TARGET_CNT(VT_Tbl_26M[channel_id-1].rf_precision_default);
    }

	if(Xtalfreq == 40)
    {
        SET_CBR_RG_SX_RFCTRL_F(VT_Tbl_40M[channel_id-1].rf_ctrl_F);
		SET_RG_SX_RFCTRL_CH(VT_Tbl_40M[channel_id-1].rf_ctrl_N);	
    	SET_RG_SX_TARGET_CNT(VT_Tbl_40M[channel_id-1].rf_precision_default);
    }

    if(Xtalfreq == 24)
    {
        SET_CBR_RG_SX_RFCTRL_F(VT_Tbl_24M[channel_id-1].rf_ctrl_F);
		SET_RG_SX_RFCTRL_CH(VT_Tbl_24M[channel_id-1].rf_ctrl_N);	
        SET_RG_SX_TARGET_CNT(VT_Tbl_24M[channel_id-1].rf_precision_default);
    }


    LOGD("Xtalfreq %d\n", Xtalfreq);
	//calibration
        SET_RG_EN_SX_LCK_BIN(0);
	    SET_RG_EN_SX_LCK_BIN(1);

        for(phy_sx_lck_bin_cnt=0; phy_sx_lck_bin_cnt < SX_LCK_BIN_CNT_MAX; phy_sx_lck_bin_cnt++) {
            phy_sx_lck_bin_rdy = GET_LCK_BIN_RDY;
            if (phy_sx_lck_bin_rdy) break;
        }

        //recover registers after calibration
	SET_RG_EN_SX_LCK_BIN(0);
			
	if(phy_sx_lck_bin_rdy)
	{
                #ifdef __LOG_DRV_PHY_CHANNEL_CHANGE__
	//	LOG_PRINTF("Lock of ch %d!!!\n", channel_id);
                #endif
		LOGD("Lock channel %d\n", channel_id);
                return true;
	}
	else
	{
                #ifdef __LOG_DRV_PHY_CHANNEL_CHANGE__
	//	LOG_PRINTF("Lock failed!!!\n");
                #endif
		LOGD("Lock Failed\n");
		return false;
	}

}

bool drv_phy_data_rate_change(int data_rate_no)
{

  

  sdio_write_reg(0xce000004, 0x00000000 ) ;  // turn off phy
  sdio_write_reg(0xce000000, 0x80000016 ) ;  // turn on phy clk

  sdio_write_reg(rg_data_rate_select[data_rate_no][1], rg_data_rate_select[data_rate_no][2] ) ;  // select data-rate

  sdio_write_reg(0xce000004, 0x0000017F ) ;  // turn on phy

  if(sdio_read_reg(rg_data_rate_select[data_rate_no][2])==rg_data_rate_select[data_rate_no][3])

	return true;

  else

	return false;

}


void Start_TXFrame(s32 Txpacket, int Data_rate, int Power_index  )
{
	// power on calibration


//	 sdio_write_reg(0xce01008c,txiq_cal);



	 //tx phy script

	// sdio_write_reg(0xce01000C, 0x00000001 ) ; // rx pad sw
	 sdio_write_reg(0xce000020, 0x20000400 ) ; 
	 sdio_write_reg(0xce000004, 0x0000017F ) ; 
	 sdio_write_reg(0xce000020, 0x20000400 ) ; 
	 sdio_write_reg(0xce00007C, 0x01011003 ) ;
	 sdio_write_reg(0xce000094, 0x38012525 ) ;
	 sdio_write_reg(0xce000098, 0x01010101 ) ; 
	 sdio_write_reg(0xce0010B4, 0x00003001 ) ; 
	 sdio_write_reg(0xce0030A4, 0x00001901 ) ; 
	

	 // data_rate select
	 drv_phy_data_rate_change(Data_rate) ;


	 //TXpacket number

	 SET_RG_TX_CNT_TARGET (Txpacket) ;

	 SET_RG_TX_GAIN_OFFSET(Power_index);

	  // TXframe Enable

	  SET_RG_TX_START( 1 );
	 //  sdio_write_reg(0xce000018, 0x0055083d ) ;



}

void power_index(int power_idx)
{
	SET_RG_TX_GAIN_OFFSET(power_idx);
}

void Start_RXFrame()
{

	// load rf script
//	 u32 loop_i;
//	 for (loop_i = 0; loop_i <((sizeof(CabrioE_RF_20140326))/(sizeof(CabrioE_RF_20140326[0]))); loop_i++) {
//		sdio_write_reg(CabrioE_RF_20140326[loop_i][0], CabrioE_RF_20140326[loop_i][1] );
//	 }

		// phy script
	 	 u32 phy_i;
	 for (phy_i = 0; phy_i <((sizeof(CabrioE_PHY_20140326))/(sizeof(CabrioE_PHY_20140326[0]))); phy_i++) {
		sdio_write_reg(CabrioE_PHY_20140326[phy_i][0], CabrioE_PHY_20140326[phy_i][1] );
	 }

     sdio_write_reg(0xce01000C,(((0) << 0) | (sdio_read_reg(0xce01000C)) & 0xfffffffe));  // RX pad sw off





	 SET_RG_POST_CLK_EN(1) ; // post clk enable
   
	 sdio_write_reg(0xce000080, 0x0110000F ) ;   // RSSI offset

	 	//Sleep(10);
		SET_RG_PACKET_STAT_EN_11GN(0);
		SET_RG_PACKET_STAT_EN_11B(0);
	    SET_RG_PACKET_STAT_EN_11GN(1);
		SET_RG_PACKET_STAT_EN_11B(1);

}


void RX_Reset_Counters()
{
	    SET_RG_PACKET_STAT_EN_11GN(0);
		SET_RG_PACKET_STAT_EN_11B(0);
	    SET_RG_PACKET_STAT_EN_11GN(1);
		SET_RG_PACKET_STAT_EN_11B(1);


}

void RXFrame_Statics(int preamble, u32 *Static_Totcount, u32 *Static_Errcount, u32 *Static_RSSI )

{
	if (preamble == 1)
	{
	*Static_Totcount= GET_GN_PACKET_CNT;
	*Static_Errcount= GET_GN_PACKET_ERR_CNT;
	*Static_RSSI = GET_GN_RSSI;
	SET_RG_PACKET_STAT_EN_11GN(0);
	}
	else
	{ 
	*Static_Totcount = GET_B_PACKET_CNT;
	*Static_Errcount = GET_B_PACKET_ERR_CNT;
	*Static_RSSI = GET_B_RSSI;
	SET_RG_PACKET_STAT_EN_11B(0);
	}
}

bool Power_on_cal ( )
{

//	bool status;

	 u32 loop_i;
	 for (loop_i = 0; loop_i <((sizeof(FT_SPI_cmd_6032))/(sizeof(FT_SPI_cmd_6032[0]))); loop_i++) {
		sdio_write_reg(FT_SPI_cmd_6032[loop_i][0],FT_SPI_cmd_6032[loop_i][1] );
		
	 }
	 //Sleep(300);

 
	 
	 sdio_write_reg(0x00000244,0x00000009);     //calibration  channel 

//	sdio_write_reg(0x00000230,(((1) << 8)  | (sdio_read_reg(0x00000230)) &  0xfffff0ff));  // dpd low current setting 
//	sdio_write_reg(0x00000230,(((9) << 12) | (sdio_read_reg(0x00000230)) &  0xffff0fff));  // dpd channel setting 
	sdio_write_reg(0x00000220,(((1) << 1)  | (sdio_read_reg(0x00000220)) &  0xfffffffd));  // cal channel lock enable
	sdio_write_reg(0x00000220,(((1) << 5)  | (sdio_read_reg(0x00000220)) &  0xffffffdf));  // cal txdc enable
	sdio_write_reg(0x00000220,(((1) << 6)  | (sdio_read_reg(0x00000220)) &  0xffffffbf));  // cal txiq enable


//	 status =  (((sdio_read_reg(0x00000228))    & 0x00000020 ) >> 5) &  (((sdio_read_reg(0x00000228))    & 0x00000040 ) >> 6) ;


//	 if (status==0)
//	 {
//		 return false;
//	 }

//	 *txdc_cal = GET_RG_TX_DAC_IQOFFSET      ;
//	 *txiq_cal = GET_RG_TX_IQ_THETA_ALPHA    ;


	 return true;
}




bool Xtal_freq_offset( s8 freq_index )   
{

	SET_RG_XOSC_CBANK_XO(8+freq_index);

	if ( GET_RG_XOSC_CBANK_XO != 8+freq_index )
		return false ;

	return true;

}


void Stop_TXFrame()
{
	SET_RG_TX_START( 0 );
}


