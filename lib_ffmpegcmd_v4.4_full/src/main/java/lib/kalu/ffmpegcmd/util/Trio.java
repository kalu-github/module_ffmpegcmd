package lib.kalu.ffmpegcmd.util;

public class Trio<A, B, C> {

    /**
     * The first element of the trio.
     */
    protected final A first;

    /**
     * The second element of the trio.
     */
    protected final B second;

    /**
     * The third element of the trio.
     */
    protected final C third;

    public Trio(final A first, final B second, final C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

}
