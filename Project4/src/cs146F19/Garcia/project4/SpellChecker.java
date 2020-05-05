package cs146F19.Garcia.project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {
	
	/**
	 * Using the provided dictionary Red Black Tree, lookup all words in the "wordsToCheck" against the dictionary
	 * @param dictionary
	 * 		Dictionary as a Red Black Tree
	 * @param wordsToCheck
	 * 		ArrayList of words to be spell checked
	 * @return
	 * 		ArrayList unfound - All words not found in dictionary (either misspelled or just not included)
	 */
	public static ArrayList<String> checkSpelling (RBTree<String> dictionary, ArrayList<String> wordsToCheck) {
		ArrayList<String> unfound = new ArrayList<String>();										// ArrayList for all words not found in dictionary (either because word is misspelled or dictionary doesn't include it)
		for (String s: wordsToCheck) {																// For all words in wordsToCheck ArrayList,
			if (dictionary.lookup(s) == null)														// Lookup the word in the RBTree, if it is not found,
				unfound.add(s);																		// Add it to the unfound ArrayList
		}
		return unfound;
	}
	
	/**
	 * Create a dictionary file as a Red Black Tree
	 * @param dictionaryFile
	 * 		Path to file (as a String) containing words for dictionary
	 * @return
	 * 		RBTree - a Red Black Tree with each node being a word from the dictionary
	 */
	public static RBTree<String> createDictionary(String dictionaryFile) {
		ArrayList<String> dictionaryAsArrayList = wordsFromFile(dictionaryFile);					// ArrayList holding all elements from passed in dictionaryFile
		RBTree<String> dictionary = new RBTree<String>();											// Create RBTree for dictionary
		for (String s: dictionaryAsArrayList)
			dictionary.insert(s);																	// Add each string from ArrayList into RBTree
		return dictionary;
	}
	
	/**
	 * Takes a text file and adds each word as an element in an ArrayList removing all characters that isn't A-Z, a-z, or ' and make all letters lowercase
	 * @param file
	 * 		File path as a String
	 * @return ArrayList
	 * 		ArrayList containing all elements
	 */
	public static ArrayList<String> wordsFromFile(String file) {
		ArrayList<String> words = new ArrayList<String>();											// Create an ArrayList to store all words from file passed in
		try {
			Scanner scan = new Scanner(new File(file));												// Scanner to read file
			String line;																			// String to hold each line of file
			String[] wordsInLine;																	// Array to hold each word in line of file
			while (scan.hasNextLine()) {
				line = scan.nextLine().trim();														// Set line to nextLine if available
				wordsInLine = line.split("\\s+");													// Set wordsInLine to words broken up by space
				for (int i = 0; i < wordsInLine.length; i++) {
					wordsInLine[i] = wordsInLine[i].replaceAll("[^a-zA-Z']", "").toLowerCase();		// Remove all characters from each word that isn't A-Z, a-z, or ' and make all letters lowercase
					words.add(wordsInLine[i]);														// For each word, add it to wordstoCheck ArrayList
				}		
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return words;
	}
}