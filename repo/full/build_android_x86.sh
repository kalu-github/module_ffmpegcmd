#!/bin/bash
#Change NDK to your Android NDK location
PLATFORM=$NDK/platforms/android-18/arch-x86/
PREBUILT=$NDK/toolchains/x86-4.9/prebuilt/linux-x86_64

GENERAL="\
--enable-small \
--enable-cross-compile \
--extra-libs="-lgcc" \
--arch=x86 \
--cc=$PREBUILT/bin/i686-linux-android-gcc \
--cross-prefix=$PREBUILT/bin/i686-linux-android- \
--nm=$PREBUILT/bin/i686-linux-android-nm \
--extra-cflags="-I${PREFIX}/x264/android/x86/include" \
--extra-ldflags="-L${PREFIX}/x264/android/x86/lib" \
--extra-cflags="-I${PREFIX}/../mp3lame/include" \
--extra-ldflags="-L${PREFIX}/mp3lame/local/x86" "

temp_prefix=${PREFIX}/ffmpeg/android/x86
rm -rf $temp_prefix
export PATH=$PREBUILT/bin/:$PATH/

function build_x86
{
  ./configure \
  --pkg-config="pkg-config --static" \
  --logfile=log_build_x86.txt \
  --target-os=android \
  --prefix=${temp_prefix} \
  ${GENERAL} \
  --sysroot=$PLATFORM \
  --host-cflags="-fPIC" \
  --host-cppflags="-fPIC" \
  --host-ldflags="-fPIC" \
  --extra-cflags="-fPIC" \
  --extra-cxxflags="-fPIC" \
  --extra-ldflags="-lx264 -Wl,-rpath-link=$PLATFORM/usr/lib -L$PLATFORM/usr/lib -fPIC -nostdlib -lc -lm -ldl -llog" \
  ${COMMON_SET}

  make clean
  make -j10
  make install
}

export PKG_CONFIG_PATH=${PREFIX}/x264/android/x86/lib/pkgconfig
export PKG_CONFIG_LIBDIR=${PREFIX}/x264/android/x86/lib/pkgconfig
#exec /usr/bin/pkg-config "$@"
build_x86
cp config.h $temp_prefix/config.h
echo Android x86 builds finished