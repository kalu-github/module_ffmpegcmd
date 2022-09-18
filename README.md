#
####  ffmpegcmd-so => 编译源码 => [链接](https://github.com/kalu-github/ffmpegcmd-android)

#
####  v4.4.2 => 支持模块 => [链接](https://github.com/kalu-github/ffmpeg_command/blob/master/README_v4.4.2.md)

#
####  v4.4.2-mini => 支持模块 => [链接](https://github.com/kalu-github/ffmpeg_command/blob/master/README_v4.4.2_mini.md)

#
####  v5.1.1 => 支持模块 => [链接](https://github.com/kalu-github/ffmpeg_command/blob/master/README_v5.1.1.md)

#
####  v5.1.1-mini => 支持模块 => [链接](https://github.com/kalu-github/ffmpeg_command/blob/master/README_v5.1.1_mini.md)

#
####  已知问题
```
1. 5.1.1版本不支持 -f -lavfi命令【编译问题？？】
```

#
## FFmpeg常用命令
```
## 音频转码
1.pcm => wav
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.wav
2.pcm => amr
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.amr
3.pcm => aac
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.aac
4.pcm => mp3
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.mp3
```
```
## 音频混音
1.mp3、2.mp3、3.mp3、4.mp3 混音到 from.mp3 生成 result.mp3
ffmpeg -y -i from.mp3 -i 1.mp3 -i 2.mp3 -i 3.mp3 -i 4.mp3 -filter_complex [1]adelay=10000|10000[a1];[2]adelay=20000|20000[a2];[3]adelay=30000|30000[a3];[4]adelay=40000|40000[a4];[0][a1][a2][a3][a4]amix=5 result.mp3
```
```
## 视频混音
1.mp3、2.mp3、3.mp3、4.mp3 混音到 from.mp4 生成 result.mp4
ffmpeg -y -i from.mp4 -i 1.mp3 -i 2.mp3 -i 3.mp3 -i 4.mp3 -filter_complex [1]adelay=10000|10000[a1];[2]adelay=20000|20000[a2];[3]adelay=30000|30000[a3];[4]adelay=40000|40000[a4];[0][a1][a2][a3][a4]amix=5 result.mp4
```
```
## 音频-采样数
"-ar", // 采样数
"22050",
```
```
## 音频-码率
"-b:a", // 音频码率
"48000",
```
```
## 音频-降噪
"-af",
"asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40",
```

#
## 历史版本
```
1. https://www.videohelp.com/software/ffmpeg/old-versions
2. http://www.ffmpeg.org/releases/
```

#
## 更新记录
```
20220415
自动获取媒资时长, 处理过程自动传递总时长、处理时长、处理进度百分比3个字段
void onProgress(@NonNull long duration, @NonNull long position, @NonNull float progress);
```

#
#### 模块
```
libavutil：
核心工具库，该模块是最基本的模块之一，其它这么多模块会依赖此模块做一些音视频处理操作。
```
```
libavformat： 
文件格式和协议库，该模块是最重要的模块之一，封装了Protocol层、Demuxer层、muxer层，使用协议和格式对于开发者是透明的。
```
```
libavcodec: 
编解码库，该模块也是最重要模块之一，封装了Codec层，但是有一些Codec是具备自己的License的，FFmpeg是不会默认添加，例如libx264,FDK-AAC, lame等库，但FFmpeg就像一个平台一样，可以将其它的第三方的Codec以插件的方式添加进来，然后为开发者提供统一的接口。
```
```
libswrsample：
音频重采样库，可以对数字音频进行声道数、数据格式、采样率等多种基本信息的转换。
```
```
libswscale：
视频压缩和格式转换库，可以进行视频分辨率修改、YUV格式数据与RGB格式数据互换。
```
```
libavdevice：
输入输出设备库，编译ffplay就需要确保该模块是打开的，时时也需要libSDL预先编译，因为该设备播放声音和播放视频使用的都是libSDL库。
```
```
libavfilter:
音视频滤镜库，该模块包含了音频特效和视频特效的处理，在使用FFmpeg的API进行编解码的过程中，直接使用该模块为音视频数据做特效物理非常方便同时也非常高效的一种方式。
```
```
libpostproc:
音视频后期处理库，当使用libavfilter的时候需要打开该模块开关，因为Filter中会使用该库中的一些基础函数。
```
