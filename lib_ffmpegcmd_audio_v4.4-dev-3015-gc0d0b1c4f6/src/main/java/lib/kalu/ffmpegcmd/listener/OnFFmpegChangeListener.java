package lib.kalu.ffmpegcmd.listener;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import lib.kalu.ffmpegcmd.bean.LogMessage;

@Keep
public interface OnFFmpegChangeListener {

    void onStart(Long executionId);

    void onSucc(long executionId);

    void onFail(long executionId, String error);

    void onCancel(long executionId);

    void onProgress(@NonNull long duration, @NonNull long position, @NonNull float progress);

    default void onWarning(long executionId, String error) {

    }

    default void onMessage(LogMessage logMessage) {
    }
}
