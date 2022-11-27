import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class with two different methods to count inversions in an array of integers.
 * @author Xintong Qi (Amy), xq2224
 * @version 1.0.0 November 21, 2022
 */
public class InversionCounter {

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses nested loops to run in Theta(n^2) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsSlow(int[] array) {
        long result = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i+1; j < array.length; j++) {
                if (array[i] > array[j]){
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * The helper method for the mergesort method
     * @param array the array to be sorted
     * @param scratch the temp array to store sorted elements
     * @param low the lower index
     * @param high the higher index
     */
    private static long mergesortHelper(int[] array, int[] scratch, int low, int high, long count) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            count = mergesortHelper(array, scratch, low, mid, count);
            count = mergesortHelper(array, scratch, mid + 1, high, count);
            // merge
            int i = low, j = mid + 1;
            for (int k = low; k <= high; k++) {
                if (i <= mid && (j > high || array[i] <= array[j])) {
                    // cases that end up in the if statement
                    // 1. no more elements left in the right half: j > high
                    // 2. array[i] <= array[j]
                    // if no more elements on the left: i > mid, no choice but to enter else
                    scratch[k] = array[i++];
                } else {
                    if (i <= mid) {
                        count += (mid-i+1);
                    }
                    scratch[k] = array[j++];
                }
            }

            if (high + 1 - low >= 0) System.arraycopy(scratch, low, array, low, high + 1 - low);
        }
        return count;
    }

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses mergesort to run in Theta(n lg n) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsFast(int[] array) {
        // Make a copy of the array so you don't actually sort the original
        // array.
        int[] arrayCopy = new int[array.length],
              scratch =  new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        return mergesortHelper(arrayCopy, scratch, 0, array.length - 1, 0);
    }

    /**
     * Reads an array of integers from stdin.
     * @return an array of integers
     * @throws IOException if data cannot be read from stdin
     * @throws NumberFormatException if there is an invalid character in the
     * input stream
     */
    private static int[] readArrayFromStdin() throws IOException,
                                                     NumberFormatException {
        List<Integer> intList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        int value = 0, index = 0, ch;
        boolean valueFound = false;
        while ((ch = reader.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                valueFound = true;
                value = value * 10 + (ch - 48);
            } else if (ch == ' ' || ch == '\n' || ch == '\r') {
                if (valueFound) {
                    intList.add(value);
                    value = 0;
                }
                valueFound = false;
                if (ch != ' ') {
                    break;
                }
            } else {
                throw new NumberFormatException(
                        "Invalid character '" + (char)ch +
                        "' found at index " + index + " in input stream.");
            }
            index++;
        }

        int[] array = new int[intList.size()];
        Iterator<Integer> iterator = intList.iterator();
        index = 0;
        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }
        return array;
    }

    public static void main(String[] args) {
        if (args.length > 1){  // wrong num of arguments, return
            System.err.println("Usage: java InversionCounter [slow]");
            System.exit(1);
        } else if (args.length == 1) {  // slow
            if (!args[0].equals("slow")) {  // wrong argument, return
                System.err.println("Error: Unrecognized option '" + args[0] + "'.");
                System.exit(1);
            }
            System.out.print("Enter sequence of integers, each followed by a space: ");
            // read input
            try {
                int[] input = readArrayFromStdin();
                if (input.length == 0){
                    System.err.println("Error: Sequence of integers not received.");
                    System.exit(1);
                }
                long result = countInversionsSlow(input);
                System.out.println("Number of inversions: " + result);
            } catch (NumberFormatException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        } else {
            System.out.print("Enter sequence of integers, each followed by a space: ");
            // read input
            try {
                int[] input = readArrayFromStdin();
                if (input.length == 0){
                    System.err.println("Error: Sequence of integers not received.");
                    System.exit(1);
                }
                long result = countInversionsFast(input);
                System.out.println("Number of inversions: " + result);
            } catch (NumberFormatException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        System.exit(0);
    }

}
