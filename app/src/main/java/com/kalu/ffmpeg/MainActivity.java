package com.kalu.ffmpeg;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import lib.kalu.ffmpegcmd.FFmpeg;
import lib.kalu.ffmpegcmd.OnFFmpegChangeListener;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FFmpeg.setLogger(true);

        String version = FFmpeg.getVersion();
        ((TextView) findViewById(R.id.ffmpeg_version)).setText(version);
        String information = FFmpeg.getInformation();
        ((TextView) findViewById(R.id.information_version)).setText(information);
        String details = FFmpeg.getDetails();
        ((TextView) findViewById(R.id.details_version)).setText(details);

        new Thread(new Runnable() {
            @Override
            public void run() {

                // 1
                init();

                // 2
//                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String v25Path = absolutePath + "/v25.mp4";
//                long duration = FFmpeg.getDuration(v25Path);
//                Log.e("ffmpeg_main", "getDuration = " + duration);

                // 3
//                String avInputFormats = FFmpeg.getAVInputFormats();
//                CacheUtil.setCache(getApplicationContext(), "v5_formats", avInputFormats);
//                Log.e("ffmpeg_main", "getAVInputFormats = " + avInputFormats);

//                // 3
//                String avCodecs = FFmpeg.getAVCodecs();
//                CacheUtil.setCache(getApplicationContext(), "v5_codes", avCodecs);
//                Log.e("ffmpeg_main", "getAVCodecs = " + avCodecs);
//
//                String protocols = FFmpeg.getProtocols();
//                CacheUtil.setCache(getApplicationContext(), "v5_protocols", protocols);
//                Log.e("ffmpeg_main", "getProtocols = " + protocols);
//
//                String fIltersJNI = FFmpeg.getFilters();
//                CacheUtil.setCache(getApplicationContext(), "v5_filters", fIltersJNI);
//                Log.e("ffmpeg_main", "getFilters = " + fIltersJNI);
            }
        }).start();

        // 初始化
        findViewById(R.id.button_process_init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        // i
        findViewById(R.id.button_is).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String fromPath = absolutePath + "/v25.mp4";
                String toPath = absolutePath + "/v25_i.mp4";
                String s = FFmpegUtils.toM3u8(fromPath, toPath);
                Log.e("FFM", "cmd => " + s);
            }
        });
        // ts
        findViewById(R.id.button_ts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String path = absolutePath + "/v25.mp4";
                String s = FFmpegUtils.toM3u8(path, absolutePath + "/");
                Log.e("FFM", "cmd => " + s);
            }
        });

        // button_create_null_mp3
        findViewById(R.id.button_create_null_mp3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = getCacheDir().getAbsolutePath() + "/null_" + System.nanoTime() + ".mp3";
                FFmpegUtils.createNullMusic(getApplicationContext(), 300.456F, path);
            }
        });

        // pcm to aac
        // ffmpeg -y -f s16be -ac 1 -ar 16000 -acodec pcm_s16le -i /data/user/0/com.hw.hear/files/a6.pcm /data/user/0/com.hw.hear/files/new_mp3.mp3
        findViewById(R.id.button_process_pcm1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String xiri = absolutePath + "/xiri.pcm";
                String xiri_cmd = absolutePath + "/" + "xiri_cmd.aac";

                List<String> asList = Arrays.asList(
                        "ffmpeg",
                        "-y",
                        "-f",
                        "s16le",
                        "-ac",
                        "1",
                        "-ar",
                        "16000",
                        "-acodec",
                        "pcm_s16le",
                        "-i",
                        xiri,
                        xiri_cmd);

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < asList.size(); i++) {
                    builder.append(asList.get(i));
                    builder.append(" ");
                }

                String cmds = builder.toString();
                Log.e("FFM", "cmd => " + cmds);
                startFFmpeg(asList);
            }
        });
        findViewById(R.id.button_process_pcm1_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String xiri = absolutePath + "/xiri.pcm";
                playPCM(xiri);
            }
        });
        findViewById(R.id.button_process_pcm1_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
                String xiri_cmd = absolutePath + "/" + "xiri_cmd.aac";
                playMP3(xiri_cmd);
            }
        });

        findViewById(R.id.button_process_pcm2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String a7 = absolutePath + "/a7.pcm";
//                String a7_cmd = absolutePath + "/a7_" + System.nanoTime() + ".mp3";
                String a7_cmd = absolutePath + "/a7_cmd.mp3";
                FFmpegUtils.pcmToMp3(a7, a7_cmd);
//                startFFmpeg(asList);
            }
        });
        findViewById(R.id.button_process_pcm2_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String a7 = absolutePath + "/a7.pcm";
                playPCM(a7);
            }
        });
        findViewById(R.id.button_process_pcm2_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String a7_cmd = absolutePath + "/a7_cmd.mp3";
                playMP3(a7_cmd);
            }
        });

        // 指定音频位置混音
        // ffmpeg -y -i a8.mp3 -i a9.mp3 -i a9.mp3 -i a9.mp3 -filter_complex  '[1]adelay=10000|10000[1_];  [2]adelay=20000|20000[2_];  [3]adelay=30000|30000[3_];  [0][1_][2_][3_]amix=4'  apptend.mp3
        findViewById(R.id.button_process_mp3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";

                String mp3_a8 = absolutePath + "/a8.mp3";
                String mp3_a9 = absolutePath + "/a9.mp3";
                String mp3_a1 = absolutePath + "/a1.mp3";
                String mp3_a2 = absolutePath + "/a2.mp3";
                String mp3_a3 = absolutePath + "/a3.mp3";
                String mp3_a8_cmd = absolutePath + "/" + "a8_cmd.mp3";

                // ffmpeg -y -i a8.mp3 -i a9.mp3 -i a9.mp3 -i a9.mp3 -filter_complex  '[1]adelay=10000|10000[1_];  [2]adelay=20000|20000[2_];  [3]adelay=30000|30000[3_];  [0][1_][2_][3_]amix=4'  apptend.mp3
                List<String> asList = Arrays.asList(
//                        "ffmpeg",
                        "-y",
                        "-i",
                        mp3_a8,
                        "-i",
                        mp3_a9,
                        "-i",
                        mp3_a1,
                        "-i",
                        mp3_a2,
                        "-i",
                        mp3_a3,
                        "-filter_complex",
                        "[1]adelay=10000|10000[a1];[2]adelay=20000|20000[a2];[3]adelay=30000|30000[a3];[4]adelay=40000|40000[a4];[0][a1][a2][a3][a4]amix=5",
                        mp3_a8_cmd
                );
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < asList.size(); i++) {
                    builder.append(asList.get(i));
                    builder.append(" ");
                }

                String cmds = builder.toString();
//                String cmds = "ffmpeg -y -i " + mp3_1 + " -i " + mp3_2 + " -filter_complex amix=inputs=2:duration=shortest " + mp3_process;
                Log.e("FFM", "cmd => " + cmds);
                startFFmpeg(asList);
            }
        });
        findViewById(R.id.button_process_mp3_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String mp3_a8 = absolutePath + "/a8.mp3";
                playMP3(mp3_a8);
            }
        });
        findViewById(R.id.button_process_mp3_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String mp3_a8_cmd = absolutePath + "/" + "a8_cmd.mp3";
                playMP3(mp3_a8_cmd);
            }
        });

        findViewById(R.id.button_process_mp4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";

                String mp3_a1 = absolutePath + "/a1.mp3";
                String mp3_a2 = absolutePath + "/a2.mp3";
                String mp3_a3 = absolutePath + "/a3.mp3";
                String mp3_a4 = absolutePath + "/a4.mp3";
                String mp3_a5 = absolutePath + "/a5.mp3";
                String mp4_v25 = absolutePath + "/v25.mp4";
                String mp4_v25_cmd = absolutePath + "/v25_cmd.mp4";

                // ffmpeg -y -i a8.mp3 -i a9.mp3 -i a9.mp3 -i a9.mp3 -filter_complex  '[1]adelay=10000|10000[1_];  [2]adelay=20000|20000[2_];  [3]adelay=30000|30000[3_];  [0][1_][2_][3_]amix=4'  apptend.mp3
                List<String> asList = Arrays.asList(
//                        "ffmpeg",
                        "-y",
                        "-i",
                        mp4_v25,
                        "-i",
                        mp3_a1,
                        "-i",
                        mp3_a2,
                        "-i",
                        mp3_a3,
                        "-i",
                        mp3_a4,
                        "-filter_complex",
                        "[1]adelay=5000|5000[a1];[2]adelay=10000|10000[a2];[3]adelay=15000|15000[a3];[4]adelay=20000|20000[a4];[0][a1][a2][a3][a4]amix=5",
                        mp4_v25_cmd
                );
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < asList.size(); i++) {
                    builder.append(asList.get(i));
                    builder.append(" ");
                }

                String cmds = builder.toString();
//                String cmds = "ffmpeg -y -i " + mp3_1 + " -i " + mp3_2 + " -filter_complex amix=inputs=2:duration=shortest " + mp3_process;
                Log.e("FFM", "cmd => " + cmds);
                startFFmpeg(asList);
            }
        });
        findViewById(R.id.button_process_mp4_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String mp4_v25 = absolutePath + "/v25.mp4";
                String mp4_v25_cmd = absolutePath + "/v25_cmd.mp4";
                playMP4(mp4_v25);
            }
        });
        findViewById(R.id.button_process_mp4_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//                String absolutePath = "/storage/emulated/0";
                String mp4_v25_cmd = absolutePath + "/v25_cmd.mp4";
                playMP4(mp4_v25_cmd);
            }
        });
    }

    private void init() {

        String absolutePath = getApplicationContext().getCacheDir().getAbsolutePath();
//        String absolutePath = "/storage/emulated/0";
        List<String> list = Arrays.asList("a1.mp3", "a2.mp3", "a3.mp3", "a4.mp3", "a5.mp3", "a6.pcm", "a7.pcm", "xiri.pcm", "a8.mp3", "a9.mp3", "v25.mp4", "v35.mp4");
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            boolean copyFile = copyFile(getApplicationContext(), s, absolutePath + "/" + s);
            if (!copyFile) {
                Toast.makeText(getApplicationContext(), "初始化资源文件 => 错误", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        File file_test_video = new File(absolutePath + "/test_video.mp4");
        file_test_video.delete();
//        try {
//            file_test_video.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file_test_audio = new File(absolutePath + "/test_audio.mp3");
        file_test_audio.delete();
//        try {
//            file_test_audio.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file_test_audio_pcm_a6 = new File(absolutePath + "/a6_cmd.mp3");
        file_test_audio_pcm_a6.delete();
//        try {
//            file_test_audio_pcm.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file_test_audio_pcm_a7 = new File(absolutePath + "/a7_cmd.mp3");
        file_test_audio_pcm_a7.delete();
//        try {
//            file_test_audio_pcm.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file_test_audio_pcm_a8_cmd = new File(absolutePath + "/a8_cmd.mp3");
        file_test_audio_pcm_a8_cmd.delete();
//        try {
//            file_test_audio_pcm_a8_cmd.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file_test_v25_cmd = new File(absolutePath + "/v25_cmd.mp4");
        file_test_v25_cmd.delete();
//        try {
//            file_test_audio_pcm_a8_cmd.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file_xiri_cmd = new File(absolutePath + "/xiri_cmd.mp4");
        file_xiri_cmd.delete();
//        try {
//            file_test_audio_pcm_a8_cmd.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File file_xiri_cmd_aac = new File(absolutePath + "/xiri_cmd.aac");
        file_xiri_cmd_aac.delete();
//        try {
//            file_xiri_cmd_aac.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private boolean copyFile(Context context, String assetsPath, String savePath) {
        // assetsPath 为空时即 /assets
        try {
            InputStream is = context.getAssets().open(assetsPath);
            FileOutputStream fos = new FileOutputStream(new File(savePath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            is.close();
            fos.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private final Handler mHandle = new Handler(this);

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (msg.what == 1000) {
            TextView textView = findViewById(R.id.ffmpeg_version);
            textView.setText("进度 => " + msg.arg1);
        }
        return false;
    }

    private void playMP3(String path) {
        if (null == path || path.length() <= 0 || !new File(path).exists()) {
            Toast.makeText(getApplicationContext(), "未转码", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
                mediaPlayer.start();
            }
        }).start();
    }

    private void playPCM(String path) {
        if (null == path || path.length() <= 0 || !new File(path).exists()) {
            Toast.makeText(getApplicationContext(), "未转码", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                int bufferSize = AudioTrack.getMinBufferSize(48000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 48000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
                FileInputStream fis = null;
                try {
                    audioTrack.play();
                    fis = new FileInputStream(path);
                    byte[] buffer = new byte[bufferSize];
                    int len = 0;
                    while ((len = fis.read(buffer)) != -1) {
                        audioTrack.write(buffer, 0, len);
                    }

                } catch (Exception e) {
                } finally {
                    if (audioTrack != null) {
                        audioTrack.stop();
                        audioTrack = null;
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void playMP4(String path) {
        if (null == path || path.length() <= 0 || !new File(path).exists()) {
            Toast.makeText(getApplicationContext(), "未转码", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        VideoView videoView = findViewById(R.id.video);
        videoView.pause();
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
    }

    private void startFFmpeg(List<String> strings) {

        FFmpeg.executeCmd(strings, new OnFFmpegChangeListener() {

            @Override
            public void progress(float progress) {
                Message message = Message.obtain();
                message.arg1 = (int) progress;
                message.what = 1000;
                mHandle.sendMessage(message);
            }

            @Override
            public void fail() {

            }

            @Override
            public void success() {

            }
        });
    }
}