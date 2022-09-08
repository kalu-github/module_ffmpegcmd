package lib.kalu.ffmpegcmd.util;

import android.util.Log;

import androidx.annotation.NonNull;

public class LogUtil {

    public static final String TAG = "mobile-ffmpeg";

    public static final void e(@NonNull String message) {
        e(message, null);
    }

    public static final void e(@NonNull String message, @NonNull Throwable throwable) {
        if (null == message || message.length() <= 0)
            return;
        if (null == throwable) {
            Log.e(TAG, message);
        } else {
            Log.e(TAG, message, throwable);
        }
    }
}
