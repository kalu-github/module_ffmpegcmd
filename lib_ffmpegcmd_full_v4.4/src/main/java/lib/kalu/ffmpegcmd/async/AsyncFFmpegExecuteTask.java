package lib.kalu.ffmpegcmd.async;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Keep;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.kalu.ffmpegcmd.callback.ExecuteCallback;
import lib.kalu.ffmpegcmd.callback.LogCallback;
import lib.kalu.ffmpegcmd.cmd.Cmd;
import lib.kalu.ffmpegcmd.entity.LogMessage;
import lib.kalu.ffmpegcmd.ffmpeg.FFmpeg;
import lib.kalu.ffmpegcmd.util.FFmpegLogUtil;
import lib.kalu.ffmpegcmd.util.VideoUitls;

@Keep
public class AsyncFFmpegExecuteTask extends AsyncTask<Void, Integer, Integer> {

    private final String[] arguments;
    private static ExecuteCallback sExecuteCallback;
    private final Long executionId;
    private AsyncLogCallback mAsyncLogCallback;
    protected static Handler mHandler = null;

    // 时长
    private static long mDuration = -1L;

    public AsyncFFmpegExecuteTask(final String command, final ExecuteCallback executeCallback) {
        this(FFmpeg.DEFAULT_EXECUTION_ID, command, executeCallback);
    }

    public AsyncFFmpegExecuteTask(final long executionId, final String command, final ExecuteCallback executeCallback) {
        this(executionId, FFmpeg.parseArguments(command), executeCallback);
    }

    public AsyncFFmpegExecuteTask(final String[] arguments, final ExecuteCallback executeCallback) {
        this(FFmpeg.DEFAULT_EXECUTION_ID, arguments, executeCallback);
    }

    public AsyncFFmpegExecuteTask(final long executionId, final String[] arguments, final ExecuteCallback executeCallback) {
        this.executionId = executionId;
        this.arguments = arguments;
        onDestory();
        sExecuteCallback = executeCallback;
        try {
            int index;
            if (arguments[0].equalsIgnoreCase("ffmpeg")) {
                index = 3;
            } else {
                index = 2;
            }
            long duration = VideoUitls.getDuration(arguments[index]);
            if (duration <= 0) {
                duration = -1L;
            }
            mDuration = duration;
        } catch (Exception e) {
            mDuration = -1L;
        }
        FFmpegLogUtil.logE("AsyncFFmpegExecuteTask => mDuration = " + mDuration);
        mHandler = new Handler(Looper.getMainLooper());
        mAsyncLogCallback = new AsyncLogCallback();
        enableLogCallback(mAsyncLogCallback);
    }

    private void onDestory() {
        mDuration = -1L;
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
    @Keep
    public final static void jniProgress(long progress) {
        if (null == sExecuteCallback)
            return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                float value;
                if (mDuration == -1L) {
                    value = progress;
                } else {
                    value = progress * 100 / mDuration;
                    if (value > 100) {
                        value = 100F;
                    }
                }
                sExecuteCallback.onProgress(mDuration, progress, value);
            }
        });
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
                            sExecuteCallback.onMessage(message);
                        }
                    });
            }
        }
    }
}
