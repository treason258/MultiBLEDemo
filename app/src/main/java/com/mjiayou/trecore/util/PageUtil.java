package com.mjiayou.trecore.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页机制工具类
 *
 * @author treason
 * @date 2014年9月24日
 */
public class PageUtil {

    private static final String TAG = "PageUtil";

    private static final int ITEM_ALL_COUNT = 4;
    public static final int ITEM_DEFAULT = 0;
    public static final int ITEM_HOME_ATTENTION = 1;
    public static final int ITEM_HOME_HOT = 2;
    public static final int ITEM_HOME_NEW = 3;

    // 默认每页显示数量
    private static int COUNT = 12;

    // 默认第一页页码
    private static int INDEX = 0;

    // 当前页码
    public static List<Integer> mListPage = new ArrayList<>();

    // 当前是否是下拉刷新
    public static List<Boolean> mListIsPullRefresh = new ArrayList<>();

    // 当前是否是上拉加载
    public static List<Boolean> mListIsPullLoadMore = new ArrayList<>();

    static {
        // mListPage
        mListPage = new ArrayList<>();
        for (int i = 0; i < ITEM_ALL_COUNT; i++) {
            mListPage.add(INDEX);
        }

        // mListIsPullRefresh
        mListIsPullRefresh = new ArrayList<>();
        for (int i = 0; i < ITEM_ALL_COUNT; i++) {
            mListIsPullRefresh.add(true);
        }

        // mListIsPullLoadMore
        mListIsPullLoadMore = new ArrayList<>();
        for (int i = 0; i < ITEM_ALL_COUNT; i++) {
            mListIsPullLoadMore.add(false);
        }
    }

    /**
     * 默认每页显示数量
     */
    public static int defaultCount() {
        return COUNT;
    }

    /**
     * 当前是否是下拉刷新
     */
    public static boolean isPullRefresh(int item) {
        return mListIsPullRefresh.get(item);
    }

    public static boolean isPullRefresh() {
        return isPullRefresh(ITEM_DEFAULT);
    }

    /**
     * 当前是否是上拉加载
     */
    public static boolean isPullLoadMore(int item) {
        return mListIsPullLoadMore.get(item);
    }

    public static boolean isPullLoadMore() {
        return isPullLoadMore(ITEM_DEFAULT);
    }

    /**
     * 下拉刷新-页码归一
     */
    public static int pageReset(int item) {
        mListIsPullRefresh.set(item, true);
        mListIsPullLoadMore.set(item, false);
        mListPage.set(item, INDEX);
        return mListPage.get(item);
    }

    public static int pageReset() {
        return pageReset(ITEM_DEFAULT);
    }

    /**
     * 上拉加载-页码累加
     */
    public static int pageAdd(int item) {
        mListIsPullRefresh.set(item, false);
        mListIsPullLoadMore.set(item, true);
        mListPage.set(item, mListPage.get(item) + 1);
        return mListPage.get(item);
    }

    public static int pageAdd() {
        return pageAdd(ITEM_DEFAULT);
    }

    /**
     * 上拉加载-行数累加
     */
    public static int rowAdd(int item) {
        mListIsPullRefresh.set(item, false);
        mListIsPullLoadMore.set(item, true);
        mListPage.set(item, mListPage.get(item) + COUNT);
        return mListPage.get(item);
    }

    public static int rowAdd() {
        return rowAdd(ITEM_DEFAULT);
    }
}