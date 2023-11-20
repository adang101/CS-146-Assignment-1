import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Utils {
	public static void readInts(List<Integer> result){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the sequence with spaces e.g. 1 3 5 3 1 1 3 5 ");
        String input = sc.nextLine();
        String[] numbers = input.split("\\s+");
        
        for (int x = 0; x < numbers.length; x++) {
            Integer item = Integer.parseInt(numbers[x]);
            result.add(item);
        }
    }
    public static void printInts(List<Integer> result) {
        String outputStr = String.join(" ", 
            result.stream().map(Object::toString).collect(Collectors.toList()));
        System.out.println(outputStr);
    }
}