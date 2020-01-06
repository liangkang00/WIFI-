LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -lm -llog
LOCAL_LDFLAGS := -llog
LOCAL_MODULE    := libssvWifiTool
LOCAL_SRC_FILES := \
	ssvWifiTool.cpp \
	sdio_rw.cpp \
	SSV6051_ATE.cpp \
	SDIO-EXE.cpp

include $(BUILD_SHARED_LIBRARY)
