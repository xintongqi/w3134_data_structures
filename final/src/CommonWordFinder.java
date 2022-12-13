import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Common word finder using bst/avl/hash
 * @author Xintong Qi (Amy), xq2224
 * @version 1.0.1 December 12, 2022
 */
public class CommonWordFinder {

    /**
     * Main method: the entry point of the common word finder project
     * TODO: more descriptions here
     * @param args an array of strings in the format of filename bst|avl|hash [limit]
     */
    public static void main(String[] args) {
        /* check validity of argument */
        if (args.length!=2 && args.length!=3) { // invalid argument
            System.err.println("Usage: java CommonWordFinder <filename> <bst|avl|hash> [limit]");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (!file.exists() || file.isDirectory()) { // invalid file
            System.err.println("Error: Cannot open file '" + args[0] + "' for input.");
            System.exit(1);
        }

        if (!args[1].equals("bst") && !args[1].equals("avl")
                && !args[1].equals("hash")) { // invalid data structure
            System.err.println("Error: Invalid data structure '" + args[1] + "' received.");
            System.exit(1);
        }

        int limit = 10; // set to 10 by default
        if (args.length == 3) { // invalid limit
            try {
                limit = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid limit '" + args[2] + "' received.");
                System.exit(1);
            }
        }

        if (limit <= 0) { // invalid limit
            System.err.println("Error: Invalid limit '" + args[2] + "' received.");
            System.exit(1);
        }

        /* parse input file */
        MyMap<String, Integer> myMap = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            if (args[1].equals("bst")) {
                myMap = new BSTMap<>();
            } else if (args[1].equals("avl")) {
                myMap = new AVLTreeMap<>();
            } else {
                myMap = new MyHashMap<>();
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] segments = line.split(" ");
                for (String segment : segments) {
                    if (segment.charAt(0) == '-') // invalid, skip
                        continue;
                    if (!Character.isLetterOrDigit(segment.charAt(segment.length()-1))) // remove punctuation
                        segment = segment.substring(0, segment.length()-1);
                    segment = segment.toLowerCase();

                    // insert words one by one
                    Integer value = myMap.put(segment, 1);
                    if (value != null)
                        myMap.put(segment, value+1);
                }
            }

            reader.close();
        } catch (IOException e) { // I/O error during input processing
            System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
            System.exit(1);
        }

        /* output result */
        



        // todo: testing purposes, remove afterwards
        if (args[1].equals("bst")) {
            BSTMap<String, Integer> bst = (BSTMap<String, Integer>) myMap;
            System.out.println(bst.toAsciiDrawing());
            System.out.println();
            System.out.println("Height:                   " + bst.height());
            System.out.println("Total nodes:              " + bst.size());
            System.out.printf("Successful search cost:   %.3f\n",
                    bst.successfulSearchCost());
            System.out.printf("Unsuccessful search cost: %.3f\n",
                    bst.unsuccessfulSearchCost());
        } else if (args[1].equals("avl")) {
            AVLTreeMap<String, Integer> avlTree = (AVLTreeMap<String, Integer>) myMap;
            System.out.println(avlTree.toAsciiDrawing());
            System.out.println();
            System.out.println("Height:                   " + avlTree.height());
            System.out.println("Total nodes:              " + avlTree.size());
            System.out.printf("Successful search cost:   %.3f\n",
                    avlTree.successfulSearchCost());
            System.out.printf("Unsuccessful search cost: %.3f\n",
                    avlTree.unsuccessfulSearchCost());
        } else {
            MyHashMap<String, Integer> map = (MyHashMap<String, Integer>) myMap;
            System.out.println("Size            : " + map.size());
            System.out.println("Table size      : " + map.getTableSize());
            System.out.println("Load factor     : " + map.getLoadFactor());
            System.out.println("Max chain length: " + map.computeMaxChainLength());
            System.out.println();
            System.out.println(map);
        }


    }
}
