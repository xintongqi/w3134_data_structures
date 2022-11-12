/**
 * @author Xintong Qi
 * UNI: xq2224
 * Programming Assignment 2 - Recursion exercises
 * COMS W3134
 *
 * Note: All methods must be written recursively. No credit will be given for
 * methods written without recursion, even if they produce the correct output.
 *
 * This is a class for a bunch of recursion methods for practices.
 * All methods are tested against RecursionTestCases.java. Passed 30 of 30 tests.
 * All methods written in recursion.
 */
public class Recursion {

    /**
     * Returns the value of x * y, computed via recursive addition.
     * x is added y times. Both x and y are non-negative.
     * @param x  non-negative integer multiplicand 1
     * @param y  non-negative integer multiplicand 2
     * @return   x * y
     */
    public static int recursiveMultiplication(int x, int y) {
        if (y==0){
            return 0; // base case
        }
        return x + recursiveMultiplication(x, y-1);
    }

/******************************************************************************/
    /**
     * Reverses a string via recursion.
     * @param s  the non-null string to reverse
     * @return   a new string with the characters in reverse order
     */
    public static String reverse(String s) {
        if (s.length()==0){
            return s; // base case
        }
        return reverse(s.substring(1))+s.charAt(0); // trim the first char and append it to the end of the substring
    }

/******************************************************************************/
    /**
     * Returns the maximum value in the array.
     * This is the helper method to do the recursion.
     * @param array the array of integers to traverse
     * @param index the current index
     * @param max   the current max value
     * @return      the maximum value in the array
     */
    private static int maxHelper(int[] array, int index, int max) {
        if (array.length==0){
            return max; // input is an empty array
        }
        if (index == array.length-1){
            return array[index] > max ? array[index] : max; // base case
        }
        if (array[index] > max) {
            return maxHelper(array, index+1, array[index]); // max is updated to current max
        }else{
            return maxHelper(array, index+1, max); // no update
        }
    }

    /**
     * Returns the maximum value in the array.
     * Uses a helper method to do the recursion.
     * @param array  the array of integers to traverse
     * @return       the maximum value in the array
     */
    public static int max(int[] array) {
        return maxHelper(array, 0, Integer.MIN_VALUE);
    }

/******************************************************************************/

    /**
     * Returns whether or not a string is a palindrome, a string that is
     * the same both forward and backward.
     * @param s  the string to process
     * @return   a boolean indicating if the string is a palindrome
     */
    public static boolean isPalindrome(String s) {
        if (s.length()==0 || s.length()==1){
            return true; // base case
        }
        if (s.charAt(0) != s.charAt(s.length()-1)){
            return false; // dies somewhere
        }
        return isPalindrome(s.substring(1,s.length()-1)); // satisfies for now, check substring
    }

/******************************************************************************/
    /**
     * Returns whether or not the integer key is in the array of integers.
     * This is the helper method to do the recursion.
     * @param key   the value to seek
     * @param array the array to traverse
     * @param index the current index
     * @return      a boolean indicating if the key is found in the portion of the array
     */
    private static boolean memberHelper(int key, int[] array, int index) {
        if (array.length==0){
            return false; // empty array, always return false
        }
        if (array[index]==key){
            return true; // found, return
        }else{
            if (index==array.length-1){
                return false; // base case
            }
            return memberHelper(key, array, index+1); // not found not base case, continue search in subarray
        }
    }

    /**
     * Returns whether or not the integer key is in the array of integers.
     * Uses a helper method to do the recursion.
     * @param key    the value to seek
     * @param array  the array to traverse
     * @return       a boolean indicating if the key is found in the array
     */
    public static boolean isMember(int key, int[] array) {
        return memberHelper(key, array, 0);
    }

/******************************************************************************/
    /**
     * Returns a new string where identical chars that are adjacent
     * in the original string are separated from each other by a tilde '~'.
     * @param s  the string to process
     * @return   a new string where identical adjacent characters are separated
     *           by a tilde
     */
    public static String separateIdentical(String s) {
        if (s.length()==0 || s.length()==1){
            return s; // empty string and base case
        }
        if (s.charAt(s.length()-1)==s.charAt(s.length()-2)){
            return separateIdentical(s.substring(0, s.length()-1)) + "~" + s.charAt(s.length()-1); // append ~ and check substring
        }
        return separateIdentical(s.substring(0, s.length()-1)) + s.charAt(s.length()-1); // append nothing and check substring
    }
}
