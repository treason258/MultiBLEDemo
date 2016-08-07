//
// Created by treason on 16/8/7.
//

#include "com_mjiayou_trecore_test_jni_TestTreJNIUtil.h"

/*
 * Class:     com_mjiayou_trecore_test_jni_TestTreJNIUtil
 * Method:    getHello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_mjiayou_trecore_test_jni_TestTreJNIUtil_getHello
        (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env,
                                "Hello, I am from TreJNI. And who are you? Whatever, fuck you!");
}