package com.mjiayou.trecore.util;

import android.content.Context;
import android.os.Message;

import com.mjiayou.trecore.bean.TCResponse;
import com.mjiayou.trecore.helper.Params;

/**
 * Created by treason on 15-11-27.
 */
public class ResponseUtil {

    /**
     * 校验网络返回是否异常
     * <p/>
     * true：返回异常，当前页面需要retrun；
     * <br/>
     * false：返回正常，继续执行；
     */
    public static boolean checkResponseError(Context context, TCResponse response) {
        boolean isError = true;

        try {
            if (response != null && response.getRet().equals(Params.STATUS_CODE_SUCCESS)) {
                isError = false;
            } else {
                if (response != null && response.getMsg() != null) {
                    ToastUtil.show(context, response.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isError;
    }

    public static boolean checkResponseError(Context context, Message msg) {
        boolean isError = true;

        try {
            TCResponse response = (TCResponse) msg.obj;
            isError = checkResponseError(context, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isError;
    }

//    /**
//     * 校验网络返回是否异常
//     * <p/>
//     * true：返回异常，当前页面需要retrun；
//     * <br/>
//     * false：返回正常，继续执行；
//     */
//    public static boolean checkResponseBundleError(Context context, TCResponseBundle responseBundle) {
//        boolean isError = true;
//
//        try {
//            if (responseBundle != null && responseBundle.getHeader() != null) {
//                if (responseBundle.getHeader().getRet().equals(Params.STATUS_CODE_SUCCESS)) {
//                    isError = false;
//                } else {
//                    ToastUtil.show(context, responseBundle.getHeader().getMsg());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return isError;
//    }
//
//    public static boolean checkResponseBundleError(Context context, Message msg) {
//        boolean isError = true;
//
//        try {
//            TCResponseBundle response = (TCResponseBundle) msg.obj;
//            isError = checkResponseBundleError(context, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return isError;
//    }
}
