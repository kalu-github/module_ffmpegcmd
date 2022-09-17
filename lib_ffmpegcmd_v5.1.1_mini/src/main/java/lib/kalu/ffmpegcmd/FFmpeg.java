package lib.kalu.ffmpegcmd;

import android.util.Log;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Keep
public final class FFmpeg {

    private static final String TAG = "ffmpeg-cmd-java";
    private static boolean ENABLE = false;

    static {
        System.loadLibrary("ffmpegcmd");
        System.loadLibrary("ffmpegcmd-jni");
    }

    /*****************/

    private static native int setLoggerJNI(boolean enable);

    private static native long getDurationJNI(String mediaPath);

    private static native int cancelJNI();

    private static native int executeJNI(String command, long totalTime, OnFFmpegChangeListener listener);

    private static native String getVersionJNI();

    private static native String getAVInputFormatsJNI();

    private static native String getAVCodecsJNI();

    private static native String getProtocolsJNI();

    private static native String getFiltersJNI();

    private static native String getBSFJNI();

    /*****************/

    public static String getVersion() {
        return getVersionJNI();
    }

    public static String getAVInputFormats() {
        return getAVInputFormatsJNI();
    }

    public static String getAVCodecs() {
        return getAVCodecsJNI();
    }

    public static String getProtocols() {
        return getProtocolsJNI();
    }

    public static String getBSF() {
        return getBSFJNI();
    }

    public static String getFilters() {
        return getFiltersJNI();
    }

    public static int setLogger(boolean v) {
        ENABLE = v;
        return setLoggerJNI(ENABLE);
    }

    public static long getDuration(String v) {
        return getDurationJNI(v);
    }

    public static int cancle() {
        return cancelJNI();
    }

    public static int executeCmd(List<String> list) {
        return executeCmd(list, null);
    }

    public static int executeCmd(List<String> list, OnFFmpegChangeListener listener) {
        int size = list.size();
        String[] strings = list.toArray(new String[size]);
        return executeCmd(strings, listener);
    }

    public static int executeCmd(String[] strings) {
        return executeCmd(strings, null);
    }

    public static int executeCmd(String[] strings, OnFFmpegChangeListener listener) {
        StringBuilder builder = new StringBuilder();
        int length = strings.length;
        for (int i = 0; i < length; i++) {
            String s = strings[i];
            if (null == s || s.trim().length() <= 0)
                continue;
            builder.append(s);
            if (i + 1 < length) {
                builder.append(" ");
            }
        }
        String command = builder.toString();
        return executeCmd(command, listener);
    }

    public static int executeCmd(String command) {
        return executeCmd(command, -1, null);
    }

    public static int executeCmd(String command, OnFFmpegChangeListener listener) {
        return executeCmd(command, -1, listener);
    }

    public static int executeCmd(String command, long totalTime, OnFFmpegChangeListener listener) {
        if (ENABLE) {
            Log.e(TAG, "executeCmd => " + command);
        }
        return executeJNI(command, totalTime, listener);
    }
}
