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
--extra-cflags="-I${PREFIX}/x264/android/arm64/include" \
--extra-ldflags="-L${PREFIX}/x264/android/arm64/lib" \
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
  --extra-ldflags="-lx264 -Wl,-rpath-link=$PLATFORM/usr/lib -L$PLATFORM/usr/lib -nostdlib -lc -lm -ldl -llog" \
  ${COMMON_SET}
  make clean
  make -j10
  make install
}

export PKG_CONFIG_PATH=${PREFIX}/x264/android/arm64/lib/pkgconfig
export PKG_CONFIG_LIBDIR=${PREFIX}/x264/android/arm64/lib/pkgconfig
#exec /usr/bin/pkg-config "$@"
build_arm64
cp config.h $temp_prefix/config.h
echo Android ARM64 builds finished