//
// Created by treason on 16/8/7.
//

#include "com_mjiayou_trecore_test_TestTreJNIUtil.h"

/*
 * Class:     com_mjiayou_trecore_test_TestTreJNIUtil
 * Method:    getHello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_mjiayou_trecore_test_TestTreJNIUtil_getHello
        (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "Hello, I am from TreJNI. And who are you?");
}