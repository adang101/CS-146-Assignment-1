package Assignment3;

/* ------------------------------------------------------------------
  In a file (unsorted) unsorted_names_160k.txt, the words given are
  in random order. 

  Task:
  * Create an unbalanced binary tree (class BinaryTree)
  * Insert the words into the tree in the order in which you read the 
        words. By reading the words in the same order as they appear 
        in the file, you will ensure that the trees are consistent 
        between different implementations. 
  * Support the operations insert and traverse
  ---------------------------------------------------------------- */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    private boolean successfulDeletion;

    private Node root;

    private class Node {
        private String key;
        private Node left, right;

        Node(String key) {
            this.key = key;
            left = null;
            right = null;
        }
    }

    
    // Default Constructor
    public BinaryTree() {
        root = null;
        successfulDeletion = false;
    }

    public void insert(String key) {
        if (root == null) {
            root = new Node(key);
        } else {
            insert(key, root);
        }
    }

    private void insert(String key, Node node) {
        if (key.compareTo(node.key) < 0) {
            if (node.left == null) {
                node.left = new Node(key);
            } else {
                insert(key, node.left);
            }
        } else {
            if (node.right == null) {
                node.right = new Node(key);
            } else {
                insert(key, node.right);
            }
        }
    }

    public void traverse() {
        traverse(root, 0);
        System.out.println("\n");
    }

    // Pre-order traversal
    private void traverse(Node node, int level) {
        if (node != null) {
            // Print level
            for (int i = 0; i < level; i++) {
                System.out.print(" ");
            }
            System.out.print(node.key + " ");
            if (node.left != null) {
                System.out.print("/L->");
                traverse(node.left, level + 1);
            }
            if (node.right != null) {
                System.out.print("/R->");
                traverse(node.right, level + 1);
            }
        }
    }
    // In-order traversal
    public void traverseInOrder() {
        traverseInOrder(root);
        System.out.println("\n");
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(node.key + " ");
            traverseInOrder(node.right);
        }
    }

    // In-order traversal - Write to a file
    public String traverseInOrderString() {
        StringBuilder sb = new StringBuilder();
        traverseInOrderString(root, sb);
        return sb.toString();
    }
    
    private void traverseInOrderString(Node node, StringBuilder sb) {
        if (node != null) {
            traverseInOrderString(node.left, sb);
            sb.append(node.key).append("\n");
            traverseInOrderString(node.right, sb);
        }
    }

    // Public method to call search
    public boolean search(String key) {
        return search(key, root);
    }

    // Private method to search for a key
    private boolean search(String key, Node node) {
        if (node == null) {
            return false;
        } else if (key.compareTo(node.key) == 0) {
            return true;
        } else if (key.compareTo(node.key) < 0) {
            return search(key, node.left);
        } else {
            return search(key, node.right);
        }
    }

    // Public method to call remove
    public boolean remove(String key) {
        boolean deleted = false;
        root = remove(key, root);
        if (successfulDeletion) {
            deleted = true;
            successfulDeletion = false;
        }
        return deleted;
    }

    // Private method to remove a key
    private Node remove(String key, Node node) {
        if (node == null) {
            return null;
        }
    
        if (key.compareTo(node.key) < 0) {
            node.left = remove(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(key, node.right);
        } else {
            // Key to delete is found in this node
            successfulDeletion = true;
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                // Node has two children
                node.key = findMinKey(node.right);
                node.right = remove(node.key, node.right);
            }
        }
        return node;
    }
    
    private String findMinKey(Node node) {
        if (node.left == null) {
            return node.key;
        }
        return findMinKey(node.left);
    }

}
