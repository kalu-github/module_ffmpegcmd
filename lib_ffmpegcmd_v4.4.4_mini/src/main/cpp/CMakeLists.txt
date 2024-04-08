cmake_minimum_required(VERSION 3.4.1)
add_definitions(-DNDEBUG)
add_definitions(-w)

#set(CMAKE_C_FLAGS "${CMAKE_CXX_FLAGS} -fstack-protector-all")
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wl,-Bsymbolic")

# 解决dlopen failed: "has text relocations"
if (${ANDROID_ABI} STREQUAL "x86")
    add_compile_options(-fPIC)
    set(CMAKE_POSITION_INDEPENDENT_CODE ON)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fpic")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fpic")
    #    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-z,notext")
endif ()
# 解决dlopen failed: "has text relocations"
if (${ANDROID_ABI} STREQUAL "x86_64")
    add_compile_options(-fPIC)
    set(CMAKE_POSITION_INDEPENDENT_CODE ON)
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fpic")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fpic")
    #    set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-z,notext")
endif ()

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

# cmd
file(GLOB libcmd
        ${CMAKE_SOURCE_DIR}/cmd/*.c)
file(GLOB libcmd-include
        ${CMAKE_SOURCE_DIR}/cmd/*.h)

add_library( # Sets the name of the library.
        ffmpegcmd
        SHARED
        ${libcmd}
        ${libcmd-include})

target_compile_options(ffmpegcmd
        PRIVATE
        -fPIC)
set_property(TARGET ffmpegcmd PROPERTY POSITION_INDEPENDENT_CODE ON)

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
