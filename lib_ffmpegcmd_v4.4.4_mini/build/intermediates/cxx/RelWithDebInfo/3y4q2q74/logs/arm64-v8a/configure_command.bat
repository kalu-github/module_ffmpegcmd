@echo off
"C:\\Users\\kalu\\AppData\\Local\\Android\\Sdk\\cmake\\3.18.1\\bin\\cmake.exe" ^
  "-HD:\\Android\\Huan_Component\\ffmpeg_command\\lib_ffmpegcmd_v4.4.4_mini\\src\\main\\cpp" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=arm64-v8a" ^
  "-DCMAKE_ANDROID_ARCH_ABI=arm64-v8a" ^
  "-DANDROID_NDK=C:\\Users\\kalu\\AppData\\Local\\Android\\Sdk\\ndk\\16.1.4479499" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\kalu\\AppData\\Local\\Android\\Sdk\\ndk\\16.1.4479499" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\kalu\\AppData\\Local\\Android\\Sdk\\ndk\\16.1.4479499\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\kalu\\AppData\\Local\\Android\\Sdk\\cmake\\3.18.1\\bin\\ninja.exe" ^
  "-DCMAKE_CXX_FLAGS=-std=c++11 -Wl,-Bsymbolic" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=D:\\Android\\Huan_Component\\ffmpeg_command\\lib_ffmpegcmd_v4.4.4_mini\\build\\intermediates\\cxx\\RelWithDebInfo\\3y4q2q74\\obj\\arm64-v8a" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=D:\\Android\\Huan_Component\\ffmpeg_command\\lib_ffmpegcmd_v4.4.4_mini\\build\\intermediates\\cxx\\RelWithDebInfo\\3y4q2q74\\obj\\arm64-v8a" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BD:\\Android\\Huan_Component\\ffmpeg_command\\lib_ffmpegcmd_v4.4.4_mini\\.cxx\\RelWithDebInfo\\3y4q2q74\\arm64-v8a" ^
  -GNinja
