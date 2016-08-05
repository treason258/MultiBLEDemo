package com.mjiayou.trecore.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * Created by treason on 15/6/8.
 */
public class FileUtil {

    /**
     * 创建文件夹，如果文件目录不存在，则递归创建文件目录
     */
    public static File createFolder(File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    public static File createFolder(String folderPath) {
        File folder = new File(folderPath);
        return createFolder(folder);
    }

    /**
     * 创建文件
     */
    public static File createFile(File file) throws IOException {
        if (!file.exists()) {
            createFolder(file.getParent());
            file.createNewFile();
        }
        return file;
    }

    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        return createFile(file);
    }

    /**
     * 文件数据流写入文件
     */
    public static void writeToFile(String filePath, InputStream inputStream) {
        try {
            File file = createFile(filePath);

            OutputStream output = null;
            try {
                output = new FileOutputStream(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                try {
                    final byte[] buffer = new byte[1024];
                    int read;

                    while ((read = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                    }
                    output.flush();
                } finally {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToFile(String fileDir, String fileName, InputStream stream) {
        writeToFile(fileDir + fileName, stream);
    }

    /**
     * 保存文件数据
     */
    public static void writeToFile(String filePath, byte[] bytes) {
        try {
            File file = createFile(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    public static void writeToFile(String filePath, String content) {
        writeToFile(filePath, content.getBytes());
    }

    public static void writeToFile(String fileDir, String fileName, String content) {
        writeToFile(fileDir + fileName, content);
    }

    /**
     * 读取文件数据
     */
    public static String readFromFile(String filePath) {
        String content = "";
        try {
            File file = createFile(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            fileInputStream.close();
            content = new String(buffer);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return content;
    }

    public static String readFromFile(String fileDir, String fileName) {
        return readFromFile(fileDir + fileName);
    }

    /**
     * 获取文件夹大小
     */
    public static long getFolderSize(String fileDir) {
        long size = 0l;
        try {
            File file = new File(fileDir);
            if (!file.exists()) {
                return size;
            }

            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    size = size + getFolderSize(files[i].getAbsolutePath());
                } else {
                    size = size + files[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

//    public static long getFileSize(String filePath) {
//        long size = 0l;
//        try {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                return size;
//            }
//
//            File[] files = file.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isDirectory()) {
//                    size = size + getFolderSize(files[i].getPath());
//                } else {
//                    size = size + files[i].length();
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }

    /**
     * 删除指定目录下文件及目录
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) { // 处理目录
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) { // 如果是文件，删除
                        file.delete();
                    } else { // 目录
                        if (file.listFiles().length == 0) { // 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     */
    public static String getFormatSize(long size) {
        double kiloByte = size / 1024.0d;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024.0d;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024.0d;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024.0d;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}