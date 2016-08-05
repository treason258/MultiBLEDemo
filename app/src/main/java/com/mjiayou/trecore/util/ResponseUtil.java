package com.mjiayou.trecore.util;

import android.content.Context;
import android.os.Message;

import com.mjiayou.trecore.bean.TCResponse;
import com.mjiayou.trecore.widget.TCParams;

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
            if (response != null && response.getHeader() != null) {
                if (response.getHeader().getErrorCode().equals(TCParams.ERROR_CODE_SUCCESS)) {
                    isError = false;
                } else {
                    ToastUtil.show(context, response.getHeader().getErrorReason());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isError;
    }

    /**
     * 校验网络返回是否异常
     * <p/>
     * true：返回异常，当前页面需要retrun；
     * <br/>
     * false：返回正常，继续执行；
     */
    public static boolean checkResponseError(Context context, Message msg) {
        boolean isError = true;

        try {
            TCResponse response = (TCResponse) msg.obj;
            return checkResponseError(context, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isError;
    }
}
