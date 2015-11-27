#include <string.h>
#include <jni.h>
#include <stdlib.h>
#include <iostream>
#include <android/log.h>
#include "TTMessage.h"
#include "Util.h"

extern "C"{

void
Java_com_baidao_quotation_MessageProxy_initMapping(JNIEnv* env, jobject jobj){
    message2::Init();
}

jbyteArray
Java_com_baidao_quotation_MessageProxy_getRequestBuffer(JNIEnv* env, jobject jobj, jstring user, jstring pwd, jstring token){
    std::string block;
    message2 message(&block , true);

    message.set_msg_type(MESSAGE::LOGON);
    message.add_field_int32("RequestID", 1);
    message.add_field_int32("HeartBtInt", 5000);

    const char *userName = env->GetStringUTFChars(user, JNI_FALSE);
    message.add_field_string("Username", userName);
    env->ReleaseStringUTFChars(user, userName);

    const char* password = env->GetStringUTFChars(pwd, JNI_FALSE);
    message.add_field_string("Password", password);
    env->ReleaseStringUTFChars(pwd, password);

    const char* userToken = env->GetStringUTFChars(token, JNI_FALSE);
    message.add_field_string("DATA", userToken);
    env->ReleaseStringUTFChars(token, userToken);

    message.add_field_int32("CLIENT_TYPE", 2100000001);
    message.add_field_string("CLIENT_VERSION", "7.2.0");
    message.flush();

    int length = block.size();
    jbyteArray array = env->NewByteArray(length);
    env->SetByteArrayRegion(array, 0, length, (const jbyte*)block.data());
    return array;
}

jbyteArray
Java_com_baidao_quotation_MessageProxy_getCodeTable(JNIEnv* env, jobject jobj, jstring appVersion, int clientType){
    std::string block;
    message2 message(&block, true);

    message.set_msg_type(MESSAGE::SECURITYLIST);
    message.add_field_int32("RequestID", 1);
    message.add_field_string("SubjectName", "ALL");
    message.add_field_int32("CLIENT_TYPE", clientType);

    const char* version = env->GetStringUTFChars(appVersion, JNI_FALSE);
    message.add_field_string("CLIENT_VERSION", version);
    env->ReleaseStringUTFChars(appVersion, version);

    message.flush();

    int length = block.size();
    jbyteArray array = env->NewByteArray(length);
    env->SetByteArrayRegion(array, 0, length, (const jbyte*)block.data());
    return array;

}

jobject
Java_com_baidao_quotation_MessageProxy_parseCodeTableMessage(JNIEnv* env, jobject jobj, jbyteArray buffer, int length){
    char* data = (char*)env->GetByteArrayElements(buffer, 0);

    std::string block;
    message2 message;
    message.decode(data, length);//, MESSAGE::SECURITYLIST);

    jclass cls = env->FindClass("com/baidao/quotation/Category");
    jmethodID id = env->GetMethodID(cls, "<init>", "()V");
    jobject paramOut = env->NewObjectA(cls, id, 0);

    const char* value = NULL;
    int outLength = 0;
    double dValue;

    jfieldID market = env->GetFieldID(cls, "market", "Ljava/lang/String;");
    if(message.get_field_string("Market", value, outLength)){
        env->SetObjectField(paramOut, market, (jstring)env->NewStringUTF(value));
    }

    std::string strId = value;
    strId += ".";

    jfieldID preSettlementPx = env->GetFieldID(cls, "preSettlementPx", "D");
    if(message.get_field_double("PreSettlementPx", dValue)){
        env->SetDoubleField(paramOut, preSettlementPx, dValue);
    }

    jfieldID prevClosedPx = env->GetFieldID(cls, "prevClosedPx", "D");
    if(message.get_field_double("PrevClosePx", dValue)){
        env->SetDoubleField(paramOut, prevClosedPx, dValue);
    }

    jfieldID nickName = env->GetFieldID(cls, "nickName", "Ljava/lang/String;");
    if(message.get_field_string("SecurityID", value, outLength)){
        env->SetObjectField(paramOut, nickName, (jstring)env->NewStringUTF(value));
    }

    strId += value;

    jfieldID fieldId = env->GetFieldID(cls, "id", "Ljava/lang/String;");
    env->SetObjectField(paramOut, fieldId, (jstring)env->NewStringUTF(strId.c_str()));


    jfieldID name = env->GetFieldID(cls, "name", "Ljava/lang/String;");
    if(message.get_field_string("AbbrName", value, outLength)){
        env->SetObjectField(paramOut, name, (jstring)env->NewStringUTF(value));
    }

    jfieldID reservedString = env->GetFieldID(cls, "reserveString_1", "Ljava/lang/String;");
    if(message.get_field_string("ReserveString_1", value, outLength)){
        env->SetObjectField(paramOut, reservedString, (jstring)env->NewStringUTF(value));
    }

    jfieldID marketName = env->GetFieldID(cls, "marketName", "Ljava/lang/String;");
    if(message.get_field_string("MarketTradingTarget", value, outLength)){
        env->SetObjectField(paramOut, marketName, (jstring)env->NewStringUTF(value));
    }

    jfieldID bondCategory = env->GetFieldID(cls, "bondCategory", "Ljava/lang/String;");
    if(message.get_field_string("BondCategory", value, outLength)){
        env->SetObjectField(paramOut, bondCategory, (jstring)env->NewStringUTF(value));
    }

    jfieldID bidQuoteSide = env->GetFieldID(cls, "bidQuoteSide", "Ljava/lang/String;");
    if(message.get_field_string("BidQuoteSide", value, outLength)){
        env->SetObjectField(paramOut, bidQuoteSide, (jstring)env->NewStringUTF(value));
    }

    jfieldID offerQuoteSide = env->GetFieldID(cls, "offerQuoteSide", "Ljava/lang/String;");
    if(message.get_field_string("OfferQuoteSide", value, outLength)){
        env->SetObjectField(paramOut, offerQuoteSide, (jstring)env->NewStringUTF(value));
    }

    jfieldID offSet = env->GetFieldID(cls, "offSet", "I");
    if(message.get_field_int32("OffSet", outLength)){
        env->SetIntField(paramOut, offSet, outLength);
    }

    jfieldID decimalDigits = env->GetFieldID(cls, "decimalDigits", "I");
    if(message.get_field_int32("MinuteSpan", outLength)){
        env->SetIntField(paramOut, decimalDigits, outLength);
    }

    env->ReleaseByteArrayElements(buffer, (jbyte*)data, 0);
    return paramOut;
}

jbyteArray
Java_com_baidao_quotation_MessageProxy_getUnsubscription(JNIEnv* env, jobject jobj, jstring tableId){
    std::string block;
    message2 message(&block, true);

    message.set_msg_type(MESSAGE::UNSUBSCRIBE);
    message.add_field_int32("RequestID", 1);

    const char* subject = env->GetStringUTFChars(tableId, JNI_FALSE);
    message.add_field_string("SubjectName", subject);
    env->ReleaseStringUTFChars(tableId, subject);

    message.flush();

    int length = block.size();
    jbyteArray array = env->NewByteArray(length);
    env->SetByteArrayRegion(array, 0, length, (const jbyte*)block.data());
    return array;
}

jbyteArray
Java_com_baidao_quotation_MessageProxy_getSubscription(JNIEnv* env, jobject jobj, jstring tableId){
    std::string block;
    message2 message(&block, true);

    message.set_msg_type(MESSAGE::SUBSCRIBE);
    message.add_field_int32("RequestID", 1);

    const char* subject = env->GetStringUTFChars(tableId, JNI_FALSE);
    message.add_field_string("SubjectName", subject);
    env->ReleaseStringUTFChars(tableId, subject);

    message.add_field_int32("Snapshot", 1);

    message.flush();

    int length = block.size();
    jbyteArray array = env->NewByteArray(length);
    env->SetByteArrayRegion(array, 0, length, (const jbyte*)block.data());
    return array;

}

jobject
Java_com_baidao_quotation_MessageProxy_parseSnapshot(JNIEnv* env, jobject jobj, jbyteArray buffer, int length){
    char* data = (char*)env->GetByteArrayElements(buffer, 0);

    message2 message;
    message.decode(data, length);//, MESSAGE::SUBSCRIBE);

    const char* value = NULL;
    int outLength = 0;
    double dValue = 0;
    long lValue = 0;
    bool isSGE = false;

    if(message.get_field_string("Market", value, outLength)){
        isSGE = (strcmp(value, "SGE") == 0);
    }

    jclass cls = env->FindClass(isSGE ? "com/baidao/quotation/SHGSnapshot" : "com/baidao/quotation/Snapshot");
    jmethodID id = env->GetMethodID(cls, "<init>", "()V");
    jobject paramOut = env->NewObjectA(cls, id, 0);

    jfieldID market = env->GetFieldID(cls, "market", "Ljava/lang/String;");
    env->SetObjectField(paramOut, market, (jstring)env->NewStringUTF(value));

    jfieldID securityId = env->GetFieldID(cls, "securityId", "Ljava/lang/String;");
    if(message.get_field_string("SecurityID", value, outLength)){
        env->SetObjectField(paramOut, securityId, (jstring)env->NewStringUTF(value));
    }

    jfieldID dateTime = env->GetFieldID(cls, "dateTime", "J");
    if(message.get_field_int64("DateTime", lValue)){
        env->SetLongField(paramOut, dateTime, lValue);
    }

    jfieldID lastPx = env->GetFieldID(cls, "latestPx", "D");
    if(message.get_field_double("LatestPx", dValue)){
        env->SetDoubleField(paramOut, lastPx, dValue);
    }

    jfieldID openPx = env->GetFieldID(cls, "openPx", "D");
    if(message.get_field_double("OpenPx", dValue)){
        env->SetDoubleField(paramOut, openPx, dValue);
    }

    jfieldID highPx = env->GetFieldID(cls, "highPx", "D");
    if(message.get_field_double("HighPx", dValue)){
        env->SetDoubleField(paramOut, highPx, dValue);
    }

    jfieldID lowPx = env->GetFieldID(cls, "lowPx", "D");
    if(message.get_field_double("LowPx", dValue)){
        env->SetDoubleField(paramOut, lowPx, dValue);
    }

    jfieldID preOpenPosition = env->GetFieldID(cls, "preOpenPosition", "D");
    if(message.get_field_double("PreOpenPosition", dValue)){
        env->SetDoubleField(paramOut, preOpenPosition, dValue);
    }

    jfieldID bidPx1 = env->GetFieldID(cls, "bidPx1", "D");
    if(message.get_field_double("BidPx1", dValue)){
        env->SetDoubleField(paramOut, bidPx1, dValue);
    }

    jfieldID askPx1 = env->GetFieldID(cls, "askPx1", "D");
    if(message.get_field_double("AskPx1", dValue)){
        env->SetDoubleField(paramOut, askPx1, dValue);
    }

    jfieldID avgPx = env->GetFieldID(cls, "avgPx", "D");
    if(message.get_field_double("AvgPx", dValue)){
        env->SetDoubleField(paramOut, avgPx, dValue);
    }

    if(isSGE){
        jfieldID prevClosePx = env->GetFieldID(cls, "prevClosePx", "D");
        if(message.get_field_double("PrevClosePx", dValue)){
            env->SetDoubleField(paramOut, prevClosePx, dValue);
        }

        jfieldID tradeVolume = env->GetFieldID(cls, "tradeVolume", "D");
        if(message.get_field_double("TradeVolume", dValue)){
            env->SetDoubleField(paramOut, tradeVolume, dValue);
        }

        jfieldID totalTradeVolume = env->GetFieldID(cls, "totalTradeVolume", "D");
        if(message.get_field_double("TotalTradeVolume", dValue)){
            env->SetDoubleField(paramOut, totalTradeVolume, dValue);
        }

        jfieldID bidVolume1 = env->GetFieldID(cls, "bidVolume1", "D");
        if(message.get_field_double("BidVolume1", dValue)){
            env->SetDoubleField(paramOut, bidVolume1, dValue);
        }

        jfieldID askVolume1 = env->GetFieldID(cls, "askVolume1", "D");
        if(message.get_field_double("AskVolume1", dValue)){
            env->SetDoubleField(paramOut, askVolume1, dValue);
        }

        jfieldID bidPx2 = env->GetFieldID(cls, "bidPx2", "D");
        if(message.get_field_double("BidPx2", dValue)){
            env->SetDoubleField(paramOut, bidPx2, dValue);
        }

        jfieldID bidVolume2 = env->GetFieldID(cls, "bidVolume2", "D");
        if(message.get_field_double("BidVolume2", dValue)){
            env->SetDoubleField(paramOut, bidVolume2, dValue);
        }

        jfieldID askPx2 = env->GetFieldID(cls, "askPx2", "D");
        if(message.get_field_double("AskPx2", dValue)){
            env->SetDoubleField(paramOut, askPx2, dValue);
        }

        jfieldID askVolume2 = env->GetFieldID(cls, "askVolume2", "D");
        if(message.get_field_double("AskVolume2", dValue)){
            env->SetDoubleField(paramOut, askVolume2, dValue);
        }

        jfieldID bidPx3 = env->GetFieldID(cls, "bidPx3", "D");
        if(message.get_field_double("BidPx3", dValue)){
            env->SetDoubleField(paramOut, bidPx3, dValue);
        }

        jfieldID bidVolume3 = env->GetFieldID(cls, "bidVolume3", "D");
        if(message.get_field_double("BidVolume3", dValue)){
            env->SetDoubleField(paramOut, bidVolume3, dValue);
        }

        jfieldID askPx3 = env->GetFieldID(cls, "askPx3", "D");
        if(message.get_field_double("AskPx3", dValue)){
            env->SetDoubleField(paramOut, askPx3, dValue);
        }

        jfieldID askVolume3 = env->GetFieldID(cls, "askVolume3", "D");
        if(message.get_field_double("AskVolume3", dValue)){
            env->SetDoubleField(paramOut, askVolume3, dValue);
        }

        jfieldID bidPx4 = env->GetFieldID(cls, "bidPx4", "D");
        if(message.get_field_double("BidPx4", dValue)){
            env->SetDoubleField(paramOut, bidPx4, dValue);
        }

        jfieldID bidVolume4 = env->GetFieldID(cls, "bidVolume4", "D");
        if(message.get_field_double("BidVolume4", dValue)){
            env->SetDoubleField(paramOut, bidVolume4, dValue);
        }

        jfieldID askPx4 = env->GetFieldID(cls, "askPx4", "D");
        if(message.get_field_double("AskPx4", dValue)){
            env->SetDoubleField(paramOut, askPx4, dValue);
        }

        jfieldID askVolume4 = env->GetFieldID(cls, "askVolume4", "D");
        if(message.get_field_double("AskVolume4", dValue)){
            env->SetDoubleField(paramOut, askVolume4, dValue);
        }

        jfieldID bidPx5 = env->GetFieldID(cls, "bidPx5", "D");
        if(message.get_field_double("BidPx5", dValue)){
            env->SetDoubleField(paramOut, bidPx5, dValue);
        }

        jfieldID bidVolume5 = env->GetFieldID(cls, "bidVolume5", "D");
        if(message.get_field_double("BidVolume5", dValue)){
            env->SetDoubleField(paramOut, bidVolume5, dValue);
        }

        jfieldID askPx5 = env->GetFieldID(cls, "askPx5", "D");
        if(message.get_field_double("AskPx5", dValue)){
            env->SetDoubleField(paramOut, askPx5, dValue);
        }

        jfieldID askVolume5 = env->GetFieldID(cls, "askVolume5", "D");
        if(message.get_field_double("AskVolume5", dValue)){
            env->SetDoubleField(paramOut, askVolume5, dValue);
        }
    }

    env->ReleaseByteArrayElements(buffer, (jbyte*)data, 0);

    return paramOut;
}

jbyteArray
Java_com_baidao_quotation_MessageProxy_getHeartbeatBuffer(JNIEnv* env, jobject jobj){
    std::string block;
    message2 message(&block, true);
    message.set_msg_type(MESSAGE::HEARTBEAT) ;
    message.add_field_int64("TestReqID", time(NULL)) ;
    message.flush();

    int length = block.size();
    jbyteArray array = env->NewByteArray(length);
    env->SetByteArrayRegion(array, 0, length, (const jbyte*)block.data());
    return array;
}

jboolean
Java_com_baidao_quotation_MessageProxy_isHeartbeat(JNIEnv* env, jobject jobj, jbyteArray buffer, int length){
    char* data = (char*)env->GetByteArrayElements(buffer, 0);

    message2 message;
    message.decode(data, length);
    env->ReleaseByteArrayElements(buffer, (jbyte*)data, 0);
    return message.get_msg_type() == MESSAGE::HEARTBEAT;
}

jboolean
Java_com_baidao_quotation_MessageProxy_isQiankun(JNIEnv* env, jobject jobj, jbyteArray buffer, int length){
    char* data = (char*)env->GetByteArrayElements(buffer, 0);

    message2 message;
    message.decode(data, length);
    env->ReleaseByteArrayElements(buffer, (jbyte*)data, 0);
    return message.get_msg_type() == 151;
}

jobject
Java_com_baidao_quotation_MessageProxy_parseQiankun(JNIEnv* env, jobject jobj, jbyteArray buffer, int length){
    char* data = (char*)env->GetByteArrayElements(buffer, 0);

    message2 message;
    message.decode(data, length);

    const char* value = NULL;
    int outLength = 0;
    double dValue = 0;
    long lValue = 0;
    bool isSGE = false;

    jclass cls = env->FindClass("com/baidao/quotation/Qiankun");
    jmethodID id = env->GetMethodID(cls, "<init>", "()V");
    jobject paramOut = env->NewObjectA(cls, id, 0);

    jfieldID size = env->GetFieldID(cls, "size", "I");
    if(message.get_field_int32("Size", outLength)){
        env->SetIntField(paramOut, size, outLength);
    }

    __android_log_print(4, "tag", "~~~~427");

    jfieldID field = env->GetFieldID(cls, "field", "I");
    if(message.get_field_int32("I32Field", outLength)){
        env->SetIntField(paramOut, field, outLength);
    }
    __android_log_print(4, "tag", "~~~~433");

    jfieldID dateTime = env->GetFieldID(cls, "dateTime", "J");
    if(message.get_field_int64("DateTime", lValue)){
        env->SetLongField(paramOut, dateTime, lValue);
    }
    __android_log_print(4, "tag", "~~~~439");

    jfieldID beginDateTime = env->GetFieldID(cls, "beginDateTime", "J");
    if(message.get_field_int64("BeginDateTime", lValue)){
        env->SetLongField(paramOut, beginDateTime, lValue);
    }
    __android_log_print(4, "tag", "~~~~445");

    jfieldID updateTime = env->GetFieldID(cls, "updateTime", "J");
    if(message.get_field_int64("UpdateTime", lValue)){
        env->SetLongField(paramOut, updateTime, lValue);
    }
    __android_log_print(4, "tag", "~~~~451");

    jfieldID sid = env->GetFieldID(cls, "sid", "Ljava/lang/String;");
    env->SetObjectField(paramOut, sid, (jstring)env->NewStringUTF(message.get_subject().c_str()));

    env->ReleaseByteArrayElements(buffer, (jbyte*)data, 0);

    return paramOut;
    }

    jboolean
    Java_com_baidao_quotation_MessageProxy_isCategoryNotice(JNIEnv* env, jobject jobj, jbyteArray buffer, int length){
        char* data = (char*)env->GetByteArrayElements(buffer, 0);

        message2 message;
        message.decode(data, length);
        env->ReleaseByteArrayElements(buffer, (jbyte*)data, 0);
        return message.get_subject() == "SYS.NOTICE.SECURITYLIST" || message.get_subject() == "SYS.NOTICE.MOBILE.SECURITYLIST";
    }

    jobject
    Java_com_baidao_quotation_MessageProxy_parseCategoryNotice(JNIEnv* env, jobject jobj, jbyteArray buffer, int length){
        char* data = (char*)env->GetByteArrayElements(buffer, 0);

        message2 message;
        message.decode(data, length);

        const char* value = NULL;
        int outLength = 0;
        double dValue = 0;

        jclass cls = env->FindClass("com/baidao/quotation/CategoryNotice");
        jmethodID id = env->GetMethodID(cls, "<init>", "()V");
        jobject result = env->NewObjectA(cls, id, 0);

        jfieldID market = env->GetFieldID(cls, "market", "Ljava/lang/String;");
        if(message.get_field_string("Market", value, outLength)){
            env->SetObjectField(result, market, (jstring)env->NewStringUTF(value));
        }

        jfieldID securityId = env->GetFieldID(cls, "securityId", "Ljava/lang/String;");
        if(message.get_field_string("SecurityID", value, outLength)){
            env->SetObjectField(result, securityId, (jstring)env->NewStringUTF(value));
        }

        jfieldID preSettlementPx = env->GetFieldID(cls, "preSettlementPx", "D");
        if(message.get_field_double("PreSettlementPx", dValue)){
            env->SetDoubleField(result, preSettlementPx, dValue);
        }

        jfieldID prevClosedPx = env->GetFieldID(cls, "prevClosedPx", "D");
        if(message.get_field_double("PrevClosePx", dValue)){
            env->SetDoubleField(result, prevClosedPx, dValue);
        }

        env->ReleaseByteArrayElements(buffer, (jbyte*)data, 0);

        return result;
    }
}
