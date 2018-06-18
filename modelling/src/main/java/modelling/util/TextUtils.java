package modelling.util;

import java.util.regex.Pattern;

/**
 * A collection of functions that have to do with handling text
 *
 * @author neubig
 */
public class TextUtils {

    public static final Pattern whiteSpace = Pattern.compile("\\s+");
    public static String whiteSpaceString = " \t";

    /**
     * Splits the input string into an array of strings, each containing one character
     *
     * @param string The string to be split
     * @return The output array of one-character strings
     */
    public static String[] splitChars(String string) {
        String[] ret = new String[string.length()];
        for (int i = 0; i < ret.length; ret[i] = string.substring(i, ++i)) ;
        return ret;
    }

    /**
     * join an array together with string glue between the words
     *
     * @param glue The string to be added between the words
     * @param arr  The array of doubles to be printed
     * @return A string with the array joined together
     */
    public static String join(String glue, double[] arr) {
        if (arr == null) return null;
        StringBuffer sb = new StringBuffer();
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++)
            sb.append(glue).append(arr[i]);
        return sb.toString();
    }

    /**
     * join an array together with string glue between the words
     *
     * @param glue The string to be added between the words
     * @param arr  The array of objects to be printed
     * @return A string with the array joined together
     */
    public static String join(String glue, Object[] arr) {
        StringBuffer sb = new StringBuffer();
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++)
            sb.append(glue).append(arr[i]);
        return sb.toString();
    }

    /**
     * join an array together with string glue between the words
     *
     * @param glue The string to be added between the words
     * @param arr  The array of objects to be printed
     * @return A string with the array joined together
     */
    public static String join(String glue, int[] arr) {
        StringBuffer sb = new StringBuffer();
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++)
            sb.append(glue).append(arr[i]);
        return sb.toString();
    }

    public static String join(String glue, boolean[] arr) {
        StringBuffer sb = new StringBuffer();
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++)
            sb.append(glue).append(arr[i]);
        return sb.toString();
    }
}