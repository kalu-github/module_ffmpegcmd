package lib.kalu.ffmpegcmd;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FFmpegUtil {

//    /**
//     * ffmpeg.exe -i "D:\in.mp4" -c:v libx264 -preset superfast -x264opts keyint=25 -acodec copy -f mp4 "D:\out.mp4"
//     *
//     * @param fromPath
//     * @param toPath
//     * @return
//     */
//    public static String addI(String fromPath, String toPath) {
//        try {
//            ArrayList<String> arrays = new ArrayList<>();
//            arrays.add("-y");
//            arrays.add("-i");
//            arrays.add(fromPath);
//            arrays.add("-c:v");
//            arrays.add("libx264");
//            arrays.add("-preset");
//            arrays.add("superfast");
//            arrays.add("-x264opts");
//            arrays.add("keyint=25");
//            arrays.add("-acodec");
//            arrays.add("copy");
//            arrays.add("-f");
//            arrays.add("mp4");
//            arrays.add(toPath);
//            int execute = FFmpeg.executeCmd(arrays);
//            if (execute != 0)
//                throw new IllegalArgumentException("ffmpeg fail");
//            Log.e("FFmpegUtil", "addI => result = " + toPath);
//            return toPath + "index.m3u8";
//        } catch (Exception e) {
//            Log.e("FFmpegUtil", "addI => fail");
//            return null;
//        }
//    }
//
//    /**
//     * 先用ffmpeg把abc.mp4文件转换为abc.ts文件：
//     * <p>
//     * ffmpeg -y -i abc.mp4 -vcodec copy -acodec copy -vbsf h264_mp4toannexb abc.ts
//     * <p>
//     * <p>
//     * <p>
//     * 再用ffmpeg把abc.ts文件切片并生成playlist.m3u8文件，5秒一个切片：
//     * <p>
//     * ffmpeg -i abc.ts -c copy -map 0 -f segment -segment_list playlist.m3u8 -segment_time 5 abc%03d.ts
//     * <p>
//     * <p>
//     * ffmpeg -i Wildlife.wmv -codec:v libx264 -codec:a mp3 -map 0 -f ssegment -segment_format mpegts -segment_list ./m3u8/index.m3u8 -segment_time 10 ./m3u8/’%03d.ts’
//     *
//     * @return
//     */
//    public static String clipTS(String fromPath, String toPath) {
//        try {
//            Log.e("FFmpegUtil", "clipTS => fromPath = " + fromPath);
//            Log.e("FFmpegUtil", "clipTS => toPath = " + toPath);
//            if (!toPath.endsWith(File.separator))
//                throw new Exception("'" + toPath + "' is not endsWith '/'");
//            ArrayList<String> arrays = new ArrayList<>();
//            arrays.add("-y");
//            arrays.add("-i");
//            arrays.add(fromPath);
//            arrays.add("-codec:v");
//            arrays.add("libx264");
//            arrays.add("-codec:a");
//            arrays.add("mp3");
//            arrays.add("-map");
//            arrays.add("0");
//            arrays.add("-f");
//            arrays.add("segment");
//            arrays.add("-segment_format");
//            arrays.add("mpegts");
//            arrays.add("-segment_list");
//            arrays.add(toPath + "index.m3u8");
//            arrays.add("-segment_time");
//            arrays.add("1");
//            arrays.add(toPath + "’%03d.ts");
//            int execute = FFmpeg.executeCmd(arrays);
//            if (execute != 0)
//                throw new IllegalArgumentException("ffmpeg fail");
//            Log.e("FFmpegUtil", "clipTS => result = " + toPath + "index.m3u8");
//            return toPath + "index.m3u8";
//        } catch (Exception e) {
//            Log.e("FFmpegUtil", "clipTS => " + e.getMessage());
//            return null;
//        }
//    }

    public static boolean clipAudio(String videoPath, String audioPath) {
        return clipAudio(videoPath, audioPath, null);
    }

    public static boolean clipAudio(String videoPath, String audioPath, OnFFmpegChangeListener listener) {
        try {
            Log.e("FFmpegUtil", "clipAudio => videoPath = " + videoPath + ", audioPath = " + audioPath);
            if (null == videoPath || videoPath.length() == 0)
                throw new Exception("videoPath error: " + videoPath);
            if (null == audioPath || audioPath.length() == 0)
                throw new Exception("audioPath error: " + audioPath);
            File fileVideo = new File(videoPath);
            if (!fileVideo.exists())
                throw new Exception("fileVideo error: not exists");
            File fileAudio = new File(audioPath);
            if (fileAudio.exists()) {
                Log.e("FFmpegUtil", "clipAudio => delete fileAudio");
                fileAudio.delete();
            }
            // ffmpeg -i input.mp4 -vn -b:a 128k -ar 44k -c:a mp3 output.mp3
            ArrayList<String> arrays = new ArrayList<>();
            arrays.add("-y");
            arrays.add("-i");
            arrays.add(videoPath);
            arrays.add("-vn");
            arrays.add("-b:a"); // 音频码率
            arrays.add("48000");
            arrays.add("-ar"); // 采样数
            arrays.add("16000");
            arrays.add("-c:a");
            arrays.add("mp3");
            arrays.add("-af"); // -af volume=-10dB
            arrays.add("volume=-20dB");
            arrays.add(audioPath);
            int execute;
            if (null != listener) {
                execute = FFmpeg.executeCmd(arrays, listener);
            } else {
                execute = FFmpeg.executeCmd(arrays);
            }
            if (execute != 0)
                throw new IllegalArgumentException("execute error: " + execute);
            Log.e("FFmpegUtil", "clipAudio => succ");
            return true;
        } catch (Exception e) {
            Log.e("FFmpegUtil", "clipAudio => " + e.getMessage());
            return false;
        }
    }

    public static boolean createNullAudio(float second, String audioPath) {
        return createNullAudio(second, audioPath, null);
    }

    public static boolean createNullAudio(float second, String audioPath, OnFFmpegChangeListener listener) {
        try {
            Log.e("FFmpegUtil", "createNullAudio => second = " + second + ", audioPath = " + audioPath);
            if (second <= 0)
                throw new Exception("second error: " + second);
            File fileAudio = new File(audioPath);
            if (fileAudio.exists()) {
                Log.e("FFmpegUtil", "createNullAudio => delete fileAudio");
                fileAudio.delete();
            }
            // ffmpeg -y -f lavfi -i anullsrc -t 20 D:\\ffmpeg_test\\silence.wav
            ArrayList<String> arrays = new ArrayList<>();
            arrays.add("-y");
            arrays.add("-f");
            arrays.add("lavfi");
            arrays.add("-i");
            arrays.add("anullsrc");
            arrays.add("-t");
            arrays.add(String.valueOf(second));
            arrays.add("-ar"); // 采样数
            arrays.add("16000");
            arrays.add("-b:a"); // 音频码率
            arrays.add("48000");
//            arrays.add("-af"); // 降噪
//            arrays.add("asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40");
            arrays.add(audioPath);
            int execute;
            if (null != listener) {
                execute = FFmpeg.executeCmd(arrays, listener);
            } else {
                execute = FFmpeg.executeCmd(arrays);
            }
            if (execute != 0)
                throw new Exception("execute error: " + execute);
            Log.e("FFmpegUtil", "createNullAudio => succ");
            return true;
        } catch (Exception e) {
            Log.e("FFmpegUtil", "createNullAudio => " + e.getMessage());
            return false;
        }
    }

    public static boolean mixAudio(String fromAudioPath, String mixAudioPath, LinkedHashMap<Integer, String> map) {
        return mixAudio(fromAudioPath, mixAudioPath, map, null);
    }

    public static boolean mixAudio(String fromAudioPath, String mixAudioPath, LinkedHashMap<Integer, String> map, OnFFmpegChangeListener listener) {
        try {
            Log.e("FFmpegUtil", "mixAudio => fromAudioPath = " + fromAudioPath + ", mixAudioPath = " + mixAudioPath);
            if (null == fromAudioPath || fromAudioPath.length() == 0)
                throw new Exception("fromAudioPath error: " + fromAudioPath);
            if (null == mixAudioPath || mixAudioPath.length() == 0)
                throw new Exception("mixAudioPath error: " + mixAudioPath);
            if (null == map || map.isEmpty())
                throw new Exception("map error: " + map);
            File fromFile = new File(fromAudioPath);
            if (!fromFile.exists())
                throw new Exception("fromFile error: not exists");
            File mixFile = new File(mixAudioPath);
            if (mixFile.exists()) {
                Log.e("FFmpegUtil", "mixAudio => delete mixFile");
                mixFile.delete();
            }
            ArrayList<String> arrays = new ArrayList<>();
            arrays.add("-y");
//            arrays.add("-vol");
//            arrays.add("1000");
            arrays.add("-i");
            arrays.add(fromAudioPath);

            // 2 => append-cmd
            int index = 0;
            StringBuilder cmd = new StringBuilder();
            StringBuilder suffix = new StringBuilder();
            suffix.append("[0]");

            Set<Map.Entry<Integer, String>> entries = map.entrySet();
            for (Map.Entry<Integer, String> entry : entries) {

                Integer start = entry.getKey();
                String path = entry.getValue();
                Log.e("FFmpegUtil", "mixAudio => i = " + index + ", start = " + start + ", path = " + path);
                if (start <= 0 || null == path || path.length() == 0)
                    continue;
                File file = new File(path);
                if (null == file || !file.exists())
                    continue;

                // a
                index = index + 1;
                suffix.append("[a" + index + "]");
                // b
                arrays.add("-i");
                arrays.add(path);
                // c
                cmd.append("[" + index + "]adelay=" + start + "|" + start + "[a" + index + "];");
            }

            int count = index + 1;
            suffix.append("amix=" + count);

            String stringCmd = cmd.toString();
            String stringSuffix = suffix.toString();
            arrays.add("-filter_complex");
            arrays.add(stringCmd + stringSuffix);
            arrays.add("-ar"); // 采样数
            arrays.add("44100");
            arrays.add("-b:a"); // 音频码率
            arrays.add("48000");
//            arrays.add("-af"); // 降噪
//            arrays.add("asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40");
            arrays.add(mixAudioPath);
            int execute;
            if (null != listener) {
                execute = FFmpeg.executeCmd(arrays, listener);
            } else {
                execute = FFmpeg.executeCmd(arrays);
            }
            if (execute != 0)
                throw new Exception("execute error: " + execute);
            Log.e("FFmpegUtil", "mixAudio => succ");
            return true;
        } catch (Exception e) {
            Log.e("FFmpegUtil", "mixAudio => " + e.getMessage());
            return false;
        }
    }

    public static boolean pcmToMp3(String pcmPath, String mp3Path) {
        return pcmToMp3(pcmPath, mp3Path, null);
    }

    public static boolean pcmToMp3(String pcmPath, String mp3Path, OnFFmpegChangeListener listener) {
        try {
            Log.e("FFmpegUtil", "pcmToMp3 => pcmPath = " + pcmPath + ", mp3Path = " + mp3Path);
            if (null == pcmPath || pcmPath.length() == 0)
                throw new Exception("pcmPath error: " + pcmPath);
            if (null == mp3Path || mp3Path.length() == 0)
                throw new Exception("mp3Path error: " + mp3Path);
            File pcmFile = new File(pcmPath);
            if (!pcmFile.exists())
                throw new Exception("pcmFile error: not exists");
            File mp3File = new File(mp3Path);
            if (mp3File.exists()) {
                Log.e("FFmpegUtil", "pcmToMp3 => delete mp3File");
                mp3File.delete();
            }
            List<String> arrays = Arrays.asList(
                    "ffmpeg",
                    "-y",
                    "-vol",
                    "1000",
                    "-f",
                    "s16le",
                    "-ac",
                    "1",
                    "-ar",
                    "16000",
                    "-acodec",
                    "pcm_s16le",
                    "-i",
                    pcmPath,
                    "-ar", // 采样数
                    "44100",
                    "-b:a", // 音频码率
                    "48000",
//                    "-af",
//                    "asendcmd=0.0 afftdn sn start,asendcmd=0.4 afftdn sn stop,afftdn=nr=20:nf=-40",
                    mp3Path);
            int execute;
            if (null != listener) {
                execute = FFmpeg.executeCmd(arrays, listener);
            } else {
                execute = FFmpeg.executeCmd(arrays);
            }
            if (execute != 0)
                throw new Exception("execute error: " + execute);
            Log.e("FFmpegUtil", "pcmToMp3 => succ");
            return true;
        } catch (Exception e) {
            Log.e("FFmpegUtil", "pcmToMp3 => " + e.getMessage());
            return false;
        }
    }

    public static boolean compressVideo(String fromPath, String savePath, OnFFmpegChangeListener listener) {
        try {
            Log.e("FFmpegUtil", "scaleVideo => fromPath = " + fromPath + ", savePath = " + savePath);
            if (null == fromPath || fromPath.length() == 0)
                throw new Exception("error: fromPath null");
            if (null == savePath || savePath.length() == 0)
                throw new Exception("error: savePath null");
            File fromFile = new File(fromPath);
            if (!fromFile.exists())
                throw new Exception("error: fromFile not exists");
            File saveFile = new File(savePath);
            if (saveFile.exists()) {
                saveFile.delete();
            }
            List<String> arrays = Arrays.asList(
                    "ffmpeg",
                    "-y",
                    "-i",
                    fromPath,
                    "-r", // 目标帧率设置为10fps。
                    "10",
                    "-c:v", // 视频编码器 x264
                    "libx264",
                    "-b:v", // 视频码率
                    "1000k",
                    "-bufsize",  // 视频码率控制缓冲器的大小
                    "1000k",
                    "-c:a", // 音频编码器 libmp3lame
                    "libmp3lame",
                    "-b:a",  // 音频码率
                    "16k",
                    "-preset", // 选择快速编码预设。
                    "veryfast",
                    "-crf",
                    "50", // -crf 28：设置CRF值（常量速率因子），范围从0（无损）到51（最糟），通常使用18到28的值。
                    savePath);
            int execute;
            if (null != listener) {
                execute = FFmpeg.executeCmd(arrays, listener);
            } else {
                execute = FFmpeg.executeCmd(arrays);
            }
            if (execute != 0)
                throw new Exception("execute error: " + execute);
            Log.e("FFmpegUtil", "compressVideo => succ");
            return true;
        } catch (Exception e) {
            Log.e("FFmpegUtil", "compressVideo => " + e.getMessage());
            return false;
        }
    }
}
