package Assignment3;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        // Create a new tree
        /*twoThreeTree tree = new twoThreeTree(3);
        System.out.println(tree.toString());
        System.out.println("break");

        // Add names to the tree
        tree.insert(4);
        System.out.println(tree.toString());
        System.out.println("break");

        tree.insert(1);
        System.out.println(tree.toString());
        System.out.println("break");

        tree.insert(5);
        System.out.println(tree.toString());
        System.out.println("break");*/
        
        // Test 2-3 Tree w/ 21 names
        twoThreeTree TwentyOneTree = new twoThreeTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/21_unsorted_names.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                TwentyOneTree.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        TwentyOneTree.traverse();

        // Do an inorder traversal of an unbalanced tree
        BinaryTree unbalancedTree21 = new BinaryTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/21_unsorted_names.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                unbalancedTree21.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        unbalancedTree21.traverseInOrder();

        // #3 Do an inorder traversal of an unbalanced tree that sorts 160k name file
        // Do an inorder traversal of the unbalanced tree and write the output to a file
        BinaryTree unbalancedTree160k = new BinaryTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/unsorted_names_160k.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                unbalancedTree160k.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter("Assignment3/Text Files/inOrderTraversal_unsorted_names_160k.txt")) {
            out.println(unbalancedTree160k.traverseInOrderString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // #4. a) Test 2-3 Tree w/ 100 unsorted names
        // Write the keys of the 100 2-3 tree in ascending order to twoThreeTree_100_names_sorted_ascending_order.txt
        twoThreeTree OneHundredTree = new twoThreeTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/100_unsorted_names.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                OneHundredTree.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (PrintWriter out = new PrintWriter("Assignment3/Text Files/twoThreeTree_100_names_sorted_ascending_order.txt")) {
            out.println(OneHundredTree.writeKeysToFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // #4. b) Test Unbalanced Tree w/ 100 unsorted names
        // Write the keys of the 100 unbalanced tree in ascending order to unbalancedTree_100_names_sorted_ascending_order.txt
        BinaryTree unbalancedTree100 = new BinaryTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/100_unsorted_names.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                unbalancedTree100.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter("Assignment3/Text Files/unbalancedTree_100_names_sorted_ascending_order.txt")) {
            out.println(unbalancedTree100.traverseInOrderString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        
    }
}