# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

find_library(log-lib log)
find_library(m-lib m)
find_library(z-lib z)

configure_file(${Project_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libffmpegcmd.so ${Project_BINARY_DIR}/libffmpegcmd.so COPYONLY)
add_library(ffmpegcmd-lib
        SHARED
        IMPORTED)
set_target_properties(ffmpegcmd-lib
        PROPERTIES IMPORTED_LOCATION
        libffmpegcmd.so)

include_directories(
        ffmpegcmd
        include
        include/${ANDROID_ABI}
)


add_library( # Sets the name of the library.
        ffmpegcmd-jni

        SHARED

        ffmpegcmd/api_main.c;
        ffmpegcmd/api_cmd.c;
        ffmpegcmd/ffmpeg_hw.c;
        ffmpegcmd/cmdutils.c;
        ffmpegcmd/ffmpeg.c;
        ffmpegcmd/ffmpeg_filter.c;
        ffmpegcmd/ffmpeg_opt.c;
        ffmpegcmd/opt_common.c;
        ffmpegcmd/ffmpeg_mux.c;
        )

target_link_libraries( # Specifies the target library.
        ffmpegcmd-jni

        # Links the target library to the log library
        # included in the NDK.
        ffmpegcmd-lib
        ${log-lib}
        ${m-lib}
        ${z-lib})
