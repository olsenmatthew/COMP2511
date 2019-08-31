package example;

import java.util.Scanner;

public class Splitter {

	public static void main(String[] args){
		System.out.println("Enter a sentence specified by spaces only: ");

		Scanner sc = new Scanner(System.in);
		String sentence = sc.nextLine();

		String[] results = sentence.split(" ");

		for (String word : results) {
			System.out.println(word);
		}

		sc.close();
	} 
}
