// SSV6051_ATE.cpp : Defines the exported functions for the DLL application.
//

#include "SDIO-EXE.h"
#include "SSV6051_ATE.h"
#include "log.h"
const u32 CabrioE_RF_20140326[][2] = { // Table from Eden
    {0xCE010000,0x40002000},
    {0xCE010004,0x00024FC0},
    {0xCE010008,0x002DF69A},
    {0xCE01000C,0x151558C5},
    {0xCE010010,0x01011A88},
    {0xCE010014,0x3D3E84FE},
    {0xCE010018,0x01457D79},
    {0xCE01001C,0x000103A7},
    {0xCE010020,0x000103A6},
    {0xCE010024,0x00012001},
    {0xCE010028,0x00036000},
    {0xCE01002C,0x00030CA8},
    {0xCE010030,0x20EA0224},
    {0xCE010034,0x34800755},
    {0xCE010038,0x0003E07C},
    {0xCE01003C,0x5589F6DD},
    {0xCE010040,0x005508B9},
    {0xCE010044,0x07C08BFF},
    {0xCE010048,0xFCCCCF27},
    {0xCE01004C,0x07700830},
    {0xCE010050,0x00444000},
    {0xCE010054,0x00007FF4},
    {0xCE010058,0x0000000E},
    {0xCE01005C,0x00088018},
    {0xCE010060,0x00406000},
    {0xCE010064,0x08820820},
    {0xCE010068,0x00820820},
    {0xCE01006C,0x00820820},
    {0xCE010070,0x00820820},
    {0xCE010074,0x00820820},
    {0xCE010078,0x00820820},
    {0xCE01007C,0x00820820},
    {0xCE010080,0x00820820},
    {0xCE010084,0x00004080},
    {0xCE010088,0x200800FE},
    {0xCE01008C,0xAAAAAAAA},
    {0xCE010090,0xAAAAAAAA},
    {0xCE010094,0x0000A407},
    {0xCE010098,0x00000430},
    {0xCE01009C,0x2AA3F024},
    {0xCE0100A0,0x00EC4CC5},
    {0xCE0100A4,0x00000F13},
    {0xCE0100A8,0x00098900},
    {0xCE0100AC,0x00000000},
	{0xC0000304,0x00000001},  //pad sw
	{0xC0000308,0x00000001},  //pad sw
	{0xC000030C,0x00000001},  //pad sw
	{0xC0001D08,0x00000001},  //LDO to DCDC mode
};

#if 0
 void Open_sdio()
{

        //ssv6xxx_drv_module_init();
        // then, try to select 'sdio' first, if fail, just left 'sim' remaining


        //if (ssv6xxx_drv_select("sdio") == false)
        //{
			// then, try to select 'spi', if fail, just left 'sim' remaining
        //    printf("Try to connecting CABRIO via SPI...\n");
        //}

		//Sleep(100);

    u16 txdc_cal;
	u16 txiq_cal;

	Power_on_cal ();

	//Sleep(300);

	txdc_cal = GET_RG_TX_DAC_IQOFFSET      ;
	txiq_cal = GET_RG_TX_IQ_THETA_ALPHA    ;


	// load rf script
	 s32 loop_i;
	 for (loop_i = 0; loop_i <((sizeof(CabrioE_RF_20140326))/(sizeof(CabrioE_RF_20140326[0]))); loop_i++) {
		sdio_write_reg(CabrioE_RF_20140326[loop_i][0], CabrioE_RF_20140326[loop_i][1] );
    }

	 // load power cal value
	 SET_RG_TX_DAC_IQOFFSET(txdc_cal);
	 SET_RG_TX_IQ_THETA_ALPHAT(txiq_cal);
	 
}

void Close_sdio()
{

//ssv6xxx_drv_module_release();

}
#endif

#if 1
void rf_calibration()
{
	LOGD("rf_calibration do nothing\n");
}

#else
void rf_calibration()
{
    u16 txdc_cal;
	u16 txiq_cal;
	s32 loop_i;
	
	txdc_cal = GET_RG_TX_DAC_IQOFFSET      ;
	txiq_cal = GET_RG_TX_IQ_THETA_ALPHA    ;	
	
	// load rf script
	for (loop_i = 0; loop_i <((sizeof(CabrioE_RF_20140326))/(sizeof(CabrioE_RF_20140326[0]))); loop_i++) {
		sdio_write_reg(CabrioE_RF_20140326[loop_i][0], CabrioE_RF_20140326[loop_i][1] );
	}
	
	// load power cal value
	SET_RG_TX_DAC_IQOFFSET(txdc_cal);
	SET_RG_TX_IQ_THETA_ALPHAT(txiq_cal);	
	
	LOGD("rf_calibration end\n");
}
#endif

bool sdio_drv_phy_channel_change(int Xtalfreq, s32 channel_id)
{
	bool status;

	status = drv_phy_channel_change(Xtalfreq, channel_id);

	return status;
}

bool sdio_drv_phy_data_rate_change(int data_rate_id)
{

	bool status;
	status = drv_phy_data_rate_change(data_rate_id);

	return status;
}

void sdio_Start_TXFrame(s32 Txpacket, int Data_rate, int Power_index  )
{
	Start_TXFrame(Txpacket,  Data_rate, Power_index  );
}

void sdio_IQ_change(u32 IQ_phase, u32 IQ_amp)
{
	u32 iq = ((IQ_phase & 0x001f) | ((IQ_amp & 0x001f) << 8)) & 0x1f1f;

	LOGD("GET_RG_TX_IQ_THETA_ALPHA %08x, iq %08x\n", GET_RG_TX_IQ_THETA_ALPHA, iq);
	SET_RG_TX_IQ_THETA_ALPHAT(iq);
}

void sdio_Stop_TXFrame()
{
	Stop_TXFrame();
}

void sdio_Start_RXFrame()
{
	Start_RXFrame();
}

void sdio_RX_Reset_Counters()
{
	RX_Reset_Counters();
}

void sdio_RXFrame_Statics(int preamble, u32 *Static_Totcount, u32 *Static_Errcount, u32 *Static_RSSI )
{
	RXFrame_Statics(preamble, Static_Totcount, Static_Errcount, Static_RSSI );
}



 bool sdio_Xtal_freq_offset( s8 freq_index )
 {
	 bool status;
	status = Xtal_freq_offset( freq_index );
     return status;
 }

#if 0
 void sdio_efuse_read_all_map()
 {

	 efuse_read_all_map();


 }

 void sdio_write_efuse_item(enum efuse_data_item idx,u8 *value)
 {

	 write_efuse_item( idx, value);

 }

 bool sdio_write_to_efuse()
 {
	 bool status;
	 status = write_to_efuse();
	 return status;

 }

 int sdio_get_efuse_capacity_size()
 {
	 int size;
	 size = get_efuse_capacity_size();

	 return size;

 }

 int sdio_get_efuse_available_size()
 {
	  int size;
	 size = get_efuse_available_size();

	 return size;


 }
#endif
