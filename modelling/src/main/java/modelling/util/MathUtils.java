package modelling.util;

import java.util.stream.IntStream;

/**
 * A collection of functions that handle functions regarding mathematics.
 * @author neubig
 *
 */
public class MathUtils {
    /**
     * return the sum of a float array
     * @param arr the array
     * @return the sum
     */
    public static final float sum(float[] arr) {
        float ret = 0;
        if(arr != null)
            for(float d : arr)
                ret += d;
        return ret;
    }

    /**
     * return the sum of an int array
     * @param arr the array
     * @return the sum
     */
    public static final int sum(int[] arr) {
        if(arr != null)
            return IntStream.of(arr).sum();
        else throw new NullPointerException("arr is null");
    }

    //	/**
    //	 * Add two values (probabilities) that are in log format
    //	 * @param a The first value
    //	 * @param b The second value
    //	 * @return The sum of a and b
    //	 */
    //	public static final float logAddition(float a, float b) {
    //		if(b == Double.NEGATIVE_INFINITY)
    //			return a;
    //		return Math.log10(Math.pow(10, a-b)+1)+b;
    //	}

    /**
     * Add two values (probabilities) that are in log format
     * @param a The first value
     * @param b The second value
     * @return The sum of a and b
     */
    public static final float logAddition(float a, float b) {
        if(b == Float.NEGATIVE_INFINITY)
            return a;
        return (float)Math.log10(Math.pow(10, a-b)+1)+b;
    }
}
