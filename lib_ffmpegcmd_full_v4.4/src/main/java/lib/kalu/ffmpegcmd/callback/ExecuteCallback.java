package lib.kalu.ffmpegcmd.callback;

import lib.kalu.ffmpegcmd.entity.LogMessage;

public interface ExecuteCallback {


    void onStart(Long executionId);

//    /**
//     * <p>Called when an asynchronous FFmpeg execution is completed.
//     *
//     * @param executionId id of the execution that completed
//     * @param returnCode  return code of the execution completed, 0 on successful completion, 255
//     *                    on user cancel, other non-zero codes on error
//     */
//    void apply(long executionId, int returnCode);

    /**
     * 如果外部传递了当前操作视频的时长，那么返回的是百分比进度，反之返回的是操作视频对应的时长进度
     *
     * @param v
     */
    void onProgress(float v);

    void onSuccess(long executionId);

    void onFailure(long executionId, String error);

    void onCancel(long executionId);

    void onFFmpegExecutionMessage(LogMessage logMessage);



}
