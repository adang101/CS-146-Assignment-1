import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchNames {
    public static void main(String[] args) {
        // Start program timer
        long start = System.currentTimeMillis();

        // Create an array to hold the names that are being 
        // searched for
        String[] names = {"Makar Alexeevich", "Joseph Bazdeev", 
            "Boris Drubetskoy"};

        String readFile = "WarAndPeace.txt";

        try(BufferedReader in = new BufferedReader(
            new FileReader(readFile))) {
            
            // String to hold the line read from the file
            String currLine;

            // int to count the line nums as the file is read
            int lineNum = 0;

            // String to hold the last word of the prev line 
            //    read from the file
            String prevWord = "";

            // Loop through the file until the end is reached (null)
            while((currLine = in.readLine()) != null) {
                // Increment the line number
                lineNum++;

                /* Merge the previous line with the current line to 
                    account for situations where the name is split 
                    between two lines
                 */
                String mergedLine = prevWord + currLine;

                // Look for the desired names in the merged line
                for (String findName : names) {
                    int pos = mergedLine.indexOf(findName);
                    while(pos != -1) {
                        // Adjust position by subtracting the length 
                        //     of the last word
                        int adjPos = pos - prevWord.length();

                        // Print the line num, char pos, and the name found
                        System.out.printf("%d %d %s%n", lineNum, 
                            adjPos + 1, findName);

                        // Look for the the name again
                        pos = mergedLine.indexOf(findName, pos + 1);
                    }
                }
                /* Store the last word at the end of the current 
                 * line to be used in the next loop
                 */
               prevWord = currLine.substring(currLine.lastIndexOf(" ") + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Elapsed time: " + elapsed + "ms");
    }
}