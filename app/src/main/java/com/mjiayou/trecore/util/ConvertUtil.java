package com.mjiayou.trecore.util;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mjiayou.trecore.bean.entity.TCSinaStatuses;
import com.mjiayou.trecore.bean.entity.TCUser;
import com.mjiayou.trecore.helper.GsonHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import com.mjiayou.trecoredemo.R;

/**
 * Created by treason on 16/5/14.
 */
public class ConvertUtil {

    // ******************************** List ********************************

    /**
     * array TO list
     */
    public static List<String> parseArrayToList(String[] array) {
        return Arrays.asList(array);
    }

    /**
     * list TO array
     */
    public static String[] parseListToArray(List<String> list) {
        String[] array = new String[list.size()];
        return list.toArray(array);
    }

    /**
     * list TO array
     */
    public static float[] parseListToArrayFloat(List<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * string TO list
     */
    public static List<String> parseStringToList(String str) {
        return parseArrayToList(str.split(","));
    }

    /**
     * list TO string
     */
    public static String parseListToString(List<String> list) {
        String result = "";
        if (list == null) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("")) {
                continue;
            }
            if (result.equals("")) {
                result += list.get(i);
            } else {
                result += "," + list.get(i);
            }
        }
        return result;
    }

    // ******************************** JsonArray ********************************

    /**
     * jsonArray TO list
     */
    public static List<String> parseList(String jsonArray) {
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return GsonHelper.get().fromJson(jsonArray, type);
    }

    // ******************************** other ********************************

    /**
     * 字符串保持显示count个字符之内
     */
    public static String parseStringLimitChar(String text, int count) {
        if (text.length() > count) {
            text = text.substring(0, count) + "...";
        }
        return text;
    }

    /**
     * 使Map按key进行排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * gzip解压缩
     */
    public static String getRealString(byte[] data) {
        byte[] h = new byte[2];
        h[0] = (data)[0];
        h[1] = (data)[1];
        int head = (data[0] << 8) | data[1] & 0xFF;
        boolean t = head == 0x1f8b;
        InputStream in;
        StringBuilder sb = new StringBuilder();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            if (t) {
                in = new GZIPInputStream(bis);
            } else {
                in = bis;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * convertStreamToString
     */
    public static String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(is), 1024);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } finally {
            StreamUtil.closeQuietly(is);
        }
    }

    /**
     * 转换Bundle
     */
    public static String parseBundle(Bundle data) {
        StringBuilder sb = new StringBuilder();
        for (String key : data.keySet()) {
            sb.append(key + "=" + (data.get(key) == null ? "null" : data.get(key).toString()) + "\r\n");
        }
        return sb.toString();
    }

    /**
     * 转换Map<String, Object>
     */
    public static String parseMap(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (String key : data.keySet()) {
            sb.append(key + "=" + (data.get(key) == null ? "null" : data.get(key).toString()) + "\r\n");
        }
        return sb.toString();
    }

    /**
     * List<SinaStatuses> TO List<String>
     */
    public static List<String> parseSinaStatusesToString(List<TCSinaStatuses> data) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String name = data.get(i).getUser().getName();
            String location = data.get(i).getUser().getLocation();
            String text = data.get(i).getText();
            result.add(name + "(" + location + ")" + ":" + text);
        }
        return result;
    }

    // ******************************** custom ********************************

}
