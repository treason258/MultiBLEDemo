package com.mjiayou.trecore.bean.entity;

import android.text.TextUtils;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by treason on 15/3/9.
 */
public class TCUMLogin {

    // treason@20151029-微博-网页端
//    10-29 05:49:12.263 5084-5084/? I/ToastUtil: 授权开始
//    10-29 05:49:33.099 5084-5084/? I/ToastUtil: 授权成功
//    10-29 05:49:33.103 5084-5084/? I/UserLoginActivity: uid=1734508991
//    10-29 05:49:33.103 5084-5084/? I/UserLoginActivity: access_secret=2.00TBp4tBj1t7xD3fab96031f0YLSFk
//    10-29 05:49:33.103 5084-5084/? I/UserLoginActivity: expires_in=652230
//    10-29 05:49:33.103 5084-5084/? I/UserLoginActivity: access_key=2.00TBp4tBj1t7xD3fab96031f0YLSFk
//    10-29 05:49:33.103 5084-5084/? I/ToastUtil: 正在获取平台信息
//    10-29 05:49:33.383 5084-5084/? I/ToastUtil: 获取平台信息成功
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: uid=1734508991
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: favourites_count=162
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: location=北京 朝阳区
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: description=一名正在努力变的更优秀的程序员
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: verified=false
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: friends_count=405
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: gender=1
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: screen_name=马加油
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: statuses_count=943
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: followers_count=122
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: profile_image_url=http://tp4.sinaimg.cn/1734508991/180/5736903836/1
//    10-29 05:49:33.383 5084-5084/? I/UserLoginActivity: access_token=2.00TBp4tBj1t7xD3fab96031f0YLSFk

    // treason@20151029-微博-客户端
//    10-29 05:46:21.719 5084-5084/? I/ToastUtil: 授权开始
//    10-29 05:46:23.647 5084-5084/? I/ToastUtil: 授权成功
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: uid=1734508991
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: com.sina.weibo.intent.extra.USER_ICON=null
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: _weibo_appPackage=com.sina.weibo
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: com.sina.weibo.intent.extra.NICK_NAME=马加油
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: userName=马加油
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: expires_in=652418
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: refresh_token=2.00TBp4tBj1t7xD2409bafbd01PiUYB
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: _weibo_transaction=null
//    10-29 05:46:23.647 5084-5084/? I/UserLoginActivity: access_token=2.00TBp4tBj1t7xD3fab96031f0YLSFk
//    10-29 05:46:23.647 5084-5084/? I/ToastUtil: 正在获取平台信息
//    10-29 05:46:24.059 5084-5084/? I/ToastUtil: 获取平台信息成功
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: uid=1734508991
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: favourites_count=162
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: location=北京 朝阳区
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: description=一名正在努力变的更优秀的程序员
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: verified=false
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: friends_count=405
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: gender=1
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: screen_name=马加油
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: statuses_count=943
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: followers_count=122
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: profile_image_url=http://tp4.sinaimg.cn/1734508991/180/5736903836/1
//    10-29 05:46:24.059 5084-5084/? I/UserLoginActivity: access_token=2.00TBp4tBj1t7xD3fab96031f0YLSFk

    // treason@20151029-QQ-客户端
//    10-29 06:14:40.387 7019-7019/? I/ToastUtil: 授权开始
//    10-29 06:14:46.247 7019-7019/? I/ToastUtil: 授权成功
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: uid=C69B983EFDBCB74C912D314E4BB64F70
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: ret=0
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: auth_time=
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: pay_token=D880A80BF11C94EF58A9D6A08671F869
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: pf=desktop_m_qq-10000144-android-2002-
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: sendinstall=
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: appid=
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: page_type=
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: expires_in=7776000
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: openid=C69B983EFDBCB74C912D314E4BB64F70
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: pfkey=b1ec5864c73f4894fcd1a9481b405e86
//    10-29 06:14:46.247 7019-7019/? I/CallbackData: access_token=73CF42F576B318C60F1E0F5504C5A051
//    10-29 06:14:46.247 7019-7019/? I/ToastUtil: 正在获取平台数据
//    10-29 06:14:46.535 7019-7019/? I/ToastUtil: 获取平台信息成功
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: is_yellow_year_vip=0
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: vip=0
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: level=0
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: province=北京
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: yellow_vip_level=0
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: is_yellow_vip=0
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: gender=男
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: screen_name=TreTest
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: msg=
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: profile_image_url=http://q.qlogo.cn/qqapp/1104716002/C69B983EFDBCB74C912D314E4BB64F70/100
//    10-29 06:14:46.535 7019-7019/? I/CallbackData: city=东城

    // treason@20151029-微信-客户端
//    10-29 14:56:19.148 19969-19969/? I/ToastUtil: 授权开始
//    10-29 14:56:19.798 19969-19969/? I/ToastUtil: 授权成功
//    10-29 14:56:19.798 19969-19969/? I/UserLoginActivity: uid=oGkC_s3vZYr4Ze8nSRjA1aE-SayE
//    10-29 14:56:19.798 19969-19969/? I/UserLoginActivity: refresh_token_expires=604800
//    10-29 14:56:19.798 19969-19969/? I/UserLoginActivity: openid=oGkC_s3vZYr4Ze8nSRjA1aE-SayE
//    10-29 14:56:19.798 19969-19969/? I/UserLoginActivity: expires_in=7200
//    10-29 14:56:19.798 19969-19969/? I/UserLoginActivity: refresh_token=OezXcEiiBSKSxW0eoylIeLv5nxol-7sHPE0EfSB1MnO-mDyVFSe99ZIPx3OlFltrL88vIgxMDyZMT_78t3f3kJGiHULbZm4IOkONZnktMqDp3sPEfNnKSp6PJG5ehRsFcbQYC2UDDAPZV6KXE3eOyA
//    10-29 14:56:19.798 19969-19969/? I/UserLoginActivity: scope=snsapi_base,snsapi_userinfo,
//    10-29 14:56:19.798 19969-19969/? I/UserLoginActivity: access_token=OezXcEiiBSKSxW0eoylIeLv5nxol-7sHPE0EfSB1MnO-mDyVFSe99ZIPx3OlFltr1uW718Nj2rqG-nudIEp1u5dYV9q2owBPGrUheutVKWLsHyTlGBYgAREQ-DsqoJIxZy4jC404qBoPFvSaFxUcFQ
//    10-29 14:56:19.878 19969-19969/? I/ToastUtil: 获取平台信息成功
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: sex=1
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: nickname=TreTest
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: unionid=ofHuit-PIwO5mzuJiIFFl3uPfIzw
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: province=
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: openid=oGkC_s3vZYr4Ze8nSRjA1aE-SayE
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: language=zh_CN
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: headimgurl=http://wx.qlogo.cn/mmopen/ajNVdqHZLLDgZWuibvibDMEb3LTTKoKib8fNcicO4bkXnKFBTyv3nx1TktLCo0DgHH6P3w0ZafAuHibTsiciapEOHUeZpg17XQOgRjsSdQPbHc9VzE/0
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: country=中国
//    10-29 14:56:19.878 19969-19969/? I/UserLoginActivity: city=

    private SHARE_MEDIA platform;
    private String uid;

    private String login_type;
    private String third_id;
    private String third_token;
    private String third_name;
    private String third_photo;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (!TextUtils.isEmpty(login_type)) {
            sb.append("login_type=" + login_type + "\r\n");
        }
        if (!TextUtils.isEmpty(third_id)) {
            sb.append("third_id=" + third_id + "\r\n");
        }
        if (!TextUtils.isEmpty(third_token)) {
            sb.append("third_token=" + third_token + "\r\n");
        }
        if (!TextUtils.isEmpty(third_name)) {
            sb.append("third_name=" + third_name + "\r\n");
        }
        if (!TextUtils.isEmpty(third_photo)) {
            sb.append("third_photo=" + third_photo + "\r\n");
        }
        return sb.toString();
    }

    public SHARE_MEDIA getPlatform() {
        if (platform == null) {
            platform = SHARE_MEDIA.SINA;
        }
        return platform;
    }

    public void setPlatform(SHARE_MEDIA platform) {
        this.platform = platform;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getThird_id() {
        return third_id;
    }

    public void setThird_id(String third_id) {
        this.third_id = third_id;
    }

    public String getThird_token() {
        return third_token;
    }

    public void setThird_token(String third_token) {
        this.third_token = third_token;
    }

    public String getThird_name() {
        return third_name;
    }

    public void setThird_name(String third_name) {
        this.third_name = third_name;
    }

    public String getThird_photo() {
        return third_photo;
    }

    public void setThird_photo(String third_photo) {
        this.third_photo = third_photo;
    }

}
