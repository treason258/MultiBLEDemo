package com.mjiayou.trecore.bean.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by treason on 16/5/14.
 */
public class TCUser implements Serializable {

//    fansNumber	字符串	是	粉丝数量。
//    nickname	字符串	是	别名
//    accountName	字符串	是	登录用户名
//    photo	字符串	否	头像
//    userId	字符串	是	用户id
//    personalSignature	字符串	是	个性签名
//    attentionNum	字符串	是	关注数量
//    moneyNum	字符串	是	当前余额（充值金币）
//    currentAmt	字符串	是	待结算金额（映票收入）
//    uuid	字符串	是	uuid
//    attentionFlag	字符串	是	关注状态
//    gradeNo	字符串	是	级别
//    sex	字符串	是	性别
//    age	字符串	是	年龄
//    constellation	字符串	是	星座
//    emotional	字符串	是	情感
//    fansList	字符串	是	粉丝列表
//    attentionList		字符串	是	关注列表

//    "id": "686e1edebda74f3cb7e13df171450942",
//    "uuid": "10000127",
//    "gradeNo": "1",
//    "nickname": "184*****352"
//    "fansNumber": "9",
//    "gradeId": "10",
//    "gradeTitle": "小白球童",
//    "integral": "0",
//    "nickname": "186*****121",
//    "accountName": "18600574121",
//    "topicNum": "0",
//    "userNo": "10000012",
//    "userId": "63839d3ff9574fe7b48ee339c50b47e8",
//    "attentionNum": "8",
//    "moneyNum": "0",
//    "currentAmt": "0.0"

    // 性别定义 0男 1女
    public static final String GENDER_MAN = "0";
    public static final String GENDER_WOMAN = "1";

    // 0接收直播推送  1 禁止直播推送（仅限于关注列表）
    public static final String BAN_LIVE_FLAG_OPEN = "0";
    public static final String BAN_LIVE_FLAG_CLOSE = "1";

    private String uuid; // uuid
    private String fansNumber; // 粉丝数量
    private String gradeNo; // 级别
    private String integral; // 积分
    private String nickname; // 昵称
    private String accountName; // 电话号
    private String topicNum; // 点播数量
    private String userId; // 用户id
    private String attentionNum; // 关注数量
    private String moneyNum; // 用户余额(充值金币)
    private String currentAmt; // 待结算金额(映票收入)
    private String attentionFlag; // 0-已关注；1-曾关注过；2-没有关系
    private List<TCUser> fansList; // 粉丝列表
    private List<TCUser> attentionList; // 关注列表
    private String photo; // 头像
    private String personalSignature; // 个性签名
    private String sex; // 性别
    private String like; // 爱好
    private String banLiveFlag; // 0接收直播推送  1 禁止直播推送（仅限于关注列表）

    private String demand_num; // 点播数量
    private String id;

    private String flag; // 0-单方面的关系（关注对方或被关注）；1-互相关注的关系

    private String age;
    private String constellation;
    private String emotional;

    private String birthdate;

    public String getBanLiveFlag() {
        return banLiveFlag;
    }

    public void setBanLiveFlag(String banLiveFlag) {
        this.banLiveFlag = banLiveFlag;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getEmotional() {
        return emotional;
    }

    public void setEmotional(String emotional) {
        this.emotional = emotional;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemand_num() {
        return demand_num;
    }

    public void setDemand_num(String demand_num) {
        this.demand_num = demand_num;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(String attentionFlag) {
        this.attentionFlag = attentionFlag;
    }

    public List<TCUser> getFansList() {
        return fansList;
    }

    public void setFansList(List<TCUser> fansList) {
        this.fansList = fansList;
    }

    public List<TCUser> getAttentionList() {
        return attentionList;
    }

    public void setAttentionList(List<TCUser> attentionList) {
        this.attentionList = attentionList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGradeNo() {
        return gradeNo;
    }

    public void setGradeNo(String gradeNo) {
        this.gradeNo = gradeNo;
    }

    public String getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(String fansNumber) {
        this.fansNumber = fansNumber;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(String topicNum) {
        this.topicNum = topicNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(String attentionNum) {
        this.attentionNum = attentionNum;
    }

    public String getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(String moneyNum) {
        this.moneyNum = moneyNum;
    }

    public String getCurrentAmt() {
        return currentAmt;
    }

    public void setCurrentAmt(String currentAmt) {
        this.currentAmt = currentAmt;
    }
}
