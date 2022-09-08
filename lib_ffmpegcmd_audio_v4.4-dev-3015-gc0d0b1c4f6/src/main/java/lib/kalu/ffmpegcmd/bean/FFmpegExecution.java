package lib.kalu.ffmpegcmd.bean;

import lib.kalu.ffmpegcmd.FFmpeg;

import java.util.Date;

public class FFmpegExecution {
    private final Date startTime;
    private final long executionId;
    private final String command;

    public FFmpegExecution(final long executionId, final String[] arguments) {
        this.startTime = new Date();
        this.executionId = executionId;
        this.command = FFmpeg.argumentsToString(arguments);
    }

    public Date getStartTime() {
        return startTime;
    }

    public long getExecutionId() {
        return executionId;
    }

    public String getCommand() {
        return command;
    }

}
