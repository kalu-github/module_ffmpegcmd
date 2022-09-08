package lib.kalu.ffmpegcmd.bean;

public class VideoInfo {

    public String duration;
    public String keyTitle;
    public String mimetype;
    public String bitrate;
    public String fps;
    public String videoWidth;
    public String videoHeight;

    public VideoInfo(String duration, String keyTitle, String mimetype, String bitrate, String fps, String videoWidth, String videoHeight) {
        this.duration = duration;
        this.keyTitle = keyTitle;
        this.mimetype = mimetype;
        this.bitrate = bitrate;
        this.fps = fps;
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
    }

    public VideoInfo() {
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getKeyTitle() {
        return keyTitle;
    }

    public void setKeyTitle(String keyTitle) {
        this.keyTitle = keyTitle;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public String getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(String videoWidth) {
        this.videoWidth = videoWidth;
    }

    public String getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(String videoHeight) {
        this.videoHeight = videoHeight;
    }
}