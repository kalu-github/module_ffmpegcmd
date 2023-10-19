#!/bin/bash
#arm64 最小必须是android-21
PLATFORM=$NDK/platforms/android-21/arch-arm64
PREBUILT=$NDK/toolchains/aarch64-linux-android-4.9/prebuilt/linux-x86_64

GENERAL="\
--enable-small \
--enable-cross-compile \
--extra-libs="-lgcc" \
--arch=aarch64 \
--cc=$PREBUILT/bin/aarch64-linux-android-gcc \
--cross-prefix=$PREBUILT/bin/aarch64-linux-android- \
--nm=$PREBUILT/bin/aarch64-linux-android-nm \
--extra-cflags="-I${PREFIX}/../mp3lame/include" \
--extra-ldflags="-L${PREFIX}/mp3lame/local/arm64-v8a" "

temp_prefix=${PREFIX}/ffmpeg/android/arm64-v8a
rm -rf $temp_prefix
export PATH=$PREBUILT/bin/:$PATH/

function build_arm64
{
  ./configure \
  --pkg-config="pkg-config --static" \
  --logfile=conflog.txt \
  --target-os=android \
  --prefix=${temp_prefix} \
  ${GENERAL} \
  --sysroot=$PLATFORM \
  --extra-cflags="" \
  --extra-ldflags="-Wl,-rpath-link=$PLATFORM/usr/lib -L$PLATFORM/usr/lib -nostdlib -lc -lm -ldl -llog" \
  ${COMMON_SET}
  make clean
  make -j10
  make install

  aarch64-linux-android-gcc \
  --sysroot=$PLATFORM \
  -L$temp_prefix/lib \
  -shared -o $temp_prefix/${SONAME} \
  -Wl,--whole-archive \
  libavcodec/libavcodec.a \
  libavdevice/libavdevice.a \
  libavfilter/libavfilter.a \
  libavresample/libavresample.a \
  libswresample/libswresample.a \
  libavformat/libavformat.a \
  libavutil/libavutil.a \
  libpostproc/libpostproc.a \
  libswscale/libswscale.a \
  ${PREFIX}/mp3lame/local/arm64-v8a/libmp3lame.a \
  -Wl,--no-whole-archive -lm -lz

  cp $temp_prefix/${SONAME} $temp_prefix/libffmpeg-debug.so
  aarch64-linux-android-strip --strip-unneeded $temp_prefix/${SONAME}

  echo SO-Dir=${temp_prefix}/${SONAME}
}

#exec /usr/bin/pkg-config "$@"
build_arm64
cp config.h $temp_prefix/config.h
echo Android ARM64 builds finished