package lib.kalu.ffmpegcmd;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import lib.kalu.ffmpegcmd.bean.MediaBean;

public class FFutil {

    static final String TAG = "ffcmd";
    static final String MESSAGE = "illegal short term buffer state detected";
    static final String JSON = "{";

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

    static String checkOutputCommand(@NonNull String output) {
        try {
            String[] split = output.split(MESSAGE);
            if (null != split && split.length >= 2) {
                output = JSON + split[1];
            }
            return output;
        } catch (Exception e) {
            return output;
        }
    }

    static String getFormatOutputCommand(@NonNull String output) {
        try {
            JSONObject object = new JSONObject(output);
            JSONObject format = object.optJSONObject("format");
            return format.toString();
        } catch (Exception e) {
            return "{}";
        }
    }
}
