package lib.kalu.ffmpegcmd;

import android.os.AsyncTask;

import androidx.annotation.Keep;

import lib.kalu.ffmpegcmd.callback.GetMediaInformationCallback;
import lib.kalu.ffmpegcmd.entity.MediaInformation;
import lib.kalu.ffmpegcmd.FFprobe;

@Keep
public class AsyncGetMediaInformationTask extends AsyncTask<String, MediaInformation, MediaInformation> {
    private final String path;
    private final GetMediaInformationCallback getMediaInformationCallback;

    public AsyncGetMediaInformationTask(final String path, final GetMediaInformationCallback getMediaInformationCallback) {
        this.path = path;
        this.getMediaInformationCallback = getMediaInformationCallback;
    }

    @Override
    protected MediaInformation doInBackground(final String... arguments) {
        return FFprobe.getMediaInformation(path);
    }

    @Override
    protected void onPostExecute(final MediaInformation mediaInformation) {
        if (getMediaInformationCallback != null) {
            getMediaInformationCallback.apply(mediaInformation);
        }
    }

}
