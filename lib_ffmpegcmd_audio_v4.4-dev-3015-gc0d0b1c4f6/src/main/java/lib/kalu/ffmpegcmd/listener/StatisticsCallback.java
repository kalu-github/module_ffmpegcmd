package lib.kalu.ffmpegcmd.listener;

import lib.kalu.ffmpegcmd.bean.Statistics;

@FunctionalInterface
public interface StatisticsCallback {

    void apply(final Statistics statistics);

}
