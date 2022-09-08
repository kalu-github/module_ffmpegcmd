package lib.kalu.ffmpegcmd;

import androidx.annotation.Keep;

import lib.kalu.ffmpegcmd.bean.MediaInformation;
import lib.kalu.ffmpegcmd.bean.MediaInformationParser;

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

    public static String getMediaOutputCommand(String path) {
        String[] strings = new String[]{"-v", "error", "-hide_banner", "-print_format", "json", "-show_format", "-show_streams", "-i", path};
        return getOutputCommand(strings);
    }

    public static String getMediaFramatOutputCommand(String path) {
        String[] strings = new String[]{"-v", "error", "-hide_banner", "-print_format", "json", "-show_format", "-show_streams", "-i", path};
        String output = getOutputCommand(strings);
        return FFutil.getFormatOutputCommand(output);
    }

    public static String getOutputCommand(String[] strings) {
        int v = execute(strings);
        if (v == 0) {
            String output = FFcmd.getLastCommandOutput();
            String s = FFutil.checkOutputCommand(output);
            return s;
        } else {
            return null;
        }
    }
}
