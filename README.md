# ffmpeg-command
```
https://github.com/yangkun19921001/AVFFmpegLib
```

## cpu_features
```
https://github.com/google/cpu_features/tags => v0.7.0
```

## pcm to audio
```
1.pcm => wav
ffmpeg.exe -f s16le -ar 8000 -ac 1 -i audio.pcm out.wav
2.pcm => amr
ffmpeg.exe -f s16le -ar 8000 -ac 1 -i audio.pcm out.amr
3.pcm => aac
ffmpeg.exe -f s16le -ar 8000 -ac 1 -i audio.pcm out.aac
4.pcm => mp3
ffmpeg.exe -f s16le -ar 8000 -ac 1 -i audio.pcm out.mp3
```