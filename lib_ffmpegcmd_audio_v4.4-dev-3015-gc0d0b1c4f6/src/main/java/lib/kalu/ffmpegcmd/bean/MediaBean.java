package lib.kalu.ffmpegcmd.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public class MediaBean implements Serializable {

    private FormatBean format;
    private List<StreamsBean> streams;

    public FormatBean getFormat() {
        return format;
    }

    public void setFormat(FormatBean format) {
        this.format = format;
    }

    public List<StreamsBean> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamsBean> streams) {
        this.streams = streams;
    }

    @Keep
    public static class StreamsBean implements Serializable {

        private int index;
        private String codec_name;
        private String codec_long_name;
        private String profile;
        private String codec_type;
        private String codec_time_base;
        private String codec_tag_string;
        private String codec_tag;
        private int width;
        private int height;
        private int coded_width;
        private int coded_height;
        private int has_b_frames;
        private String pix_fmt;
        private int level;
        private String color_range;
        private String color_space;
        private String color_transfer;
        private String color_primaries;
        private String chroma_location;
        private int refs;
        private String is_avc;
        private String nal_length_size;
        private String r_frame_rate;
        private String avg_frame_rate;
        private String time_base;
        private int start_pts;
        private String start_time;
        private int duration_ts;
        private String duration;
        private String bit_rate;
        private String bits_per_raw_sample;
        private String nb_frames;
        private DispositionBean disposition;
        private TagsBean tags;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getCodec_name() {
            return codec_name;
        }

        public void setCodec_name(String codec_name) {
            this.codec_name = codec_name;
        }

        public String getCodec_long_name() {
            return codec_long_name;
        }

        public void setCodec_long_name(String codec_long_name) {
            this.codec_long_name = codec_long_name;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getCodec_type() {
            return codec_type;
        }

        public void setCodec_type(String codec_type) {
            this.codec_type = codec_type;
        }

        public String getCodec_time_base() {
            return codec_time_base;
        }

        public void setCodec_time_base(String codec_time_base) {
            this.codec_time_base = codec_time_base;
        }

        public String getCodec_tag_string() {
            return codec_tag_string;
        }

        public void setCodec_tag_string(String codec_tag_string) {
            this.codec_tag_string = codec_tag_string;
        }

        public String getCodec_tag() {
            return codec_tag;
        }

        public void setCodec_tag(String codec_tag) {
            this.codec_tag = codec_tag;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getCoded_width() {
            return coded_width;
        }

        public void setCoded_width(int coded_width) {
            this.coded_width = coded_width;
        }

        public int getCoded_height() {
            return coded_height;
        }

        public void setCoded_height(int coded_height) {
            this.coded_height = coded_height;
        }

        public int getHas_b_frames() {
            return has_b_frames;
        }

        public void setHas_b_frames(int has_b_frames) {
            this.has_b_frames = has_b_frames;
        }

        public String getPix_fmt() {
            return pix_fmt;
        }

        public void setPix_fmt(String pix_fmt) {
            this.pix_fmt = pix_fmt;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getColor_range() {
            return color_range;
        }

        public void setColor_range(String color_range) {
            this.color_range = color_range;
        }

        public String getColor_space() {
            return color_space;
        }

        public void setColor_space(String color_space) {
            this.color_space = color_space;
        }

        public String getColor_transfer() {
            return color_transfer;
        }

        public void setColor_transfer(String color_transfer) {
            this.color_transfer = color_transfer;
        }

        public String getColor_primaries() {
            return color_primaries;
        }

        public void setColor_primaries(String color_primaries) {
            this.color_primaries = color_primaries;
        }

        public String getChroma_location() {
            return chroma_location;
        }

        public void setChroma_location(String chroma_location) {
            this.chroma_location = chroma_location;
        }

        public int getRefs() {
            return refs;
        }

        public void setRefs(int refs) {
            this.refs = refs;
        }

        public String getIs_avc() {
            return is_avc;
        }

        public void setIs_avc(String is_avc) {
            this.is_avc = is_avc;
        }

        public String getNal_length_size() {
            return nal_length_size;
        }

        public void setNal_length_size(String nal_length_size) {
            this.nal_length_size = nal_length_size;
        }

        public String getR_frame_rate() {
            return r_frame_rate;
        }

        public void setR_frame_rate(String r_frame_rate) {
            this.r_frame_rate = r_frame_rate;
        }

        public String getAvg_frame_rate() {
            return avg_frame_rate;
        }

        public void setAvg_frame_rate(String avg_frame_rate) {
            this.avg_frame_rate = avg_frame_rate;
        }

        public String getTime_base() {
            return time_base;
        }

        public void setTime_base(String time_base) {
            this.time_base = time_base;
        }

        public int getStart_pts() {
            return start_pts;
        }

        public void setStart_pts(int start_pts) {
            this.start_pts = start_pts;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getDuration_ts() {
            return duration_ts;
        }

        public void setDuration_ts(int duration_ts) {
            this.duration_ts = duration_ts;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getBit_rate() {
            return bit_rate;
        }

        public void setBit_rate(String bit_rate) {
            this.bit_rate = bit_rate;
        }

        public String getBits_per_raw_sample() {
            return bits_per_raw_sample;
        }

        public void setBits_per_raw_sample(String bits_per_raw_sample) {
            this.bits_per_raw_sample = bits_per_raw_sample;
        }

        public String getNb_frames() {
            return nb_frames;
        }

        public void setNb_frames(String nb_frames) {
            this.nb_frames = nb_frames;
        }

        public DispositionBean getDisposition() {
            return disposition;
        }

        public void setDisposition(DispositionBean disposition) {
            this.disposition = disposition;
        }

        public TagsBean getTags() {
            return tags;
        }

        public void setTags(TagsBean tags) {
            this.tags = tags;
        }
    }

    @Keep
    public static class FormatBean implements Serializable {

        private String filename;
        private int nb_streams;
        private int nb_programs;
        private String format_name;
        private String start_time;
        private String duration;
        private String size;
        private String bit_rate;
        private int probe_score;
        private TagsBean tags;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public int getNb_streams() {
            return nb_streams;
        }

        public void setNb_streams(int nb_streams) {
            this.nb_streams = nb_streams;
        }

        public int getNb_programs() {
            return nb_programs;
        }

        public void setNb_programs(int nb_programs) {
            this.nb_programs = nb_programs;
        }

        public String getFormat_name() {
            return format_name;
        }

        public void setFormat_name(String format_name) {
            this.format_name = format_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getBit_rate() {
            return bit_rate;
        }

        public void setBit_rate(String bit_rate) {
            this.bit_rate = bit_rate;
        }

        public int getProbe_score() {
            return probe_score;
        }

        public void setProbe_score(int probe_score) {
            this.probe_score = probe_score;
        }

        public TagsBean getTags() {
            return tags;
        }

        public void setTags(TagsBean tags) {
            this.tags = tags;
        }
    }

    @Keep
    public static class TagsBean implements Serializable {

        private String major_brand;
        private String minor_version;
        private String compatible_brands;
        private String encoder;

        private String language;
        private String handler_name;
        private String vendor_id;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getHandler_name() {
            return handler_name;
        }

        public void setHandler_name(String handler_name) {
            this.handler_name = handler_name;
        }

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }

        public String getMajor_brand() {
            return major_brand;
        }

        public void setMajor_brand(String major_brand) {
            this.major_brand = major_brand;
        }

        public String getMinor_version() {
            return minor_version;
        }

        public void setMinor_version(String minor_version) {
            this.minor_version = minor_version;
        }

        public String getCompatible_brands() {
            return compatible_brands;
        }

        public void setCompatible_brands(String compatible_brands) {
            this.compatible_brands = compatible_brands;
        }

        public String getEncoder() {
            return encoder;
        }

        public void setEncoder(String encoder) {
            this.encoder = encoder;
        }
    }

    @Keep
    public static class DispositionBean implements Serializable {

        private int defaultX;
        private int dub;
        private int original;
        private int comment;
        private int lyrics;
        private int karaoke;
        private int forced;
        private int hearing_impaired;
        private int visual_impaired;
        private int clean_effects;
        private int attached_pic;
        private int timed_thumbnails;

        public int getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public int getDub() {
            return dub;
        }

        public void setDub(int dub) {
            this.dub = dub;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getLyrics() {
            return lyrics;
        }

        public void setLyrics(int lyrics) {
            this.lyrics = lyrics;
        }

        public int getKaraoke() {
            return karaoke;
        }

        public void setKaraoke(int karaoke) {
            this.karaoke = karaoke;
        }

        public int getForced() {
            return forced;
        }

        public void setForced(int forced) {
            this.forced = forced;
        }

        public int getHearing_impaired() {
            return hearing_impaired;
        }

        public void setHearing_impaired(int hearing_impaired) {
            this.hearing_impaired = hearing_impaired;
        }

        public int getVisual_impaired() {
            return visual_impaired;
        }

        public void setVisual_impaired(int visual_impaired) {
            this.visual_impaired = visual_impaired;
        }

        public int getClean_effects() {
            return clean_effects;
        }

        public void setClean_effects(int clean_effects) {
            this.clean_effects = clean_effects;
        }

        public int getAttached_pic() {
            return attached_pic;
        }

        public void setAttached_pic(int attached_pic) {
            this.attached_pic = attached_pic;
        }

        public int getTimed_thumbnails() {
            return timed_thumbnails;
        }

        public void setTimed_thumbnails(int timed_thumbnails) {
            this.timed_thumbnails = timed_thumbnails;
        }
    }
}
