/**
 * This pattern is used to abstract traversal logic of a collection.
 * By doing that, we take care of separation of concerns. The collection itself stores information about the data and their connectivity only.
 * And the iterator class takes care of traversal.
 * In this example, a TreeNode is an element of a collection. It is only concerned with value and left and right children
 * Traversal is taken care of by BfsTree class which implements TreeIterable interface and implements the iteration logic.
 */

import java.util.LinkedList;
import java.util.Queue;
class IteratorPattern {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        root.left.left.left = new TreeNode(8);

        BfsTree tree = new BfsTree(root);

        while(tree.hasNext()) {
            System.out.println(tree.next().value);
        }
    }
}

interface TreeIterable<T> {
    public boolean hasNext();
    public T next();
}

public class TreeNode {
    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int value) {
        this.value = value;
        left = right = null;
    }
}

public class BfsTree implements TreeIterable<TreeNode> {
    Queue<TreeNode> queue;

    public BfsTree(TreeNode root) {
        queue = new LinkedList<>();
        queue.offer(root);
    }

    @Override
    public boolean hasNext() {
        if(queue.peek() == null) return false;
        return true;
    }

    @Override
    public TreeNode next() {
        if(queue.peek() == null) return null;
        if(queue.peek().left != null) queue.offer(queue.peek().left);
        if(queue.peek().right != null) queue.offer(queue.peek().right);
        return queue.poll();
    }
}