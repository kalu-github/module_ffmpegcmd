package lib.kalu.ffmpegcmd;

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
