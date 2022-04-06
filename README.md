## 说明
```
standard => 支持解码，无扩展库
audio => 支持编码、解码，有音频扩展库
full => 支持编码、解码，有音频、视频扩展库
```

## 音频转码
```
1.pcm => wav
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.wav
2.pcm => amr
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.amr
3.pcm => aac
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.aac
4.pcm => mp3
ffmpeg -y -f s16le -ar 16000 -ac 1 -acodec pcm_s16le -i audio.pcm out.mp3
```

## 音频混音
```
1.mp3、2.mp3、3.mp3、4.mp3 混音到 from.mp3 生成 result.mp3
ffmpeg -y -i from.mp3 -i 1.mp3 -i 2.mp3 -i 3.mp3 -i 4.mp3 -filter_complex [1]adelay=10000|10000[a1];[2]adelay=20000|20000[a2];[3]adelay=30000|30000[a3];[4]adelay=40000|40000[a4];[0][a1][a2][a3][a4]amix=5 result.mp3
```

## 视频混音
```
1.mp3、2.mp3、3.mp3、4.mp3 混音到 from.mp4 生成 result.mp4
ffmpeg -y -i from.mp4 -i 1.mp3 -i 2.mp3 -i 3.mp3 -i 4.mp3 -filter_complex [1]adelay=10000|10000[a1];[2]adelay=20000|20000[a2];[3]adelay=30000|30000[a3];[4]adelay=40000|40000[a4];[0][a1][a2][a3][a4]amix=5 result.mp4
```

## 音频-采样数
```
"-ar", // 采样数
"22050",
```

## 音频-码率
```
"-b:a", // 音频码率
"48000",
```

## 音频-降噪
```
"-af",
"asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40",
```
