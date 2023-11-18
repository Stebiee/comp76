public class Main {
    public static void main(String[] args) {
        MyBinarySearchTree<Integer> tree = new MyBinarySearchTree<>();

        tree.insert(4);
        tree.insert(5);
        tree.insert(7);
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
        tree.insert(6);

        tree.inorder(data -> {
            System.out.print(data + " ");
            return null;
        });
        System.out.println();

        tree.preorder(data -> {
            System.out.print(data + " ");
            return null;
        });
        System.out.println();

        tree.postorder(data -> {
            System.out.print(data + " ");
            return null;
        });
        System.out.println();

    }
}
