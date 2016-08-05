package com.mjiayou.trecore.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.view.WheelTime;
import com.mjiayou.trecore.util.DateUtil;
import com.mjiayou.trecore.util.UserUtil;
import com.mjiayou.trecore.util.ViewUtil;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

import com.mjiayou.trecore.widget.TCConfigs;
import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/7/4.
 */
public class TCBirthdayDialog extends TCDialog {

    // root
    @Optional
    @InjectView(R.id.view_root)
    ViewGroup mViewRoot;
    // dialog
    @InjectView(R.id.layout_dialog)
    LinearLayout mLayoutDialog;
    // 年龄、星座
    @InjectView(R.id.tv_age)
    TextView mTvAge;
    @InjectView(R.id.tv_constellation)
    TextView mTvConstellation;
    // timepicker
    @InjectView(R.id.layout_timepicker)
    LinearLayout mLayoutTimePicker;
    // 确定
    @InjectView(R.id.tv_submit)
    TextView mTvSubmit;

    private WheelTime mWheelTime;
    private OnTimeSelectedListener mOnTimeSelectedListener;
    private View.OnClickListener mOnSubmitClickListener;

    /**
     * 构造函数
     */
    public TCBirthdayDialog(Context context, int themeResId) {
        super(context, themeResId);
        mResourceId = R.layout.tc_layout_birthday_dialog;

        // 窗口显示位置
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        // 窗口显示动画
        getWindow().setWindowAnimations(R.style.tc_bottom_menu_animation);

        // 可返回按钮取消
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public TCBirthdayDialog(Context context) {
        this(context, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mResourceId);
        ButterKnife.inject(this);

        // 窗口宽度
        if (mViewRoot != null) {
            ViewUtil.setWidthAndHeight(mViewRoot, TCConfigs.getScreenWidth(WIDTH_RATIO_FULL), WindowManager.LayoutParams.WRAP_CONTENT);
        }

        try {
            String birthday = UserUtil.getUserInfo().getBirthdate();
            Calendar calendar = Calendar.getInstance();
            if (!TextUtils.isEmpty(birthday)) {
                calendar = DateUtil.parseCalendar(birthday, DateUtil.FormatType.FORMAT_102);
            } else {
                calendar.set(1990, Calendar.JANUARY, 1);
            }

            // mTvAge mTvConstellation
            int age = DateUtil.getAge(calendar);
            String constellation = DateUtil.getConstellation(calendar);
            mTvAge.setText(age + "岁");
            mTvConstellation.setText(constellation);
            if (mOnTimeSelectedListener != null) {
                mOnTimeSelectedListener.onTimeSelected(calendar, age, constellation);
            }

            //默认选中当前时间
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            mWheelTime = new WheelTime(mLayoutTimePicker, TimePickerView.Type.YEAR_MONTH_DAY);
            mWheelTime.setPicker(year, month, day, hours, minute);
            mWheelTime.setOnTimeSelectListener(new WheelTime.OnTimeChangedListener() {
                @Override
                public void onChanged(Date date) {
                    Calendar calendar = DateUtil.parseCalendar(date);
                    int age = DateUtil.getAge(calendar);
                    String constellation = DateUtil.getConstellation(calendar);
                    mTvAge.setText(age + "岁");
                    mTvConstellation.setText(constellation);
                    if (mOnTimeSelectedListener != null) {
                        mOnTimeSelectedListener.onTimeSelected(calendar, age, constellation);
                    }
                }
            });

            mTvSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mOnSubmitClickListener != null) {
                        mOnSubmitClickListener.onClick(v);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// ******************************** 自定义操作 ********************************

    public interface OnTimeSelectedListener {
        void onTimeSelected(Calendar calendar, int age, String constellation);

    }

    public void setOnTimeSelectListener(OnTimeSelectedListener onTimeSelectedListener) {
        this.mOnTimeSelectedListener = onTimeSelectedListener;
    }

    public void setOnSubmitClickListener(View.OnClickListener onSubmitClickListener) {
        this.mOnSubmitClickListener = onSubmitClickListener;
    }
}