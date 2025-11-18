package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Scanner;

public class Solver {
	public static void main(String[] args) {
		// System.out.println("number of arguments: " + args.length);
		// for (int i = 0; i < args.length; i++) {
		// 	System.out.println(args[i]);
		// }

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}
		
		// TODO
		File input = new File(args[0]);
		String[] rubiksCube = new String[9];
		try {
            Scanner reader = new Scanner(input); 
            int index = 0;
			while(reader.hasNextLine()) {
                rubiksCube[index] = reader.nextLine();
                index++;
			}	
			reader.close(); //done reading  (should i put this in 'finally' ?)
		} catch (IOException ioe) {
            System.out.println(ioe); 
		} 

        // for (String i: rubiksCube) { //sanity check also i dont wanna do what i did in assignment1 anymore lol
        //     System.out.println(i);
        // }

        char[] facelets = new char[54];
        convertFileToFacelets(rubiksCube, facelets);
        // for (char i: facelets) { //sanity check to make sure that I actually converted it properly in facelets
        //     System.out.println(i);
        // }  

        convertFaceletToCubies(facelets);

		// String answer = solveRubiks(rubiksCube);
        // File output = new File(args[1]);ß
		//great kenneth attempt of solving it
	}

    private static void convertFileToFacelets(String[] rubiks, char[] facelets) {
        //based of kociemba, normal format is U R F D B L
        //brute force it by grabbing U -> R -> F -> ... so on
        int index = 0; 
        for (int y = 0; y < 3; y++) { //because up is from 0 to 2 based on the rubiks cube
            for (int x = 3; x < 6; x++) {
                facelets[index] = rubiks[y].charAt(x);
                index++;
            }
        }   //index should end at 9 here

        // R starts at x = 6 and y = 3  (but basically brute force it the same way lol)
        for (int y = 3; y < 6; y++) {
            for (int x = 6; x < 9; x++) {
                facelets[index] = rubiks[y].charAt(x);
                index++;
            }
        }
        //after this is just easier math compared to my assignment1 lol (kenneth's assignment1)

        for (int y = 3; y < 6; y++) {
            for (int x = 3; x < 6; x++) {
                facelets[index] = rubiks[y].charAt(x);
                index++;
            }
        }

        for (int y = 6; y < 9; y++) {
            for (int x = 3; x < 6; x++) {
                facelets[index] = rubiks[y].charAt(x);
                index++;
            }
        }        

        for (int y = 3; y < 6; y++) {
            for (int x = 9; x < 12; x++) {
                facelets[index] = rubiks[y].charAt(x);
                index++;
            }
        }

        for (int y = 3; y < 6; y++) {
            for (int x = 0; x < 3; x++) {
                facelets[index] = rubiks[y].charAt(x);
                index++;
            }
        }
    }

    private static void convertFaceletToCubies(char[] rubiks) {
        System.out.println("doing god's work");
    }

	private static String solveRubiks(String theRubiks) {

		// i think do A* or IDA* here first and then check how well it does the job 
		doMoves();
		return "hello";
	}

	private static void doMoves() {
		//add my moves here lol
		System.out.println("do the moves here i think");
		// String moves = "hello for now";
		// char currMove; //FBRLUD
        // for (int i = 0; i < moves.length(); i++) {
        //     currMove = moves.charAt(i);
        //     if (currMove == 'F') {
        //         String newCube = "";
        //         newCube += this.theRubkisCube.substring(0,17);
        //         newCube = newCube + theRubkisCube.charAt(23) + theRubkisCube.charAt(36) + theRubkisCube.charAt(49) + "\n";
        //         newCube = newCube + theRubkisCube.substring(21, 23) + theRubkisCube.charAt(63) + theRubkisCube.charAt(50) + theRubkisCube.charAt(37) + theRubkisCube.charAt(24) + theRubkisCube.charAt(17) + theRubkisCube.substring(28, 34);
        //         newCube = newCube + theRubkisCube.substring(34,36) + theRubkisCube.charAt(64) + theRubkisCube.charAt(51) + theRubkisCube.charAt(38) + theRubkisCube.charAt(25) + theRubkisCube.charAt(18) + theRubkisCube.substring(41, 47);
        //         newCube = newCube + theRubkisCube.substring(47, 49) + theRubkisCube.charAt(65) + theRubkisCube.charAt(52) + theRubkisCube.charAt(39) + theRubkisCube.charAt(26) + theRubkisCube.charAt(19) + theRubkisCube.substring(54, 60);
        //         newCube = newCube + theRubkisCube.substring(60,63) + theRubkisCube.charAt(53) + theRubkisCube.charAt(40) + theRubkisCube.charAt(27) + "\n";
        //         newCube += theRubkisCube.substring(67, 81);
        //         this.theRubkisCube = newCube;
        //     } else if (currMove == 'B') {
        //         String newCube = "   ";
        //         newCube = newCube + theRubkisCube.charAt(29) + theRubkisCube.charAt(42) + theRubkisCube.charAt(55) + "\n";
        //         newCube += theRubkisCube.substring(7, 21);
        //         newCube = newCube + theRubkisCube.charAt(5) + theRubkisCube.substring(22, 29) + theRubkisCube.charAt(79) + theRubkisCube.charAt(56) + theRubkisCube.charAt(43) + theRubkisCube.charAt(30) + "\n";
        //         newCube = newCube + theRubkisCube.charAt(4) + theRubkisCube.substring(35, 42) + theRubkisCube.charAt(78) + theRubkisCube.charAt(57) + theRubkisCube.charAt(44) + theRubkisCube.charAt(31) + "\n";
        //         newCube = newCube + theRubkisCube.charAt(3) + theRubkisCube.substring(48, 55) + theRubkisCube.charAt(77) + theRubkisCube.charAt(58) + theRubkisCube.charAt(45) + theRubkisCube.charAt(32) + "\n";
        //         newCube += theRubkisCube.substring(60, 77) + theRubkisCube.charAt(21) + theRubkisCube.charAt(34) + theRubkisCube.charAt(47);
        //         this.theRubkisCube = newCube;
        //     } else if (currMove == 'R') {
        //         String newCube = "   ";
        //         newCube = newCube + theRubkisCube.substring(3, 5) + theRubkisCube.charAt(26) + "\n";
        //         newCube = newCube + theRubkisCube.substring(7, 12) + theRubkisCube.charAt(39) + "\n";
        //         newCube = newCube + theRubkisCube.substring(14,19) + theRubkisCube.charAt(52) + "\n";
        //         newCube = newCube + theRubkisCube.substring(21, 26) + theRubkisCube.charAt(65) + theRubkisCube.charAt(53) + theRubkisCube.charAt(40) + theRubkisCube.charAt(27) + theRubkisCube.charAt(19) + theRubkisCube.substring(31, 34);
        //         newCube = newCube + theRubkisCube.substring(34, 39) + theRubkisCube.charAt(72) + theRubkisCube.charAt(54) + theRubkisCube.charAt(41) + theRubkisCube.charAt(28) + theRubkisCube.charAt(12) + theRubkisCube.substring(44, 47);
        //         newCube = newCube + theRubkisCube.substring(47, 52) + theRubkisCube.charAt(79) + theRubkisCube.charAt(55) + theRubkisCube.charAt(42) + theRubkisCube.charAt(29) + theRubkisCube.charAt(5) + theRubkisCube.substring(57, 60); 
        //         newCube = newCube + theRubkisCube.substring(60, 65) + theRubkisCube.charAt(56) + "\n";
        //         newCube = newCube + theRubkisCube.substring(67, 72) + theRubkisCube.charAt(43) + "\n";
        //         newCube = newCube + theRubkisCube.substring(74, 79) + theRubkisCube.charAt(30) + "\n";
        //         this.theRubkisCube = newCube;
        //     } else if (currMove == 'L') {
        //         String newCube = "   ";
        //         newCube = newCube + theRubkisCube.charAt(58) + theRubkisCube.substring(4, 7);
        //         newCube = newCube + theRubkisCube.substring(7, 10) + theRubkisCube.charAt(45) + theRubkisCube.substring(11, 14);
        //         newCube = newCube + theRubkisCube.substring(14, 17) + theRubkisCube.charAt(32) + theRubkisCube.substring(18, 21);
        //         newCube = newCube + theRubkisCube.charAt(47) + theRubkisCube.charAt(34) + theRubkisCube.charAt(21) + theRubkisCube.charAt(3) + theRubkisCube.substring(25, 32) + theRubkisCube.charAt(63) + "\n";
        //         newCube = newCube + theRubkisCube.charAt(48) + theRubkisCube.charAt(35) + theRubkisCube.charAt(22) + theRubkisCube.charAt(10) + theRubkisCube.substring(38, 45) + theRubkisCube.charAt(70) + "\n";
        //         newCube = newCube + theRubkisCube.charAt(49) + theRubkisCube.charAt(36) + theRubkisCube.charAt(23) + theRubkisCube.charAt(17) + theRubkisCube.substring(51, 58) + theRubkisCube.charAt(77) + "\n";
        //         newCube = newCube + theRubkisCube.substring(60, 63) + theRubkisCube.charAt(24) + theRubkisCube.substring(64, 67);
        //         newCube = newCube + theRubkisCube.substring(67, 70) + theRubkisCube.charAt(37) + theRubkisCube.substring(71, 75);
        //         newCube = newCube + theRubkisCube.substring(75, 77) + theRubkisCube.charAt(50) + theRubkisCube.substring(78, 81);
        //         this.theRubkisCube = newCube;
        //     } else if (currMove == 'U') {
        //         String newCube = "   ";
        //         newCube = newCube + theRubkisCube.charAt(17) +  theRubkisCube.charAt(10) + theRubkisCube.charAt(3) + "\n";
        //         newCube = newCube + theRubkisCube.substring(7, 10) + theRubkisCube.charAt(18) + theRubkisCube.charAt(11) + theRubkisCube.charAt(4) + "\n";
        //         newCube = newCube + theRubkisCube.substring(14, 17) + theRubkisCube.charAt(19) + theRubkisCube.charAt(12) + theRubkisCube.charAt(5) + "\n";
        //         newCube = newCube + theRubkisCube.substring(24, 33) + theRubkisCube.substring(21, 24) + "\n";
        //         newCube = newCube + theRubkisCube.substring(34, 81);
        //         this.theRubkisCube = newCube;
        //     } else if (currMove == 'D') {
        //         String newCube = "";
        //         newCube = newCube + theRubkisCube.substring(0, 47);
        //         newCube = newCube + theRubkisCube.substring(56, 59) + theRubkisCube.substring(47, 56) + "\n";
        //         newCube = newCube + theRubkisCube.substring(60, 81);
        //         this.theRubkisCube = newCube;
        //     } else {
        //         System.out.println("Character is invalid");
        //         throw new IllegalArgumentException("lol");
        //     }
            // i think using a switch makes more sense here honestly 
        }
}