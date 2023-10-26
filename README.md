#
#### 说明
```
**_full.aar => ffmpeg+lamemp3+x246[全量]
```
```
**_lite.aar => ffmpeg+lamemp3+x246[精简]
```
```
**_full.aar => ffmpeg+lamemp3[精简]
```

#
#### 更新
```
2023-10-20
1. 新增lib_ffmpegcmd_v4.4.4_mini_release_20231020.aar
```

#
#### 已知问题
```
1. 5.1.1版本不支持 -f -lavfi命令【编译问题 => ？？】
```
```
2. progress传递处理进度数据不准确【超过100% => bug】
```

#
## 历史版本
```
1. https://www.videohelp.com/software/ffmpeg/old-versions
2. http://www.ffmpeg.org/releases/
```

#
## ffmpeg编译参数对比
```
一、参数解释
以下源自FFmpeg6.0版本，翻译源自ChatGpt
貌似FFmepg已经满足了几乎所有的需求，只要通过对configure的配置即可生成需要的文件，比如删减版的.so库，所以这里对其参数进行记录
通过./configure --help即可得知所有的选项。该命令需要根据版本进行使用，有的版本可能会对命令进行修改，增加、删除
Help options:
--help 显示帮助信息
--quiet 禁止显示详细信息
--list-decoders 显示所有可用的解码器
--list-encoders 显示所有可用的编码器
--list-hwaccels 显示所有可用的硬件加速器
--list-demuxers 显示所有可用的解封装器（输入格式）
--list-muxers 显示所有可用的封装器（输出格式）
--list-parsers 显示所有可用的解析器
--list-protocols 显示所有可用的协议
--list-bsfs 显示所有可用的比特流过滤器
--list-indevs 显示所有可用的输入设备
--list-outdevs 显示所有可用的输出设备
--list-filters 显示所有可用的滤镜
Standard options:
--logfile=FILE 将测试和输出记录到FILE中 [ffbuild/config.log]
--disable-logging 禁止记录配置调试信息
--fatal-warnings 如果产生任何配置警告，则失败
--prefix=PREFIX 安装到PREFIX目录下 [/usr/local]
--bindir=DIR 将二进制文件安装到DIR目录下 [PREFIX/bin]
--datadir=DIR 将数据文件安装到DIR目录下 [PREFIX/share/ffmpeg]
--docdir=DIR 将文档安装到DIR目录下 [PREFIX/share/doc/ffmpeg]
--libdir=DIR 将库文件安装到DIR目录下 [PREFIX/lib]
--shlibdir=DIR 将共享库文件安装到DIR目录下 [LIBDIR]
--incdir=DIR 将头文件安装到DIR目录下 [PREFIX/include]
--mandir=DIR 将man页安装到DIR目录下 [PREFIX/share/man]
--pkgconfigdir=DIR 将pkg-config文件安装到DIR目录下 [LIBDIR/pkgconfig]
--enable-rpath 使用rpath允许在动态链接器搜索路径之外安装库文件链接程序时使用rpath（谨慎使用）
--install-name-dir=DIR Darwin系统中安装目标的目录名称
这些参数用于控制FFmpeg的配置、安装和输出设置。你可以根据需要使用这些参数来定制你的FFmpeg安装和使用环境。

Licensing options:
--enable-gpl 允许使用GPL代码，生成的库和二进制文件将使用GPL许可 [否]
--enable-version3 升级（L）GPL到版本3 [否]
--enable-nonfree 允许使用非自由代码，生成的库和二进制文件将不可再分发 [否]
Configuration options:
--disable-static 不构建静态库 [否]
--enable-shared 构建共享库 [否]
--enable-small 优化尺寸而非速度
--disable-runtime-cpudetect 禁用运行时检测CPU能力（生成较小的二进制文件）
--enable-gray 启用完全灰度支持（比较慢的颜色处理）
--disable-swscale-alpha 禁用swscale中的Alpha通道支持
--disable-all 禁用构建组件、库和程序
--disable-autodetect 禁用自动检测的外部库 [否]
Program options:
--disable-programs 不构建命令行程序
--disable-ffmpeg 禁用ffmpeg构建
--disable-ffplay 禁用ffplay构建
--disable-ffprobe 禁用ffprobe构建
Documentation options:
--disable-doc 不构建文档
--disable-htmlpages 不构建HTML文档页面
--disable-manpages 不构建man文档页面
--disable-podpages 不构建POD文档页面
--disable-txtpages 不构建文本文档页面
这些参数用于配置和构建FFmpeg的不同方面。你可以根据需要启用或禁用这些选项，以满足你的特定需求和限制。例如，你可以选择是否启用GPL代码、构建静态库还是共享库、构建特定的程序，以及是否构建文档等。

Component options:
--disable-avdevice 禁用libavdevice构建
--disable-avcodec 禁用libavcodec构建
--disable-avformat 禁用libavformat构建
--disable-swresample 禁用libswresample构建
--disable-swscale 禁用libswscale构建
--disable-postproc 禁用libpostproc构建
--disable-avfilter 禁用libavfilter构建
--disable-pthreads 禁用pthreads [自动检测]
--disable-w32threads 禁用Win32线程 [自动检测]
--disable-os2threads 禁用OS/2线程 [自动检测]
--disable-network 禁用网络支持 [否]
--disable-dct 禁用DCT代码
--disable-dwt 禁用DWT代码
--disable-error-resilience 禁用错误恢复代码
--disable-lsp 禁用LSP代码
--disable-mdct 禁用MDCT代码
--disable-rdft 禁用RDFT代码
--disable-fft 禁用FFT代码
--disable-faan 禁用浮点AAN（I）DCT代码
--disable-pixelutils 禁用libavutil中的像素工具
这些参数用于控制FFmpeg中不同组件的构建和支持。你可以根据需要禁用特定的组件，例如禁用音频/视频解码器（avcodec）、封装器（avformat）、滤镜（avfilter）等。此外，还可以禁用特定的代码模块，如DCT、DWT、FFT等。这些选项允许你根据项目要求进行定制，减少生成的库和二进制文件的大小或限制特定功能的使用。

Individual component options:
--disable-everything 禁用下面列出的所有组件
--disable-encoder=NAME 禁用编码器NAME
--enable-encoder=NAME 启用编码器NAME
--disable-encoders 禁用所有编码器
--disable-decoder=NAME 禁用解码器NAME
--enable-decoder=NAME 启用解码器NAME
--disable-decoders 禁用所有解码器
--disable-hwaccel=NAME 禁用硬件加速器NAME
--enable-hwaccel=NAME 启用硬件加速器NAME
--disable-hwaccels 禁用所有硬件加速器
--disable-muxer=NAME 禁用复用器NAME
--enable-muxer=NAME 启用复用器NAME
--disable-muxers 禁用所有复用器
--disable-demuxer=NAME 禁用解复用器NAME
--enable-demuxer=NAME 启用解复用器NAME
--disable-demuxers 禁用所有解复用器
--enable-parser=NAME 启用解析器NAME
--disable-parser=NAME 禁用解析器NAME
--disable-parsers 禁用所有解析器
--enable-bsf=NAME 启用比特流过滤器NAME
--disable-bsf=NAME 禁用比特流过滤器NAME
--disable-bsfs 禁用所有比特流过滤器
--enable-protocol=NAME 启用协议NAME
--disable-protocol=NAME 禁用协议NAME
--disable-protocols 禁用所有协议
--enable-indev=NAME 启用输入设备NAME
--disable-indev=NAME 禁用输入设备NAME
--disable-indevs 禁用所有输入设备
--enable-outdev=NAME 启用输出设备NAME
--disable-outdev=NAME 禁用输出设备NAME
--disable-outdevs 禁用所有输出设备
--disable-devices 禁用所有设备
--enable-filter=NAME 启用滤镜NAME
--disable-filter=NAME 禁用滤镜NAME
--disable-filters 禁用所有滤镜
这些参数用于对FFmpeg中的各个组件进行个别控制。你可以使用这些参数来启用或禁用特定的编码器、解码器、复用器、解复用器、解析器、比特流过滤器、协议、输入设备、输出设备和滤镜等。通过设置这些选项，你可以根据需求定制FFmpeg的构建和功能，只包括你所需的组件和功能。

以下参数是用于配置FFmpeg与外部库的支持。通过使用这些参数，可以使FFmpeg链接到相应的外部库，从而启用与这些库相关的组件和功能。
以下是其中一些参数的说明：
--disable-alsa: 禁用ALSA支持。
--disable-bzlib: 禁用bzlib支持。
--enable-libopus: 启用libopus库，用于Opus音频的编码和解码。
--enable-libass: 启用libass库，用于字幕的渲染。
--enable-libfreetype: 启用libfreetype库，用于字幕的渲染。
--enable-libx264: 启用libx264库，用于H.264视频的编码。
--enable-libx265: 启用libx265库，用于HEVC视频的编码。
--enable-libvpx: 启用libvpx库，用于VP8和VP9视频的编码和解码。
--enable-libwebp: 启用libwebp库，用于WebP图片的编码。
--enable-libmp3lame: 启用libmp3lame库，用于MP3音频的编码。
--enable-libvorbis: 启用libvorbis库，用于Vorbis音频的编码和解码。
--enable-libvpx: 启用通过libvpx进行VP8和VP9的解码和编码。
--enable-libwebp: 启用通过libwebp进行WebP的编码。
--enable-libx264: 启用通过x264进行H.264的编码。
--enable-libx265: 启用通过x265进行HEVC的编码。
--enable-libxavs: 启用通过xavs进行AVS的编码。
--enable-libxavs2: 启用通过xavs2进行AVS2的编码。
--enable-libxcb: 启用通过XCB进行X11抓取。
--enable-libxcb-shm: 启用通过XCB进行X11共享内存通信。
--enable-libxcb-xfixes: 启用通过XCB进行X11鼠标渲染。
--enable-libxcb-shape: 启用通过XCB进行X11形状渲染。
--enable-libxvid: 启用通过xvidcore进行Xvid的编码。注意，已经存在原生的MPEG-4/Xvid编码器。
--enable-libxml2: 启用使用C库libxml2进行XML解析，用于dash和imf解复用支持。
--enable-libzimg: 启用z.lib，用于zscale滤镜。
--enable-libzmq: 启用通过libzmq进行消息传递。
--enable-libzvbi: 启用通过libzvbi进行teletext支持。
--enable-lv2: 启用LV2音频滤镜。
--enable-mbedtls: 启用mbedTLS，用于https支持，如果未使用openssl、gnutls或libtls。
--enable-mediacodec: 启用Android MediaCodec支持。
--enable-mediafoundation: 启用通过MediaFoundation进行编码。
--disable-metal: 禁用Apple Metal框架。
--enable-libmysofa: 启用libmysofa，用于sofalizer滤镜。
--enable-openal: 启用OpenAL 1.1采集支持。
--enable-opencl: 启用OpenCL处理。
--enable-opengl: 启用OpenGL渲染。
--enable-openssl: 启用openssl，用于https支持，如果未使用gnutls、libtls或mbedtls。
--enable-pocketsphinx: 启用PocketSphinx，用于asr滤镜。
--disable-sndio: 禁用sndio支持。
--disable-schannel: 禁用SChannel SSP，在Windows上用于TLS支持，如果未使用openssl和gnutls。
--disable-sdl2: 禁用sdl2支持。
--disable-securetransport: 禁用Secure Transport，在OSX上用于TLS支持，如果未使用openssl和gnutls。
--enable-vapoursynth: 启用VapourSynth解复用器。
--disable-xlib: 禁用xlib支持。
--disable-zlib: 禁用zlib支持。
这些参数允许根据需要启用或禁用特定的外部库，并相应地启用或禁用与这些库相关的编码器、解码器、过滤器和功能。需要注意的是，有些库需要显式启用，而不会自动检测到系统库。

下面是FFmpeg中硬件加速相关参数的解释：

--disable-amf: 禁用AMF视频编码代码。AMF是AMD的硬件加速框架。
--disable-audiotoolbox: 禁用Apple AudioToolbox代码。AudioToolbox是苹果的音频处理框架。
--enable-cuda-nvcc: 启用Nvidia CUDA编译器。
--disable-cuda-llvm: 禁用使用clang进行CUDA编译。CUDA是Nvidia的并行计算平台和编程模型。
--disable-cuvid: 禁用Nvidia CUVID支持。CUVID是Nvidia的视频解码加速框架。
--disable-d3d11va: 禁用Microsoft Direct3D 11视频加速代码。
--disable-dxva2: 禁用Microsoft DirectX 9视频加速代码。
--disable-ffnvcodec: 禁用动态链接的Nvidia代码。
--enable-libdrm: 启用DRM代码（适用于Linux）。DRM是Direct Rendering Manager的缩写，是Linux内核中用于图形硬件加速的子系统。
--enable-libmfx: 启用Intel MediaSDK（又称Quick Sync Video）通过libmfx的代码。Quick Sync Video是Intel的硬件加速视频编解码技术。
--enable-libvpl: 启用Intel oneVPL通过libvpl的代码，如果未使用libmfx。oneVPL是Intel的多媒体处理库。
--enable-libnpp: 启用基于Nvidia Performance Primitives的代码。Nvidia Performance Primitives（NPP）是一套高性能图像和信号处理函数库。
--enable-mmal: 启用Broadcom Multi-Media Abstraction Layer（Raspberry Pi）通过MMAL的代码。MMAL是Raspberry Pi上的多媒体抽象层。
--disable-nvdec: 禁用Nvidia视频解码加速（通过硬件加速）。nvdec是Nvidia的视频解码器。
--disable-nvenc: 禁用Nvidia视频编码代码。nvenc是Nvidia的视频编码器。
--enable-omx: 启用OpenMAX IL代码。OpenMAX IL是一种用于媒体处理的开放标准。
--enable-omx-rpi: 启用适用于Raspberry Pi的OpenMAX IL代码。
--enable-rkmpp: 启用Rockchip Media Process Platform代码。Rockchip是一家集成电路设计公司，RKMPP是其提供的硬件加速解决方案。
--disable-v4l2-m2m: 禁用V4L2 mem2mem代码。V4L2是Video4Linux2的缩写，是Linux内核中用于视频设备的API。
--disable-vaapi: 禁用Video Acceleration API代码（主要适用于Unix/Intel）。Video Acceleration API（VA-API）是用于视频解码和编码加速的开放标
准。

--disable-vdpau: 禁用Nvidia Video Decode and Presentation API for Unix代码。VDPAU是Nvidia用于Unix系统的视频解码和显示API。
--disable-videotoolbox: 禁用VideoToolbox代码。VideoToolbox是苹果的视频编码和解码框架。
--disable-vulkan: 禁用Vulkan代码。Vulkan是一种跨平台的图形和计算API。
这些参数允许根据系统的硬件和需求启用或禁用特定的硬件加速功能。

下面是FFmpeg中工具链选项的解释：
--arch=ARCH: 选择目标架构。ARCH可以是x86、x86_64、ARM等。
--cpu=CPU: 选择所需的最低CPU要求（影响指令选择，在旧的CPU上可能会导致崩溃）。
--cross-prefix=PREFIX: 使用PREFIX作为交叉编译工具的前缀。
--progs-suffix=SUFFIX: 程序名称后缀。
--enable-cross-compile: 假设使用交叉编译器。
--sysroot=PATH: 交叉编译树的根目录。
--sysinclude=PATH: 交叉编译系统头文件的位置。
--target-os=OS: 编译器目标操作系统。
--target-exec=CMD: 在目标上运行可执行文件的命令。
--target-path=DIR: 目标上构建目录的路径。
--target-samples=DIR: 目标上示例目录的路径。
--tempprefix=PATH: 强制使用固定的目录/前缀而不是mktemp进行检查。
--toolchain=NAME: 根据名称设置工具链的默认值（如gcc-asan、clang-asan、gcc-msan等）。可以根据不同的需求选择不同的工具链配置。
--nm=NM: 使用nm工具NM进行符号表处理，默认为nm -g。
--ar=AR: 使用ar工具AR进行静态库打包，默认为ar。
--as=AS: 使用汇编器AS，默认为空。
--ln_s=LN_S: 使用符号链接工具LN_S，默认为ln -s -f。
--strip=STRIP: 使用strip工具STRIP进行剥离，默认为strip。
--windres=WINDRES: 使用Windows资源编译器WINDRES，默认为windres。
--x86asmexe=EXE: 使用与nasm兼容的汇编器EXE，默认为nasm。
--cc=CC: 使用C编译器CC，默认为gcc。
--cxx=CXX: 使用C++编译器CXX，默认为g++。
--objcc=OCC: 使用ObjC编译器OCC，默认为gcc。
--dep-cc=DEPCC: 使用依赖性生成器DEPCC，默认为gcc。
--nvcc=NVCC: 使用Nvidia CUDA编译器NVCC或clang。
--ld=LD: 使用链接器LD，默认为空。
--metalcc=METALCC: 使用Metal编译器METALCC，用于macOS平台，默认为xcrun -sdk macosx metal。
--metallib=METALLIB: 使用Metal链接器METALLIB，用于macOS平台，默认为xcrun -sdk macosx metallib。
--pkg-config=PKGCONFIG: 使用pkg-config工具PKGCONFIG，用于配置库的编译和链接选项，默认为pkg-config。
--pkg-config-flags=FLAGS: 传递额外的标志给pkgconf工具，用于配置库的编译和链接选项。
--ranlib=RANLIB: 使用ranlib工具RANLIB，用于创建静态库的索引，默认为ranlib。
--doxygen=DOXYGEN: 使用DOXYGEN生成API文档，默认为doxygen。
--host-cc=HOSTCC: 使用主机C编译器HOSTCC。
--host-cflags=HCFLAGS: 在为主机编译时使用HCFLAGS。
--host-cppflags=HCPPFLAGS: 在为主机编译时使用HCPPFLAGS。
--host-ld=HOSTLD: 使用主机链接器HOSTLD。
--host-ldflags=HLDFLAGS: 在为主机链接时使用HLDFLAGS。
--host-extralibs=HLIBS: 在为主机链接时使用HLIBS。
--host-os=OS: 主机操作系统。
--extra-cflags=ECFLAGS: 将ECFLAGS添加到CFLAGS中，额外的C编译选项。
--extra-cxxflags=ECFLAGS: 将ECFLAGS添加到CXXFLAGS中，额外的C++编译选项。
--extra-objcflags=FLAGS: 将FLAGS添加到OBJCFLAGS中，额外的Objective-C编译选项。
--extra-ldflags=ELDFLAGS: 将ELDFLAGS添加到LDFLAGS中，额外的链接选项。
--extra-ldexeflags=ELDFLAGS: 将ELDFLAGS添加到LDEXEFLAGS中，额外的可执行文件链接选项。
--extra-ldsoflags=ELDFLAGS: 将ELDFLAGS添加到LDSOFLAGS中，额外的共享库链接选项。
--extra-libs=ELIBS: 添加ELIBS中的额外库。
--extra-version=STRING: 版本字符串后缀。
--optflags=OPTFLAGS: 覆盖与优化相关的编译器标志。
--nvccflags=NVCCFLAGS: 覆盖nvcc的标志。
--build-suffix=SUFFIX: 库名称后缀。
--enable-pic: 构建位置无关代码。
--enable-thumb: 编译为Thumb指令集。
--enable-lto[=arg]: 使用链接时优化。
--env="ENV=override": 覆盖环境变量。
这些选项用于配置工具链和编译器的参数，以便根据特定需求进行编译。

下面是FFmpeg中高级选项的解释：
--malloc-prefix=PREFIX: 使用PREFIX作为malloc和相关函数的前缀。
--custom-allocator=NAME: 使用支持的自定义分配器。
--disable-symver: 禁用符号版本控制。
--enable-hardcoded-tables: 使用预先编译的表而不是运行时生成。
--disable-safe-bitstream-reader: 在比特流读取器中禁用缓冲区边界检查（更快，但可能导致崩溃）。
--sws-max-filter-size=N: swscale使用的最大滤波器大小，默认为256。
这些选项属于FFmpeg的高级配置选项，通常适用于对FFmpeg库有深入了解的专业人士。它们允许更精细的控制和优化FFmpeg的行为。对于一般用户来说，这些选项很少使用，而是依赖于默认设置。

下面是FFmpeg中优化选项的解释：
--disable-asm: 禁用所有汇编优化。
--disable-altivec: 禁用AltiVec优化（适用于PowerPC架构）。
--disable-vsx: 禁用VSX优化（适用于PowerPC架构）。
--disable-power8: 禁用POWER8优化（适用于PowerPC架构）。
--disable-amd3dnow: 禁用3DNow!优化（适用于AMD x86架构）。
--disable-amd3dnowext: 禁用3DNow!扩展优化（适用于AMD x86架构）。
--disable-mmx: 禁用MMX优化（多媒体扩展，适用于x86架构）。
--disable-mmxext: 禁用MMXEXT优化（MMX扩展，适用于x86架构）。
--disable-sse: 禁用SSE优化（流式SIMD扩展，适用于x86架构）。
--disable-sse2: 禁用SSE2优化（流式SIMD扩展2，适用于x86架构）。
--disable-sse3: 禁用SSE3优化（流式SIMD扩展3，适用于x86架构）。
--disable-ssse3: 禁用SSSE3优化（逐位SIMD扩展3，适用于x86架构）。
--disable-sse4: 禁用SSE4优化（流式SIMD扩展4，适用于x86架构）。
--disable-sse42: 禁用SSE4.2优化（流式SIMD扩展4.2，适用于x86架构）。
--disable-avx: 禁用AVX优化（高级矢量扩展，适用于x86架构）。
--disable-xop: 禁用XOP优化（扩展操作集，适用于x86架构）。
--disable-fma3: 禁用FMA3优化（浮点乘加操作，适用于x86架构）。
--disable-fma4: 禁用FMA4优化（浮点乘加操作4，适用于x86架构）。
--disable-avx2: 禁用AVX2优化（高级矢量扩展2，适用于x86架构）。
--disable-avx512: 禁用AVX-512优化（高级矢量扩展-512，适用于x86架构）。
--disable-avx512icl: 禁用AVX-512ICL优化（高级矢量扩展-512ICL，适用于x86架构）。
--disable-aesni: 禁用AESNI优化（高级加密标准新指令集，适用于x86架构）。
--disable-armv5te: 禁用armv5te优化（适用于ARM架构）。
--disable-armv6: 禁用armv6优化（适用于ARM架构）。
--disable-armv6t2: 禁用armv6t2优化（适用于ARM架构）。
--disable-vfp: 禁用VFP（Vector Floating Point）优化（适用于ARM架构）。
--disable-neon: 禁用NEON（ARM架构上的SIMD指令集）优化（适用于ARM架构）。
--disable-inline-asm: 禁用内联汇编。
--disable-x86asm: 禁用独立的x86汇编。
--disable-mipsdsp: 禁用MIPS DSP ASE R1（数字信号处理指令集）优化（适用于MIPS架构）。
--disable-mipsdspr2: 禁用MIPS DSP ASE R2（数字信号处理指令集）优化（适用于MIPS架构）。
--disable-msa: 禁用MSA（MIPS SIMD Architecture）优化（适用于MIPS架构）。
--disable-mipsfpu: 禁用MIPS浮点数优化（适用于MIPS架构）。
--disable-mmi: 禁用Loongson MMI（Multimedia Instructions）优化（适用于Loongson架构）。
--disable-lsx: 禁用Loongson LSX（指令集扩展）优化（适用于Loongson架构）。
--disable-lasx: 禁用Loongson LASX（指令集扩展）优化（适用于Loongson架构）。
--disable-rvv: 禁用RISC-V Vector（向量指令集）优化（适用于RISC-V架构）。
--disable-fast-unaligned: 将非对齐访问视为较慢的操作。
这些选项用于控制在编译FFmpeg时应用的特定硬件优化。禁用某些优化可以在某些情况下确保兼容性或简化构建过程，但可能会导致性能下降。这些优化选项通常是为专业人士和高级用户准备的，并且需要针对特定的硬件架构进行编译。对于一般用户来说，默认的优化选项通常已经足够了。默认情况下，FFmpeg会尝试根据目标架构自动启用适当的优化。

下面是FFmpeg中一些开发者选项的解释：
--disable-debug: 禁用调试符号。
--enable-debug=LEVEL: 设置调试级别。LEVEL是一个可选参数，用于指定调试级别的详细程度。
--disable-optimizations: 禁用编译器优化。
--enable-extra-warnings: 启用更多的编译器警告。
--disable-stripping: 禁用对可执行文件和共享库的剥离。
--assert-level=level: 设置断言测试的级别。可选级别为0（默认）、1或2，级别越高运行时的速度越慢。
--enable-memory-poisoning: 使用任意数据填充堆中未初始化的分配空间。
--valgrind=VALGRIND: 通过valgrind运行“make fate”测试，以检测内存泄漏和错误。指定valgrind二进制文件的路径。不能与–target-exec参数组合使用。
--enable-ftrapv: 检测算术溢出。
--samples=PATH: 设置FATE测试样本的位置。如果未设置，则使用在执行make命令时指定的$FATE_SAMPLES路径。
--enable-neon-clobber-test: 检查NEON寄存器是否被破坏（仅用于调试目的）。
--enable-xmm-clobber-test: 检查XMM寄存器是否被破坏（仅在Win64环境下使用，仅用于调试目的）。
--enable-random、--disable-random、--enable-random=LIST、--disable-random=LIST: 随机启用/禁用组件。可以随机启用或禁用特定的组件或组件组。LIST是一个逗号分隔的列表，包含NAME[:PROB]条目，其中NAME是组件（组）的名称，PROB是与NAME关联的概率（默认为0.5）。
--random-seed=VALUE: 设置用于–enable/disable-random的种子值。
--disable-valgrind-backtrace: 在Valgrind下不打印回溯信息（仅适用于禁用优化构建）。
--enable-ossfuzz: 启用fuzzer工具的构建。
--libfuzzer=PATH: 指定libfuzzer的路径。
--ignore-tests=TESTS: 忽略测试结果的测试列表，用逗号分隔（不包括名称前缀中的"fate-"）。
--enable-linux-perf: 启用Linux性能监视器API。
--enable-macos-kperf: 启用macOS kperf（私有）API。
--disable-large-tests: 禁用使用大量内存的测试。
--disable-ptx-compression: 即使有可能，也不要压缩CUDA PTX代码。
这些选项在开发FFmpeg本身时非常有用，可以控制调试、优化、测试和构建过程中的各种行为和功能。普通用户通常不需要使用这些选项。
```