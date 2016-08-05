package com.mjiayou.trecore.bean;

/**
 * Created by treason on 15-10-21.
 */
public class TCRequestBody {

    private String num; // 当前页数 0首页 1第二页......
    private String num_no; // 每页显示条数

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNum_no() {
        return num_no;
    }

    public void setNum_no(String num_no) {
        this.num_no = num_no;
    }
}
