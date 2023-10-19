#
#### libffmepgcmd.so编译源码
```
https://github.com/kalu-github/ffmpegcmd_android_build
```

#
#### 编译环境
```
deepin + ndk14b
```

#
#### 更新记录
```
2022-1-04
1. 新增ffmpeg4.4.3 arm64
```
```
2022-10-31
1. 新增ffmpeg4.4.3
```
```
2022-09-18
1. 新增ffmpeg4.4.2、ffmpeg5.1.1
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

ffmpeg编译参数对比
精简版	增强版
--enable-gpl	+
--enable-shared	+
--disable-static	+
--enable-version3	+
--enable-pthreads	+
--enable-small	+
--disable-vda	+
--disable-iconv	+
--disable-encoders	--enable-encoders
--enable-libx264	+
--enable-neon	+
--enable-yasm	+
--enable-libfdk_aac	+
--enable-encoder=libx264	+
--enable-encoder=libfdk_aac	+
--enable-encoder=mjpeg	+
--enable-encoder=png	+
--enable-nonfree	+
--enable-muxers	+
--enable-muxer=mov	-
--enable-muxer=mp4	-
--enable-muxer=h264	-
--enable-muxer=avi	-
--disable-decoders	--enable-decoders
--enable-decoder=aac	-
--enable-decoder=aac_latm	-
--enable-decoder=h264	-
--enable-decoder=mpeg4	-
--enable-decoder=mjpeg	-
--enable-decoder=png	-
--disable-demuxers	--enable-demuxers
--enable-demuxer=image2	-
--enable-demuxer=h264	-
--enable-demuxer=aac	-
--enable-demuxer=avi	-
--enable-demuxer=mpc	-
--enable-demuxer=mpegts	-
--enable-demuxer=mov	-
--disable-parsers	--enable-parsers
--enable-parser=aac	-
--enable-parser=ac3	-
--enable-parser=h264	-
--enable-protocols	+
--enable-zlib	+
--enable-avfilter	+
--disable-outdevs	+
--disable-ffprobe	+
--disable-ffplay	+
--disable-ffmpeg	+
--disable-ffserver	+
--disable-debug	+
--disable-postproc	+
--disable-avdevice	+
--disable-symver	+
--disable-stripping	+