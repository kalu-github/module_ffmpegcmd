package lib.kalu.ffmpegcmd;

import android.content.Context;

import androidx.annotation.Keep;

import lib.kalu.ffmpegcmd.entity.FFmpegExecution;
import lib.kalu.ffmpegcmd.entity.Level;
import lib.kalu.ffmpegcmd.callback.LogCallback;
import lib.kalu.ffmpegcmd.callback.StatisticsCallback;
import lib.kalu.ffmpegcmd.entity.LogMessage;
import lib.kalu.ffmpegcmd.entity.Packages;
import lib.kalu.ffmpegcmd.entity.Signal;
import lib.kalu.ffmpegcmd.entity.Statistics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Keep
class FFcmd {

    private static int lastReturnCode = 0;

    static String MOBILE_FFMPEG_PIPE_PREFIX = "mf_pipe_";

    private static LogCallback logCallbackFunction;

    private static StatisticsCallback statisticsCallbackFunction;

    private static Statistics lastReceivedStatistics;

    private static int lastCreatedPipeIndex;

    private static List<FFmpegExecution> executions;

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("swscale");
        System.loadLibrary("swresample");
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("avfilter");
        System.loadLibrary("avcmd");

        lastReceivedStatistics = new Statistics();

        enableRedirection();

        lastCreatedPipeIndex = 0;

        executions = Collections.synchronizedList(new ArrayList<FFmpegExecution>());
    }

    /**
     * Default constructor hidden.
     */
    private FFcmd() {
    }

    /**
     * <p>Enables log and statistics redirection.
     * <p>When redirection is not enabled FFmpeg/FFprobe logs are printed to stderr. By enabling
     * redirection, they are routed to Logcat and can be routed further to a callback function.
     * <p>Statistics redirection behaviour is similar. Statistics are not printed at all if
     * redirection is not enabled. If it is enabled then it is possible to define a statistics
     * callback function but if you don't, they are not printed anywhere and only saved as
     * <code>lastReceivedStatistics</code> data which can be polled with
     * {@link #getLastReceivedStatistics()}.
     * <p>Note that redirection is enabled by default. If you do not want to use its functionality
     * please use {@link #disableRedirection()} to disable it.
     */
    static void enableRedirection() {
        enableNativeRedirection();
    }

    static void disableRedirection() {
        disableNativeRedirection();
    }

    static Level getLogLevel() {
        int v = getNativeLogLevel();
        return Level.from(v);
    }

    static void setLogLevel(Level level) {
        if (level != null) {
            setNativeLogLevel(level.getValue());
        }
    }

    /**
     * <p>Sets a callback function to redirect FFmpeg/FFprobe logs.
     *
     * @param newLogCallback new log callback function or NULL to disable a previously defined callback
     * @
     */
    static void enableLogCallback(LogCallback newLogCallback) {
        logCallbackFunction = newLogCallback;
    }

    /**
     * <p>Sets a callback function to redirect FFmpeg statistics.
     *
     * @param statisticsCallback new statistics callback function or NULL to disable a previously defined callback
     */
    static void enableStatisticsCallback(StatisticsCallback statisticsCallback) {
        statisticsCallbackFunction = statisticsCallback;
    }

    /**
     * <p>Log redirection method called by JNI/native part.
     *
     * @param executionId id of the execution that generated this log, 0 by default
     * @param levelValue  log level as defined in {@link Level}
     * @param logMessage  redirected log message
     */
    private static void log(long executionId, int levelValue, byte[] logMessage) {
        Level level = Level.from(levelValue);
        String text = new String(logMessage);

        // AV_LOG_STDERR logs are always redirected
        int nativeLogLevel = getNativeLogLevel();
        if ((nativeLogLevel == Level.AV_LOG_QUIET.getValue() && levelValue != Level.AV_LOG_STDERR.getValue()) || levelValue > nativeLogLevel) {
            // LOG NEITHER PRINTED NOR FORWARDED
            return;
        }

        if (logCallbackFunction != null) {
            try {
                logCallbackFunction.apply(new LogMessage(executionId, level, text));
            } catch (Exception e) {
                FFutil.e("Exception thrown inside LogCallback block", e);
            }
        } else {
            FFutil.e(text);
        }
    }

    /**
     * <p>Statistics redirection method called by JNI/native part.
     *
     * @param executionId      id of the execution that generated this statistics, 0 by default
     * @param videoFrameNumber last processed frame number for videos
     * @param videoFps         frames processed per second for videos
     * @param videoQuality     quality of the video stream
     * @param size             size in bytes
     * @param time             processed duration in milliseconds
     * @param bitrate          output bit rate in kbits/s
     * @param speed            processing speed = processed duration / operation duration
     */
    private static void statistics(long executionId, int videoFrameNumber,
                                   float videoFps, float videoQuality, long size,
                                   int time, double bitrate, double speed) {
        Statistics newStatistics = new Statistics(executionId, videoFrameNumber, videoFps, videoQuality, size, time, bitrate, speed);
        lastReceivedStatistics.update(newStatistics);

        if (statisticsCallbackFunction != null) {
            try {
                statisticsCallbackFunction.apply(lastReceivedStatistics);
            } catch (Exception e) {
                FFutil.e("Exception thrown inside StatisticsCallback block", e);
            }
        }
    }

    /**
     * <p>Returns the last received statistics data.
     *
     * @return last received statistics data
     */
    static Statistics getLastReceivedStatistics() {
        return lastReceivedStatistics;
    }

    /**
     * <p>Resets last received statistics. It is recommended to call it before starting a new execution.
     */
    static void resetStatistics() {
        lastReceivedStatistics = new Statistics();
    }

    /**
     * <p>Sets and overrides <code>fontconfig</code> configuration directory.
     *
     * @param path directory which contains fontconfig configuration (fonts.conf)
     * @return zero on success, non-zero on error
     */
    static int setFontconfigConfigurationPath(String path) {
        return setNativeEnvironmentVariable("FONTCONFIG_PATH", path);
    }

    /**
     * <p>Registers fonts inside the given path, so they are available to use in FFmpeg filters.
     *
     * <p>Note that you need to build <code>MobileFFmpeg</code> with <code>fontconfig</code>
     * enabled or use a prebuilt package with <code>fontconfig</code> inside to use this feature.
     *
     * @param context           application context to access application data
     * @param fontDirectoryPath directory which contains fonts (.ttf and .otf files)
     * @param fontNameMapping   custom font name mappings, useful to access your fonts with more friendly names
     */
    static void setFontDirectory(Context context, String fontDirectoryPath, Map<String, String> fontNameMapping) {
        File cacheDir = context.getCacheDir();
        int validFontNameMappingCount = 0;

        File tempConfigurationDirectory = new File(cacheDir, ".mobileffmpeg");
        if (!tempConfigurationDirectory.exists()) {
            boolean tempFontConfDirectoryCreated = tempConfigurationDirectory.mkdirs();
            FFutil.e(String.format("Created temporary font conf directory: %s.", tempFontConfDirectoryCreated));
        }

        File fontConfiguration = new File(tempConfigurationDirectory, "fonts.conf");
        if (fontConfiguration.exists()) {
            boolean fontConfigurationDeleted = fontConfiguration.delete();
            FFutil.e(String.format("Deleted old temporary font configuration: %s.", fontConfigurationDeleted));
        }

        /* PROCESS MAPPINGS FIRST */
        StringBuilder fontNameMappingBlock = new StringBuilder("");
        if (fontNameMapping != null && (fontNameMapping.size() > 0)) {
            fontNameMapping.entrySet();
            for (Map.Entry<String, String> mapping : fontNameMapping.entrySet()) {
                String fontName = mapping.getKey();
                String mappedFontName = mapping.getValue();

                if ((fontName != null) && (mappedFontName != null) && (fontName.trim().length() > 0) && (mappedFontName.trim().length() > 0)) {
                    fontNameMappingBlock.append("        <match target=\"pattern\">\n");
                    fontNameMappingBlock.append("                <test qual=\"any\" name=\"family\">\n");
                    fontNameMappingBlock.append(String.format("                        <string>%s</string>\n", fontName));
                    fontNameMappingBlock.append("                </test>\n");
                    fontNameMappingBlock.append("                <edit name=\"family\" mode=\"assign\" binding=\"same\">\n");
                    fontNameMappingBlock.append(String.format("                        <string>%s</string>\n", mappedFontName));
                    fontNameMappingBlock.append("                </edit>\n");
                    fontNameMappingBlock.append("        </match>\n");

                    validFontNameMappingCount++;
                }
            }
        }

        String fontConfig = "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE fontconfig SYSTEM \"fonts.dtd\">\n" +
                "<fontconfig>\n" +
                "    <dir>.</dir>\n" +
                "    <dir>" + fontDirectoryPath + "</dir>\n" +
                fontNameMappingBlock +
                "</fontconfig>";

        AtomicReference<FileOutputStream> reference = new AtomicReference<>();
        try {
            FileOutputStream outputStream = new FileOutputStream(fontConfiguration);
            reference.set(outputStream);

            outputStream.write(fontConfig.getBytes());
            outputStream.flush();

            FFutil.e(String.format("Saved new temporary font configuration with %d font name mappings.", validFontNameMappingCount));

            setFontconfigConfigurationPath(tempConfigurationDirectory.getAbsolutePath());

            FFutil.e(String.format("Font directory %s registered successfully.", fontDirectoryPath));

        } catch (IOException e) {
            FFutil.e(String.format("Failed to set font directory: %s.", fontDirectoryPath), e);
        } finally {
            if (reference.get() != null) {
                try {
                    reference.get().close();
                } catch (IOException e) {
                    // DO NOT PRINT THIS ERROR
                }
            }
        }
    }

    /**
     * <p>Returns package name.
     *
     * @return guessed package name according to supported external libraries
     * @since 3.0
     */
    static String getPackageName() {
        return Packages.getPackageName();
    }

    /**
     * <p>Returns supported external libraries.
     *
     * @return list of supported external libraries
     * @since 3.0
     */
    static List<String> getExternalLibraries() {
        return Packages.getExternalLibraries();
    }

    /**
     * <p>Creates a new named pipe to use in <code>FFmpeg</code> operations.
     *
     * <p>Please note that creator is responsible of closing created pipes.
     *
     * @param context application context
     * @return the full path of named pipe
     */
    static String registerNewFFmpegPipe(Context context) {

        // PIPES ARE CREATED UNDER THE CACHE DIRECTORY
        File cacheDir = context.getCacheDir();

        String newFFmpegPipePath = cacheDir + File.separator + MOBILE_FFMPEG_PIPE_PREFIX + (++lastCreatedPipeIndex);

        // FIRST CLOSE OLD PIPES WITH THE SAME NAME
        closeFFmpegPipe(newFFmpegPipePath);

        int rc = registerNewNativeFFmpegPipe(newFFmpegPipePath);
        if (rc == 0) {
            return newFFmpegPipePath;
        } else {
            FFutil.e(String.format("Failed to register new FFmpeg pipe %s. Operation failed with rc=%d.", newFFmpegPipePath, rc));
            return null;
        }
    }

    /**
     * <p>Closes a previously created <code>FFmpeg</code> pipe.
     *
     * @param ffmpegPipePath full path of ffmpeg pipe
     */
    static void closeFFmpegPipe(String ffmpegPipePath) {
        File file = new File(ffmpegPipePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * <p>Returns FFmpeg version bundled within the library.
     *
     * @return FFmpeg version
     */
    static String getFFmpegVersion() {
        return getNativeFFmpegVersion();
    }

    /**
     * <p>Returns MobileFFmpeg library version.
     *
     * @return MobileFFmpeg version
     */
    static String getVersion() {
        return getNativeVersion();
    }

    /**
     * <p>Returns MobileFFmpeg library build date.
     *
     * @return MobileFFmpeg library build date
     */
    static String getBuildDate() {
        return getNativeBuildDate();
    }

    /**
     * <p>Returns return code of last executed command.
     *
     * @return return code of last executed command
     * @since 3.0
     */
    static int getLastReturnCode() {
        return lastReturnCode;
    }

    /**
     * <p>Sets an environment variable.
     *
     * @param variableName  environment variable name
     * @param variableValue environment variable value
     * @return zero on success, non-zero on error
     */
    static int setEnvironmentVariable(String variableName, String variableValue) {
        return setNativeEnvironmentVariable(variableName, variableValue);
    }

    /**
     * <p>Registers a new ignored signal. Ignored signals are not handled by the library.
     *
     * @param signal signal number to ignore
     */
    static void ignoreSignal(Signal signal) {
        ignoreNativeSignal(signal.getValue());
    }

    /**
     * Updates return code value for the last executed command.
     *
     * @param newLastReturnCode new last return code value
     */
    static void setLastReturnCode(int newLastReturnCode) {
        lastReturnCode = newLastReturnCode;
    }

    /**
     * <p>Lists ongoing FFmpeg executions.
     *
     * @return list of ongoing FFmpeg executions
     */
    static List<FFmpegExecution> listFFmpegExecutions() {
        return new ArrayList<>(executions);
    }

    /**
     * <p>Enables native redirection. Necessary for log and statistics callback functions.
     */
    private static native void enableNativeRedirection();

    /**
     * <p>Disables native redirection
     */
    private static native void disableNativeRedirection();

    /**
     * Sets native log level
     *
     * @param level log level
     */
    private static native void setNativeLogLevel(int level);

    /**
     * Returns native log level.
     *
     * @return log level
     */
    private static native int getNativeLogLevel();

    /**
     * <p>Returns FFmpeg version bundled within the library natively.
     *
     * @return FFmpeg version
     */
    private native static String getNativeFFmpegVersion();

    /**
     * <p>Returns MobileFFmpeg library version natively.
     *
     * @return MobileFFmpeg version
     */
    private native static String getNativeVersion();


    /**
     * <p>Creates natively a new named pipe to use in <code>FFmpeg</code> operations.
     *
     * <p>Please note that creator is responsible of closing created pipes.
     *
     * @param ffmpegPipePath full path of ffmpeg pipe
     * @return zero on successful creation, non-zero on error
     */
    private native static int registerNewNativeFFmpegPipe(String ffmpegPipePath);

    /**
     * <p>Returns MobileFFmpeg library build date natively.
     *
     * @return MobileFFmpeg library build date
     */
    private native static String getNativeBuildDate();

    /**
     * <p>Sets an environment variable natively.
     *
     * @param variableName  environment variable name
     * @param variableValue environment variable value
     * @return zero on success, non-zero on error
     */
    private native static int setNativeEnvironmentVariable(String variableName, String variableValue);

    /**
     * <p>Registers a new ignored signal natively. Ignored signals are not handled by the library.
     *
     * @param signum signal number
     */
    private native static void ignoreNativeSignal(int signum);

    /******/

    static int ffmpegExecute(long executionId, String[] arguments) {
        FFmpegExecution currentFFmpegExecution = new FFmpegExecution(executionId, arguments);
        executions.add(currentFFmpegExecution);

        try {
            int lastReturnCode = nativeFFmpegExecute(executionId, arguments);
            FFcmd.setLastReturnCode(lastReturnCode);

            return lastReturnCode;
        } finally {
            executions.remove(currentFFmpegExecution);
        }
    }

    private native static int nativeFFmpegExecute(long executionId, String[] arguments);

    /******/

    static void ffmpegCancel(long executionId) {
        nativeFFmpegCancel(executionId);
    }

    private native static void nativeFFmpegCancel(long executionId);

    /******/

    static int ffprobeExecute(String[] arguments) {
        return nativeFFprobeExecute(arguments);
    }

    private native static int nativeFFprobeExecute(String[] arguments);

    /******/

    static String getLastCommandOutput() {
        String output = getNativeLastCommandOutput();
        if (null != output && output.length() > 0) {
            output = output.replace('\r', '\n');
        }
        return output;
    }

    private native static String getNativeLastCommandOutput();
}
