package lib.kalu.ffmpegcmd;

import androidx.annotation.Keep;

@Keep
public interface OnFFmpegChangeListener {

    default void start() {
    }

    default void complete() {
    }

    default void progress(float progress) {

    }

    void fail();

    void success();
}
