#include <jni.h>
#include "include/api_cmd.h"
#include <android/log.h>
#include <libavutil/log.h>
#include <libavformat/avformat.h>
#include <libavfilter/avfilter.h>
#include <stdbool.h>
#include <libavcodec/bsf.h>

extern int hasRegistered;
typedef struct CallBackJNI {
    JNIEnv *env;
    jobject obj;
    jmethodID methodID;
} CallBackJNI;
const char *TAG = "ffmpeg-cmd-jni";

void android_log(void *ptr, int level, const char *fmt, va_list vl) {
    //自定义的日志
    if (level == 3) {
        __android_log_vprint(ANDROID_LOG_ERROR, TAG, fmt, vl);
    } else if (level == 2) {
        __android_log_vprint(ANDROID_LOG_DEBUG, TAG, fmt, vl);
    } else if (level == 1) {
        __android_log_vprint(ANDROID_LOG_VERBOSE, TAG, fmt, vl);
    } else {
        if (level <= 16) {//ffmpeg 来的日志
            __android_log_vprint(ANDROID_LOG_ERROR, TAG, fmt, vl);
        } else if (level <= 24) {
            __android_log_vprint(ANDROID_LOG_WARN, TAG, fmt, vl);
        } else {
//            __android_log_vprint(ANDROID_LOG_VERBOSE, TAG, fmt, vl);
        }
    }
}

void callProgressJNI(int64_t handle, int what, float progress) {
    if (handle != 0) {
        av_log(NULL, AV_LOG_ERROR, "callProgressJNI => command = %f", progress);
        struct CallBackJNI *onActionListener = (struct CallBackInfo *) (handle);
        JNIEnv *env = onActionListener->env;
        (*env)->CallVoidMethod(env, onActionListener->obj, onActionListener->methodID, progress);
    }
}

JNIEXPORT jint JNICALL
Java_lib_kalu_ffmpegcmd_FFmpeg_setLogger(JNIEnv *env, jclass clazz, jboolean showLog) {
    if (showLog) {
        av_log_set_callback(android_log);
    } else {
        av_log_set_callback(NULL);
    }
    return 0;
}

JNIEXPORT jlong JNICALL
Java_lib_kalu_ffmpegcmd_FFmpeg_getDuration(JNIEnv *env, jclass clazz, jstring media_path) {
    if (NULL == media_path) {
        return -1;
    }
    if (!hasRegistered) {
        avformat_network_init();
        hasRegistered = true;
    }
    const char *mediaPath = (*env)->GetStringUTFChars(env, media_path, 0);
    AVFormatContext *in_fmt_ctx = NULL;
    int ret = 0;
    if ((ret = avformat_open_input(&in_fmt_ctx, mediaPath, NULL, NULL)) < 0) {
        av_log(NULL, AV_LOG_ERROR, "Cannot open input file mediaPath=%s", mediaPath);
        return ret;
    }
    if ((ret = avformat_find_stream_info(in_fmt_ctx, NULL)) < 0) {
        av_log(NULL, AV_LOG_ERROR, "Cannot find stream information\n");
        return ret;
    }
    int64_t videoDuration = 0;
    for (int i = 0; i < in_fmt_ctx->nb_streams; i++) {
        AVStream *stream;
        stream = in_fmt_ctx->streams[i];
        int64_t temp = stream->duration * 1000 * stream->time_base.num /
                       stream->time_base.den;
        if (temp > videoDuration)
            videoDuration = temp;
    }
    if (NULL != in_fmt_ctx)
        avformat_close_input(&in_fmt_ctx);

    (*env)->ReleaseStringUTFChars(env, media_path, mediaPath);
    return videoDuration;
}

JNIEXPORT jint JNICALL
Java_lib_kalu_ffmpegcmd_FFmpeg_cancel(JNIEnv *env, jclass clazz) {
    return cancelCommand();
}

JNIEXPORT jint JNICALL
Java_lib_kalu_ffmpegcmd_FFmpeg_execute(JNIEnv *env, jclass type, jstring command_,
                                          jlong totalTime, jobject callback) {
    int ret = 0;
    const char *command = (*env)->GetStringUTFChars(env, command_, 0);
    if (NULL != callback) {
        jclass jniClass = (*env)->GetObjectClass(env, callback);
        jmethodID progressMID = (*env)->GetMethodID(env, jniClass, "progress", "(F)V");
        jmethodID failMID = (*env)->GetMethodID(env, jniClass, "fail", "()V");
        jmethodID successMID = (*env)->GetMethodID(env, jniClass, "success", "()V");
        jmethodID startMID = (*env)->GetMethodID(env, jniClass, "start", "()V");
        jmethodID completeMID = (*env)->GetMethodID(env, jniClass, "complete", "()V");

        CallBackJNI callBackJni;
        callBackJni.env = env;
        callBackJni.obj = callback;
        callBackJni.methodID = progressMID;

        // 1
        av_log(NULL, AV_LOG_ERROR, "executeJNI => start");
        (*env)->CallVoidMethod(env, callback, startMID);
        // 2
        ret = executeCommand((int64_t) (&callBackJni), command, callProgressJNI, totalTime);
        // 3
        av_log(NULL, AV_LOG_ERROR, "executeJNI => complete");
        (*env)->CallVoidMethod(env, callback, completeMID);
        if (ret < 0) {
            av_log(NULL, AV_LOG_ERROR, "executeJNI => fail");
            (*env)->CallVoidMethod(env, callback, failMID);
        } else {
            av_log(NULL, AV_LOG_ERROR, "executeJNI => success");
            (*env)->CallVoidMethod(env, callback, successMID);
        }
        (*env)->DeleteLocalRef(env, jniClass);
    } else {
        ret = executeCommand(0, command, NULL, totalTime);
    }
    (*env)->ReleaseStringUTFChars(env, command_, command);
    return ret;
}