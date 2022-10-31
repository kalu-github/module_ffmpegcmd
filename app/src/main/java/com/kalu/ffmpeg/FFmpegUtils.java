package com.kalu.ffmpeg;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.kalu.ffmpegcmd.FFmpeg;
import lib.kalu.ffmpegcmd.OnFFmpegChangeListener;

public final class FFmpegUtils {

    public static boolean createNullMusic(Context context, @NonNull float second, @NonNull String savePath) {
        try {
            Log.e("FFmpegUtils", "createNullMusic[1] => second = " + second + ", savePath = " + savePath);

            if (second <= 0)
                throw new IllegalArgumentException("second is <= 0");
//            String cmd = "ffmpeg -y -f lavfi -i anullsrc -t " + second + " " + savePath;
            ArrayList<String> arrays = new ArrayList<>();
            arrays.add("ffmpeg");
            arrays.add("-y");
            arrays.add("-f");
            arrays.add("lavfi");
            arrays.add("-i");
            arrays.add("anullsrc");
            arrays.add("-t");
            arrays.add(String.valueOf(second));
            arrays.add("-ar"); // 采样数
            arrays.add("44100");
            arrays.add("-b:a"); // 音频码率
            arrays.add("48000");
//            arrays.add("-af"); // 降噪
//            arrays.add("asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40");
            arrays.add(savePath);
            int execute = FFmpeg.executeCmd(arrays, new OnFFmpegChangeListener() {

                @Override
                public void start() {
                    Toast.makeText(context, "开始", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void complete() {
                }

                @Override
                public void progress(float progress) {
                    Log.e("FFmpegUtils", "progress => progress = "+progress);
                }

                @Override
                public void fail() {
                    Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void success() {
                    Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                }
            });
            if (execute != 0)
                throw new IllegalArgumentException("ffmpeg fail");
            Log.e("FFmpegUtils", "createNullMusic[2] => second = " + second + ", savePath = " + savePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FFmpegUtils", "createNullMusic[3] => second = " + second + ", savePath = " + savePath);
            return false;
        }
    }

    public static boolean mixMusic(@NonNull String backgroundPath, @NonNull String mixPath, @NonNull Map<Integer, String> data) {
        try {
            Log.e("FFmpegUtils", "mixMusic[1] => backgroundPath = " + backgroundPath + ", mixPath = " + mixPath + ", data = " + data);
            if (null == backgroundPath || backgroundPath.length() <= 0)
                return false;
            if (null == mixPath || mixPath.length() <= 0)
                return false;
            if (null == data || data.isEmpty())
                return false;
            // 1
            File mix = new File(mixPath);
            if (mix.exists()) {
                mix.delete();
            }
            ArrayList<String> arrays = new ArrayList<>();
            arrays.add("-y");
            arrays.add("-vol");
            arrays.add("1000");
            arrays.add("-i");
            arrays.add(backgroundPath);

            // 2 => append-cmd
            int index = 0;
            StringBuilder cmd = new StringBuilder();
            StringBuilder suffix = new StringBuilder();
            suffix.append("[0]");

            Set<Map.Entry<Integer, String>> entries = data.entrySet();
            for (Map.Entry<Integer, String> entry : entries) {

                Integer start = entry.getKey();
                String path = entry.getValue();
                if (start <= 0 || null == path || path.length() <= 0)
                    continue;
                File file = new File(path);
                if (null == file || !file.exists())
                    continue;

                // a
                index = index + 1;
                suffix.append("[a" + index + "]");
                // b
                arrays.add("-i");
                arrays.add(path);
                // c
                cmd.append("[" + index + "]adelay=" + start + "|" + start + "[a" + index + "];");
            }

            int count = index + 1;
            suffix.append("amix=" + count);

            String stringCmd = cmd.toString();
            String stringSuffix = suffix.toString();
            arrays.add("-filter_complex");
            arrays.add(stringCmd + stringSuffix);
            arrays.add("-ar"); // 采样数
            arrays.add("44100");
            arrays.add("-b:a"); // 音频码率
            arrays.add("48000");
//            arrays.add("-af"); // 降噪
//            arrays.add("asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40");
            arrays.add(mixPath);
            int execute = FFmpeg.executeCmd(arrays);
            if (execute != 0)
                throw new IllegalArgumentException("ffmpeg fail");
            Log.e("FFmpegUtils", "mixMusic[2] => backgroundPath = " + backgroundPath + ", mixPath = " + mixPath + ", data = " + data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FFmpegUtils", "mixMusic[3] => backgroundPath = " + backgroundPath + ", mixPath = " + mixPath + ", data = " + data);
            return false;
        }
    }

    public static boolean pcmToMp3(@NonNull String pcmPath, @NonNull String savePath) {
        try {
            Log.e("FFmpegUtils", "pcmToMp3[1] => pcmPath = " + pcmPath + ", savePath = " + savePath);
            List<String> arrays = Arrays.asList(
                    "ffmpeg",
                    "-y",
//                    "-vol",
//                    "2000",
                    "-f",
                    "s16le",
                    "-ac",
                    "1",
                    "-ar",
                    "44100",
                    "-acodec",
                    "pcm_s16le",
                    "-i",
                    pcmPath,
                    "-ar", // 采样数
                    "44100",
                    "-b:a", // 音频码率
                    "48000",
//                    "-af",
//                    "highpass=f=200,lowpass=f=3000",
//                    "-vf",
//                    "nlmeans=h=2:range=3:temporal=1",
//                    "-af",
//                    "asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40",
                    savePath);
            int execute = FFmpeg.executeCmd(arrays, new OnFFmpegChangeListener() {
                @Override
                public void fail() {

                }

                @Override
                public void success() {

                }
            });
            if (execute != 0)
                throw new IllegalArgumentException("ffmpeg fail");
            Log.e("FFmpegUtils", "pcmToMp3[2] => pcmPath = " + pcmPath + ", savePath = " + savePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FFmpegUtils", "pcmToMp3[3] => pcmPath = " + pcmPath + ", savePath = " + savePath);
            return false;
        }
    }
}
