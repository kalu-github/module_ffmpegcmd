package lib.kalu.ffmpegcmd.bean;

public enum Level {

    /**
     * This log level is defined by MobileFFmpeg. It is used to specify logs printed to stderr by
     * ffmpeg. Logs that has this level are not filtered and always redirected.
     *
     * @since 4.3.1
     */
    AV_LOG_STDERR(-16),

    /**
     * Print no output.
     */
    AV_LOG_QUIET(-8),

    /**
     * Something went really wrong and we will crash now.
     */
    AV_LOG_PANIC(0),

    /**
     * Something went wrong and recovery is not possible.
     * For example, no header was found for a format which depends
     * on headers or an illegal combination of parameters is used.
     */
    AV_LOG_FATAL(8),

    /**
     * Something went wrong and cannot losslessly be recovered.
     * However, not all future data is affected.
     */
    AV_LOG_ERROR(16),

    /**
     * Something somehow does not look correct. This may or may not
     * lead to problems. An example would be the use of '-vstrict -2'.
     */
    AV_LOG_WARNING(24),

    /**
     * Standard information.
     */
    AV_LOG_INFO(32),

    /**
     * Detailed information.
     */
    AV_LOG_VERBOSE(40),

    /**
     * Stuff which is only useful for libav* developers.
     */
    AV_LOG_DEBUG(48),

    /**
     * Extremely verbose debugging, useful for libav* development.
     */
    AV_LOG_TRACE(56);

    private int value;

    /**
     * <p>Returns enumeration defined by value.
     *
     * @param value level value
     * @return enumeration defined by value
     */
    public static Level from(final int value) {
        if (value == AV_LOG_STDERR.getValue()) {
            return AV_LOG_STDERR;
        } else if (value == AV_LOG_QUIET.getValue()) {
            return AV_LOG_QUIET;
        } else if (value == AV_LOG_PANIC.getValue()) {
            return AV_LOG_PANIC;
        } else if (value == AV_LOG_FATAL.getValue()) {
            return AV_LOG_FATAL;
        } else if (value == AV_LOG_ERROR.getValue()) {
            return AV_LOG_ERROR;
        } else if (value == AV_LOG_WARNING.getValue()) {
            return AV_LOG_WARNING;
        } else if (value == AV_LOG_INFO.getValue()) {
            return AV_LOG_INFO;
        } else if (value == AV_LOG_VERBOSE.getValue()) {
            return AV_LOG_VERBOSE;
        } else if (value == AV_LOG_DEBUG.getValue()) {
            return AV_LOG_DEBUG;
        } else {
            return AV_LOG_TRACE;
        }
    }

    /**
     * Returns level value.
     *
     * @return level value
     */
    public int getValue() {
        return value;
    }

    /**
     * Creates new enum.
     *
     * @param value level value
     */
    Level(final int value) {
        this.value = value;
    }

}
