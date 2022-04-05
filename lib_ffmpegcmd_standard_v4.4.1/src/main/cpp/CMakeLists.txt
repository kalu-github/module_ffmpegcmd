cmake_minimum_required(VERSION 3.4.1)


#设置静态库和动态库的输出路径
#set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../../../libs/${ANDROID_ABI})

#编解码库
add_library(avcodec SHARED IMPORTED)
set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/jni/${CMAKE_ANDROID_ARCH_ABI}/libavcodec.so)

#文件格式和封装协议
add_library(avformat SHARED IMPORTED)
set_target_properties(avformat PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/jni/${CMAKE_ANDROID_ARCH_ABI}/libavformat.so)

#重采样
add_library(avdevice SHARED IMPORTED)
set_target_properties(avdevice PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/jni/${CMAKE_ANDROID_ARCH_ABI}/libavdevice.so)

#重采样
add_library(swresample SHARED IMPORTED)
set_target_properties(swresample PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/jni/${CMAKE_ANDROID_ARCH_ABI}/libswresample.so)

#工具包
add_library(avutil SHARED IMPORTED)
set_target_properties(avutil PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/jni/${CMAKE_ANDROID_ARCH_ABI}/libavutil.so)

#过滤
add_library(avfilter SHARED IMPORTED)
set_target_properties(avfilter PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/jni/${CMAKE_ANDROID_ARCH_ABI}/libavfilter.so)

#视频转码
add_library(swscale SHARED IMPORTED)
set_target_properties(swscale PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/jni/${CMAKE_ANDROID_ARCH_ABI}/libswscale.so)

#头文件
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/)

FILE(GLOB MOBILE_FFMPEG
        SHARED
        ${CMAKE_SOURCE_DIR}/ffmpeg-core.c
        ${CMAKE_SOURCE_DIR}/ffprobe-core.c
        ${CMAKE_SOURCE_DIR}/android_lts_support.c
        ${CMAKE_SOURCE_DIR}/ffmpeg_exception.c
        ${CMAKE_SOURCE_DIR}/fftools_cmdutils.c
        ${CMAKE_SOURCE_DIR}/fftools_ffmpeg.c
        ${CMAKE_SOURCE_DIR}/fftools_ffprobe.c
        ${CMAKE_SOURCE_DIR}/fftools_ffmpeg_opt.c
        ${CMAKE_SOURCE_DIR}/fftools_ffmpeg_hw.c
        ${CMAKE_SOURCE_DIR}/fftools_ffmpeg_filter.c
        ${CMAKE_SOURCE_DIR}/ffmpeg_progress.c
        )

find_library(
        log-lib
        log)

#add_library(
#        ffmpeg_abidetect
#        SHARED
#        ${CMAKE_SOURCE_DIR}/ffmpeg_abidetect.c
#        ${log-lib}
#)

add_library(
        ffcmd
        SHARED
        ${MOBILE_FFMPEG}
        ${log-lib}
)

target_link_libraries(
        ffcmd
        android z ${log-lib} atomic m
        avcodec
        avfilter
        avformat
        avutil
        swresample
        swscale
        avdevice
)