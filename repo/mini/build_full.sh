#!/usr/bin/env bash

chmod a+x build_android_*.sh

export COMMON_SET="--enable-static \
  --disable-symver \
  --disable-network \
  --disable-debug \
  --disable-doc \
  --disable-htmlpages \
  --disable-manpages \
  --disable-podpages \
  --disable-txtpages \
  --disable-ffplay \
  --disable-ffmpeg \
  --disable-ffprobe \
  --enable-avdevice \
  --enable-avcodec \
  --enable-avformat \
  --enable-swresample \
  --enable-swscale \
  --enable-postproc \
  --enable-avfilter \
  --enable-bsfs \
  --enable-devices \
  --enable-protocols \
  --enable-parsers \
  --enable-muxers \
  --enable-demuxers \
  --enable-decoders \
  --enable-encoders \
  --enable-swscale \
  --enable-filters \
  --enable-gpl \
  --enable-libx264 \
  --enable-zlib \
  --enable-libmp3lame \
  --enable-jni \
  --enable-nonfree \
  --enable-mediacodec \
  --enable-asm \
  --enable-version3 "

# Build arm v7a
# make distclean
# ./build_android_armeabi_v7a.sh

# Build arm64 v8a
# make distclean
# ./build_android_arm64_v8a.sh

# Build x86
# make distclean
# ./build_android_x86.sh

# Build x86_64
# make distclean
./build_android_x86_64.sh
