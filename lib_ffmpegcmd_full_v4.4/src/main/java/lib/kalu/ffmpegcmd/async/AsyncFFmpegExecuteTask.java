package lib.kalu.ffmpegcmd.async;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.kalu.ffmpegcmd.callback.ExecuteCallback;
import lib.kalu.ffmpegcmd.callback.LogCallback;
import lib.kalu.ffmpegcmd.cmd.Cmd;
import lib.kalu.ffmpegcmd.entity.LogMessage;
import lib.kalu.ffmpegcmd.ffmpeg.FFmpeg;

public class AsyncFFmpegExecuteTask extends AsyncTask<Void, Integer, Integer> {
    private final String[] arguments;
    private static ExecuteCallback sExecuteCallback;
    private final Long executionId;
    private static Long mVideoduration = -11L;
    private AsyncLogCallback mAsyncLogCallback;
    protected static Handler mHandler = null;


    public AsyncFFmpegExecuteTask(final String command, final ExecuteCallback executeCallback) {
        this(FFmpeg.parseArguments(command), executeCallback);
    }

    public AsyncFFmpegExecuteTask(final String[] arguments, final ExecuteCallback executeCallback) {
        this(FFmpeg.DEFAULT_EXECUTION_ID, arguments, -1, executeCallback);
    }

    public AsyncFFmpegExecuteTask(final String[] arguments, long duration, final ExecuteCallback executeCallback) {
        this(FFmpeg.DEFAULT_EXECUTION_ID, arguments, duration, executeCallback);
    }

    public AsyncFFmpegExecuteTask(final long executionId, final String command, long videoduration, final ExecuteCallback executeCallback) {
        this(executionId, FFmpeg.parseArguments(command), videoduration, executeCallback);
    }

    public AsyncFFmpegExecuteTask(final long executionId, final String[] arguments, long videoduration, final ExecuteCallback executeCallback) {
        this.executionId = executionId;
        this.arguments = arguments;
        onDestory();
        sExecuteCallback = executeCallback;
        this.mVideoduration = videoduration;
        mHandler = new Handler(Looper.getMainLooper());
        mAsyncLogCallback = new AsyncLogCallback();
        enableLogCallback(mAsyncLogCallback);
    }

    private void onDestory() {
        mVideoduration = 0L;
        if (sExecuteCallback != null) {
            sExecuteCallback = null;
        }
        if (mAsyncLogCallback != null) {
            enableLogCallback(null);
            mAsyncLogCallback = null;
        }

        if (mHandler != null) {
            mHandler = null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (sExecuteCallback != null) {
            sExecuteCallback.onStart(executionId);
        }

    }

    private void enableLogCallback(AsyncLogCallback asyncLogCallback) {
        Cmd.enableLogCallback(asyncLogCallback);
    }

    /**
     * 子线程中执行
     *
     * @param unused
     * @return
     */
    @Override
    protected Integer doInBackground(final Void... unused) {
        return Cmd.ffmpegExecute(executionId, this.arguments);
    }


    /**
     * 主线程中执行
     *
     * @param rc
     */
    @Override
    protected void onPostExecute(final Integer rc) {
        synchronized (this) {
            if (sExecuteCallback != null) {
                if (rc == 0)
                    sExecuteCallback.onSuccess(executionId);
                else if (rc == 255)
                    sExecuteCallback.onCancel(executionId);
            }
        }
    }


    /**
     * C++ 调用
     *
     * @param progress
     */
    public static void progress(final float progress) {
        if (mVideoduration != -1 && sExecuteCallback != null) {
            final float v = progress / mVideoduration * 100;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (judgeContainsStr(v)) {
                        return;
                    }
                    if (v > 0f)
                        sExecuteCallback.onProgress(v);
                }
            });
        } else {
            if (sExecuteCallback != null)
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sExecuteCallback.onProgress(progress);
                    }
                });
        }
    }

    /**
     * 判断是否存在字母
     *
     * @param num
     */
    public static boolean judgeContainsStr(String num) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(num);
        return m.matches();
    }

    public static boolean judgeContainsStr(float num_f) {
        boolean contains;
        String num = String.valueOf(num_f);
        try {
            contains = judgeContainsStr(num);
        } catch (Exception e) {
            contains = false;
        }
        return contains;
    }

    /**
     * 执行的 LOG message
     */
    protected class AsyncLogCallback implements LogCallback {
        @Override
        public void apply(final LogMessage message) {
            synchronized (this) {
                switch (message.getLevel()) {
                    case AV_LOG_FATAL:
                    case AV_LOG_ERROR:
                        if (sExecuteCallback != null && mHandler != null)
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sExecuteCallback.onFailure(executionId, message.getText());
                                }
                            });
                        break;
                    default:
                        break;
                }
                if (sExecuteCallback != null && mHandler != null)
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            sExecuteCallback.onFFmpegExecutionMessage(message);
                        }
                    });
            }
        }
    }
}
