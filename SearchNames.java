import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchNames {
    public static void main(String[] args) {
        // File textFile = new File("WarAndPeace.txt");
        String txtFile = "WarAndPeace.txt";
        
        try (BufferedReader in = new BufferedReader(new FileReader(txtFile))) {
            String ln;

            while((ln = in.readLine()) != null) {
                String[] words = ln.split(" ");
                for (String word : words) {
                    if (word.length() > 0) {
                        System.out.println(word);
                    }
                }
            }
        }

    }
}