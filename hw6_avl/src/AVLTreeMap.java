/**
 * Xintong Qi (Amy), xq2224
 */

/**
 * Class that implements an AVL tree which implements the MyMap interface.
 * @author Brian S. Borowski
 * @version 1.0 October 28, 2022
 */
public class AVLTreeMap<K extends Comparable<K>, V> extends BSTMap<K, V>
        implements MyMap<K, V> {
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Creates an empty AVL tree map.
     */
    public AVLTreeMap() { }

    public AVLTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a AVL tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created via a divide-and-conquer
     * approach. If sorted is false, the pairs will be inserted in the order
     * they are received, and the tree will be rotated to maintain the AVL tree
     * balance property.
     * @param elements an array of key-value pairs
     */
    public AVLTreeMap(Pair<K, V>[] elements, boolean sorted) {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        Pair<K, V> pair = pairs[mid];
        Node<K, V> parent = new Node<>(pair.key, pair.value);
        size++;
        parent.left = createBST(pairs, low, mid - 1);
        if (parent.left != null) {
            parent.left.parent = parent;
        }
        parent.right = createBST(pairs, mid + 1, high);
        if (parent.right != null) {
            parent.right.parent = parent;
        }
        // This line is critical for being able to add additional nodes or to
        // remove nodes. Forgetting this line leads to incorrectly balanced
        // trees.
        parent.height =
                Math.max(avlHeight(parent.left), avlHeight(parent.right)) + 1;
        return parent;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
        NodeOldValuePair nvp = new NodeOldValuePair(null, null);
        nvp = insertAndBalance(key, value, root, nvp);
        return nvp.oldValue;
    }

    private Node<K, V> removeHelper(K key, Node<K,V> node){
        if (node == null){
            return null;
        }
        int compareResult = key.compareTo(node.key);

        if (compareResult < 0){
            node.left = removeHelper(key, node.left);
        } else if (compareResult > 0) {
            node.right = removeHelper(key, node.right);
        } else if (node.left != null && node.right != null) {
            Node<K,V> minNode = treeMinimum(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = removeHelper(node.key, node.right);
            if (node.right != null){
                node.right.parent = node;
            }
        } else {
            Node<K,V> tmp = node.parent;
            node = (node.left != null) ? node.left : node.right;
            if (node != null){
                node.parent = tmp;
            }
        }
        return balance(node);
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key) {
        // reinvent the wheel: iterativeSearch() in BSTMap
        Node<K,V> n = root;
        while(n != null){
            if (n.key.compareTo(key) < 0){
                n = n.right;
            } else if (n.key.compareTo(key) > 0){
                n = n.left;
            } else {
                break;
            }
        }

        if (n == null) { // nothing to remove
            return null;
        }
        V val = n.value;
        if (height() == 0 && key.compareTo(root.key) == 0){
            root = null;
        } else {
            root = removeHelper(key, root);
        }
        size--;
        return val;
    }

    private NodeOldValuePair insertAndBalance(
            K key, V value, Node<K, V> t, NodeOldValuePair nvp) {
        if (t == null) {
            size++;
            nvp.node = new Node<K, V>(key, value);
            if (root == null) {
                root = nvp.node;
            }
            return nvp;
        }
        int comparison = key.compareTo(t.key);

        // TODO
        // Complete the missing section of code here.
        if (comparison < 0){
            t.left = insertAndBalance(key, value, t.left, nvp).node;
            t.left.parent = t;
        } else if (comparison > 0) {
            t.right = insertAndBalance(key, value, t.right, nvp).node;
            t.right.parent = t;
        } else {
            nvp.oldValue = t.value; // replace old value
            t.value = value;
        }

        Node<K, V> n = balance(t);
        nvp.node = n;
        return nvp;
    }

    private Node<K, V> balance(Node<K, V> t) {
        // TODO
        if (t == null){
            return t;
        }

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE){
            if (t.left != null && height(t.left.left) >= height(t.left.right)){
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        } else {
            if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE){
                if (t.right!= null && height(t.right.right) >= height(t.right.left)){
                    t = rotateWithRightChild(t);
                } else {
                    t = doubleWithRightChild(t);
                }
            }
        }
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private int avlHeight(Node<K, V> t) {
        return t == null ? -1 : t.height;
    }

    private Node<K, V> rotateWithLeftChild(Node<K, V> k2) {
        // TODO
        Node<K,V> k1 = k2.left;
        k2.left = k1.right;
        if (k1.right != null) {
            k1.right.parent = k2;
        }

        k1.right = k2;
        k1.parent = k2.parent;
        k2.parent = k1;

        k2.height = Math.max(height(k2.left), height(k2.right)) +1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;

        if (k2.key.compareTo(root.key)==0){
            // reset root
            root = k1;
        }
        return k1;
    }

    private Node<K, V> rotateWithRightChild(Node<K, V> k1) {
        // TODO
        Node<K,V> k2 = k1.right;
        k1.right = k2.left;
        if (k2.left != null){
            k2.left.parent = k1;
        }

        k2.left = k1;
        k2.parent = k1.parent;
        k1.parent = k2;

        k1.height = Math.max(height(k1.right), height(k1.left)) + 1;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;

        if (k1.key.compareTo(root.key)==0){
            // reset root
            root = k2;
        }
        return k2;
    }

    private Node<K, V> doubleWithLeftChild(Node<K, V> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node<K, V> doubleWithRightChild(Node<K, V> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private class NodeOldValuePair {
        Node<K, V> node;
        V oldValue;

        NodeOldValuePair(Node<K, V> n, V oldValue) {
            this.node = n;
            this.oldValue = oldValue;
        }
    }

    public static void main(String[] args) {
        boolean usingInts = true;
        if (args.length > 0) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                usingInts = false;
            }
        }

        AVLTreeMap avlTree;
        if (usingInts) {
            @SuppressWarnings("unchecked")
            Pair<Integer, Integer>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    int val = Integer.parseInt(args[i]);
                    pairs[i] = new Pair<>(val, val);
                } catch (NumberFormatException nfe) {
                    System.err.println("Error: Invalid integer '" + args[i]
                            + "' found at index " + i + ".");
                    System.exit(1);
                }
            }
            avlTree = new AVLTreeMap<Integer, Integer>(pairs);
        } else {
            @SuppressWarnings("unchecked")
            Pair<String, String>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                pairs[i] = new Pair<>(args[i], args[i]);
            }
            avlTree = new AVLTreeMap<String, String>(pairs);
        }

        System.out.println(avlTree.toAsciiDrawing());
        System.out.println();

//        System.out.println("Remove " + 3);
//        avlTree.remove(3);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//
//        System.out.println("Remove " + 7);
//        avlTree.remove(7);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 8);
//        avlTree.remove(8);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 6);
//        avlTree.remove(6);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 1);
//        avlTree.remove(1);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 4);
//        avlTree.remove(4);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 5);
//        avlTree.remove(5);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 2);
//        avlTree.remove(2);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 0);
//        avlTree.remove(0);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();
//
//        System.out.println("Remove " + 9);
//        avlTree.remove(9);
//        System.out.println(avlTree.toAsciiDrawing());
//        System.out.println();

        System.out.println("Height:                   " + avlTree.height());
        System.out.println("Total nodes:              " + avlTree.size());
        System.out.printf("Successful search cost:   %.3f\n",
                avlTree.successfulSearchCost());
        System.out.printf("Unsuccessful search cost: %.3f\n",
                avlTree.unsuccessfulSearchCost());
        avlTree.printTraversal(PREORDER);
        avlTree.printTraversal(INORDER);
        avlTree.printTraversal(POSTORDER);
    }
}
