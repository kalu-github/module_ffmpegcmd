# ffmpegcmd
-dontwarn lib.kalu.ffmpegcmd.**

# listener
-keep class lib.kalu.ffmpegcmd.OnFFmpegChangeListener{*;}

# native
-keep class lib.kalu.ffmpegcmd.FFmpeg
-keepclasseswithmembernames class lib.kalu.ffmpegcmd.FFmpeg {
    public static native <methods>;
#    public static native int cancel();
#    public static native int execute(java.lang.String, long, ***);
#    public static native java.lang.String getVersion();
#    public static native java.lang.String getInformation();
#    public static native java.lang.String getDetails();
#    public static native long getDuration(java.lang.String);
#    public static native int setLogger(boolean);
}

# util
-keep class lib.kalu.ffmpegcmd.FFmpegUtil
-keepclassmembers public final class lib.kalu.ffmpegcmd.FFmpegUtil {
    public static boolean clipAudio(java.lang.String, java.lang.String);
    public static boolean clipAudio(java.lang.String, java.lang.String, ***);
    public static boolean createNullAudio(float, java.lang.String);
    public static boolean createNullAudio(float, java.lang.String, ***);
    public static boolean mixAudio(java.lang.String, java.lang.String, ***);
    public static boolean mixAudio(java.lang.String, java.lang.String, ***, ***);
    public static boolean pcmToMp3(java.lang.String, java.lang.String);
    public static boolean pcmToMp3(java.lang.String, java.lang.String, ***);
    public static boolean compressVideo(java.lang.String, java.lang.String);
    public static boolean compressVideo(java.lang.String, java.lang.String, ***);
    public static int executeCmd(***);
    public static int executeCmd(***, ***);
    public static int executeCmd(java.lang.String[]);
    public static int executeCmd(java.lang.String[], ***);
    public static int executeCmd(java.lang.String);
    public static int executeCmd(java.lang.String, ***);
    public static int executeCmd(java.lang.String, long, ***);
    public static int cancle();
    public static java.lang.String getDetail();
    public static java.lang.String getInformation();
    public static java.lang.String getVersion();
    public static int setLogger(boolean);
    public static long getDuration(java.lang.String);
}