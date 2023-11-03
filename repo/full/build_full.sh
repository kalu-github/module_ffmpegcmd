#!/usr/bin/env bash

chmod a+x build_android_*.sh

export COMMON_SET="--enable-static \
  --disable-symver \
  --disable-network \
  --disable-programs \
  --disable-ffmpeg \
  --disable-ffplay \
  --disable-ffprobe \
  --disable-doc \
  --disable-htmlpages \
  --disable-manpages \
  --disable-podpages \
  --disable-txtpages \
  --enable-avcodec \
  --enable-avfilter \
  --enable-avresample \
  --enable-postproc \
  --enable-swscale \
  --enable-avdevice \
  --enable-avformat \
  --enable-avutil \
  --enable-swresample \
  --enable-decoders \
  --enable-encoders \
  --enable-encoder=libx264 \
  --enable-encoder=libmp3lame \
  --enable-hwaccels \
  --enable-parsers \
  --enable-demuxers \
  --enable-muxers \
  --enable-protocols \
  --enable-filters \
  --enable-bsfs \
  --enable-indevs \
  --enable-outdevs \
  --enable-libx264 \
  --enable-libmp3lame \
  --enable-optimizations \
  --enable-zlib \
  --disable-debug \
  --enable-small \
  --enable-pic \
  --disable-x86asm \
  --disable-asm \
  --disable-yasm \
  --disable-stripping \
  --disable-jni \
  --disable-mediacodec \
  --enable-gpl \
  --enable-nonfree \
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
