package lib.kalu.ffmpegcmd.callback;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import lib.kalu.ffmpegcmd.entity.LogMessage;

@Keep
public interface ExecuteCallback {

    void onStart(Long executionId);

    void onSuccess(long executionId);

    void onFailure(long executionId, String error);

    void onCancel(long executionId);

    void onProgress(@NonNull long duration, @NonNull long position, @NonNull float progress);

    default void onMessage(LogMessage logMessage){
    }
}
