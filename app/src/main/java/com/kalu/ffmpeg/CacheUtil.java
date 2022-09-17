package com.kalu.ffmpeg;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * description: 缓存
 * create by kalu on 2020-06-03
 */
public final class CacheUtil {

    public static final boolean setCache(Context context, String key, String value) {


        boolean result;
        FileOutputStream out = null;
        BufferedWriter writer = null;

        try {

            StringBuilder builder = new StringBuilder();
            builder.append("user@");

            String string1 = key.toString();
            String toLowerCase = string1.toLowerCase();
            builder.append(toLowerCase);

            builder.append("@");
            String string = builder.toString();

            out = context.openFileOutput(string, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));

            // 正常保存
            writer.write(value);

            writer.flush();
            result = true;

        } catch (Exception e) {
            result = false;
//            LogUtil.e("cacheutil", "setCache =>" + e.getMessage(), e);
        } finally {

            if (null != out) {
                try {
                    out.close();
                } catch (Exception e) {
//                    LogUtil.e("cacheutil", "setCache =>" + e.getMessage(), e);
                }
            }

            if (null != writer) {
                try {
                    writer.close();
                } catch (Exception e) {
//                    LogUtil.e("cacheutil", "setCache =>" + e.getMessage(), e);
                }
            }
        }
        return result;
    }

    public static final String getCache(Context context, String key) {

        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {

            StringBuilder builder = new StringBuilder();
            builder.append("user@");

            String toLowerCase = key.toLowerCase();
            builder.append(toLowerCase);

            builder.append("@");
            String filename = builder.toString();
            String absolutePath = context.getFilesDir().getAbsolutePath();

            File file = new File(absolutePath + File.separator + filename);

            // succ
            if (null != file && file.exists()) {

                in = context.openFileInput(filename);
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

            }
            // fail
            else {
                content.append("");
            }

        } catch (Exception e) {
//            LogUtil.e("cacheutil", "getCache =>" + e.getMessage(), e);
        } finally {

            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
//                    LogUtil.e("cacheutil", "getCache =>" + e.getMessage(), e);
                }
            }

            if (null != reader) {
                try {
                    reader.close();
                } catch (Exception e) {
//                    LogUtil.e("cacheutil", "getCache =>" + e.getMessage(), e);
                }
            }
        }

        String value = content.toString();
        return value;
    }
}
