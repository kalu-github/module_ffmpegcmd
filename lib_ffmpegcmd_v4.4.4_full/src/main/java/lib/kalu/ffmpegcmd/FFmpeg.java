package lib.kalu.ffmpegcmd;

import java.util.List;

public final class FFmpeg {

    static {
        System.loadLibrary("ffmpegcmd");
    }

    private static native String getVersion();

    private static native String getInformation();

    private static native String getDetails();

    private static native int setLogger(boolean enable);

    private static native long getDuration(String mediaPath);

    private static native int cancel();

    private static native int execute(String command, long totalTime, OnFFmpegChangeListener listener);

    /*****************/

    static int executeCmd(List<String> list) {
        return executeCmd(list, null);
    }

    static int executeCmd(List<String> list, OnFFmpegChangeListener listener) {
        int size = list.size();
        String[] strings = list.toArray(new String[size]);
        return executeCmd(strings, listener);
    }

    static int executeCmd(String[] strings) {
        return executeCmd(strings, null);
    }

    static int executeCmd(String[] strings, OnFFmpegChangeListener listener) {
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

    static int executeCmd(String command) {
        return executeCmd(command, -1, null);
    }

    static int executeCmd(String command, OnFFmpegChangeListener listener) {
        return executeCmd(command, -1, listener);
    }

    static int executeCmd(String command, long totalTime, OnFFmpegChangeListener listener) {
        return execute(command, totalTime, listener);
    }

    static int executeCancle() {
        return cancel();
    }

    static long executeGetDuration(String fromPath) {
        return getDuration(fromPath);
    }

    static int executeSetLogger(boolean enable) {
        return setLogger(enable);
    }

    static String executeGetVersion() {
        return getVersion();
    }

    static String executeGetInformation() {
        return getInformation();
    }

    static String executeGetDetails() {
        return getDetails();
    }
}
