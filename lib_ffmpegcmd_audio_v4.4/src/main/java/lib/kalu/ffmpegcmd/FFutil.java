package lib.kalu.ffmpegcmd;

import android.util.Log;

import androidx.annotation.NonNull;

public class FFutil {

    static final String TAG = "ffcmd";

    static final void e(@NonNull String message) {
        e(message, null);
    }

    static final void e(@NonNull String message, @NonNull Throwable throwable) {
        if (!BuildConfig.DEBUG)
            return;
        if (null == message || message.length() <= 0)
            return;
        if (null == throwable) {
            Log.e(TAG, message);
        } else {
            Log.e(TAG, message, throwable);
        }
    }
}
