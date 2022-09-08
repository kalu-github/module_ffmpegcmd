package lib.kalu.ffmpegcmd;

import androidx.annotation.Keep;

import lib.kalu.ffmpegcmd.entity.MediaInformation;
import lib.kalu.ffmpegcmd.entity.MediaInformationParser;

@Keep
public class FFprobe {

    private FFprobe() {
    }

    public static int execute(String command) {
        return execute(FFmpeg.format(command));
    }

    public static int execute(final String[] arguments) {
        int code = FFcmd.ffprobeExecute(arguments);
        FFcmd.setLastReturnCode(code);
        return code;
    }

    public static MediaInformation getMediaInfo(String path) {
        String[] strings = new String[]{"-i", path};
        int v = execute(strings);
        if (v == 0) {
            return MediaInformationParser.from(FFcmd.getLastCommandOutput());
        } else {
            FFutil.e(FFcmd.getLastCommandOutput());
            return null;
        }
    }

    public static MediaInformation getMediaInformation(String path) {
        return getMediaInformationFromCommandArguments(new String[]{"-v", "error", "-hide_banner", "-print_format", "json", "-show_format", "-show_streams", "-i", path});
    }

    /**
     * <p>Returns media information for the given command.
     *
     * <p>This method does not support executing multiple concurrent operations. If you execute
     * multiple operations (execute or getMediaInformation) at the same time, the response of this
     * method is not predictable.
     *
     * @param command command to execute
     * @return media information
     * @since 4.3.3
     */
    public static MediaInformation getMediaInformationFromCommand(final String command) {
        return getMediaInformationFromCommandArguments(FFmpeg.format(command));
    }

    /**
     * <p>Returns media information for given file.
     *
     * <p>This method does not support executing multiple concurrent operations. If you execute
     * multiple operations (execute or getMediaInformation) at the same time, the response of this
     * method is not predictable.
     *
     * @param path    path or uri of media file
     * @param timeout complete timeout
     * @return media information
     * @since 3.0
     * @deprecated this method is deprecated since v4.3.1. You can still use this method but
     * <code>timeout</code> parameter is not effective anymore.
     */
    public static MediaInformation getMediaInformation(final String path, final Long timeout) {
        return getMediaInformation(path);
    }

    private static MediaInformation getMediaInformationFromCommandArguments(final String[] arguments) {
        final int rc = execute(arguments);

        if (rc == 0) {
            return MediaInformationParser.from(FFcmd.getLastCommandOutput());
        } else {
            FFutil.e(FFcmd.getLastCommandOutput());
            return null;
        }
    }

}
