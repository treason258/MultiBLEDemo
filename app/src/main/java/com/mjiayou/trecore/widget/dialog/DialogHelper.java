package com.mjiayou.trecore.widget.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.mjiayou.trecore.bean.entity.TCMenu;
import com.mjiayou.trecore.ui.TCActivity;
import com.mjiayou.trecore.util.MenuUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.util.UserUtil;
import com.mjiayou.trecore.widget.TCConfigs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by treason on 16/5/26.
 */
public class DialogHelper {

    private static final String TAG = "DialogHelper";

    // ******************************** AlertDialog ********************************

    /**
     * 创建 AlertDialog
     */
    public static AlertDialog.Builder createAlertBuilder(Context context, String title, String message,
                                                         String posiText, DialogInterface.OnClickListener onPosiClickListener,
                                                         String negaText, DialogInterface.OnClickListener onNegaClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(posiText)) {
            builder.setPositiveButton(posiText, onPosiClickListener);
        }
        if (!TextUtils.isEmpty(negaText)) {
            builder.setNegativeButton(negaText, onNegaClickListener);
        }
        return builder;
    }

    // ******************************** AlertDialog - Item ********************************

    /**
     * 创建 Item 样式的 AlertDialog
     */
    public static AlertDialog.Builder createAlertMenuBuilder(Context context, String title,
                                                             String[] items, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (null != items) {
            builder.setItems(items, onClickListener);
        }
        return builder;
    }

    // ******************************** ProgressDialog ********************************

    /**
     * createProgressDialog
     */
    public static ProgressDialog createProgressDialog(Context context, String title, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        }
        return dialog;
    }

    // ******************************** TCAlertDialog ********************************

    /**
     * 创建 TCAlertDialog
     */
    public static TCAlertDialog createTCAlertDialog(Context context, CharSequence title, CharSequence message,
                                                    CharSequence okStr, CharSequence cancelStr,
                                                    boolean cancelable, final TCAlertDialog.OnTCActionListener onTCActionListener) {
        final TCAlertDialog dialog = new TCAlertDialog(context);

        // title
        dialog.setTitle(title);
        // message
        dialog.setMessage(message);
        // menu
        dialog.setOkMenu(okStr);
        dialog.setCancelMenu(cancelStr);
        dialog.setTCActionListener(onTCActionListener);
        // setCancelable
        dialog.setCancelable(cancelable);

        return dialog;
    }

    // ******************************** showLoading ********************************

    public static void showLoading(Context context, boolean show) {
//        switch (TCConfigs.LOADING_STYLE) {
//            default:
//            case TCParams.LOADING_STYLE_DEFAULT_PROGRESS: {
//                if (show) {
//                    DefaultProgressDialog.createDialog(context).show();
//                } else {
//                    DefaultProgressDialog.dismissDialog();
//                }
//                break;
//            }
//            case TCParams.LOADING_STYLE_TC_PROGRESS: {
//                if (show) {
//                    TCProgressDialog.createDialog(context).show();
//                } else {
//                    TCProgressDialog.dismissDialog();
//                }
//                break;
//            }
//            case TCParams.LOADING_STYLE_TC_LOADING: {
//                if (show) {
//                    TCLoadingDialog.createDialog(context).show();
//                } else {
//                    TCLoadingDialog.dismissDialog();
//                }
//                break;
//            }
//        }
    }

    public static void updateLoading(String message) {
//        switch (TCConfigs.LOADING_STYLE) {
//            default:
//            case TCParams.LOADING_STYLE_DEFAULT_PROGRESS: {
//                DialogHelper.updateLoading(message);
//                break;
//            }
//            case TCParams.LOADING_STYLE_TC_PROGRESS: {
//                TCProgressDialog.updateDialog(message);
//                break;
//            }
//            case TCParams.LOADING_STYLE_TC_LOADING: {
//                TCLoadingDialog.updateDialog(message);
//                break;
//            }
//        }
    }

    // ******************************** TCAlertMenuDialog ********************************

    /**
     * 创建 TCAlertMenuDialog
     */
    public static TCAlertMenuDialog createTCAlertMenuDialog(Context context, String title, String message,
                                                            List<TCMenu> tcMenus) {
        final TCAlertMenuDialog dialog = new TCAlertMenuDialog(context);

        // title
        dialog.setTitle(title);
        // message
        dialog.setMessage(message);
        // menu
        dialog.setMenu(tcMenus);

        return dialog;
    }

    // ******************************** TCBottomMenuDialog ********************************

    /**
     * 创建 TCBottomMenuDialog
     */
    public static TCBottomMenuDialog createTCBottomMenuDialog(final Context context, TCBottomMenuDialog.LayoutType layoutType,
                                                              String title, String message,
                                                              List<TCMenu> tcMenus) {
        final TCBottomMenuDialog dialog = new TCBottomMenuDialog(context, layoutType);

        // title
        dialog.setTitle(title);
        // message
        dialog.setMessage(message);
        // menu
        dialog.setMenu(tcMenus);

        return dialog;
    }

    public static TCBottomMenuDialog createTCBottomMenuDialog(final Context context, String title, String message,
                                                              List<TCMenu> tcMenus) {
        return createTCBottomMenuDialog(context, TCBottomMenuDialog.LayoutType.DEFAULT, title, message, tcMenus);
    }

    // ******************************** TCBirthdayDialog ********************************

    public static TCBirthdayDialog createTCBirthdayDialog(final Context context,
                                                          TCBirthdayDialog.OnTimeSelectedListener onTimeSelectedListener,
                                                          View.OnClickListener onSubmitClickListener) {
        final TCBirthdayDialog dialog = new TCBirthdayDialog(context);

        // listener
        dialog.setOnTimeSelectListener(onTimeSelectedListener);
        dialog.setOnSubmitClickListener(onSubmitClickListener);

        return dialog;
    }


    // ******************************** showTCAlertDialogDemo ********************************

    public static final long TIME_PROGRESS = 3 * 1000;
    public static final long TIME_PROGRESS_INTERVAL = 1000;

    public static String getOnTickMessage(long millisUntilFinished) {
        return "请稍后... | onTick -> " + millisUntilFinished;
    }

    /**
     * showTCAlertDialogDemo
     */
    public static void showDialogDemo(final Context context) {

        List<TCMenu> tcMenus = new ArrayList<>();
        tcMenus.add(new TCMenu("0 - createAlertBuilder", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createAlertBuilder
                DialogHelper.createAlertBuilder(context,
                        "title", "message",
                        "ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtil.show(context, "ok - onClick");
                            }
                        },
                        "cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtil.show(context, "cancel - onClick");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .show();
            }
        }));
        tcMenus.add(new TCMenu("1 - createAlertMenuBuilder", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createAlertMenuBuilder
                DialogHelper.createAlertMenuBuilder(context, "title",
                        new String[]{"0-菜单0", "1-菜单1", "2-菜单2"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        ToastUtil.show(context, "菜单0 - onClick");
                                        break;
                                    case 1:
                                        ToastUtil.show(context, "菜单1 - onClick");
                                        break;
                                    case 2:
                                        ToastUtil.show(context, "菜单2 - onClick");
                                        break;
                                }
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setCancelable(false)
                        .show();
            }
        }));
        tcMenus.add(new TCMenu("2 - createProgressDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createProgressDialog
                final ProgressDialog progressDialog = DialogHelper.createProgressDialog(context, "title", "message");
                progressDialog.setTitle("title");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // CountDownTimer
                new CountDownTimer(TIME_PROGRESS, TIME_PROGRESS_INTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        progressDialog.setMessage(getOnTickMessage(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                    }
                }.start();
            }
        }));
        tcMenus.add(new TCMenu("3 - createTCAlertDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createTCAlertDialog
                DialogHelper.createTCAlertDialog(context, "title", "message", "ok", "cancel", false,
                        new TCAlertDialog.OnTCActionListener() {
                            @Override
                            public void onOkAction() {
                                ToastUtil.show(context, "onOkAction");
                            }

                            @Override
                            public void onCancelAction() {
                                ToastUtil.show(context, "onCancelAction");
                            }
                        }).show();
            }
        }));
        tcMenus.add(new TCMenu("4 - DefaultProgressDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DefaultProgressDialog
                DefaultProgressDialog.createDialog(context).show();

                // CountDownTimer
                new CountDownTimer(TIME_PROGRESS, TIME_PROGRESS_INTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        DefaultProgressDialog.updateDialog(getOnTickMessage(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        DefaultProgressDialog.dismissDialog();
                    }
                }.start();
            }
        }));
        tcMenus.add(new TCMenu("5 - TCProgressDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TCProgressDialog
                TCProgressDialog.createDialog(context).show();

                // CountDownTimer
                new CountDownTimer(TIME_PROGRESS, TIME_PROGRESS_INTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        TCProgressDialog.updateDialog(getOnTickMessage(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        TCProgressDialog.dismissDialog();
                    }
                }.start();
            }
        }));
        tcMenus.add(new TCMenu("6 - TCLoadingDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TCLoadingDialog
                TCLoadingDialog.createDialog(context).show();

                // CountDownTimer
                new CountDownTimer(TIME_PROGRESS, TIME_PROGRESS_INTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        TCLoadingDialog.updateDialog(getOnTickMessage(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        TCLoadingDialog.dismissDialog();
                    }
                }.start();
            }
        }));
        tcMenus.add(new TCMenu("7 - TCActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TCActivity.showLoading()
                if (context instanceof TCActivity) {
                    ((TCActivity) context).showLoading(true);
                }

                // CountDownTimer
                new CountDownTimer(TIME_PROGRESS, TIME_PROGRESS_INTERVAL) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (context instanceof TCActivity) {
                            ((TCActivity) context).updateLoading(getOnTickMessage(millisUntilFinished));
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (context instanceof TCActivity) {
                            ((TCActivity) context).showLoading(false);
                        }
                    }
                }.start();
            }
        }));
        tcMenus.add(new TCMenu("8 - DialogHelper", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DialogHelper.showLoading()
                DialogHelper.showLoading(context, true);

                // CountDownTimer
                new CountDownTimer(10 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        DialogHelper.updateLoading(getOnTickMessage(millisUntilFinished));
                    }

                    @Override
                    public void onFinish() {
                        DialogHelper.showLoading(context, false);
                    }
                }.start();
            }
        }));
        tcMenus.add(new TCMenu("9 - UserCenterDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // UserCenterDialog
                UserCenterDialog.createDialog(context, UserUtil.getUUID(), null, UserUtil.getUUID()).show();
            }
        }));
        tcMenus.add(new TCMenu("10 - createTCAlertMenuDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createTCAlertMenuDialog
                DialogHelper.createTCAlertMenuDialog(context, "选择操作", null, MenuUtil.getTCMenus(context)).show();
            }
        }));
        tcMenus.add(new TCMenu("11 - createTCBottomMenuDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createTCBottomMenuDialog
                DialogHelper.createTCBottomMenuDialog(context, "选择操作", null, MenuUtil.getTCMenus(context)).show();
            }
        }));
        tcMenus.add(new TCMenu("12 - ManagerListDialog", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ManagerListDialog
                ManagerListDialog.createDialog(context, UserUtil.getUUID()).show();
            }
        }));

        DialogHelper.createTCAlertMenuDialog(context, "DialogDemo", null, tcMenus).show();
    }
}
