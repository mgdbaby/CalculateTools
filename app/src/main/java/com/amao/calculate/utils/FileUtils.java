package com.amao.calculate.utils;

import android.text.TextUtils;

import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 文件处理工具类
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 17-12-26 上午11:08
 */
public class FileUtils {

    /****
     * 创建文件目录
     * @param dir 文件目录地址
     */
    public static void mkdirs(String dir) {
        File imgCache = new File(dir);
        if (!imgCache.exists()) {
            boolean success = imgCache.mkdirs();
            if (!success) {
                LogUtil.e("mkdirs fail. path = " + dir);
            }
        }
    }

    /**
     * 文件是否存在
     */
    public static boolean isFileExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        return file.exists() && file.length() > 0;
    }


    /**
     * 将文件流转换为文件
     */
    public static boolean inputStreamToFile(InputStream is, String filePath) {
        FileOutputStream fos = null;
        try {
            if (is != null && is.available() > 0) {
                fos = new FileOutputStream(filePath);
                byte[] b = new byte[2048];
                while (is.read(b) != -1) {
                    fos.write(b);
                }
                return true;
            }
        } catch (IOException e) {
            LogUtil.e(e);
        } finally {
            IOUtil.closeQuietly(fos);
        }
        return false;
    }
}
