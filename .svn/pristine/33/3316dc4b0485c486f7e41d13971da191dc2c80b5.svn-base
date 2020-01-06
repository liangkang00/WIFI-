#include"types.h"

void  Open_sdio();

void  Close_sdio();

void rf_calibration();

bool sdio_drv_phy_channel_change(int Xtalfreq, s32 channel_id);
//Xtalfreq options = 24, 26, 40.  ,    default value is 26
//channel_id = 1~14

bool sdio_drv_phy_data_rate_change(int data_rate_id);
/*
0     1M             15    MCS0    
1     2M             16    MCS1
2     5.5M           17    MCS2 
3     11M            18    MCS3 
4     2M SP          19    MCS4 
5     5.5M SP        20    MCS5 
6     11M SP         21    MCS6 
7     6M             22    MCS7 
8     9M             23    SGI-MCS0  
9     12M            24    SGI-MCS1 
10    18M            25    SGI-MCS2  
11    24M            26    SGI-MCS3  
12    36M            27    SGI-MCS4  
13    48M            28    SGI-MCS5 
14    54M            29    SGI-MCS6 
				 30    SGI-MCS7
*/

void sdio_Start_TXFrame(s32 Txpacket, int Data_rate, int Power_index  );
// Txpacket = 0~ 0xffffffff
// data_rate = 0~30
// power_index = (0 -> no offset, 1 -> -0. 5 dB, 2 -> - 1 dB, ... , 7 -> -3.5 dB
//               15 -> +0.5 dB, 14 -> +1 dB, ... , 9 -> +3.5 dB, 8 -> +4 dB

void sdio_IQ_change(u32 IQ_phase, u32 IQ_Amp);

void sdio_Stop_TXFrame();

void sdio_Start_RXFrame();

void sdio_RX_Reset_Counters();

void sdio_RXFrame_Statics(int preamble, u32 *Static_Totcount, u32 *Static_Errcount, u32 *Static_RSSI );
// preamble 11b=0, 11gn=1

bool sdio_Xtal_freq_offset( s8 freq_index );
// freq_index range = -8 ~ +7

void sdio_efuse_read_all_map();

//void sdio_write_efuse_item(enum efuse_data_item idx,u8 *value);
/* efuse_data_item
EFUSE_R_CALIBRAION_RESULT = 1,
EFUSE_SAR_RESULT,
EFUSE_MAC,
EFUSE_CRYSTAL_FREQUECY_OFFSET,
EFUSE_DC_CALIBRAION_RESULT,
EFUSE_IQ_CALIBRAION_RESULT,
EFUSE_TX_POWER_INDEX_1,
EFUSE_TX_POWER_INDEX_2
*/

bool sdio_write_to_efuse();
// if true, write efuse success, else efuse fail.

int sdio_get_efuse_capacity_size();

int sdio_get_efuse_available_size();

/* efuse example
	 efuse_read_all_map();
	 u8 value = 0xa4;
	 write_efuse (EFUSE_SAR_RESULT , &value);
	  value = 0x78;
	 write_efuse (EFUSE_DC_CALIBRAION_RESULT , &value);

	 write_to_efuse();
*/
