package lib.kalu.ffmpegcmd.callback;

import lib.kalu.ffmpegcmd.entity.MediaInformation;

@FunctionalInterface
public interface GetMediaInformationCallback {

    void apply(MediaInformation mediaInformation);
}
