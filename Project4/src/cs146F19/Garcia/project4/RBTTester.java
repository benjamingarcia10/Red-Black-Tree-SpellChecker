package cs146F19.Garcia.project4;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class RBTTester {

	@Test
	// Test the Red Black Tree
	public void test() {
		RBTree<String> rbt = new RBTree<String>();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
		assertEquals("DBACFEHGIJ", makeString(rbt));
		String str = "Color: 1, Key:D Parent: \n" + "Color: 1, Key:B Parent: D\n" + "Color: 1, Key:A Parent: B\n"
				+ "Color: 1, Key:C Parent: B\n" + "Color: 1, Key:F Parent: D\n" + "Color: 1, Key:E Parent: F\n"
				+ "Color: 0, Key:H Parent: F\n" + "Color: 1, Key:G Parent: H\n" + "Color: 1, Key:I Parent: H\n"
				+ "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
	}
	
	@Test
	// Test Red Black Tree creation and insertion with left rotation
	public void testRBLeft() {
		RBTree<String> rbt = new RBTree<String>();
		assertEquals(0, rbt.size);
		rbt.insert("A");
		assertEquals(1, rbt.size);
		assertEquals("A", makeString(rbt));
		rbt.insert("C");
		rbt.insert("B");
		assertEquals("BAC", makeString(rbt));
	}
	
	@Test
	// Test Red Black Tree creation and insertion with right rotation
	public void testRBRight() {
		RBTree<String> rbt = new RBTree<String>();
		assertEquals(0, rbt.size);
		rbt.insert("C");
		assertEquals(1, rbt.size);
		assertEquals("C", makeString(rbt));
		rbt.insert("A");
		rbt.insert("B");
		assertEquals("BAC", makeString(rbt));
	}
	
	@Test
	// Test Red Black Tree creation and verify tree becomes balanced and doesn't remain same as when inserted
	public void testRBBalance() {
		RBTree<String> rbt = new RBTree<String>();
		rbt.insert("A");
		rbt.insert("B");
		rbt.insert("C");
		rbt.insert("D");
		rbt.insert("E");
		rbt.insert("F");
		rbt.insert("G");
		assertFalse(makeString(rbt).equals("ABCDEFG"));
	}
	
	@Test
	// Test dictionary creation method and spell checker and calculate time for both
	public void testSpellCheck() {
		long dictStart = System.nanoTime();														// Start time to create dictionary as Red Black Tree
		RBTree<String> dictionary = SpellChecker.createDictionary("data\\dictionary.txt");					// Create dictionary as Red Black Tree
		long dictEnd = System.nanoTime();															// End time to create dictionary as Red Black Tree
		
		ArrayList<String> wordsToCheck = SpellChecker.wordsFromFile("data\\poem.txt");						// Create ArrayList for all words in poem test file (used to spell check)
		
		long lookupStart = System.nanoTime();																// Start time to lookup all words from "wordsToCheck" ArrayList in dictionary
		//long lookupStart = System.currentTimeMillis();
		ArrayList<String> unfound = SpellChecker.checkSpelling(dictionary, wordsToCheck);					// Lookup all words from "wordsToCheck" ArrayList in dictionary
		long lookupEnd = System.nanoTime();																	// Start time to lookup all words from "wordsToCheck" ArrayList in dictionary
		//long lookupEnd = System.currentTimeMillis();
		
		long dictTotalTime = dictEnd - dictStart;
		long lookupTotalTime = lookupEnd - lookupStart;
		System.out.printf("Time spent to create dictionary file from text file to Red Black Tree was %d nanoseconds.\n", dictTotalTime);
		System.out.printf("Time spent to complete lookup calls using dictionary file and poem text file was %d nanoseconds.\n", lookupTotalTime);
		
		if (unfound.size() == 0)
			System.out.println("All words were found in dictionary.");
		else {
			System.out.printf("%d words not found: ", unfound.size());
			for (String s: unfound)
				System.out.print(s + " ");
		}
	}
	
	@Test
	// Test dictionary creation method and verify dictionary is proper size
	public void createDictionary() {
		RBTree<String> dictionary = SpellChecker.createDictionary("data\\dictionary.txt");					// Create dictionary as Red Black Tree
		assertEquals(349900, dictionary.size);
	}
	
	@Test
	// Test wordsFromFile method and verify ArrayList is filled
	public void wordsInFile() {
		ArrayList<String> wordsToCheck = SpellChecker.wordsFromFile("data\\poem.txt");						// Create ArrayList for all words in poem test file (used to spell check)
		assertTrue(wordsToCheck.size() != 0);
	}
	
	public static String makeString(RBTree t) {
		class MyVisitor implements RBTree.Visitor {
			String result = "";

			public void visit(RBTree.Node n) {
				result = result + n.key;
			}
		}
		MyVisitor v = new MyVisitor();
		t.preOrderVisit(v);
		return v.result;
	}

	public static String makeStringDetails(RBTree t) {
		{
			class MyVisitor implements RBTree.Visitor {
				String result = "";

				public void visit(RBTree.Node n) {
					if (!(n.key).equals(""))
						result = result + "Color: " + n.color + ", Key:" + n.key + " Parent: " + n.parent.key + "\n";
				}
			}
			MyVisitor v = new MyVisitor();
			t.preOrderVisit(v);
			return v.result;
		}
	}
}