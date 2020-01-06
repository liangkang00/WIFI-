//
// Created by Xuan on 2019/6/5.
//

#include "rf_tool.h"

/*
 * sdio_rw.cpp
 *
 *  Created on: 2014/12/16
 *      Author: ssv
 */

#include <fcntl.h> //file open
#include <unistd.h> //file close
#include <stdlib.h> //system
#include <stdio.h>
#include <memory.h>
#include "log.h"
#include "types.h"

#include <stddef.h>
#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include<unistd.h>

#define PHY_DIR  "/sys/class/ieee80211/"

static FILE *fd_w = NULL, *fd_r = NULL;

static char * rf_get_cmd_file(void){
	static char file_name[128] = {0};
	DIR* dp;
    struct dirent* ep;

	if(file_name[0] != 0){
	    //LOGD("No need get file again!return %s\n",file_name);
		return file_name;
	}

    char check_path[128];

     if(access("/proc/ssv/ssv_cmd",F_OK) == 0){

        strcpy(file_name,"/proc/ssv/ssv_cmd");
        LOGD("find file %s",file_name);
        return file_name;
     }
     LOGD("Open dir %s",PHY_DIR);
    dp = opendir(PHY_DIR);
    if (dp != NULL)
    {
        while (ep = readdir(dp)) {
            sprintf(check_path,"%s%s/device/driver",PHY_DIR,ep->d_name);
            //puts(ep->d_name);
            LOGD("check %s ???",check_path);
            if(access(check_path,F_OK) == 0){
                LOGD("check_path[%s] -->OK\n",check_path);
				sprintf(file_name,"/proc/ssv/%s/ssv_cmd",ep->d_name);
				 if(access(file_name,F_OK) == 0){

                    LOGD("find ssv cmd name:[%s]\n",file_name);
                                    (void) closedir(dp);
                                    return file_name;
                 }

                 sprintf(file_name,"/proc/tu_ssv/%s/ssv_cmd",ep->d_name);
                 if(access(file_name,F_OK) == 0){

                     LOGD("find ssv cmd name:[%s]\n",file_name);
                     (void) closedir(dp);
                     return file_name;
                  }
            }
        }

        (void) closedir(dp);
        LOGD("Couldn't open the directory.");

		return NULL;
    }
    else {
		LOGD("Couldn't open the directory.");
		return NULL;
	}

}

int checkWifiDriver(void){
    LOGD("checkWifiDriver ......");
    if(rf_get_cmd_file() == NULL){
        return -1;
    }else{
        return 0;
    }
}


void rf_tool_open()
{
    char *file_name = rf_get_cmd_file();
    if(file_name == NULL){
        return;
    }

	if (fd_r == NULL)
		fd_r = fopen(rf_get_cmd_file(), "r");
	if (fd_w == NULL)
		fd_w = fopen(rf_get_cmd_file(), "w");
}

void rf_tool_close()
{
	if (fd_w) {
		fclose(fd_w);
		fd_w = NULL;
	}
	if (fd_r) {
		fclose(fd_r);
		fd_r = NULL;
	}
}

int cli_read(char* out, int len)
{
	rf_tool_open();
	char cli_read[128] = {'\0'};
    int read_len = 0;

    fseek(fd_r, 0, SEEK_SET);

    read_len = fread(cli_read,1, sizeof(cli_read), fd_r);
    if(read_len > 0 && read_len <= len){
        memcpy(out,cli_read,read_len);
    }
	rf_tool_close();
	return read_len;
}


char cli_write(const char *cmd){
    rf_tool_open();
    char reg_write[128] = {'\0'};

    fseek(fd_w, 0, SEEK_SET);
    sprintf(reg_write, "%s\0", cmd);
    LOGD("write cmd: %s\n", reg_write);

    int len = fwrite(reg_write,1, strlen(reg_write)+1, fd_w);
    fflush(fd_w);

    rf_tool_close();
    return true;
}

char rf_write_cmd(const char *cmd)
{
	char reg_write[32] = {'\0'};
	sprintf(reg_write, "rf %s\0", cmd);

	return cli_write(reg_write);
}

char rf_reset(void){
	//# Reset MIB
	return rf_write_cmd("mib reset");
}

char rf_set_count(int val){

	if(val == 0 || val == 1){
		char cmd_buf[128] = {0};
		sprintf(cmd_buf,"count %d\0",val);
		return rf_write_cmd(cmd_buf);
	}
    return false;
}

char rf_set_rate(int index){
	//# Reset MIB
	char cmd_buf[128] = {0};
	sprintf(cmd_buf,"rate %d\0",index);
	return rf_write_cmd(cmd_buf);
}

void rf_set_channel(int channel,int bw40){
	char cmd_buf[128] = {0};
	if(bw40 == 1){
		sprintf(cmd_buf,"ch %d bw40\0",channel);
	}else{
		sprintf(cmd_buf,"ch %d\0",channel);
	}
	cli_write(cmd_buf);
}


//rf stop
void rf_stop(){
	rf_write_cmd("unblock");
}

//rf tx
void rf_start_TX(int channel,int bw40, int Data_rate){
	char cmd_buf[128] = {0};
	char buf[512] = {0};
	//# Set block mode
	rf_stop();
	cli_read(buf,512);
	rf_write_cmd("block");

	cli_read(buf,512);
	//# disable ack policy
	rf_write_cmd("ack disable");
	cli_read(buf,512);

	//set channle
	rf_set_channel(channel,bw40);
	cli_read(buf,512);

	//rf rate
	rf_set_rate(Data_rate);
	cli_read(buf,512);

}


void rf_start_RX(int channel){
	char cmd_buf[128] = {0};
	//# Set block mode
	rf_write_cmd("block");
	//# disable ack policy
	rf_write_cmd("ack disable");

	// # Set channel default bw20
	rf_set_channel(channel,0);

	//# Reset MIB
	rf_reset();
}


