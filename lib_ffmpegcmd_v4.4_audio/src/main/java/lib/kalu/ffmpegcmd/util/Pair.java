package lib.kalu.ffmpegcmd.util;

public class Pair<A, B> {

    /**
     * The first element of the pair.
     */
    protected A first;

    /**
     * The second element of the pair.
     */
    protected B second;

    public Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

}
