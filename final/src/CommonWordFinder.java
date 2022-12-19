import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Common word finder using bst/avl/hash
 * @author Xintong Qi (Amy), xq2224
 * @version 1.0.1 December 12, 2022
 */
public class CommonWordFinder {
    private static int limit = 10; // set to 10 by default
    private static Pair<String, Integer>[] words; // array to store the words

    /**
     * Main method: the entry point of the common word finder project
     * This program uses one of the three data structures bst|avl|hash to locate
     * the most common words in a text file and list them out
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

        /* process input */
        MyMap<String, Integer> myMap = null;
        BufferedReader reader;
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
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.setLength(0);
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (Character.isLetter(c) || c=='-' || c=='\'')
                        sb.append(c);
                    if (Character.isWhitespace(c) || i == line.length()-1) {
                        String s = sb.toString().toLowerCase();
                        while (s.length() > 0 && s.charAt(0) == '-') // remove all leading -
                            s = s.substring(1);
                        if (s.length()<1)
                            continue;
                        Integer val = myMap.put(s, 1);
                        if (val != null) {
                            myMap.put(s, val+1);
                        }
                        sb.setLength(0);
                    }
                }
            }
            reader.close();
        } catch (IOException e) { // I/O error during input processing
            System.err.println("Error: An I/O error occurred reading '" + args[1] + "'.");
            System.exit(1);
        }
        if (limit > myMap.size()) // not so many unique words
            limit = myMap.size();

        /* construct array */
        // sorting comes from these sources
        // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Arrays.html#sort(java.lang.Object%5B%5D)
        // https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/Comparable.html
        // https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
        // https://www.baeldung.com/java-sorting
        words = new Pair[myMap.size()];
        int maxstrlen = -1; // keep track of the longest string
        Iterator<Entry<String, Integer>> iter = myMap.iterator();
        int count=0;
        while (iter.hasNext()) {
            Entry<String, Integer> item = iter.next();
            words[count] = new Pair<>(item.key, item.value);
            if (words[count].key.length() > maxstrlen)
                maxstrlen = words[count].key.length();
            count++;
        }
        if (args[1].equals("hash"))
            Arrays.sort(words, (a,b) -> a.key.compareTo(b.key));
        Arrays.sort(words, (a,b) -> b.value.compareTo(a.value));

        /* output result */
        System.out.println("Total unique words: " + myMap.size());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            stringBuilder.setLength(0);
            for (int j = 0; j < String.valueOf(limit).length()-String.valueOf(i+1).length(); j++)
                stringBuilder.append(" ");
            stringBuilder.append((i+1) + ". " + words[i].key);
            for (int j = 0; j < maxstrlen - words[i].key.length() + 1; j++)
                stringBuilder.append(" ");
            stringBuilder.append(words[i].value + System.lineSeparator());
            System.out.print(stringBuilder);
        }
    }
}
