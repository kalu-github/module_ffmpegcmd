package lib.kalu.ffmpegcmd.util;

import android.util.Log;

import lib.kalu.ffmpegcmd.cmd.Cmd;
import lib.kalu.ffmpegcmd.entity.Level;

public class FFmpegLogUtil {

    private static final String TAG = "module_ffmpegcmd";
    public static boolean isPrint = false;

    public static void setEnable(boolean enable) {
        isPrint = enable;
    }

    public static final void logE(String messge) {
        logE(messge, null);
    }

    public static final void logE(String messge, Throwable throwable) {
        if (!isPrint)
            return;
        if (null == messge || messge.length() <= 0)
            return;
        Log.e(TAG, messge, throwable);
    }
}
