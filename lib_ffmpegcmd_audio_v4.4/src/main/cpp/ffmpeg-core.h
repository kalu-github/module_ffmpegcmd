/*
 * Copyright (c) 2018 DevYK
 *
 * This file is part of MobileFFmpeg.
 *
 * MobileFFmpeg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MobileFFmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MobileFFmpeg.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef MOBILE_FFMPEG_H
#define MOBILE_FFMPEG_H

#include <jni.h>
#include <android/log.h>

#include "libavutil/log.h"
#include "libavutil/ffversion.h"

/** Defines tag used for Android logging. */
#define LIB_NAME "ffmpeg-cmd"

/** Verbose Android logging macro. */
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LIB_NAME, __VA_ARGS__)

/** Debug Android logging macro. */
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LIB_NAME, __VA_ARGS__)

/** Info Android logging macro. */
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LIB_NAME, __VA_ARGS__)

/** Warn Android logging macro. */
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LIB_NAME, __VA_ARGS__)

/** Error Android logging macro. */
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LIB_NAME, __VA_ARGS__)

JNIEXPORT void JNICALL Java_lib_kalu_ffmpegcmd_FFcmd_enableNativeRedirection(JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_lib_kalu_ffmpegcmd_FFcmd_disableNativeRedirection(JNIEnv *, jclass);

JNIEXPORT void JNICALL Java_lib_kalu_ffmpegcmd_FFcmd_setNativeLogLevel(JNIEnv *, jclass, jint);

JNIEXPORT jint JNICALL Java_lib_kalu_ffmpegcmd_FFcmd_getNativeLogLevel(JNIEnv *, jclass);

JNIEXPORT jstring JNICALL Java_lib_kalu_ffmpegcmd_FFcmd_getNativeFFmpegVersion(JNIEnv *, jclass);

JNIEXPORT jstring JNICALL Java_lib_kalu_ffmpegcmd_FFcmd_getNativeVersion(JNIEnv *, jclass);

JNIEXPORT jint JNICALL
Java_lib_kalu_ffmpegcmd_FFcmd_nativeFFmpegExecute(JNIEnv *, jclass, jlong id, jobjectArray);

JNIEXPORT void JNICALL
Java_lib_kalu_ffmpegcmd_FFcmd_nativeFFmpegCancel(JNIEnv *, jclass, jlong);

JNIEXPORT int JNICALL
Java_lib_kalu_ffmpegcmd_FFcmd_registerNewNativeFFmpegPipe(JNIEnv *env, jclass object,
                                                          jstring ffmpegPipePath);

JNIEXPORT jstring JNICALL
Java_lib_kalu_ffmpegcmd_FFcmd_getNativeBuildDate(JNIEnv *env, jclass object);

JNIEXPORT int JNICALL
Java_lib_kalu_ffmpegcmd_FFcmd_setNativeEnvironmentVariable(JNIEnv *env, jclass object,
                                                           jstring variableName,
                                                           jstring variableValue);

JNIEXPORT jstring JNICALL
Java_lib_kalu_ffmpegcmd_FFcmd_getNativeLastCommandOutput(JNIEnv *env, jclass object);

JNIEXPORT void JNICALL
Java_lib_kalu_ffmpegcmd_FFcmd_ignoreNativeSignal(JNIEnv *env, jclass object, jint signum);

#endif
