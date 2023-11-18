import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

import javax.swing.tree.TreeNode;

/**
 * Assignment for the Binary Search Tree module.
 * You need to implement all the TODOs.
 * @author Esteban Madrigal
 */
public class MyBinarySearchTree<T extends Comparable<T>> {
    /**
     * Each node in the linked list is represented by this class. It contains a forward and backward pointer
     * to allow traversal in both directions
     */
    static class TreeNode<T> {
        T data;
        TreeNode<T> left, right;

        /**
         * Creates a new node and set its prev/next pointers.
         */
        public TreeNode(T t, TreeNode<T> left, TreeNode<T> right) {
            data = t; // node data
            this.left = left; // left subtree
            this.right = right;// right subtree
        }

        public String toString() {
            return data + "(left->" + left + ", right->" + this.right + ")";
        }
    };

    /**
     * The root of the tree.
     */ 
    TreeNode<T> root;

    /**
     * Inserts the given element in the tree
     * @param t
     * @return true if the element was inserted, false if it wasn't (ie. there was a duplicate element)
     */
    public boolean insert(T t) {
        TreeNode<T> node = new TreeNode<>(t, null, null);
        root = insertHelper(root, node);
        return true;
    }

    private TreeNode<T> insertHelper(TreeNode<T> root, TreeNode<T> node) {
        T data = node.data;

        if (root == null) {
            // node would be the first so assign it root
            root = node;
        } else if (data.compareTo((T)root.data) < 0) {
            // nodes data is less than root so go to the left
            root.left = insertHelper(root.left, node);
        } else if (data.compareTo((T)root.data) > 0){
            // nodes data is greater than root so go right 
            root.right = insertHelper(root.right, node);
        } else {
            // nodes data is equal, dont add 
            return root;
        }

        return root;
    }

    /**
     * Deletes the given element from the tree.
     * @param t
     * @return true if element existed (and was deleted), false otherwise.
     */
    public boolean delete(T t) {
        // check if t is in BST
        if (!this.search(t)) {
            System.out.println(t.toString() + " was not in BST");
            return false; // t was not in BST
        }
        // t is in BST
        deleteHelper(root, t);
        return true;
    }

    private TreeNode<T> deleteHelper(TreeNode<T> root, T t) {
        if (root == null) {
            return null; // list is empty nothing to remove 
        } else if (t.compareTo((T)root.data) < 0) {
            root.left = deleteHelper(root.left, t); // keep going to left child while less than root
        } else if (t.compareTo((T)root.data) > 0) {
            root.right = deleteHelper(root.right, t); // keep going to right child while less than root
        } else {
            // t was found
            if (root.left == null && root.right == null) {
                // node to be removed is a leaf
                root = null; // set node to null 
            } else if (root.right != null) {
                // there is a right child so successor is needed for replacement 
                root.data = successor(root);
                root.right = deleteHelper(root.right, (T)root.data);
            } else {
                // there is a left child so predecessor is needed for replacement
                root.data = predecessor(root);
                root.left = deleteHelper(root.left, (T)root.data);
            }
        }

        return root;
    }
    /**
     * @param root
     * @return the least value in the right subtree of root
     */
    private T successor(TreeNode<T> root) {
        root = root.right; // set the root to be right child of passed node

        while (root.left != null) {
            // loop will go left most child in subtree
            root = root.left;
        }

        return (T)root.data;
    }

    /**
     * 
     * @param root
     * @return the greatest value in the left subtree
     */
    private T predecessor(TreeNode<T> root) {
        root = root.left; // set the root to be left child of passed node

        while (root.right != null) {
            // loop will go to right mode child in subtree
            root = root.right;
        }

        return (T)root.data; 
    }

    // Inorder traversal, postorder traversal, preorder traversal.
    /**
     * Inorder traversal of the tree.
     * @param fn The function to call when traversing a node.
     */
    public void inorder(Function<T, Void> fn) {
        // To call the function you invoke the apply method in it. (e.g. fn.apply(t) where t is the data in
        // the TreeNode that is being traversed).
        inorder(root, fn);
    }

    private void inorder(TreeNode<T> node, Function<T, Void> fn) {
        if (node != null) {
            inorder(node.left, fn);
            fn.apply(node.data);
            inorder(node.right, fn);
        }
    }

    /**
     * Preorder traversal of the tree.
     * @param fn The function to call when traversing a node.
     */
    public void preorder(Function<T, Void> fn) {
        // To call the function you invoke the apply method in it. (e.g. fn.apply(t) where t is the data in
        // the TreeNode that is being traversed).
        preorder(root, fn);
    }

    private void preorder(TreeNode<T> node, Function<T, Void> fn) {
        if (node != null) {
            fn.apply(node.data); // root node first
            preorder(node.left, fn); // traverse left subtree
            preorder(node.right, fn); // traverse right subtree
        }
    }

    /**
     * Postorder traversal of the tree. 
     * @param fn The function to call when traversing a node.
     */
    public void postorder(Function<T, Void> fn) {
        // To call the function you invoke the apply method in it. (e.g. fn.apply(t) where t is the data in
        // the TreeNode that is being traversed).
        postorder(root, fn);
    }

    private void postorder(TreeNode<T> node, Function<T, Void> fn) {
        if (node != null) {
            postorder(node.left, fn); // traverse left subtree
            postorder(node.right, fn); // traverse right subtree
            fn.apply(node.data); // root node
        }
    }

    /**
     * Search for the value in the tree.
     * @param t The value to search for.
     * @return true if the value is found in the tree, false otherwise.
     */
    public boolean search(T t) {
        return searchHelper(root, t);
    }

    private boolean searchHelper(TreeNode<T> root, T data) {
        if (root == null) {
            return false; // tree is empty
        } else if (root.data.equals(data)) {
            return true; // root is data
        } else if (data.compareTo((T)root.data) < 0) {
            // data is less than so recursive call with left child
            return searchHelper(root.left, data);
        } else {
            // data is greater than root so recursive call with right child
            return searchHelper(root.right, data);
        }
    }
}
