#include <jni.h>
#include "api_cmd.h"
#include <android/log.h>
#include <libavutil/log.h>
#include <libavformat/avformat.h>
#include <libavfilter/avfilter.h>
#include <stdbool.h>
#include <libavcodec/bsf.h>

void getAVInputFormatsJNI(char *info) {
    void *opaque = NULL;
    // 获取所有的解封装器
    const AVInputFormat *inputFormat = av_demuxer_iterate(&opaque);
    while (inputFormat != NULL) {
        sprintf(info, "%sInput:%s\n", info, inputFormat->name);
        inputFormat = av_demuxer_iterate(&opaque);
    }
    // 获取所有封装器
    opaque = NULL;
    const AVOutputFormat *outputFormat = av_muxer_iterate(&opaque);
    while (outputFormat != NULL) {
        sprintf(info, "%sOutput:%s\n", info, outputFormat->name);
        outputFormat = av_muxer_iterate(&opaque);
    }
}

void getAVCodecs(char *info) {
    // 获取所有编码器
    void *opaque = NULL;
    const AVCodec *avCodec = av_codec_iterate(&opaque);
    while (avCodec != NULL) {
        if (av_codec_is_encoder(avCodec)) {
            sprintf(info, "%sencode:", info);
            switch (avCodec->type) {
                case AVMEDIA_TYPE_VIDEO:
                    sprintf(info, "%s(video):", info);
                    break;
                case AVMEDIA_TYPE_AUDIO:
                    sprintf(info, "%s(audio):", info);
                    break;
                default:
                    sprintf(info, "%s(other):", info);
                    break;
            }
            sprintf(info, "%s[%10s]\n", info, avCodec->name);
        }
        avCodec = av_codec_iterate(&opaque);
    }

    // 获取所有解码器
    opaque = NULL;
    avCodec = av_codec_iterate(&opaque);
    while (avCodec != NULL) {
        if (av_codec_is_decoder(avCodec)) {
            sprintf(info, "%sdecoder:", info);
            switch (avCodec->type) {
                case AVMEDIA_TYPE_VIDEO:
                    sprintf(info, "%s(video):", info);
                    break;
                case AVMEDIA_TYPE_AUDIO:
                    sprintf(info, "%s(audio):", info);
                    break;
                default:
                    sprintf(info, "%s(other):", info);
                    break;
            }
            sprintf(info, "%s[%10s]\n", info, avCodec->name);
        }
        avCodec = av_codec_iterate(&opaque);
    }
}

void getFilters(char *info) {

    // 获取所有编码器
    void *opaque = NULL;
    const AVFilter *avFilter = av_filter_iterate(&opaque);
    while (avFilter != NULL) {
        sprintf(info, "%sFilter:%s\n", info, avFilter->name);
        avFilter = av_filter_iterate(&opaque);
    }
}

void getProtocols(char *info) {
    void *opaque = NULL;
    // 获取所有协议
    const char *protocol = avio_enum_protocols(&opaque, 0);
    while (protocol != NULL) {
        sprintf(info, "%sinput_protocols:%s\n", info, protocol);
        protocol = avio_enum_protocols(&opaque, 0);
    }

    protocol = avio_enum_protocols(&opaque, 1);
    while (protocol != NULL) {
        sprintf(info, "%soutput_protocols:%s\n", info, protocol);
        protocol = avio_enum_protocols(&opaque, 1);
    }
}

void getBSF(char *info) {
    void *opaque = NULL;
    const AVBitStreamFilter *bsf = av_bsf_iterate(&opaque);
    while (bsf != NULL) {
        sprintf(info, "%sbfs:%s\n", info, bsf->name);
        bsf = av_bsf_iterate(&opaque);
    }
}

JNIEXPORT jstring JNICALL
Java_lib_kalu_ffmpegcmd_FFmpeg_getVersion(JNIEnv *env, jclass clazz) {
    // v4.4.3-release
    char info[20] = {NULL};
    sprintf(info, "%s%c", info, 'v');
    sprintf(info, "%s%s", info, av_version_info());
    sprintf(info, "%s%s", info, "-release");
    return (*env)->NewStringUTF(env, info);
}

JNIEXPORT jstring JNICALL
Java_lib_kalu_ffmpegcmd_FFmpeg_getInformation(JNIEnv *env, jclass clazz) {

    char info[1000] = {NULL};

    for (int i = 0; i <= 3; i++) {

        int v;
        switch (i) {
            case 0: // libavutil      56. 70.100
                v = avutil_version();
                sprintf(info, "%s%s", info, "libavutil: ");
                break;
            case 1://    libavcodec     58.134.100
                v = avcodec_version();
                sprintf(info, "%s%s", info, "\nlibavcodec: ");
                break;
            case 2://    libavformat    58. 76.100
                v = avformat_version();
                sprintf(info, "%s%s", info, "\nlibavformat: ");
                break;
            case 3://    libavfilter     7.110.100
                v = avfilter_version();
                sprintf(info, "%s%s", info, "\nlibavfilter: ");
                break;
            default:
                v = -1;
                break;
        }

        if (v == -1)
            continue;

        int a = v / (int) pow(2, 16);
        int b = (int) (v - a * pow(2, 16)) / (int) pow(2, 8);
        int c = v % (int) pow(2, 8);
        sprintf(info, "%s%d", info, a);
        sprintf(info, "%s%c", info, '.');
        sprintf(info, "%s%d", info, b);
        sprintf(info, "%s%c", info, '.');
        sprintf(info, "%s%d", info, c);
    }
    return (*env)->NewStringUTF(env, info);
}

jstring JNICALL
Java_lib_kalu_ffmpegcmd_FFmpeg_getDetails(JNIEnv *env, jclass clazz) {

    char info[100000] = {NULL};

    // 1
    sprintf(info, "%s%s", info, "AVInputFormats：\n");
    getAVInputFormatsJNI(info);

    // 2
    sprintf(info, "%s%s", info, "\nAVCodecs：\n");
    getAVCodecs(info);

    // 3
    sprintf(info, "%s%s", info, "\nFilters：\n");
    getFilters(info);

    // 4
    sprintf(info, "%s%s", info, "\nProtocols：\n");
    getProtocols(info);

    // 5
    sprintf(info, "%s%s", info, "\nBSF：\n");
    getBSF(info);

    return (*env)->NewStringUTF(env, info);
}