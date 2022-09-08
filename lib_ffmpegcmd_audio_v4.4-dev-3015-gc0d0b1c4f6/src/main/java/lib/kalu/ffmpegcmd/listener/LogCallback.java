package lib.kalu.ffmpegcmd.listener;

import lib.kalu.ffmpegcmd.bean.LogMessage;

@FunctionalInterface
public interface LogCallback {

    void apply(final LogMessage message);
}
