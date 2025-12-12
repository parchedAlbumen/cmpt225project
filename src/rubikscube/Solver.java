package src.rubikscube;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Scanner;

public class Solver {
	public static void main(String[] args) {
		System.out.println("number of arguments: " + args.length);

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}

        try {
            //reads the scrambled file
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            StringBuilder theContent = new StringBuilder();
            String line; 
            while ((line = reader.readLine()) != null) { //it iterates over the lines
                theContent.append(line);
            }
            reader.close(); // closing it here

            char[] facelets = convertStringToFacelets(theContent.toString());
            Cubie scrambledCube = new Cubie(facelets); //convert the facelets to cubies  

            // we want to see if it's already solved
            if (scrambledCube.isSolved()) {
                System.out.println("alreay solved bro");
                //write it to a file
                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
                writer.write("");
                writer.close();
                return;
            }

            int currHeuristic = RubiksSolver.strongHeuristic(scrambledCube);
            String solution = null;

            if (currHeuristic <= 6) {
                solution = RubiksSolver.bruteForcing(scrambledCube, 8);
            }

            if (solution == null && currHeuristic <= 10) {
                solution = RubiksSolver.bfsSolver(scrambledCube, currHeuristic + 4);
            }

            if (solution == null) {
                // we try A* here 
                solution = RubiksSolver.aStarSolver(scrambledCube);
            }
            
            //assuming that we got a solution, we start writing the solution
            BufferedWriter w = new BufferedWriter(new FileWriter(args[1])); 
            if (solution != null && !solution.isEmpty()) {
                w.write(solution);
                w.close();

                System.out.println("solution found!");
            } else {
                w.write("");
                w.close();
                System.out.println("NO SOLUTIONS FOUNDS BRO");
            }            
        } catch (Exception e) {
            System.out.println("BAD!");
        }
		
        // //reads scrambled file
		// File input = new File(args[0]);
		// String[] rubiksCube = new String[9];
		// try {
        //     Scanner reader = new Scanner(input); 
        //     int index = 0;
		// 	while(reader.hasNextLine()) {
        //         rubiksCube[index] = reader.nextLine();
        //         index++;
		// 	}	
		// 	reader.close(); //done reading  (should i put this in 'finally' ?)
		// } catch (IOException ioe) {
        //     System.out.println(ioe); 
		// } 

        // //reads solved file 
        // String[] solvedString = new String[9];
        // try {
        //     File solvedFile = new File("./rubikscube/cube_init.txt");
        //     Scanner reader = new Scanner(solvedFile);

        //     int index = 0;
        //     while (reader.hasNextLine()) {
        //         solvedString[index] = reader.nextLine();
        //         index++;
        //     }
        //     reader.close();
        // } catch (IOException ioe) {
        //     System.out.println(ioe);
        // }
        
        // //convert the file into facelets
        // char[] facelets = new char[54];
        // convertFileToFacelets(rubiksCube, facelets);

        // //convert the solved file to facelets
        // char[] solvedCubeFacelets = new char[54];
        // convertFileToFacelets(solvedString, solvedCubeFacelets);

        // // for (char i: facelets) { //sanity check to make sure that I actually converted it properly in facelets
        // //     System.out.println(i);
        // // }  

        // char[] moves = {'F', 'R', 'L', 'U', 'D', 'B'};
        // Cubie scrambledCube = new Cubie(facelets); 
        // Cubie solvedCube = new Cubie(solvedCubeFacelets);

		// // String answer = RubiksSolver.aStarSolver(scrambledCube, solvedCube, moves); BRO DOESNT WORK
        // String answer = RubiksSolver.bfsSolver(scrambledCube, solvedCube, moves);
        // System.out.println("possible answer: " + answer); //just print solution for now
        // File output = new File(args[1]);
        
        // Read the cube from file
        // String cubeState = RubiksCubeSolver.readCubeFromFile(args[0]);
        
        // if (cubeState == null) {
        //     System.out.println("Failed to read cube from file");
        //     return;
        // }
        
        // System.out.println("Initial cube state:");
        // System.out.println(cubeState);
        
        // // Solve using BFS
        // System.out.println("Solving...");
        // String solution = RubiksCubeSolver.solveBFS(cubeState, 10);
        
        // if (solution != null) {
        //     System.out.println("Solution found: " + solution);
        //     System.out.println("Number of moves: " + solution.length());
        // } else {
        //     System.out.println("No solution found within depth limit");
        // }
	}

    private static char[] convertStringToFacelets(String content) { 
        StringBuilder clean = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {
            char letter = content.charAt(i);
            if (letter != ' ') {
                clean.append(letter);
                if (clean.length() == 54) { // we are done so leave
                    break;
                }
            }
        }

        return clean.toString().toCharArray(); //return char Array of this string
    }
    
    // private static void convertFileToFacelets(String[] rubiks, char[] facelets) {
    //     //BRUTE FORCE TIME (just like assignment1 lolololol)
    //     //up
    //     int index = 0; 
    //     for (int y = 0; y < 3; y++) { //because up is from 0 to 2 based on the rubiks cube
    //         for (int x = 3; x < 6; x++) {
    //             facelets[index] = rubiks[y].charAt(x);
    //             index++;
    //         }
    //     }   //index should end at 9 here
        
    //     for (int y = 3; y < 4; y++) {
    //         for (int x = 0; x < 12; x++) {
    //             facelets[index] = rubiks[y].charAt(x);
    //             index++;
    //         }
    //     }
        
    //     for (int y = 4; y < 5; y++) {
    //         for (int x = 0; x < 12; x++) {
    //             facelets[index] = rubiks[y].charAt(x);
    //             index++;
    //         }
    //     }

    //     for (int y = 5; y < 6; y++) {
    //         for (int x = 0; x < 12; x++) {
    //             facelets[index] = rubiks[y].charAt(x);
    //             index++;
    //         }
    //     }

    //     //down
    //     for (int y = 6; y < 9; y++) {
    //         for (int x = 3; x < 6; x++) {
    //             facelets[index] = rubiks[y].charAt(x);
    //             index++;
    //         }
    //     }        
    // }
}