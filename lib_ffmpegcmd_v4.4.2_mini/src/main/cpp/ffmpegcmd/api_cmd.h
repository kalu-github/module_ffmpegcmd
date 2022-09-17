#ifndef FFMPEGCMD_FFMPEG_CMD_H
#define FFMPEGCMD_FFMPEG_CMD_H
//#include <cstdint>
//小于0失败,>0成功
#ifdef __cplusplus

extern "C" {
#endif

//小于0失败,>0成功
//int
//executeFFmpegCommand(int64_t handle, const char * command, void (*progressCallBack)(int64_t, int, float));

int
executeCommand(int64_t handle, const char * command,
                               void (*progressCallBack)(int64_t, int, float),
                               int64_t totalTime);

int cancelCommand();

#ifdef __cplusplus
}
#endif

#endif //FFMPEGCMD_FFMPEG_CMD_H
