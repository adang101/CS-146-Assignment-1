package Assignment3;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TreeAlgorithms {
    
    public static void test23Search(twoThreeTree tree, String[] names) {
        for (String name : names) {
            boolean found = tree.search(name);
            if (!found) {
                System.out.println("Name not found: " + name);
            }
        }
    }

    public static void testBinarySearch(BinaryTree tree, String[] names) {
        for (String name : names) {
            boolean found = tree.search(name);
            if (!found) {
                System.out.println("Name not found: " + name);
            }
        }
    }
    
    // Create a linear search algorithm
    public static boolean linearSearch(String[] arr, String key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static void testLinearSearch(String[] arr, String[] names) {
        for (String name : names) {
            boolean found = linearSearch(arr, name);
            if (!found) {
                System.out.println("Name not found: " + name);
            }
        }
    }

    // Create a binary array search algorithm
    public static boolean binaryArraySearch(String[] arr, String key) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            // Find the middle index
            int mid = (low + high) / 2;

            // Compare the middle index to the key
            int cmp = arr[mid].compareTo(key);

            // If the middle index is less than the key, search the right half
            if (cmp < 0) {
                low = mid + 1;
            } 
            // If the middle index is greater than the key, search the left half
            else if (cmp > 0) {
                high = mid - 1;
            } 
            // If the middle index is equal to the key, return true
            else {
                return true;
            }
        }
        return false;
    }

    public static void testBinaryArraySearch(String[] arr, String[] names) {
        for (String name : names) {
            boolean found = binaryArraySearch(arr, name);
            if (!found) {
                System.out.println("Name not found: " + name);
            }
        }
    }

    public static void main(String[] args) {
        // #5 b. Using each name in the file from 4a (100 Random names), Perform
        //          100 individual searches using the 2-3 tree in the 160k name file
        //          and record the time taken to perform the searches. (min, max, avg)
        
        // #5 b.1. Create an array of 100 random names from 4a
        String[] oneHundredRandomNames = new String[100];
        int j = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/100_names_sorted_ascending_order.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                oneHundredRandomNames[j] = line;
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // #5 b.2. Create a 2-3 tree and insert all 160k names
        twoThreeTree twoThreeTree160k = new twoThreeTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/unsorted_names_160k.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                twoThreeTree160k.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // #5 b.3. Perform 100 individual searches using the 2-3 tree: twoThreeTree160k.
        //      Assert whether or not each name is in the tree. Record the time taken (min, max, avg)
        int trials = 100;
        long[] twoThreeTrialTimes = new long[trials];

        // #5 b.3.1 Test 2-3 tree search
        for (int i = 0; i < trials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test 2-3 tree search
            test23Search(twoThreeTree160k, oneHundredRandomNames);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            twoThreeTrialTimes[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            // System.out.println("2-3 Tree Search Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #5 b.3.2 Calculate min, max, avg and print
        long twoThreeMin = twoThreeTrialTimes[0];
        long twoThreeMax = twoThreeTrialTimes[0];
        long twoThreeTotalTimeElapsed = 0;

        for (int i = 0; i < trials; i++) {
            // Search for minimum time
            if (twoThreeTrialTimes[i] < twoThreeMin) {
                twoThreeMin = twoThreeTrialTimes[i];
            }

            // Search for maximum time
            if (twoThreeTrialTimes[i] > twoThreeMax) {
                twoThreeMax = twoThreeTrialTimes[i];
            }

            // Track total time elapsed
            twoThreeTotalTimeElapsed += twoThreeTrialTimes[i];
        }

        // Calculate average time
        long twoThreeAvg = twoThreeTotalTimeElapsed / trials;

        // 5 B Print min, max, avg 
        System.out.println("\n(5.b) 2-3 Tree Search Time: \n\tMin: " + twoThreeMin + " ns. \n\tMax: " + twoThreeMax + " ns. \n\tAvg: " + twoThreeAvg + " ns");

        // #5 c. Using each name in the file from 4a (100 Random names), Perform
        //          100 individual searches using the Binary tree in the 160k name file
        //          and record the time taken to perform the searches. (min, max, avg)

        // #5 c.1. Create an array of 100 random names from 4a (Done in 5 b.1)

        // #5 c.2. Create a binary tree and insert all 160k names
        BinaryTree binaryTree160k = new BinaryTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/unsorted_names_160k.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                binaryTree160k.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // #5 c.3. Perform 100 individual searches using the binary tree: binaryTree160k.
        //      Assert whether or not each name is in the tree. Record the time taken (min, max, avg)
        long[] binaryTreeTrialTimes = new long[trials];

        // #5 c.3.1 Test binary tree search
        for (int i = 0; i < trials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test binary tree search
            testBinarySearch(binaryTree160k, oneHundredRandomNames);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            binaryTreeTrialTimes[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            //System.out.println("Binary Tree Search Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #5 c.3.2 Calculate min, max, avg and print
        long binaryTreeMin = binaryTreeTrialTimes[0];
        long binaryTreeMax = binaryTreeTrialTimes[0];
        long binaryTreeTotalTimeElapsed = 0;

        for (int i = 0; i < trials; i++) {
            // Search for minimum time
            if (binaryTreeTrialTimes[i] < binaryTreeMin) {
                binaryTreeMin = binaryTreeTrialTimes[i];
            }

            // Search for maximum time
            if (binaryTreeTrialTimes[i] > binaryTreeMax) {
                binaryTreeMax = binaryTreeTrialTimes[i];
            }

            // Track total time elapsed
            binaryTreeTotalTimeElapsed += binaryTreeTrialTimes[i];
        }

        // Calculate average time
        long binaryTreeAvg = binaryTreeTotalTimeElapsed / trials;

        // 5 C Print min, max, avg
        System.out.println("\n(5.c) Binary Tree Search Time: \n\tMin: " + binaryTreeMin + " ns. \n\tMax: " + binaryTreeMax + " ns. \n\tAvg: " + binaryTreeAvg + " ns");

        // #5 d. Using each name in the file from 4a (100 Random names), Perform
        //          100 individual searches using Linear search in the 160k name file
        //          and record the time taken to perform the searches. (min, max, avg)

        // #5 d.1. Create an array of 100 random names from 4a (Done in 5 b.1)

        // #5 d.2. Create an array of 160k names
        String[] names160k = new String[162253];
        int z = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/unsorted_names_160k.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                names160k[z] = line;
                z++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // #5 d.3. Perform 100 individual searches using Linear search: linearSearch.
        //      Assert whether or not each name is in the array. Record the time taken (min, max, avg)
        long[] linearSearchTrialTimes = new long[trials];

        // #5 d.3.1 Test linear search
        for (int i = 0; i < trials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test linear search
            testLinearSearch(names160k, oneHundredRandomNames);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            linearSearchTrialTimes[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            //System.out.println("Linear Search Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #5 d.3.2 Calculate min, max, avg and print
        long linearSearchMin = linearSearchTrialTimes[0];
        long linearSearchMax = linearSearchTrialTimes[0];
        long linearSearchTotalTimeElapsed = 0;

        for (int i = 0; i < trials; i++) {
            // Search for minimum time
            if (linearSearchTrialTimes[i] < linearSearchMin) {
                linearSearchMin = linearSearchTrialTimes[i];
            }

            // Search for maximum time
            if (linearSearchTrialTimes[i] > linearSearchMax) {
                linearSearchMax = linearSearchTrialTimes[i];
            }

            // Track total time elapsed
            linearSearchTotalTimeElapsed += linearSearchTrialTimes[i];
        }

        // Calculate average time
        long linearSearchAvg = linearSearchTotalTimeElapsed / trials;

        // 5 D Print min, max, avg
        System.out.println("\n(5.d) Linear Search Time: \n\tMin: " + linearSearchMin + " ns. \n\tMax: " + linearSearchMax + " ns. \n\tAvg: " + linearSearchAvg + " ns");
    
        // #5 e. Using each name in the file from 4a (100 Random names), Perform
        //          100 individual searches using Binary Array search in the 160k name file
        //          and record the time taken to perform the searches. (min, max, avg)

        // #5 e.1. Create an array of 100 random names from 4a (Done in 5 b.1)

        // #5 e.2. Create an array of 160k SORTED names
        // Write the keys of the 160k 2-3 tree in ascending order to twoThreeTree_160k_names_sorted_ascending_order.txt
        twoThreeTree sorted23Tree160k = new twoThreeTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/unsorted_names_160k.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sorted23Tree160k.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try (PrintWriter out = new PrintWriter("Assignment3/Text Files/twoThreeTree_160k_names_sorted_ascending_order.txt")) {
            out.println(sorted23Tree160k.writeKeysToFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        String[] oneHundredSixtyKSortedNames = new String[162253];
        int s = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/twoThreeTree_160k_names_sorted_ascending_order.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                oneHundredSixtyKSortedNames[s] = line;
                s++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // #5 e.3. Perform 100 individual searches using Binary Array search: binaryArraySearch.
        //      Assert whether or not each name is in the array. Record the time taken (min, max, avg)
        long[] binaryArraySearchTrialTimes = new long[trials];

        // #5 e.3.1 Test binary array search
        for (int i = 0; i < trials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test binary array search
            testBinaryArraySearch(oneHundredSixtyKSortedNames, oneHundredRandomNames);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            binaryArraySearchTrialTimes[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            //System.out.println("Binary Array Search Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #5 e.3.2 Calculate min, max, avg and print
        long binaryArraySearchMin = binaryArraySearchTrialTimes[0];
        long binaryArraySearchMax = binaryArraySearchTrialTimes[0];
        long binaryArraySearchTotalTimeElapsed = 0;

        for (int i = 0; i < trials; i++) {
            // Search for minimum time
            if (binaryArraySearchTrialTimes[i] < binaryArraySearchMin) {
                binaryArraySearchMin = binaryArraySearchTrialTimes[i];
            }

            // Search for maximum time
            if (binaryArraySearchTrialTimes[i] > binaryArraySearchMax) {
                binaryArraySearchMax = binaryArraySearchTrialTimes[i];
            }

            // Track total time elapsed
            binaryArraySearchTotalTimeElapsed += binaryArraySearchTrialTimes[i];
        }

        // Calculate average time
        long binaryArraySearchAvg = binaryArraySearchTotalTimeElapsed / trials;

        // 5 E Print min, max, avg
        System.out.println("\n(5.e) Binary Array Search Time: \n\tMin: " + binaryArraySearchMin + " ns. \n\tMax: " + binaryArraySearchMax + " ns. \n\tAvg: " + binaryArraySearchAvg + " ns");

        /* 6 ------------------------------------------------------------------------------------------
         * Delete some names that have gone out of fashion.
            These need to be deleted from your tree implementation. 
            6 a: Delete the top 10% of the names from both (6 a.1) 2-3 tree and (6 a.2) binary tree.
            6 b: Delete the bottom 10% of the names from both (6 b.1) 2-3 tree and (6 b.2) binary tree. 
                (Reconstruct the full tree first before deleting.
            Print the min, max and average times taken for each of the tests 6a-6b.
         ------------------------------------------------------------------------------------------ */
        
        // 6 a.1 Delete the top 10% of the names from the 2-3 tree
        // 6 a.1.1 Create an array of the top 10% of names from the 2-3 tree
        String[] topTenPercentNames23Tree = new String[16225];
        int t = 0;

        // 6 a.1.1.1 Traverse the sorted 2-3 tree and add the top 10% of names to an array
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/twoThreeTree_160k_names_sorted_ascending_order.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (t < 16225) {
                    topTenPercentNames23Tree[t] = line;
                    t++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 6 a.1.2 Delete the top 10% of names from the 2-3 tree
        //          And record the time taken to perform the deletions. (min, max, avg)
        long deleteTrials = 16225;
        long[] twoThreeTreeDeletionTrialTimes = new long[16225];

        // #6 a.1.2.1 Test 2-3 tree deletion
        // Create a new 2-3 tree for deletion
        twoThreeTree twoThreeTree160kForDeletion = twoThreeTree160k;
        for (int i = 0; i < deleteTrials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test 2-3 tree deletion
            twoThreeTree160kForDeletion.remove(topTenPercentNames23Tree[i]);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            twoThreeTreeDeletionTrialTimes[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            //System.out.println("2-3 Tree Deletion Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #6 a.1.2.2 Calculate min, max, avg and print
        long twoThreeTreeDeletionMin = twoThreeTreeDeletionTrialTimes[0];
        long twoThreeTreeDeletionMax = twoThreeTreeDeletionTrialTimes[0];

        for (int i = 0; i < deleteTrials; i++) {
            // Search for minimum time
            if (twoThreeTreeDeletionTrialTimes[i] < twoThreeTreeDeletionMin) {
                twoThreeTreeDeletionMin = twoThreeTreeDeletionTrialTimes[i];
            }

            // Search for maximum time
            if (twoThreeTreeDeletionTrialTimes[i] > twoThreeTreeDeletionMax) {
                twoThreeTreeDeletionMax = twoThreeTreeDeletionTrialTimes[i];
            }
        }
        // Calculate average time
        long twoThreeTreeDeletionAvg = twoThreeTreeDeletionMax / deleteTrials;

        // 6 A.1 Print min, max, avg
        System.out.println("\n(6.a.1) 2-3 Tree Deletion Time: \n\tMin: " + twoThreeTreeDeletionMin + " ns. \n\tMax: " + twoThreeTreeDeletionMax + " ns. \n\tAvg: " + twoThreeTreeDeletionAvg + " ns");
        
        // 6 a.2 Delete the top 10% of the names from the binary tree
        // 6 a.2.1 Create an array of the top 10% of names from the binary tree
        String[] topTenPercentNamesBinaryTree = new String[16225];
        int b = 0;

        // 6 a.2.1.1 Traverse the sorted binary tree and add the top 10% of names to an array
        // Sort the binary tree
        BinaryTree unbalancedTree160 = new BinaryTree();
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/unsorted_names_160k.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                unbalancedTree160.insert(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try (PrintWriter out = new PrintWriter("Assignment3/Text Files/binaryTree_160k_names_sorted_ascending_order.txt")) {
            out.println(unbalancedTree160.traverseInOrderString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

        // Traverse the sorted binary tree and add the top 10% of names to an array
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/binaryTree_160k_names_sorted_ascending_order.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (b < 16225) {
                    topTenPercentNamesBinaryTree[b] = line;
                    b++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 6 a.2.2 Delete the top 10% of names from the binary tree
        //          And record the time taken to perform the deletions. (min, max, avg)
        long[] binaryTreeDeletionTrialTimes = new long[16225];

        // #6 a.2.2.1 Test binary tree deletion
        // Create a new binary tree for deletion
        BinaryTree binaryTree160kForDeletion = unbalancedTree160;
        for (int i = 0; i < deleteTrials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test binary tree deletion
            binaryTree160kForDeletion.remove(topTenPercentNamesBinaryTree[i]);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            binaryTreeDeletionTrialTimes[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            //System.out.println("Binary Tree Deletion Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #6 a.2.2.2 Calculate min, max, avg and print
        long binaryTreeDeletionMin = binaryTreeDeletionTrialTimes[0];
        long binaryTreeDeletionMax = binaryTreeDeletionTrialTimes[0];

        for (int i = 0; i < deleteTrials; i++) {
            // Search for minimum time
            if (binaryTreeDeletionTrialTimes[i] < binaryTreeDeletionMin) {
                binaryTreeDeletionMin = binaryTreeDeletionTrialTimes[i];
            }

            // Search for maximum time
            if (binaryTreeDeletionTrialTimes[i] > binaryTreeDeletionMax) {
                binaryTreeDeletionMax = binaryTreeDeletionTrialTimes[i];
            }
        }
        // Calculate average time
        long binaryTreeDeletionAvg = binaryTreeDeletionMax / deleteTrials;

        // 6 A.2 Print min, max, avg
        System.out.println("\n(6.a.2) Binary Tree Deletion Time: \n\tMin: " + binaryTreeDeletionMin + " ns. \n\tMax: " + binaryTreeDeletionMax + " ns. \n\tAvg: " + binaryTreeDeletionAvg + " ns");
        
        // 6 b.1 Delete the bottom 10% of the names from the 2-3 tree
        // 6 b.1.1 Create an array of the bottom 10% of names from the 2-3 tree
        String[] bottomTenPercentNames23Tree = new String[16226];
        int t2 = 1;
        int h = 0;

        // 6 b.1.1.1 Traverse the sorted 2-3 tree and add the bottom 10% of names to an array
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/twoThreeTree_160k_names_sorted_ascending_order.txt"))) {
            String line;
            int startOfBottomTenPercent = 146028;
            while ((line = br.readLine()) != null) {
                if (t2 >= startOfBottomTenPercent) {
                    bottomTenPercentNames23Tree[h] = line;
                    h++;
                }
                t2++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 6 b.1.2 Delete the bottom 10% of names from the 2-3 tree
        //          And record the time taken to perform the deletions. (min, max, avg)
        long[] twoThreeTreeDeletionTrialTimes2 = new long[16225];

        // #6 b.1.2.1 Test 2-3 tree deletion
        // Create a new 2-3 tree for deletion
        twoThreeTree twoThreeTree160kForDeletion2 = twoThreeTree160k;

        for (int i = 0; i < deleteTrials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test 2-3 tree deletion
            twoThreeTree160kForDeletion2.remove(bottomTenPercentNames23Tree[i]);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            twoThreeTreeDeletionTrialTimes2[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            //System.out.println("2-3 Tree Deletion Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #6 b.1.2.2 Calculate min, max, avg and print
        long twoThreeTreeDeletionMin2 = twoThreeTreeDeletionTrialTimes2[0];
        long twoThreeTreeDeletionMax2 = twoThreeTreeDeletionTrialTimes2[0];

        for (int i = 0; i < deleteTrials; i++) {
            // Search for minimum time
            if (twoThreeTreeDeletionTrialTimes2[i] < twoThreeTreeDeletionMin2) {
                twoThreeTreeDeletionMin2 = twoThreeTreeDeletionTrialTimes2[i];
            }

            // Search for maximum time
            if (twoThreeTreeDeletionTrialTimes2[i] > twoThreeTreeDeletionMax2) {
                twoThreeTreeDeletionMax2 = twoThreeTreeDeletionTrialTimes2[i];
            }
        }
        // Calculate average time
        long twoThreeTreeDeletionAvg2 = twoThreeTreeDeletionMax2 / deleteTrials;

        // 6 B.1 Print min, max, avg
        System.out.println("\n(6.b.1) 2-3 Tree Deletion Time: \n\tMin: " + twoThreeTreeDeletionMin2 + " ns. \n\tMax: " + twoThreeTreeDeletionMax2 + " ns. \n\tAvg: " + twoThreeTreeDeletionAvg2 + " ns");

        // 6 b.2 Delete the bottom 10% of the names from the binary tree
        // 6 b.2.1 Create an array of the bottom 10% of names from the binary tree
        String[] bottomTenPercentNamesBinaryTree = new String[16226];
        int b2 = 1;
        int k = 0;
        // 6 b.2.1.1 Traverse the sorted binary tree and add the bottom 10% of names to an array
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment3/Text Files/binaryTree_160k_names_sorted_ascending_order.txt"))) {
            String line;
            int startOfBottomTenPercent = 146028;
            while ((line = br.readLine()) != null) {
                if (b2 >= startOfBottomTenPercent) {
                    bottomTenPercentNamesBinaryTree[k] = line;
                    k++;
                }
                b2++;
            }
        } catch (IOException e) {

    }

        // 6 b.2.2 Delete the bottom 10% of names from the binary tree
        //          And record the time taken to perform the deletions. (min, max, avg)
        long[] binaryTreeDeletionTrialTimes2 = new long[16225];

        // #6 b.2.2.1 Test binary tree deletion
        // Create a new binary tree for deletion
        BinaryTree binaryTree160kForDeletion2 = unbalancedTree160;

        for (int i = 0; i < deleteTrials; i++) {
            // Begin timer
            long startTime = System.nanoTime();

            // Test binary tree deletion
            binaryTree160kForDeletion2.remove(bottomTenPercentNamesBinaryTree[i]);

            // End timer
            long endTime = System.nanoTime();

            long timeElapsed = endTime - startTime;

            // Store time elapsed
            binaryTreeDeletionTrialTimes2[i] = timeElapsed;

            // Print time elapsed (TEST DONT INCLUDE in final assignment)
            //System.out.println("Binary Tree Deletion Time for trial: " + i + ". TimeElapsed: " + timeElapsed +" ns");
        }

        // #6 b.2.2.2 Calculate min, max, avg and print
        long binaryTreeDeletionMin2 = binaryTreeDeletionTrialTimes2[0];
        long binaryTreeDeletionMax2 = binaryTreeDeletionTrialTimes2[0];

        for (int i = 0; i < deleteTrials; i++) {
            // Search for minimum time
            if (binaryTreeDeletionTrialTimes2[i] < binaryTreeDeletionMin2) {
                binaryTreeDeletionMin2 = binaryTreeDeletionTrialTimes2[i];
            }

            // Search for maximum time
            if (binaryTreeDeletionTrialTimes2[i] > binaryTreeDeletionMax2) {
                binaryTreeDeletionMax2 = binaryTreeDeletionTrialTimes2[i];
            }
        }
        // Calculate average time
        long binaryTreeDeletionAvg2 = binaryTreeDeletionMax2 / deleteTrials;

        // 6 B.2 Print min, max, avg
        System.out.println("\n(6.b.2) Binary Tree Deletion Time: \n\tMin: " + binaryTreeDeletionMin2 + " ns. \n\tMax: " + binaryTreeDeletionMax2 + " ns. \n\tAvg: " + binaryTreeDeletionAvg2 + " ns");
    }
}
