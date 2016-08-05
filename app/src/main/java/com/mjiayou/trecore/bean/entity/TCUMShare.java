package com.mjiayou.trecore.bean.entity;

import java.io.Serializable;

/**
 * Created by treason on 15-11-18.
 */
public class TCUMShare implements Serializable {

    private String title;
    private String desc;
    private String imgUrl;
    private String targetUrl;

    public TCUMShare() {
        
    }

    public TCUMShare(String title, String desc, String imgUrl, String targetUrl) {
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.targetUrl = targetUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
