package lib.kalu.ffmpegcmd;

import android.os.AsyncTask;

import androidx.annotation.Keep;

import lib.kalu.ffmpegcmd.FFmpeg;
import lib.kalu.ffmpegcmd.FFprobe;

@Keep
public class AsyncFFprobeExecuteTask extends AsyncTask<Void, Integer, Integer> {
    private final String[] arguments;
    private final lib.kalu.ffmpegcmd.callback.ExecuteCallback ExecuteCallback;

    public AsyncFFprobeExecuteTask(final String command, final lib.kalu.ffmpegcmd.callback.ExecuteCallback executeCallback) {
        this.arguments = FFmpeg.format(command);
        this.ExecuteCallback = executeCallback;
    }

    public AsyncFFprobeExecuteTask(final String[] arguments, final lib.kalu.ffmpegcmd.callback.ExecuteCallback executeCallback) {
        this.arguments = arguments;
        ExecuteCallback = executeCallback;
    }

    @Override
    protected Integer doInBackground(final Void... unused) {
        return FFprobe.execute(this.arguments);
    }

    @Override
    protected void onPostExecute(final Integer rc) {
//        if (ExecuteCallback != null) {
//            ExecuteCallback.apply(FFmpeg.DEFAULT_EXECUTION_ID, rc);
//        }

        if (ExecuteCallback != null) {
            if (rc == 0)
                ExecuteCallback.onSuccess(FFmpeg.DEFAULT_EXECUTION_ID);
            else if (rc == 255)
                ExecuteCallback.onCancel(FFmpeg.DEFAULT_EXECUTION_ID);
            else
                ExecuteCallback.onFailure(FFmpeg.DEFAULT_EXECUTION_ID,"");
        }
    }

}
