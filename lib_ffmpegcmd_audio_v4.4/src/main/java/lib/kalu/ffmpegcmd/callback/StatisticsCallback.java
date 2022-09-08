package lib.kalu.ffmpegcmd.callback;

import lib.kalu.ffmpegcmd.entity.Statistics;

@FunctionalInterface
public interface StatisticsCallback {

    void apply(final Statistics statistics);

}
