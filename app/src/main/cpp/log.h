/*
 * log.h
 *
 *  Created on: 2014/12/16
 *      Author: ssv
 */

#ifndef LOG_H_
#define LOG_H_

#include <android/log.h>


#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG , "SSV", __VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, "SSV",__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "SSV",__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, "SSV",__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "SSV",__VA_ARGS__)


#endif /* LOG_H_ */
