LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := QuotationProtocol
LOCAL_SRC_FILES := main.cpp \
                    TTMessage.cpp \
                    Util.cpp
LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)