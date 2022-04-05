package lib.kalu.ffmpegcmd.callback;

import lib.kalu.ffmpegcmd.entity.LogMessage;

@FunctionalInterface
public interface LogCallback {

    void apply(final LogMessage message);
}
