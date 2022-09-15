## 说明
```
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

## 历史版本
```
1. https://www.videohelp.com/software/ffmpeg/old-versions
2. http://www.ffmpeg.org/releases/
```

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

## FFmpeg-v5.1.1-Enabled decoders
```
aac                     flac                    pgmyuv
aac_fixed               flashsv                 pgssub
aac_latm                flashsv2                pgx
aasc                    flic                    phm
ac3                     flv                     photocd
ac3_fixed               fmvc                    pictor
acelp_kelvin            fourxm                  pixlet
adpcm_4xm               fraps                   pjs
adpcm_adx               frwu                    png
adpcm_afc               g2m                     ppm
adpcm_agm               g723_1                  prores
adpcm_aica              g729                    prosumer
adpcm_argo              gdv                     psd
adpcm_ct                gem                     ptx
adpcm_dtk               gif                     qcelp
adpcm_ea                gremlin_dpcm            qdm2
adpcm_ea_maxis_xa       gsm                     qdmc
adpcm_ea_r1             gsm_ms                  qdraw
adpcm_ea_r2             h261                    qoi
adpcm_ea_r3             h263                    qpeg
adpcm_ea_xas            h263_v4l2m2m            qtrle
adpcm_g722              h263i                   r10k
adpcm_g726              h263p                   r210
adpcm_g726le            h264                    ra_144
adpcm_ima_acorn         h264_v4l2m2m            ra_288
adpcm_ima_alp           hap                     ralf
adpcm_ima_amv           hca                     rasc
adpcm_ima_apc           hcom                    rawvideo
adpcm_ima_apm           hevc                    realtext
adpcm_ima_cunning       hevc_v4l2m2m            rl2
adpcm_ima_dat4          hnm4_video              roq
adpcm_ima_dk3           hq_hqa                  roq_dpcm
adpcm_ima_dk4           hqx                     rpza
adpcm_ima_ea_eacs       huffyuv                 rscc
adpcm_ima_ea_sead       hymt                    rv10
adpcm_ima_iss           iac                     rv20
adpcm_ima_moflex        idcin                   rv30
adpcm_ima_mtf           idf                     rv40
adpcm_ima_oki           iff_ilbm                s302m
adpcm_ima_qt            ilbc                    sami
adpcm_ima_rad           imc                     sanm
adpcm_ima_smjpeg        imm4                    sbc
adpcm_ima_ssi           imm5                    scpr
adpcm_ima_wav           indeo2                  screenpresso
adpcm_ima_ws            indeo3                  sdx2_dpcm
adpcm_ms                indeo4                  sga
adpcm_mtaf              indeo5                  sgi
adpcm_psx               interplay_acm           sgirle
adpcm_sbpro_2           interplay_dpcm          sheervideo
adpcm_sbpro_3           interplay_video         shorten
adpcm_sbpro_4           ipu                     simbiosis_imx
adpcm_swf               jacosub                 sipr
adpcm_thp               jpeg2000                siren
adpcm_thp_le            jpegls                  smackaud
adpcm_vima              jv                      smacker
adpcm_xa                kgv1                    smc
adpcm_yamaha            kmvc                    smvjpeg
adpcm_zork              lagarith                snow
agm                     loco                    sol_dpcm
aic                     lscr                    sonic
alac                    m101                    sp5x
alias_pix               mace3                   speedhq
als                     mace6                   speex
amrnb                   magicyuv                srgc
amrwb                   mdec                    srt
amv                     metasound               ssa
anm                     microdvd                stl
ansi                    mimic                   subrip
ape                     mjpeg                   subviewer
apng                    mjpegb                  subviewer1
aptx                    mlp                     sunrast
aptx_hd                 mmvideo                 svq1
arbc                    mobiclip                svq3
argo                    motionpixels            tak
ass                     movtext                 targa
asv1                    mp1                     targa_y216
asv2                    mp1float                tdsc
atrac1                  mp2                     text
atrac3                  mp2float                theora
atrac3al                mp3                     thp
atrac3p                 mp3adu                  tiertexseqvideo
atrac3pal               mp3adufloat             tiff
atrac9                  mp3float                tmv
aura                    mp3on4                  truehd
aura2                   mp3on4float             truemotion1
av1                     mpc7                    truemotion2
avrn                    mpc8                    truemotion2rt
avrp                    mpeg1_v4l2m2m           truespeech
avs                     mpeg1video              tscc
avui                    mpeg2_v4l2m2m           tscc2
ayuv                    mpeg2video              tta
bethsoftvid             mpeg4                   twinvq
bfi                     mpeg4_v4l2m2m           txd
bink                    mpegvideo               ulti
binkaudio_dct           mpl2                    utvideo
binkaudio_rdft          msa1                    v210
bintext                 mscc                    v210x
bitpacked               msmpeg4v1               v308
bmp                     msmpeg4v2               v408
bmv_audio               msmpeg4v3               v410
bmv_video               msnsiren                vb
brender_pix             msp2                    vble
c93                     msrle                   vbn
cavs                    mss1                    vc1
ccaption                mss2                    vc1_v4l2m2m
cdgraphics              msvideo1                vc1image
cdtoons                 mszh                    vcr1
cdxl                    mts2                    vmdaudio
cfhd                    mv30                    vmdvideo
cinepak                 mvc1                    vmnc
clearvideo              mvc2                    vorbis
cljr                    mvdv                    vp3
cllc                    mvha                    vp4
comfortnoise            mwsc                    vp5
cook                    mxpeg                   vp6
cpia                    nellymoser              vp6a
cri                     notchlc                 vp6f
cscd                    nuv                     vp7
cyuv                    on2avc                  vp8
dca                     opus                    vp8_v4l2m2m
dds                     paf_audio               vp9
derf_dpcm               paf_video               vp9_v4l2m2m
dfa                     pam                     vplayer
dfpwm                   pbm                     vqa
dirac                   pcm_alaw                wavpack
dnxhd                   pcm_bluray              wcmv
dolby_e                 pcm_dvd                 webp
dpx                     pcm_f16le               webvtt
dsd_lsbf                pcm_f24le               wmalossless
dsd_lsbf_planar         pcm_f32be               wmapro
dsd_msbf                pcm_f32le               wmav1
dsd_msbf_planar         pcm_f64be               wmav2
dsicinaudio             pcm_f64le               wmavoice
dsicinvideo             pcm_lxf                 wmv1
dss_sp                  pcm_mulaw               wmv2
dst                     pcm_s16be               wmv3
dvaudio                 pcm_s16be_planar        wmv3image
dvbsub                  pcm_s16le               wnv1
dvdsub                  pcm_s16le_planar        wrapped_avframe
dvvideo                 pcm_s24be               ws_snd1
dxa                     pcm_s24daud             xan_dpcm
dxtory                  pcm_s24le               xan_wc3
dxv                     pcm_s24le_planar        xan_wc4
eac3                    pcm_s32be               xbin
eacmv                   pcm_s32le               xbm
eamad                   pcm_s32le_planar        xface
eatgq                   pcm_s64be               xl
eatgv                   pcm_s64le               xma1
eatqi                   pcm_s8                  xma2
eightbps                pcm_s8_planar           xpm
eightsvx_exp            pcm_sga                 xsub
eightsvx_fib            pcm_u16be               xwd
escape124               pcm_u16le               y41p
escape130               pcm_u24be               ylc
evrc                    pcm_u24le               yop
exr                     pcm_u32be               yuv4
fastaudio               pcm_u32le               zero12v
ffv1                    pcm_u8                  zerocodec
ffvhuff                 pcm_vidc                zlib
ffwavesynth             pcx                     zmbv
fic                     pfm
fits                    pgm
```

## FFmpeg-v5.1.1-Enabled encoders
```
a64multi                h264_v4l2m2m            ppm
a64multi5               hevc_v4l2m2m            prores
aac                     huffyuv                 prores_aw
ac3                     jpeg2000                prores_ks
ac3_fixed               jpegls                  qoi
adpcm_adx               ljpeg                   qtrle
adpcm_argo              magicyuv                r10k
adpcm_g722              mjpeg                   r210
adpcm_g726              mlp                     ra_144
adpcm_g726le            movtext                 rawvideo
adpcm_ima_alp           mp2                     roq
adpcm_ima_amv           mp2fixed                roq_dpcm
adpcm_ima_apm           mpeg1video              rpza
adpcm_ima_qt            mpeg2video              rv10
adpcm_ima_ssi           mpeg4                   rv20
adpcm_ima_wav           mpeg4_v4l2m2m           s302m
adpcm_ima_ws            msmpeg4v2               sbc
adpcm_ms                msmpeg4v3               sgi
adpcm_swf               msvideo1                smc
adpcm_yamaha            nellymoser              snow
alac                    opus                    sonic
alias_pix               pam                     sonic_ls
amv                     pbm                     speedhq
apng                    pcm_alaw                srt
aptx                    pcm_bluray              ssa
aptx_hd                 pcm_dvd                 subrip
ass                     pcm_f32be               sunrast
asv1                    pcm_f32le               svq1
asv2                    pcm_f64be               targa
avrp                    pcm_f64le               text
avui                    pcm_mulaw               tiff
ayuv                    pcm_s16be               truehd
bitpacked               pcm_s16be_planar        tta
bmp                     pcm_s16le               ttml
cfhd                    pcm_s16le_planar        utvideo
cinepak                 pcm_s24be               v210
cljr                    pcm_s24daud             v308
comfortnoise            pcm_s24le               v408
dca                     pcm_s24le_planar        v410
dfpwm                   pcm_s32be               vbn
dnxhd                   pcm_s32le               vc2
dpx                     pcm_s32le_planar        vorbis
dvbsub                  pcm_s64be               vp8_v4l2m2m
dvdsub                  pcm_s64le               wavpack
dvvideo                 pcm_s8                  webvtt
eac3                    pcm_s8_planar           wmav1
exr                     pcm_u16be               wmav2
ffv1                    pcm_u16le               wmv1
ffvhuff                 pcm_u24be               wmv2
fits                    pcm_u24le               wrapped_avframe
flac                    pcm_u32be               xbm
flashsv                 pcm_u32le               xface
flashsv2                pcm_u8                  xsub
flv                     pcm_vidc                xwd
g723_1                  pcx                     y41p
gif                     pfm                     yuv4
h261                    pgm                     zlib
h263                    pgmyuv                  zmbv
h263_v4l2m2m            phm
h263p                   png
```

## FFmpeg-v5.1.1-Enabled parsers
```
aac                     dvbsub                  mpegvideo
aac_latm                dvd_nav                 opus
ac3                     dvdsub                  png
adx                     flac                    pnm
amr                     g723_1                  qoi
av1                     g729                    rv30
avs2                    gif                     rv40
avs3                    gsm                     sbc
bmp                     h261                    sipr
cavsvideo               h263                    tak
cook                    h264                    vc1
cri                     hevc                    vorbis
dca                     ipu                     vp3
dirac                   jpeg2000                vp8
dnxhd                   mjpeg                   vp9
dolby_e                 mlp                     webp
dpx                     mpeg4video              xbm
dvaudio                 mpegaudio               xma
```

## FFmpeg-v5.1.1-Enabled demuxers
```
aa                      idcin                   pcm_f64le
aac                     idf                     pcm_mulaw
aax                     iff                     pcm_s16be
ac3                     ifv                     pcm_s16le
ace                     ilbc                    pcm_s24be
acm                     image2                  pcm_s24le
act                     image2_alias_pix        pcm_s32be
adf                     image2_brender_pix      pcm_s32le
adp                     image2pipe              pcm_s8
ads                     image_bmp_pipe          pcm_u16be
adx                     image_cri_pipe          pcm_u16le
aea                     image_dds_pipe          pcm_u24be
afc                     image_dpx_pipe          pcm_u24le
aiff                    image_exr_pipe          pcm_u32be
aix                     image_gem_pipe          pcm_u32le
alp                     image_gif_pipe          pcm_u8
amr                     image_j2k_pipe          pcm_vidc
amrnb                   image_jpeg_pipe         pjs
amrwb                   image_jpegls_pipe       pmp
anm                     image_jpegxl_pipe       pp_bnk
apc                     image_pam_pipe          pva
ape                     image_pbm_pipe          pvf
apm                     image_pcx_pipe          qcp
apng                    image_pfm_pipe          r3d
aptx                    image_pgm_pipe          rawvideo
aptx_hd                 image_pgmyuv_pipe       realtext
aqtitle                 image_pgx_pipe          redspark
argo_asf                image_phm_pipe          rl2
argo_brp                image_photocd_pipe      rm
argo_cvg                image_pictor_pipe       roq
asf                     image_png_pipe          rpl
asf_o                   image_ppm_pipe          rsd
ass                     image_psd_pipe          rso
ast                     image_qdraw_pipe        rtp
au                      image_qoi_pipe          rtsp
av1                     image_sgi_pipe          s337m
avi                     image_sunrast_pipe      sami
avr                     image_svg_pipe          sap
avs                     image_tiff_pipe         sbc
avs2                    image_vbn_pipe          sbg
avs3                    image_webp_pipe         scc
bethsoftvid             image_xbm_pipe          scd
bfi                     image_xpm_pipe          sdp
bfstm                   image_xwd_pipe          sdr2
bink                    ingenient               sds
binka                   ipmovie                 sdx
bintext                 ipu                     segafilm
bit                     ircam                   ser
bitpacked               iss                     sga
bmv                     iv8                     shorten
boa                     ivf                     siff
brstm                   ivr                     simbiosis_imx
c93                     jacosub                 sln
caf                     jv                      smacker
cavsvideo               kux                     smjpeg
cdg                     kvag                    smush
cdxl                    live_flv                sol
cine                    lmlm4                   sox
codec2                  loas                    spdif
codec2raw               lrc                     srt
concat                  luodat                  stl
data                    lvf                     str
daud                    lxf                     subviewer
dcstr                   m4v                     subviewer1
derf                    matroska                sup
dfa                     mca                     svag
dfpwm                   mcc                     svs
dhav                    mgsts                   swf
dirac                   microdvd                tak
dnxhd                   mjpeg                   tedcaptions
dsf                     mjpeg_2000              thp
dsicin                  mlp                     threedostr
dss                     mlv                     tiertexseq
dts                     mm                      tmv
dtshd                   mmf                     truehd
dv                      mods                    tta
dvbsub                  moflex                  tty
dvbtxt                  mov                     txd
dxa                     mp3                     ty
ea                      mpc                     v210
ea_cdata                mpc8                    v210x
eac3                    mpegps                  vag
epaf                    mpegts                  vc1
ffmetadata              mpegtsraw               vc1t
filmstrip               mpegvideo               vividas
fits                    mpjpeg                  vivo
flac                    mpl2                    vmd
flic                    mpsub                   vobsub
flv                     msf                     voc
fourxm                  msnwc_tcp               vpk
frm                     msp                     vplayer
fsb                     mtaf                    vqf
fwse                    mtv                     w64
g722                    musx                    wav
g723_1                  mv                      wc3
g726                    mvi                     webm_dash_manifest
g726le                  mxf                     webvtt
g729                    mxg                     wsaud
gdv                     nc                      wsd
genh                    nistsphere              wsvqa
gif                     nsp                     wtv
gsm                     nsv                     wv
gxf                     nut                     wve
h261                    nuv                     xa
h263                    obu                     xbin
h264                    ogg                     xmv
hca                     oma                     xvag
hcom                    paf                     xwma
hevc                    pcm_alaw                yop
hls                     pcm_f32be               yuv4mpegpipe
hnm                     pcm_f32le
ico                     pcm_f64be
```

## FFmpeg-v5.1.1-Enabled muxers
```
a64                     h263                    pcm_s16le
ac3                     h264                    pcm_s24be
adts                    hash                    pcm_s24le
adx                     hds                     pcm_s32be
aiff                    hevc                    pcm_s32le
alp                     hls                     pcm_s8
amr                     ico                     pcm_u16be
amv                     ilbc                    pcm_u16le
apm                     image2                  pcm_u24be
apng                    image2pipe              pcm_u24le
aptx                    ipod                    pcm_u32be
aptx_hd                 ircam                   pcm_u32le
argo_asf                ismv                    pcm_u8
argo_cvg                ivf                     pcm_vidc
asf                     jacosub                 psp
asf_stream              kvag                    rawvideo
ass                     latm                    rm
ast                     lrc                     roq
au                      m4v                     rso
avi                     matroska                rtp
avif                    matroska_audio          rtp_mpegts
avm2                    md5                     rtsp
avs2                    microdvd                sap
avs3                    mjpeg                   sbc
bit                     mkvtimestamp_v2         scc
caf                     mlp                     segafilm
cavsvideo               mmf                     segment
codec2                  mov                     smjpeg
codec2raw               mp2                     smoothstreaming
crc                     mp3                     sox
dash                    mp4                     spdif
data                    mpeg1system             spx
daud                    mpeg1vcd                srt
dfpwm                   mpeg1video              stream_segment
dirac                   mpeg2dvd                streamhash
dnxhd                   mpeg2svcd               sup
dts                     mpeg2video              swf
dv                      mpeg2vob                tee
eac3                    mpegts                  tg2
f4v                     mpjpeg                  tgp
ffmetadata              mxf                     truehd
fifo                    mxf_d10                 tta
fifo_test               mxf_opatom              ttml
filmstrip               null                    uncodedframecrc
fits                    nut                     vc1
flac                    obu                     vc1t
flv                     oga                     voc
framecrc                ogg                     w64
framehash               ogv                     wav
framemd5                oma                     webm
g722                    opus                    webm_chunk
g723_1                  pcm_alaw                webm_dash_manifest
g726                    pcm_f32be               webp
g726le                  pcm_f32le               webvtt
gif                     pcm_f64be               wsaud
gsm                     pcm_f64le               wtv
gxf                     pcm_mulaw               wv
h261                    pcm_s16be               yuv4mpegpipe
```

## FFmpeg-v5.1.1-Enabled protocols
```
async                   hls                     rtmpt
cache                   http                    rtp
concat                  httpproxy               srtp
concatf                 icecast                 subfile
crypto                  md5                     tcp
data                    mmsh                    tee
ffrtmphttp              mmst                    udp
file                    pipe                    udplite
ftp                     prompeg                 unix
gopher                  rtmp
```

## FFmpeg-v5.1.1-Enabled filters
```
abench                  convolution             nlmeans
abitscope               convolve                noformat
acompressor             copy                    noise
acontrast               crop                    normalize
acopy                   crossfeed               null
acrossfade              crystalizer             nullsink
acrossover              cue                     nullsrc
acrusher                curves                  oscilloscope
acue                    datascope               overlay
addroi                  dblur                   pad
adeclick                dcshift                 pal100bars
adeclip                 dctdnoiz                pal75bars
adecorrelate            deband                  palettegen
adelay                  deblock                 paletteuse
adenorm                 decimate                pan
aderivative             deconvolve              perms
adrawgraph              dedot                   photosensitivity
adynamicequalizer       deesser                 pixdesctest
adynamicsmooth          deflate                 pixelize
aecho                   deflicker               pixscope
aemphasis               dejudder                premultiply
aeval                   derain                  prewitt
aevalsrc                deshake                 pseudocolor
aexciter                despill                 psnr
afade                   detelecine              qp
afftdn                  dialoguenhance          random
afftfilt                dilation                readeia608
afifo                   displace                readvitc
afir                    dnn_classify            realtime
afirsrc                 dnn_detect              remap
aformat                 dnn_processing          removegrain
afreqshift              doubleweave             removelogo
afwtdn                  drawbox                 replaygain
agate                   drawgraph               reverse
agraphmonitor           drawgrid                rgbashift
ahistogram              drmeter                 rgbtestsrc
aiir                    dynaudnorm              roberts
aintegral               earwax                  rotate
ainterleave             ebur128                 scale
alatency                edgedetect              scale2ref
alimiter                elbg                    scdet
allpass                 entropy                 scharr
allrgb                  epx                     scroll
allyuv                  equalizer               segment
aloop                   erosion                 select
alphaextract            estdif                  selectivecolor
alphamerge              exposure                sendcmd
amerge                  extractplanes           separatefields
ametadata               extrastereo             setdar
amix                    fade                    setfield
amovie                  feedback                setparams
amplify                 fftdnoiz                setpts
amultiply               fftfilt                 setrange
anequalizer             field                   setsar
anlmdn                  fieldhint               settb
anlmf                   fieldmatch              shear
anlms                   fieldorder              showcqt
anoisesrc               fifo                    showfreqs
anull                   fillborders             showinfo
anullsink               firequalizer            showpalette
anullsrc                flanger                 showspatial
apad                    floodfill               showspectrum
aperms                  format                  showspectrumpic
aphasemeter             fps                     showvolume
aphaser                 framepack               showwaves
aphaseshift             framerate               showwavespic
apsyclip                framestep               shuffleframes
apulsator               freezedetect            shufflepixels
arealtime               freezeframes            shuffleplanes
aresample               gblur                   sidechaincompress
areverse                geq                     sidechaingate
arnndn                  gradfun                 sidedata
asdr                    gradients               sierpinski
asegment                graphmonitor            signalstats
aselect                 grayworld               silencedetect
asendcmd                greyedge                silenceremove
asetnsamples            guided                  sinc
asetpts                 haas                    sine
asetrate                haldclut                siti
asettb                  haldclutsrc             smptebars
ashowinfo               hdcd                    smptehdbars
asidedata               headphone               sobel
asoftclip               hflip                   spectrumsynth
aspectralstats          highpass                speechnorm
asplit                  highshelf               split
astats                  hilbert                 sr
astreamselect           histogram               ssim
asubboost               hqx                     stereotools
asubcut                 hstack                  stereowiden
asupercut               hsvhold                 streamselect
asuperpass              hsvkey                  superequalizer
asuperstop              hue                     surround
atadenoise              huesaturation           swaprect
atempo                  hwdownload              swapuv
atilt                   hwmap                   tblend
atrim                   hwupload                telecine
avectorscope            hysteresis              testsrc
avgblur                 identity                testsrc2
avsynctest              idet                    thistogram
axcorrelate             il                      threshold
bandpass                inflate                 thumbnail
bandreject              interleave              tile
bass                    join                    tiltshelf
bbox                    kirsch                  tlut2
bench                   lagfun                  tmedian
bilateral               latency                 tmidequalizer
biquad                  lenscorrection          tmix
bitplanenoise           life                    tonemap
blackdetect             limitdiff               tpad
blend                   limiter                 transpose
blockdetect             loop                    treble
blurdetect              loudnorm                tremolo
bm3d                    lowpass                 trim
bwdif                   lowshelf                unpremultiply
cas                     lumakey                 unsharp
cellauto                lut                     untile
channelmap              lut1d                   v360
channelsplit            lut2                    varblur
chorus                  lut3d                   vectorscope
chromahold              lutrgb                  vflip
chromakey               lutyuv                  vfrdet
chromanr                mandelbrot              vibrance
chromashift             maskedclamp             vibrato
ciescope                maskedmax               vif
codecview               maskedmerge             vignette
color                   maskedmin               virtualbass
colorbalance            maskedthreshold         vmafmotion
colorchannelmixer       maskfun                 volume
colorchart              mcompand                volumedetect
colorcontrast           median                  vstack
colorcorrect            mergeplanes             w3fdif
colorhold               mestimate               waveform
colorize                metadata                weave
colorkey                midequalizer            xbr
colorlevels             minterpolate            xcorrelate
colormap                mix                     xfade
colorspace              monochrome              xmedian
colorspectrum           morpho                  xstack
colortemperature        movie                   yadif
compand                 msad                    yaepblur
compensationdelay       multiply                yuvtestsrc
concat                  negate                  zoompan
```

## FFmpeg-v5.1.1-Enabled bsfs
```
aac_adtstoasc           h264_redundant_pps      opus_metadata
av1_frame_merge         hapqa_extract           pcm_rechunk
av1_frame_split         hevc_metadata           pgs_frame_merge
av1_metadata            hevc_mp4toannexb        prores_metadata
chomp                   imx_dump_header         remove_extradata
dca_core                mjpeg2jpeg              setts
dump_extradata          mjpega_dump_header      text2movsub
dv_error_marker         mov2textsub             trace_headers
eac3_core               mp3_header_decompress   truehd_core
extract_extradata       mpeg2_metadata          vp9_metadata
filter_units            mpeg4_unpack_bframes    vp9_raw_reorder
h264_metadata           noise                   vp9_superframe
h264_mp4toannexb        null                    vp9_superframe_split
```

## FFmpeg-v5.1.1-Enabled indevs
```
fbdev                   oss
lavfi                   v4l2
```

## FFmpeg-v5.1.1-Enabled outdevs
```
fbdev                   oss                     v4l2
```