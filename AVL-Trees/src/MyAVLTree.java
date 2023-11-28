import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * Assignment for the Binary Search Tree module.
 * You need to implement all the TODOs.
 * @author Esteban Madrigal
 */
public class MyAVLTree<T extends Comparable<T>> {
    /**
     * Each node in the linked list is represented by this class. It contains a forward and backward pointer
     * to allow traversal in both directions
     * 
     */
    static class TreeNode<T> {
        /**
         * balance will be positve if right heavy
         * right node height - left node height
         */
        int balance; // the balance factor for the node
        T data; // data node will contain
        /**
         * height is -1 if node is null
         * 0 if single node
         * or recursively height is the max value 
         * between left and right subtree
         *                     3 <- node
         * left height 0 -> 1     4 <- right height 0
         *    height 1 -> 0  2
         * max between left and right is 1
         * so height of node is max plus 1
         * height of node is 1 + 1 = 2
         */
        int height; // height of node in tree
        TreeNode<T> left, right; // pointers for left and right children

        /**
         * Creates a new node and set its prev/next pointers.
         */
        public TreeNode(T t, TreeNode<T> left, TreeNode<T> right) {
            data = t;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return data + "(left->" + left + ", right->" + this.right + ")";
        }
    };

    /**
     * The root of the tree.
     */ 
    TreeNode<T> root;
    int nodes = 0; // amount of nodes in the avl tree

    /**
     * Inserts the given element in the tree. Balances the tree after insertion
     * @param data
     * @return true if the element was inserted, false if it wasn't (ie. duplicate or t is null)
     */
    public boolean insert(T data) {
        if (data == null) {
            return false;
        }
        if (!search(root, data)) {
            // value is not a duplicate
            root = insert(root, data); // 
            nodes++; // increment node count
            return true;
        }
        // AVL tree contains data
        return false;
    }

    /**
     * inserts given data into the AVL tree
     * @param node
     * @param data
     * @return
     */
    private TreeNode<T> insert(TreeNode<T> node, T data) {
        // base case
        if (node == null) {
            // reached the correct position in tree
            return new TreeNode<T>(data, null, null);
        }

        if (data.compareTo(node.data) < 0) {
            // recursive call to insert on left subtree
            node.left = insert(node.left, data);
        } else {
            // data is greater
            // recursive call to insert on right subtree
            node.right = insert(node.right, data);
        }

        // after data was inserted balance the tree
        // update balance factor
        update(node);
        // balance the tree
        return balance(node);
    }

    /**
     * update the balance and height factor for node
     * if child node == null value is -1
     * @param node
     */
    private void update(TreeNode<T> node) {
        // if a childs node is null then set value to -1 else set to that childs height
        int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
        int rightNodeHeight = (node.right == null) ? -1 : node.right.height;

        // update node height to be the highest between left or right subtree
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);

        // update the balance 
        node.balance = rightNodeHeight - leftNodeHeight;
    }

    private TreeNode<T> balance(TreeNode<T> node) {
        if (node.balance <= -2) {
            // tree is left heavy

            // left left case 
            if (node.left.balance <= 0) {
                return leftLeftCase(node);
            } else {
                // left right case
                return leftRightCase(node);
            }
        } else if (node.balance >= 2) {
            // tree is right heavy

            // right right case
            if (node.right.balance >= 0) {
                return rightRightCase(node);
            } else {
                // right left case
                return rightLeftCase(node);
            }
        }

        // node has a balance of 0 1 or -1 which is balanced or offset by 1
        return node;
    }

    private TreeNode<T> leftLeftCase(TreeNode<T> node) {
        return rightRotation(node);
    }

    private TreeNode<T> leftRightCase(TreeNode<T> node) {
        node.left = leftRotation(node.left);
        return leftLeftCase(node);
    }

    private TreeNode<T> rightRightCase(TreeNode<T> node) {
        return leftRotation(node);
    }

    private TreeNode<T> rightLeftCase(TreeNode<T> node) {
        node.right = rightRotation(node.right);
        return rightRightCase(node);
    }

    /**
     * 
     * @param node
     * @return
     */
    private TreeNode<T> leftRotation(TreeNode<T> node) {
        TreeNode<T> newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        // values change for height and balance so update them
        update(node);// node must go first since its the child
        update(newParent);// if parent goes first the child balance is incorrect
        return newParent;
    }

    /**
     * 
     * @param node
     * @return
     */
    private TreeNode<T> rightRotation(TreeNode<T>node) {
        TreeNode<T> newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }

    /**
     * Deletes the given element from the tree. Balances the tree after deletion.
     * @param t
     * @return true if element existed (and was deleted), false otherwise.
     */
    public boolean delete(T data) {
        if (search(root, data)) {
            // data is inside tree
            root = delete(root, data);
            nodes--;// since element remove decrement 
            return true;
        }
        return false;
    }

    private TreeNode<T> delete(TreeNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.data) < 0) {
            // data is less than node
            node.left = delete(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = delete(node.right, data);
        } else {
            // arrived at node to delete

            // case with only right or no subtree
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                // case with left or no subtree 
                return node.left;
            } else {
                // node has left and right child
                // node is largest value in left subtree 
                // while smallest in right subtree
                // choose the successor based on Tree balance 

                // removing from left subtree
                if (node.left.height > node.right.height) {
                    // swap successor into node
                    T successor = findMax(node.left);
                    node.data = successor;

                    // remove rightmost node from left subtree
                    node.left = delete(node.left, successor);
                } else {
                    // swap successor into node
                    T successor = findMin(node.right);
                    node.data = successor;

                    // remove the leftmost node form right subtree
                    node.right = delete(node.right, successor);
                }
            }
        }

        // balance and height was changed update them 
        update(node);

        // re-balance
        return balance(node);
    }

    /**
     * goes through left children of passed node 
     * returns the furthest to the left child
     * @param node
     * @return data at child
     */
    private T findMin(TreeNode<T> node) {
        while(node.left != null) {
            node = node.left;
        }
        // arrived at left most node
        return node.data;
    }

    /**
     * goes through right children of passed node
     * returns the furthest to the right child
     * @param node
     * @return data at child
     */
    private T findMax(TreeNode<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        // arrived at right most node
        return node.data;
    }
    /**
     * searches for given element in AVL tree
     * @param data element to look for
     * @return true if element was found
     */
    public boolean search(T data){
        return search(root, data); 
        
    }

    /**
     * Search for the value in the tree.
     * @param t The value to search for.
     * @return true if the value is found in the tree, false otherwise.
     */
    private boolean search(TreeNode<T> node, T data){
        if (node == null) {
            return false; // no data in node, data not in tree
        }

        if (data.compareTo(node.data) < 0) {
            // data is less than current node recursive call on left child
            return search(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            // data is greater than current node recursive call on right child
            return search(node.right, data);
        }else {
            // node.data == data
            return true;
        }
    }
}
