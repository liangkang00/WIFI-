//
// Created by Xuan on 2019/6/5.
//

#ifndef ICOMMWIFITOOL_RF_TOOL_H
#define ICOMMWIFITOOL_RF_TOOL_H
char rf_write_cmd(const char *cmd);
void rf_start_RX(int channel);
void rf_start_TX(int channel,int bw40, int Data_rate);
void rf_stop(void);
void rf_set_channel(int channel,int bw40);
char rf_set_rate(int index);
char rf_set_count(int val);
char rf_reset(void);
char cli_write(const char *cmd);
int cli_read(char* out, int len);
int checkWifiDriver(void);
#endif //ICOMMWIFITOOL_RF_TOOL_H
