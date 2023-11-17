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
        // TODO: Implement this method.
        return false;
    }

    /**
     * Deletes the given element from the tree.
     * @param t
     * @return true if element existed (and was deleted), false otherwise.
     */
    public boolean delete(T t) {
        // TODO: Implement this method. 
        return false;
    }

    // Inorder traversal, postorder traversal, preorder traversal.
    /**
     * Inorder traversal of the tree.
     * @param fn The function to call when traversing a node.
     */
    public void inorder(Function<T, Void> fn) {
        // TODO: Do an inorder traversal of the tree and call the given function with the element
        // To call the function you invoke the apply method in it. (e.g. fn.apply(t) where t is the data in
        // the TreeNode that is being traversed).
    }

    /**
     * Preorder traversal of the tree.
     * @param fn The function to call when traversing a node.
     */
    public void preorder(Function<T, Void> fn) {
        // TODO: Do an inorder traversal of the tree and call the given function with the element
        // To call the function you invoke the apply method in it. (e.g. fn.apply(t) where t is the data in
        // the TreeNode that is being traversed).
    }

    /**
     * Postorder traversal of the tree. 
     * @param fn The function to call when traversing a node.
     */
    public void postorder(Function<T, Void> fn) {
        // TODO: Do an inorder traversal of the tree and call the given function with the element
        // To call the function you invoke the apply method in it. (e.g. fn.apply(t) where t is the data in
        // the TreeNode that is being traversed).
    }

    /**
     * Search for the value in the tree.
     * @param t The value to search for.
     * @return true if the value is found in the tree, false otherwise.
     */
    public boolean search(T t) {
        // TODO: Implement this method.
        return false;
    }
}
