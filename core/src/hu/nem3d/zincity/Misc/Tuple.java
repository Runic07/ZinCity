package hu.nem3d.zincity.Misc;

/**
 * Provides a class of 2 values combined, a tuple
 * @param <T1> The type of the first value
 * @param <T2> The type of the second value
 */
public class Tuple<T1, T2>{
    private final T1 first;
    private final T2 second;

    /**
     * Constructs a Tuple with the values of parameters
     * @param first The first value of this
     * @param second The second value of this
     */
    public Tuple(T1 first, T2 second){
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first value of this
     * @return The first value of this
     */
    public T1 getFirst() {
        return first;
    }

    /**
     * Gets the second value of this
     * @return The second value of this
     */
    public T2 getSecond() {
        return second;
    }
}