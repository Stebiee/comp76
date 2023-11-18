public class BinarySearchTree {

    Node root;// each BST should have a root node

    /**
     * inserts a node into the BST
     * @param node node to insert
     */
    public void insert(Node node) {
        root = insertHelper(root, node);
    }

    private Node insertHelper(Node root, Node node) {
        int data = node.data;

        if (root == null) {
            // node would be the first node so assign it root
            root = node;
            return root;
        } else if (data < root.data) {
            // nodes data is less than root so go to the left 
            root.left = insertHelper(root.left, node);
        } else {
            // nodes data is greater than root so go right 
            root.right = insertHelper(root.right, node);
        }

        return root;
    }

    public void display() {
        displayHelper(root);
    }

    private void displayHelper(Node root) {
        if (root == null) {
            return; // no existing node, cant do anything
        }
        displayHelper(root.left);// data all the way to the left is the least
        System.out.println(root.data);// would be the second least
        displayHelper(root.right);// would be 3rd
    }

    public boolean search(int data) {
        return searchHelper(root, data);
    }

    private boolean searchHelper(Node root, int data) {
        if (root == null) {
            return false; // tree is empty
        } else if (root.data == data) {
            return true; // root is data
        }else if (data < root.data) {
            return searchHelper(root.left, data);// go left
        } else {
            return searchHelper(root.right, data); // go right 
        }
    }

    public void remove(int data) {
        // check if data is in the BST
        if (!search(data)) {
            System.out.println(data + " is not in BST");
            return; // data is not in BST
        }
        // data is in BST
        removeHelper(root, data);

    }

    private Node removeHelper(Node root, int data) {
        if (root == null) {
            return root;
        } else if (data < root.data) {
            root.left = removeHelper(root.left, data); // go as far left as possible
        } else if (data > root.data) {
            root.right = removeHelper(root.right, data); // go as far right as possible
        } else {
            // found the node
            if (root.left == null & root.right == null) {
                // node to be removed is a leaf
                root = null;
            } else if (root.right != null) {
                // there is a right child so successor is needed for replacement
                root.data = successor(root);
                root.right = removeHelper(root.right, root.data);
            } else {
                // there is a left child so predecessor is needed for replacement
                root.data = predecessor(root);
                root.left = removeHelper(root.left, root.data);
            }
        }

        return root;
    }

    /**
     * @param root
     * @return the least value in the right subtree of root
     */
    private int successor(Node root) {
        root = root.right; // set the root to be right child of passed node

        while (root.left != null) {
            // loop will go left most child in subtree
            root = root.left;
        }

        return root.data;
    }

    /**
     * 
     * @param root
     * @return the greatest value in the left subtree
     */
    private int predecessor(Node root) {
        root = root.left; // set the root to be left child of passed node

        while (root.right != null) {
            // loop will go to right mode child in subtree
            root = root.right;
        }

        return root.data; 
    }
}