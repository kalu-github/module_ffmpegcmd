#!/usr/bin/env bash

chmod a+x build_android_*.sh

export COMMON_SET="--enable-static \
  --disable-symver \
  --disable-network \
  --disable-debug \
  --disable-programs \
  --disable-ffmpeg \
  --disable-ffplay \
  --disable-ffprobe \
  --disable-doc \
  --disable-htmlpages \
  --disable-manpages \
  --disable-podpages \
  --disable-txtpages \
  --enable-avdevice \
  --enable-avcodec \
  --enable-avformat \
  --enable-swresample \
  --enable-swscale \
  --enable-postproc \
  --enable-avfilter \
  --enable-avresample \
  --disable-bsfs \
  --disable-devices \
  --disable-indevs \
  --enable-indev=lavfi \
  --disable-outdevs \
  --disable-protocols \
  --enable-protocol=file \
  --enable-protocol=concat \
  --disable-parsers \
  --disable-muxers \
  --enable-muxer=pcm_u8 \
  --enable-muxer=pcm_s16le \
  --enable-muxer=pcm_s16be \
  --enable-muxer=mp3 \
  --enable-muxer=mov \
  --enable-muxer=mp4 \
  --enable-muxer=image2 \
  --enable-muxer=ipod \
  --disable-demuxers \
  --enable-demuxer=pcm_u8 \
  --enable-demuxer=pcm_s16le \
  --enable-demuxer=pcm_s16be \
  --enable-demuxer=mp3 \
  --enable-demuxer=mov \
  --enable-demuxer=image2 \
  --enable-demuxer=wav \
  --enable-demuxer=asf \
  --enable-demuxer=flv \
  --enable-demuxer=avi \
  --enable-demuxer=webm_dash_manifest \
  --enable-demuxer=matroska \
  --enable-demuxer=mpegts \
  --disable-decoders \
  --enable-decoder=pcm_u8 \
  --enable-decoder=pcm_s16le \
  --enable-decoder=pcm_s16be \
  --enable-decoder=adpcm_swf \
  --enable-decoder=amrnb \
  --enable-decoder=ac3 \
  --enable-decoder=aac \
  --enable-decoder=mp3 \
  --enable-decoder=wmav1 \
  --enable-decoder=wmav2 \
  --enable-decoder=mp2 \
  --enable-decoder=h264 \
  --enable-decoder=mpeg4 \
  --enable-decoder=msmpeg4v1 \
  --enable-decoder=msmpeg4v2 \
  --enable-decoder=msmpeg4v3 \
  --enable-decoder=mpeg2video \
  --enable-decoder=indeo4 \
  --disable-encoders \
  --enable-encoder=pcm_u8 \
  --enable-encoder=pcm_s16le \
  --enable-encoder=pcm_s16be \
  --enable-encoder=aac \
  --enable-encoder=libmp3lame \
  --enable-swscale \
  --disable-filters \
  --enable-filter=crop \
  --enable-filter=scale \
  --enable-filter=afade \
  --enable-filter=atempo \
  --enable-filter=copy \
  --enable-filter=aformat \
  --enable-filter=overlay \
  --enable-filter=vflip \
  --enable-filter=hflip \
  --enable-filter=transpose \
  --enable-filter=volume \
  --enable-filter=rotate \
  --enable-filter=apad \
  --enable-filter=amerge \
  --enable-filter=aresample \
  --enable-filter=setpts \
  --enable-filter=fps \
  --enable-filter=palettegen \
  --enable-filter=paletteuse \
  --enable-filter=trim \
  --enable-filter=null \
  --enable-filter=overlay \
  --enable-filter=format \
  --enable-filter=atrim \
  --enable-filter=split \
  --enable-filter=amix \
  --enable-filter=anull \
  --enable-filter=adelay \
  --enable-filter=anullsrc \
  --enable-filter=adelay \
  --enable-filter=afftdn \
  --enable-filter=pan \
  --enable-libmp3lame \
  --enable-optimizations \
  --enable-zlib \
  --enable-small \
  --disable-jni \
  --disable-mediacodec \
  --enable-gpl \
  --enable-nonfree \
  --enable-version3 "

# Build arm v7a
# make distclean
./build_android_armeabi_v7a.sh

# Build arm64 v8a
# make distclean
# ./build_android_arm64_v8a.sh

# Build x86
# make distclean
# ./build_android_x86.sh

# Build x86_64
# make distclean
# ./build_android_x86_64.sh