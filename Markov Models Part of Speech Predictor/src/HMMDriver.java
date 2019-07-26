import java.util.Scanner;

/**
 * This was my incredibly amazing UI that I created in like 4.31 minutes to help the process of grading
 * and also cuz I thought it would be nice. I admit, it is quite the UI. Please hold your applause.
 * But yeah, if you run it, you'll be able to choose a corpus, run the file accuracy tests, and the 
 * console tester. The UI's name is Sudi. I find it kinda odd that my name starts with SUD as well hmmmmm. 
 * Coincidence? I think not. Sorry this is super duper long! Enjoy.
 * 
 * P.S. my partner Dhruv did not code this class; we both had a driver, I just made mine look a little different.
 * I mention this because I don't want mistakes here to be counted for him -- that is if I made any on this state-of-
 * the-art UI. :)
 * @author Sudharsan Balasubramani and Dhruv Uppal
 *
 */

public class HMMDriver {
	
	public static void main(String[] args) throws Exception {
		Scanner reader = new Scanner(System.in);
		String tester = "";
		String trainFile = "";
		String trainFileTags = "";
		
		HMM model = new HMM("#");
		
		
		System.out.println("Welcome to this extremely useful UI, where you can perform all of the functions (Woooooo)");
		System.out.println("Anyway, I'll be your assistant, Sudi! First, let's take care of some preliminary information");
		System.out.println("Which corpus would you like to train me with?");
		
		do {
			System.out.print("Brown Corpus (B) or Simple Corpus (S) > ");
			tester = reader.next();

			if (tester.equals("B")) {
				trainFile = "brown-train-sentences.txt";    
				trainFileTags = "brown-train-tags.txt";
			} else if (tester.equals("S")) {
				trainFile = "simple-train-sentences.txt";
				trainFileTags = "simple-train-tags.txt";
			} else {
				System.out.println("Seriously? There are only 2 options bud...");
				tester = "";
			}

		} while (tester.equals(""));
		
		model.train(trainFile, trainFileTags);

		tester = "";
		
		char t = ' ';
		
		System.out.println("Alright bud! You can change the training corpus anytime you'd like. Let's begin. Here are your options: ");
		while(t != 'q') {
			reader = new Scanner(System.in);
			System.out.println("c: Console testing\nb: Brown File Test\ns: Simple File Test\nf: Change Corpus\nq: quit\n");
			System.out.print("> ");
			t = reader.next().charAt(0);
			
			switch(t) {
			case 'c':
				model.inputTest(reader);
				break;
			case 'b':
				model.fileTest("brown-test-sentences.txt", "brown-test-tags.txt");
				break;
			case 's':
				model.fileTest("simple-test-sentences.txt", "simple-test-tags.txt");
				break;
			case 'f':
				model.resetModel();
				do {
					System.out.print("Brown Corpus (B) or Simple Corpus (S) > ");
					tester = reader.next();

					if (tester.equals("B")) {
						trainFile = "brown-train-sentences.txt";
						trainFileTags = "brown-train-tags.txt";
					} else if (tester.equals("S")) {
						trainFile = "simple-train-sentences.txt";
						trainFileTags = "simple-train-tags.txt";
					} else {
						System.out.println("Seriously? There are only 2 options bud...");
						tester = "";
					}

				} while (tester.equals(""));
				model.train(trainFile, trainFileTags);
				tester = "";
				break;
				
			}
		}
		
		System.out.println("Looks like we are done for today! I'll probably see you again sometime maybe, probably not :P");
		
		reader.close();
	}

}
