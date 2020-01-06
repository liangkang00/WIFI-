/*
 * sdio_rw.cpp
 *
 *  Created on: 2014/12/16
 *      Author: ssv
 */

#include <fcntl.h> //file open
#include <unistd.h> //file close
#include <stdlib.h> //system
#include <memory.h> //system
#include <stdio.h>
#include "log.h"
#include "types.h"

#define SSV_CMD		"/sys/kernel/debug/ssv/ssv_cmd"

FILE *fd_w = NULL, *fd_r = NULL;

void sdio_reg_open()
{
	if (fd_r == NULL)
		fd_r = fopen(SSV_CMD, "r");
	if (fd_w == NULL)
		fd_w = fopen(SSV_CMD, "w");
}

void sdio_reg_close()
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

u32	sdio_read_reg(u32 addr)
{
	sdio_reg_open();
	char reg_read[32] = {'\0'};
	char buf[9];
	u32 reg_value;
	memset(buf, 0, sizeof(buf));
	fseek(fd_w, 0, SEEK_SET);
	fseek(fd_r, 0, SEEK_SET);

	sprintf(reg_read, "tool r %08x\0", addr);
	fwrite(reg_read, sizeof(reg_read), 1, fd_w);
	fflush(fd_w);

	fread(buf, 8, 1, fd_r);

	reg_value = (u32)strtol((const char *)buf, (char **)NULL, 16);
	LOGD("  *read reg: %08x, value: %08x\n", addr, reg_value);
	sdio_reg_close();
	return reg_value;
}

bool sdio_write_reg(u32 addr,  u32 data)
{
	sdio_reg_open();
	char reg_write[32] = {'\0'};
	fseek(fd_w, 0, SEEK_SET);

	sprintf(reg_write, "tool w %08x %08x\0", addr, data);
	//LOGD("reg_write: %s\n", reg_write);
	LOGD("write reg: %08x, value: %08x\n", addr, data);

	fwrite(reg_write, sizeof(reg_write), 1, fd_w);
	sdio_reg_close();
	return true;
}
