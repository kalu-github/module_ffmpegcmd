cmake_minimum_required(VERSION 3.4.1)
add_definitions(-DNDEBUG)
add_definitions(-w)

#set(CMAKE_POSITION_INDEPENDENT_CODE ON)
#set(BEGIN_WHOLE_ARCHIVE -Wl,--whole-archive)
#set(END_WHOLE_ARCHIVE -Wl,--no-whole-archive)

if (${ANDROID_ABI} STREQUAL "x86_64")
    include_directories(${ANDROID_SYSROOT}/usr/include/x86_64-linux-android)
elseif (${ANDROID_ABI} STREQUAL "x86")
    include_directories(${ANDROID_SYSROOT}/usr/include/i686-linux-android)
elseif (${ANDROID_ABI} STREQUAL "arm64-v8a")
    include_directories(${ANDROID_SYSROOT}/usr/include/aarch64-linux-android)
elseif (${ANDROID_ABI} STREQUAL "armeabi-v7a")
    include_directories(${ANDROID_SYSROOT}/usr/include/arm-linux-androideabi)
endif ()

if (${ANDROID_ABI} STREQUAL "x86")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-z,notext")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,--no-warn-shared-textrel")
endif ()

if (${ANDROID_ABI} STREQUAL "x86_64")
#    set(CMAKE_CXX_FLAGS "-fPIC")
#    set(CMAKE_C_FLAGS "-fPIC")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-z,notext")
    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,--no-warn-shared-textrel")
endif ()

set(ffmpegcmd-sources
        cmd/api_cmd.c;
        cmd/api_execute.c;
        cmd/api_version.c;
        cmd/cmdutils.c;
        cmd/ffmpeg.c;
        cmd/ffmpeg_filter.c;
        cmd/ffmpeg_hw.c;
        cmd/ffmpeg_opt.c)

add_library( # Sets the name of the library.
        ffmpegcmd

        SHARED
        ${ffmpegcmd-sources})

# ffmpeg-libmp3lame
configure_file(${CMAKE_SOURCE_DIR}/lame/${ANDROID_ABI}/lib/libmp3lame.a ${Project_BINARY_DIR}/libmp3lame.a COPYONLY)
add_library(ffmpeg-libmp3lame
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libmp3lame
        PROPERTIES IMPORTED_LOCATION
        libmp3lame.a)

# ffmpeg-libavcodec
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libavcodec.a ${Project_BINARY_DIR}/libavcodec.a COPYONLY)
add_library(ffmpeg-libavcodec
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libavcodec
        PROPERTIES IMPORTED_LOCATION
        libavcodec.a)
# ffmpeg-libavdevice
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libavdevice.a ${Project_BINARY_DIR}/libavdevice.a COPYONLY)
add_library(ffmpeg-libavdevice
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libavdevice
        PROPERTIES IMPORTED_LOCATION
        libavdevice.a)
# ffmpeg-libavfilter
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libavfilter.a ${Project_BINARY_DIR}/libavfilter.a COPYONLY)
add_library(ffmpeg-libavfilter
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libavfilter
        PROPERTIES IMPORTED_LOCATION
        libavfilter.a)
# ffmpeg-libavresample
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libavresample.a ${Project_BINARY_DIR}/libavresample.a COPYONLY)
add_library(ffmpeg-libavresample
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libavresample
        PROPERTIES IMPORTED_LOCATION
        libavresample.a)
# ffmpeg-libswresample
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libswresample.a ${Project_BINARY_DIR}/libswresample.a COPYONLY)
add_library(ffmpeg-libswresample
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libswresample
        PROPERTIES IMPORTED_LOCATION
        libswresample.a)
# ffmpeg-libavformat
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libavformat.a ${Project_BINARY_DIR}/libavformat.a COPYONLY)
add_library(ffmpeg-libavformat
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libavformat
        PROPERTIES IMPORTED_LOCATION
        libavformat.a)
# ffmpeg-libavutil
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libavutil.a ${Project_BINARY_DIR}/libavutil.a COPYONLY)
add_library(ffmpeg-libavutil
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libavutil
        PROPERTIES IMPORTED_LOCATION
        libavutil.a)
# ffmpeg-libpostproc
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libpostproc.a ${Project_BINARY_DIR}/libpostproc.a COPYONLY)
add_library(ffmpeg-libpostproc
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libpostproc
        PROPERTIES IMPORTED_LOCATION
        libpostproc.a)
# ffmpeg-libswscale
configure_file(${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/lib/libswscale.a ${Project_BINARY_DIR}/libswscale.a COPYONLY)
add_library(ffmpeg-libswscale
        STATIC
        IMPORTED)
set_target_properties(ffmpeg-libswscale
        PROPERTIES IMPORTED_LOCATION
        libswscale.a)

include_directories(
        ${CMAKE_SOURCE_DIR}lame/${ANDROID_ABI}/include
        ${CMAKE_SOURCE_DIR}/ffmpeg/${ANDROID_ABI}/include
        ${CMAKE_SOURCE_DIR}/cmd/include)

target_link_libraries( # Specifies the target library.
        ffmpegcmd

        # ffmpeg
        ffmpeg-libavdevice
        ffmpeg-libavfilter
        ffmpeg-libavformat
        ffmpeg-libavcodec
        ffmpeg-libavresample
        ffmpeg-libswresample
        ffmpeg-libavutil
        ffmpeg-libpostproc
        ffmpeg-libswscale

        # mp3lame
        ffmpeg-libmp3lame

        # Links the target library to the log library
        # included in the NDK.
        c
        m
        dl
        log
        z
        android
        jnigraphics)
