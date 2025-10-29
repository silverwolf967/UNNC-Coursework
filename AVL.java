import java.util.*;

// AVL Tree Node Class
class AVLNode {
    int value;
    int height;
    AVLNode left;
    AVLNode right;

    AVLNode(int value) {
        this.value = value;
        this.height = 1;
    }
}

// AVL Tree Class
class AVLTree{
    private AVLNode root;

    // Calculate the node height
    private int height(AVLNode node){
        if(node==null){
            return 0;
        }
        return node.height;
    }

    // Calculate the balance factor
    private int balance_Factor(AVLNode node){
        if (node == null){
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // Perform the right rotation
    private AVLNode right_Rotation(AVLNode y){
        // Find the rotating node
        AVLNode x = y.left;
        AVLNode z = x.right;

        // Rotate
        x.right=y;
        y.left=z;

        // Update heights
        y.height=Math.max(height(y.left),height(y.right))+1;
        x.height=Math.max(height(x.left),height(x.right))+1;

        // Return the new root
        return x;
    }

    // Perform the left rotation
    private AVLNode left_Rotation(AVLNode y){
        // Find the rotating node
        AVLNode x = y.right;
        AVLNode z = x.left;

        // Rotate
        x.left=y;
        y.right=z;

        // Update heights
        y.height=Math.max(height(y.left),height(y.right))+1;
        x.height=Math.max(height(x.left),height(x.right))+1;

        // Return the new root
        return x;
    }

    // Balance the AVL tree
    private AVLNode balanceNode(AVLNode node) {
        if (node == null){
            return node;
        }

        // Update height
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Update balance factor
        int balance = balance_Factor(node);

        // left-left situation
        if (balance > 1 && balance_Factor(node.left) >= 0) {
            return right_Rotation(node);
        }

        // left-right situation
        if (balance > 1 && balance_Factor(node.left) < 0) {
            node.left = left_Rotation(node.left);
            return right_Rotation(node);
        }

        // right-right situation
        if (balance < -1 && balance_Factor(node.right) <= 0) {
            return left_Rotation(node);
        }

        // right-left situation
        if (balance < -1 && balance_Factor(node.right) > 0) {
            node.right = right_Rotation(node.right);
            return left_Rotation(node);
        }

        return node;
    }

    // Usual standard insertion algorithm
    private AVLNode usual_Standard(AVLNode node, int value) {
        if (node == null){
            return new AVLNode(value);
        }

        if (value < node.value) {
            node.left = usual_Standard(node.left, value);
        } else if (value > node.value) {
            node.right = usual_Standard(node.right, value);
        } else {
            return node;
        }

        return balanceNode(node);
    }

    // Special insertion algorithm for even values
    private AVLNode specialInsert(AVLNode node, int value) {
        if (node == null) {
            return new AVLNode(value);
        }

        // for even values, we need to insert the value into the left subtree
        if (node.right != null) {
            // if right child is not null, insert the value into the right subtree
            node.right = specialInsert(node.right, value);
        } else {
            // if right child is null, insert the value into the right child
            node.right = new AVLNode(value);
        }

        return balanceNode(node);
    }

    // Insert the value into the AVL tree
    public void insert(int value) {
        if (value % 2 == 0) {
            // even value insertion
            root = specialInsert(root, value);
        } else {
            // odd value insertion
            root = usual_Standard(root, value);
        }
    }

    // Post-order traversal of the AVL tree
    public void postOrderTraversal() {
        post_Order(root);
    }

    private void post_Order(AVLNode node) {
        if (node != null) {
            post_Order(node.left);
            post_Order(node.right);
            System.out.println(node.value);
        }
    }
}

public class AVL {
    // Read integer array from input.txt file
    public static int[] readIntArrayFromInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                // Use regular expressions to match multiple delimiters
                String[] integer_number = input.split("[,;\\s]+");

                int[] intArray = new int[integer_number.length];
                for (int i = 0; i < integer_number.length; i++) {
                    if (!integer_number[i].trim().isEmpty()) {
                        intArray[i] = Integer.parseInt(integer_number[i].trim());
                    }
                }
                return intArray;
            }
        } finally {
            scanner.close();
        }
        return new int[0];
    }

    public static void main(String[] args) {
        int[] numbers = readIntArrayFromInput();
        AVLTree avlTree = new AVLTree();
        // Insert the numbers into the AVL tree
        for (int number : numbers) {
            avlTree.insert(number);
        }

        avlTree.postOrderTraversal();
    }
}
