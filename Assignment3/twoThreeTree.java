package Assignment3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/* ------------------------------------------------------------------
  In a file (unsorted) unsorted_names_160k.txt, the words given are
  in random order. 
  
  Task:
  * Create a 2-3 tree 
  * Insert the words into the tree in the order in which you read the 
        words. By reading the words in the same order as they appear 
        in the file, you will ensure that the trees are consistent 
        between different implementations. 
  * Support the operations insert and traverse
  ---------------------------------------------------------------- */

import java.util.LinkedList;
import java.util.Queue;

public class twoThreeTree {

    // Private member variables of the class
    private int size;
    private TreeNode root;
    private boolean successfulInsertion;
    private boolean successfulDeletion;
    private boolean split;
    private boolean underflow;
    private boolean first;
    private boolean singleNodeUnderflow;

    private enum Nodes {
        LEFT, MIDDLE, RIGHT, DUMMY;
    }

    // Default Constructor
    public twoThreeTree() {
        // Initializing everything to default values
        size = 0;
        root = null;
        successfulInsertion = false;
        successfulDeletion = false;
        underflow = false;
        singleNodeUnderflow = false;
        split = false;
        first = false;
    } // constructor ends here

    // Node class
    private class Node {

    }

    // TreeNode class inherits from Node
    private class TreeNode extends Node {

        // Member variables
        String keys[];             // keys for searching
        Node children[];        // references to the 2 or 3 children
        int degree;             // number of children each node has 

        // Default constructor
        TreeNode() {
            // Initializing member variables
            keys = new String[2];          // keys[0] = min key in middle subtree
            children = new Node[3];     // references to children of leaves
            degree = 0;                 // for printing, overflow, and split operations
        } // constructor ends here

        // Print method
        void print() {

            if (degree == 1) {
                System.out.print("(-,-)");
            } else if (degree == 2) {
                System.out.print("(" + keys[0] + ",-) ");
            } else {
                System.out.print("(" + keys[0] + "," + keys[1] + ") ");
            }
        } // print() ends here
    } // TreeNode ends here

    // LeafNode class inherits from Node 
    private class LeafNode extends Node {

        String key;    // to store the value

        LeafNode(String key) {
            this.key = key;
        }

        // Print method
        void print() {
            System.out.print(key + " ");
        } // print() ends here
    } // LeafNode ends here

    // Merge Root: If the root node has to split, it merges it
    // back to a single TreeNode
    private void insertKey(String key) {
        // Create an array of Node of size 2
        // to capture the 2 nodes
        Node[] array = new Node[2];
        // Call the method to insert the key
        array = insert(key, root);

        // Check if the second value in the array is null or not
        if (array[1] == null) {
            // If it is null, it means there is no need to merge the array
            root = (TreeNode) array[0]; // Simply assign the root to the first value
        } else {
            // We got two values in the array
            // and now we have to merge those into a single node

            // Create a new TreeNode and attach first and second element in the
            // array as a reference in the newly created node
            TreeNode treeRoot = new TreeNode();
            treeRoot.children[0] = array[0];
            treeRoot.children[1] = array[1];
            updateTree(treeRoot);   // Update the new node
            root = treeRoot;        // Assign root to this node
        } // if else ends here
    } // insertKey() ends here

    // Insert: Returns an array of nodes(max 2 nodes)
    // after inserting the value 
    private Node[] insert(String key, Node n) {
        Node array[] = new Node[2];
        Node catchArray[] = new Node[2];
        TreeNode t = null;
    
        if (n instanceof TreeNode) {
            t = (TreeNode) n;
        }
        if (root == null && !first) {
            first = true;
            TreeNode newNode = new TreeNode();
            t = newNode;
            t.children[0] = insert(key, t.children[0])[0];
            updateTree(t);
            array[0] = t;
            array[1] = null;
        }
        else if (t != null && !(t.children[0] instanceof LeafNode)) {
            if (key.compareTo(t.keys[0]) < 0) {
                catchArray = insert(key, t.children[0]);
                t.children[0] = catchArray[0];
                if (split) {
                    if (t.degree <= 2) {
                        split = false;
                        t.children[2] = t.children[1];
                        t.children[1] = catchArray[1];
                        updateTree(t);
                        array[0] = t;
                        array[1] = null;
                    } else if (t.degree > 2) {
                        TreeNode newNode = new TreeNode();
                        newNode.children[0] = t.children[1];
                        newNode.children[1] = t.children[2];
                        updateTree(newNode);
                        t.children[1] = catchArray[1];
                        t.children[2] = null;
                        updateTree(t);
                        array[0] = t;
                        array[1] = newNode;
                    }
                } else {
                    updateTree(t);
                    array[0] = t;
                    array[1] = null;
                }
            }
            else if (key.compareTo(t.keys[0]) >= 0 && (t.children[2] == null || key.compareTo(t.keys[1]) < 0)) {
                catchArray = insert(key, t.children[1]);
                t.children[1] = catchArray[0];
                if (split) {
                    if (t.degree <= 2) {
                        split = false;
                        t.children[2] = catchArray[1];
                        updateTree(t);
                        array[0] = t;
                        array[1] = null;
                    } else if (t.degree > 2) {
                        TreeNode newNode = new TreeNode();
                        newNode.children[0] = catchArray[1];
                        newNode.children[1] = t.children[2];
                        updateTree(newNode);
                        t.children[2] = null;
                        updateTree(t);
                        array[0] = t;
                        array[1] = newNode;
                    }
                } else {
                    updateTree(t);
                    array[0] = t;
                    array[1] = null;
                }
            }
            else if (key.compareTo(t.keys[1]) >= 0) {
                catchArray = insert(key, t.children[2]);
                t.children[2] = catchArray[0];
                if (split) {
                    if (t.degree > 2) {
                        TreeNode newNode = new TreeNode();
                        newNode.children[0] = catchArray[0];
                        newNode.children[1] = catchArray[1];
                        updateTree(newNode);
                        t.children[2] = null;
                        updateTree(t);
                        array[0] = t;
                        array[1] = newNode;

                    }
                } else {
                    updateTree(t);
                    array[0] = t;
                    array[1] = null;
                } 
            }
        }
        else if (t != null && t.children[0] instanceof LeafNode) {
            LeafNode l1 = null, l2 = null, l3 = null;
            if (t.children[0] != null && t.children[0] instanceof LeafNode) {
                l1 = (LeafNode) t.children[0];
            }
            if (t.children[1] != null && t.children[1] instanceof LeafNode) {
                l2 = (LeafNode) t.children[1];
            }
            if (t.children[2] != null && t.children[2] instanceof LeafNode) {
                l3 = (LeafNode) t.children[2];
            }
            if (t.degree <= 2) {
                if (t.degree == 1 && key.compareTo(l1.key) > 0) {
                    LeafNode leaf = new LeafNode(key);
                    t.children[1] = leaf;
                } else if (t.degree == 1 && key.compareTo(l1.key) < 0) {
                    LeafNode leaf = new LeafNode(key);
                    t.children[1] = l1;
                    t.children[0] = leaf;
                } else if (t.degree == 2 && key.compareTo(l1.key) < 0) {
                    LeafNode leaf = new LeafNode(key);
                    t.children[2] = l2;
                    t.children[1] = l1;
                    t.children[0] = leaf;
                } else if (t.degree == 2 && key.compareTo(l2.key) < 0 && key.compareTo(l1.key) > 0) {
                    LeafNode leaf = new LeafNode(key);
                    t.children[2] = l2;
                    t.children[1] = leaf;
                } else if (t.degree == 2) {
                    LeafNode leaf = new LeafNode(key);
                    t.children[2] = leaf;
                }
                updateTree(t);
                array[0] = t;
                array[1] = null;
            } 
            else if (t.degree > 2) {
                split = true;
                if (key.compareTo(l1.key) < 0) {
                    LeafNode leaf = new LeafNode(key);
                    TreeNode newNode = new TreeNode();
                    t.children[0] = leaf;
                    t.children[1] = l1;
                    t.children[2] = null;
                    updateTree(t);
                    newNode.children[0] = l2;
                    newNode.children[1] = l3;
                    updateTree(newNode);
                    array[0] = t;
                    array[1] = newNode;
                } else if (key.compareTo(l1.key) >= 0 && key.compareTo(l2.key) < 0) {
                    LeafNode leaf = new LeafNode(key);
                    TreeNode newNode = new TreeNode();
                    t.children[1] = leaf;
                    t.children[2] = null;
                    updateTree(t);
                    newNode.children[0] = l2;
                    newNode.children[1] = l3;
                    updateTree(newNode);
                    array[0] = t;
                    array[1] = newNode;
                } else if (key.compareTo(l2.key) >= 0 && key.compareTo(l3.key) < 0) {
                    LeafNode leaf = new LeafNode(key);
                    t.children[2] = null;
                    updateTree(t);
                    TreeNode newNode = new TreeNode();
                    newNode.children[0] = leaf;
                    newNode.children[1] = l3;
                    updateTree(newNode);
                    array[0] = t;
                    array[1] = newNode;
                } else if (key.compareTo(l3.key) >= 0) {
                    LeafNode leaf = new LeafNode(key);
                    t.children[2] = null;
                    updateTree(t);
                    TreeNode newNode = new TreeNode();
                    newNode.children[0] = l3;
                    newNode.children[1] = leaf;
                    updateTree(newNode);
                    array[0] = t;
                    array[1] = newNode;
                }
            }
            successfulInsertion = true;
        } else if (n == null) {
            successfulInsertion = true;
            array[0] = new LeafNode(key);
            array[1] = null;
            return array;
        }
        return array;
    } // recursive insert ends here

    // This recursive method removes the given key from the tree
    private Node remove(String key, Node n) {
        TreeNode t = null;
        if (n instanceof TreeNode) {
            t = (TreeNode) n;
        }
        if (n == null) {
            return null;
        }
        if (t != null && t.children[0] instanceof TreeNode) {
            if (key.compareTo(t.keys[0]) < 0) {
                t.children[0] = remove(key, t.children[0]);

                if (singleNodeUnderflow) {
                    TreeNode child = (TreeNode) t.children[0];
                    TreeNode rightChild = (TreeNode) t.children[1];
                    if (rightChild.degree == 2) {
                        rightChild.children[2] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[0];
                        rightChild.children[0] = child;
                        updateTree(rightChild);
                        t.children[0] = rightChild;
                        t.children[1] = t.children[2];
                        t.children[2] = null;
                        if (t.degree == 2) {
                            singleNodeUnderflow = true;
                            t = (TreeNode) t.children[0];
                        } else {
                            singleNodeUnderflow = false;
                        }
                    }
                    else if (rightChild.degree == 3) {
                        TreeNode newNode = new TreeNode();
                        newNode.children[0] = t.children[0];
                        newNode.children[1] = rightChild.children[0];
                        t.children[0] = newNode;
                        updateTree(newNode);
                        rightChild.children[0] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[2];
                        rightChild.children[2] = null;
                        updateTree(rightChild);
                        singleNodeUnderflow = false;
                    }
                }
                else if (underflow) {
                    underflow = false;
                    TreeNode child = (TreeNode) t.children[0];
                    TreeNode rightChild = (TreeNode) t.children[1];
                    if (rightChild.degree == 3) {
                        Node reference = rightChild.children[0];
                        rightChild.children[0] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[2];
                        rightChild.children[2] = null;
                        updateTree(rightChild);
                        child.children[1] = reference;
                        updateTree(child);
                    }
                    else if (rightChild.degree == 2) {
                        Node reference = child.children[0];
                        rightChild.children[2] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[0];
                        rightChild.children[0] = reference;
                        updateTree(rightChild);
                        t.children[0] = rightChild;
                        if (t.degree == 3) {
                            t.children[1] = t.children[2];
                            t.children[2] = null;
                        }
                        else {
                            Node ref = t.children[0];
                            t = (TreeNode) ref;
                            singleNodeUnderflow = true;
                        }
                    }
                }
                updateTree(t);
            }
            else if (key.compareTo(t.keys[0]) >= 0 && (t.children[2] == null || key.compareTo(t.keys[1]) < 0)) {
                t.children[1] = remove(key, t.children[1]);
                if (singleNodeUnderflow) {
                    TreeNode leftChild = (TreeNode) t.children[0];
                    TreeNode child = (TreeNode) t.children[1];
                    TreeNode rightChild = (TreeNode) t.children[2];
                    if (leftChild.degree == 2) {
                        leftChild.children[2] = child;
                        t.children[1] = rightChild;
                        t.children[2] = null;
                        updateTree(leftChild);
                        if (t.degree == 2) {
                            singleNodeUnderflow = true;
                            t = (TreeNode) t.children[0];
                        } else {
                            singleNodeUnderflow = false;
                        }
                    }
                    else if (rightChild != null && rightChild.degree == 2) {
                        rightChild.children[2] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[0];
                        rightChild.children[0] = child;
                        updateTree(rightChild);
                        t.children[1] = rightChild;
                        t.children[2] = null;
                        singleNodeUnderflow = false;

                    }
                    else if (leftChild.degree == 3) {
                        TreeNode newNode = new TreeNode();
                        newNode.children[0] = leftChild.children[2];
                        newNode.children[1] = child;
                        t.children[1] = newNode;
                        updateTree(newNode);
                        updateTree(leftChild);
                        singleNodeUnderflow = false;
                    }
                    else if (rightChild != null && rightChild.degree == 3) {
                        TreeNode newNode = new TreeNode();
                        newNode.children[0] = child;
                        newNode.children[1] = rightChild.children[0];
                        rightChild.children[0] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[2];
                        rightChild.children[2] = null;
                        t.children[1] = newNode;
                        updateTree(newNode);
                        updateTree(rightChild);
                        singleNodeUnderflow = false;
                    }
                }
                else if (underflow) {
                    underflow = false;
                    TreeNode leftChild = (TreeNode) t.children[0];
                    TreeNode child = (TreeNode) t.children[1];
                    TreeNode rightChild = (TreeNode) t.children[2];
                    if (leftChild.degree == 3) {
                        Node reference = leftChild.children[2];
                        leftChild.children[2] = null;
                        child.children[1] = child.children[0];
                        child.children[0] = reference;
                        updateTree(leftChild);
                        updateTree(child);
                    }
                    else if (rightChild != null && rightChild.degree == 3) {
                        Node reference = rightChild.children[0];
                        rightChild.children[0] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[2];
                        rightChild.children[2] = null;
                        updateTree(rightChild);
                        child.children[1] = reference;
                        updateTree(child);
                    }
                    else if (leftChild.degree == 2) {
                        Node reference = child.children[0];
                        leftChild.children[2] = reference;
                        updateTree(leftChild);
                        t.children[1] = null;
                        if (t.degree == 3) {
                            t.children[1] = t.children[2];
                            t.children[2] = null;
                        }
                        else {
                            singleNodeUnderflow = true;
                            t = (TreeNode) t.children[0];
                        }
                    }
                    else if (rightChild != null && rightChild.degree == 2) {
                        Node reference = child.children[0];
                        rightChild.children[2] = rightChild.children[1];
                        rightChild.children[1] = rightChild.children[0];
                        rightChild.children[0] = reference;
                        updateTree(rightChild);
                        t.children[1] = rightChild;
                        t.children[2] = null;
                        singleNodeUnderflow = false;
                    }
                }
                updateTree(t);
            }
            else if (key.compareTo(t.keys[1]) >= 0) {
                t.children[2] = remove(key, t.children[2]);
                if (singleNodeUnderflow) {
                    TreeNode child = (TreeNode) t.children[2];
                    TreeNode leftChild = (TreeNode) t.children[1];
                    if (leftChild.degree == 2) {
                        leftChild.children[2] = child;
                        t.children[2] = null;
                        updateTree(leftChild);
                    }
                    else if (leftChild.degree == 3) {
                        TreeNode newNode = new TreeNode();
                        newNode.children[0] = leftChild.children[2];
                        newNode.children[1] = t.children[2];
                        t.children[2] = newNode;
                        updateTree(newNode);
                        updateTree(leftChild);
                    }
                    singleNodeUnderflow = false;
                }
                else if (underflow) {
                    underflow = false;
                    TreeNode leftChild = (TreeNode) t.children[1];
                    TreeNode child = (TreeNode) t.children[2];
                    if (leftChild.degree == 3) {
                        Node reference = leftChild.children[2];
                        leftChild.children[2] = null;
                        child.children[1] = child.children[0];
                        child.children[0] = reference;
                        updateTree(leftChild);
                        updateTree(child);
                    }
                    else if (leftChild.degree == 2) {
                        Node reference = child.children[0];
                        leftChild.children[2] = reference;
                        updateTree(leftChild);
                        t.children[2] = null;
                    }
                }
                updateTree(t);
            }
        }
        else if (t != null && t.children[0] instanceof LeafNode) {
            LeafNode l1 = null, l2 = null, l3 = null;
            if (t.children[0] != null && t.children[0] instanceof LeafNode) {
                l1 = (LeafNode) t.children[0];
            }
            if (t.children[1] != null && t.children[1] instanceof LeafNode) {
                l2 = (LeafNode) t.children[1];
            }
            if (t.children[2] != null && t.children[2] instanceof LeafNode) {
                l3 = (LeafNode) t.children[2];
            }
            if (t.degree == 3) {
                if (key.equals(l1.key)) {
                    t.children[0] = l2;
                    t.children[1] = l3;
                    t.children[2] = null;
                } else if (key.equals(l2.key)) {
                    t.children[1] = l3;
                    t.children[2] = null;
                } else if (key.equals(l3.key)) {
                    t.children[2] = null;
                }
                updateTree(t);
            }
            else if (t.degree == 2) {
                underflow = true;
                if (l1.key.equals(key)) {
                    t.children[0] = l2;
                    t.children[1] = null;
                } else if (l2.key.equals(key)) {
                    t.children[1] = null;
                }
            }
            else if (t.degree == 1) {
                if (l1.key.equals(key)) {
                    t.children[0] = null;
                }
            }
            successfulDeletion = true;
        }
        return t;
    } // remove() ends here

    // This method updates the info stored in the TreeNodes
    private void updateTree(TreeNode t) {
        if (t != null) {
            if (t.children[2] != null && t.children[1] != null && t.children[0] != null) {
                t.degree = 3;
                t.keys[0] = getValueForKey(t, Nodes.LEFT);
                t.keys[1] = getValueForKey(t, Nodes.RIGHT);
            } else if (t.children[1] != null && t.children[0] != null) {
                t.degree = 2;
                t.keys[0] = getValueForKey(t, Nodes.LEFT);
                t.keys[1] = null;
            } else if (t.children[0] != null) {
                t.degree = 1;
                t.keys[1] = t.keys[0] = null;
            }
        }
    }

    // This method returns the value for the keys of the TreeNode
    private String getValueForKey(Node n, Nodes whichVal) {
        String key = null;
        TreeNode t = null;
        LeafNode l = null;
        if (n instanceof TreeNode) {
            t = (TreeNode) n;
        } else {
            l = (LeafNode) n;
        }
        if (l != null) {
            key = l.key;
        }
        if (t != null) {
            if (null != whichVal) {
                switch (whichVal) {
                    case LEFT:
                        key = getValueForKey(t.children[1], Nodes.DUMMY);
                        break;
                    case RIGHT:
                        key = getValueForKey(t.children[2], Nodes.DUMMY);
                        break;
                    case DUMMY:
                        key = getValueForKey(t.children[0], Nodes.DUMMY);
                        break;
                    default:
                        break;
                }
            }
        }
        return key;
    } // getValueForKey() ends here

    // This method searches the given key in the tree
    private boolean search(String key, Node n) {
        boolean found = false;
        TreeNode t = null;
        LeafNode l = null;
        if (n instanceof TreeNode) {
            t = (TreeNode) n;
        } else {
            l = (LeafNode) n;
        }
        if (t != null) {
            if (t.degree == 1) {
                found = search(key, t.children[0]);
            }
            else if (t.degree == 2 && key.compareTo(t.keys[0]) < 0) {
                found = search(key, t.children[0]);
            }
            else if (t.degree == 2 && key.compareTo(t.keys[0]) >= 0) {
                found = search(key, t.children[1]);
            }
            else if (t.degree == 3 && key.compareTo(t.keys[0]) < 0) {
                found = search(key, t.children[0]);
            }
            else if (t.degree == 3 && key.compareTo(t.keys[0]) >= 0 && key.compareTo(t.keys[1]) < 0) {
                found = search(key, t.children[1]);
            }
            else if (t.degree == 3 && key.compareTo(t.keys[1]) >= 0) {
                found = search(key, t.children[2]);
            }
        }
        else if (l != null && key.equals(l.key)) {
            return true;
        }
    
        return found;
    } // search() ends here

    // This method prints the keys stored in the leafNodes in order
    private void keyOrderList(Node n) {
        TreeNode t = null;
        LeafNode l = null;
        if (n instanceof TreeNode) {
            t = (TreeNode) n;
        } else {
            l = (LeafNode) n;
        }
        if (t != null) {
            if (t.children[0] != null) {
                keyOrderList(t.children[0]);
            }
            if (t.children[1] != null) {
                keyOrderList(t.children[1]);
            }
            if (t.children[2] != null) {
                keyOrderList(t.children[2]);
            }
        }
        else if (l != null) {
            System.out.print(l.key + " ");
        }
    } // keyOrderList() ends here

    // This method writes the keys stored in the leafNodes in order to a specified file
    private void keyOrderListString(Node n, StringBuilder sb) {
        TreeNode t = null;
        LeafNode l = null;
        if (n instanceof TreeNode) {
            t = (TreeNode) n;
        } else {
            l = (LeafNode) n;
        }
        if (t != null) {
            if (t.children[0] != null) {
                keyOrderListString(t.children[0], sb);
            }
            if (t.children[1] != null) {
                keyOrderListString(t.children[1], sb);
            }
            if (t.children[2] != null) {
                keyOrderListString(t.children[2], sb);
            }
        }
        else if (l != null) {
            sb.append(l.key).append("\n");
        }
    }

    // This method just prints the keys on each level
    private void traverse(Node n) {
        Queue<Node> queueOne = new LinkedList<>();
        Queue<Node> queueTwo = new LinkedList<>();
        if (n == null) {
            return;
        }
        queueOne.add(n);
        Node first = null;
        TreeNode t = null;
        while (!queueOne.isEmpty() || !queueTwo.isEmpty()) {
            while (!queueOne.isEmpty()) {
                first = queueOne.poll();
                if (first instanceof TreeNode) {
                    t = (TreeNode) first;
                    t.print();
                }
                if (t.children[0] != null && !(t.children[0] instanceof LeafNode)) {
                    queueTwo.add(t.children[0]);
                }
                if (t.children[1] != null && !(t.children[1] instanceof LeafNode)) {
                    queueTwo.add(t.children[1]);
                }
                if (t.children[2] != null && !(t.children[2] instanceof LeafNode)) {
                    queueTwo.add(t.children[2]);
                }
            }
            if (!queueOne.isEmpty() || !queueTwo.isEmpty()) {
                System.out.println();
            }
            while (!queueTwo.isEmpty()) {
                first = queueTwo.poll();
                if (first instanceof TreeNode) {
                    t = (TreeNode) first;
                    t.print();
                }
                if (t.children[0] != null && !(t.children[0] instanceof LeafNode)) {
                    queueOne.add(t.children[0]);
                }
                if (t.children[1] != null && !(t.children[1] instanceof LeafNode)) {
                    queueOne.add(t.children[1]);
                }
                if (t.children[2] != null && !(t.children[2] instanceof LeafNode)) {
                    queueOne.add(t.children[2]);
                }
            }
            if (!queueOne.isEmpty() || !queueTwo.isEmpty()) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("* * * Keys * * *");
        keyOrderList(root);
        System.out.println();
    } // traverse() ends here

    // This method determines the height of the tree
    private int height(Node n) {
        TreeNode t = null;
        LeafNode l = null;
        if (n instanceof TreeNode) {
            t = (TreeNode) n;
        } else {
            l = (LeafNode) n;
        }
        if (t != null) {
            return 1 + height(t.children[0]);
        }
    
        return 0;
    } // height() ends here

    // This is a public method to call insert
    public boolean insert(String key) {
        boolean insert = false;
        split = false;
        if (!search(key)) {
            insertKey(key);
        }
        if (successfulInsertion) {
            size++;
            insert = successfulInsertion;
            successfulInsertion = false;
        }
        return insert;
    } // insert() ends here

    // This is a public method to call search
    public boolean search(String key) {
        return search(key, root);
    } // search() ends here

    // This is a public method to call remove
    public boolean remove(String key) {
        boolean delete = false;
        singleNodeUnderflow = false;
        underflow = false;
        if (search(key)) {
            root = (TreeNode) remove(key, root);
            if (root.degree == 1 && root.children[0] instanceof TreeNode) {
                root = (TreeNode) root.children[0];
            }
        }
        if (successfulDeletion) {
            size--;
            delete = successfulDeletion;
            successfulDeletion = false;
        }
        return delete;
    } // remove() ends here

    // This is a public method to call keyOrderList
    public void keyOrderList() {
        System.out.println("Keys");
        keyOrderList(root);
        System.out.println();
    } // keyOrderlist() ends here

    // This is a public method to call traverse
    public void traverse() {
        System.out.println("Tree");
        traverse(root);
    } // traverse() ends here

    // This method returns the number of the keys stored in the tree
    public int numberOfNodes() {
        return size;
    } // numberOfNodes() ends here

    // This is a public method to call height
    public int height() {
        return height(root);
    } // height() ends here

    // This a public method to call keyOrderListString
    public String writeKeysToFile() {
        StringBuilder sb = new StringBuilder();
        keyOrderListString(root, sb);
        return sb.toString();
    } // writeKeystoFile ends here
}