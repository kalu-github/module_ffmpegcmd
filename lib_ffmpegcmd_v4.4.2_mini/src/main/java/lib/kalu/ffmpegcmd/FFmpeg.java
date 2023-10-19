package lib.kalu.ffmpegcmd;

import java.util.List;

public final class FFmpeg {

    static {
        System.loadLibrary("ffmpegcmd");
        System.loadLibrary("ffmpegcmd-jni");
    }

    /*****************/

    public static native String getVersion();

    public static native String getInformation();

    public static native String getDetails();

    /*****************/

    public static native int setLogger(boolean enable);

    public static native long getDuration(String mediaPath);

    public static native int cancel();

    private static native int execute(String command, long totalTime, OnFFmpegChangeListener listener);

    /*****************/

    public static String getABI() {
        return android.os.Build.CPU_ABI;
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
        cancel();
        return execute(command, totalTime, listener);
    }
}
