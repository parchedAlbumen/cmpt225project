package src.rubikscube;

import java.io.*;
import java.util.*;

public class RubiksCubeSolver {
    
    // The solved state
    static final String SOLVED_STATE = 
        "   OOO\n" + 
        "   OOO\n" +
        "   OOO\n" +
        "GGGWWWBBBYYY\n" +
        "GGGWWWBBBYYY\n" +
        "GGGWWWBBBYYY\n" +
        "   RRR\n" +
        "   RRR\n" +
        "   RRR\n";
    
    /**
     * Read cube state from file
     */
    static String readCubeFromFile(String filename) {
        try {
            File input = new File(filename);
            String[] rubiksCube = new String[9];
            Scanner reader = new Scanner(input); 
            int index = 0;
            while(reader.hasNextLine() && index < 9) {
                rubiksCube[index] = reader.nextLine();
                index++;
            }   
            reader.close();
            
            // Build the cube string
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                if (rubiksCube[i] != null) {
                    sb.append(rubiksCube[i]).append("\n");
                }
            }
            
            // Ensure the string is exactly 81 characters (9 lines with newlines)
            String result = sb.toString();
            if (result.length() < 81) {
                // Pad with newline if needed
                while (result.length() < 81) {
                    result += "\n";
                }
            }
            
            return result;
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e);
            return null;
        }
    }
    
    /**
     * BFS to find shortest solution
     */
    static String solveBFS(String initialState, int maxDepth) {
        if (initialState.equals(SOLVED_STATE)) {
            return "";
        }
        
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        char[] moves = {'F', 'B', 'R', 'L', 'U', 'D'};
        
        queue.add(new State(initialState, ""));
        visited.add(initialState);
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            if (current.moves.length() >= maxDepth) {
                continue;
            }
            
            // Try each move
            for (char move : moves) {
                String newState = applyMove(current.state, move);
                
                if (newState.equals(SOLVED_STATE)) {
                    return current.moves + move;
                }
                
                if (!visited.contains(newState)) {
                    visited.add(newState);
                    queue.add(new State(newState, current.moves + move));
                }
            }
        }
        
        return null;
    }
    
    /**
     * Apply a single move to the cube
     */
    static String applyMove(String cube, char move) {
        switch (move) {
            case 'F': return applyF(cube);
            case 'B': return applyB(cube);
            case 'R': return applyR(cube);
            case 'L': return applyL(cube);
            case 'U': return applyU(cube);
            case 'D': return applyD(cube);
            default: return cube;
        }
    }
    
    static String applyF(String cube) {
        String newCube = "";
        newCube += cube.substring(0,17);
        newCube = newCube + cube.charAt(23) + cube.charAt(36) + cube.charAt(49) + "\n";
        newCube = newCube + cube.substring(21, 23) + cube.charAt(63) + cube.charAt(50) + cube.charAt(37) + cube.charAt(24) + cube.charAt(17) + cube.substring(28, 34);
        newCube = newCube + cube.substring(34,36) + cube.charAt(64) + cube.charAt(51) + cube.charAt(38) + cube.charAt(25) + cube.charAt(18) + cube.substring(41, 47);
        newCube = newCube + cube.substring(47, 49) + cube.charAt(65) + cube.charAt(52) + cube.charAt(39) + cube.charAt(26) + cube.charAt(19) + cube.substring(54, 60);
        newCube = newCube + cube.substring(60,63) + cube.charAt(53) + cube.charAt(40) + cube.charAt(27) + "\n";
        newCube += cube.substring(67, Math.min(81, cube.length()));
        return newCube;
    }
    
    static String applyB(String cube) {
        String newCube = "   ";
        newCube = newCube + cube.charAt(29) + cube.charAt(42) + cube.charAt(55) + "\n";
        newCube += cube.substring(7, 21);
        newCube = newCube + cube.charAt(5) + cube.substring(22, 29) + cube.charAt(79) + cube.charAt(56) + cube.charAt(43) + cube.charAt(30) + "\n";
        newCube = newCube + cube.charAt(4) + cube.substring(35, 42) + cube.charAt(78) + cube.charAt(57) + cube.charAt(44) + cube.charAt(31) + "\n";
        newCube = newCube + cube.charAt(3) + cube.substring(48, 55) + cube.charAt(77) + cube.charAt(58) + cube.charAt(45) + cube.charAt(32) + "\n";
        newCube += cube.substring(60, 77) + cube.charAt(21) + cube.charAt(34) + cube.charAt(47);
        return newCube;
    }
    
    static String applyR(String cube) {
        String newCube = "   ";
        newCube = newCube + cube.substring(3, 5) + cube.charAt(26) + "\n";
        newCube = newCube + cube.substring(7, 12) + cube.charAt(39) + "\n";
        newCube = newCube + cube.substring(14,19) + cube.charAt(52) + "\n";
        newCube = newCube + cube.substring(21, 26) + cube.charAt(65) + cube.charAt(53) + cube.charAt(40) + cube.charAt(27) + cube.charAt(19) + cube.substring(31, 34);
        newCube = newCube + cube.substring(34, 39) + cube.charAt(72) + cube.charAt(54) + cube.charAt(41) + cube.charAt(28) + cube.charAt(12) + cube.substring(44, 47);
        newCube = newCube + cube.substring(47, 52) + cube.charAt(79) + cube.charAt(55) + cube.charAt(42) + cube.charAt(29) + cube.charAt(5) + cube.substring(57, 60); 
        newCube = newCube + cube.substring(60, 65) + cube.charAt(56) + "\n";
        newCube = newCube + cube.substring(67, 72) + cube.charAt(43) + "\n";
        newCube = newCube + cube.substring(74, 79) + cube.charAt(30) + "\n";
        return newCube;
    }
    
    static String applyL(String cube) {
        String newCube = "   ";
        newCube = newCube + cube.charAt(58) + cube.substring(4, 7);
        newCube = newCube + cube.substring(7, 10) + cube.charAt(45) + cube.substring(11, 14);
        newCube = newCube + cube.substring(14, 17) + cube.charAt(32) + cube.substring(18, 21);
        newCube = newCube + cube.charAt(47) + cube.charAt(34) + cube.charAt(21) + cube.charAt(3) + cube.substring(25, 32) + cube.charAt(63) + "\n";
        newCube = newCube + cube.charAt(48) + cube.charAt(35) + cube.charAt(22) + cube.charAt(10) + cube.substring(38, 45) + cube.charAt(70) + "\n";
        newCube = newCube + cube.charAt(49) + cube.charAt(36) + cube.charAt(23) + cube.charAt(17) + cube.substring(51, 58) + cube.charAt(77) + "\n";
        newCube = newCube + cube.substring(60, 63) + cube.charAt(24) + cube.substring(64, 67);
        newCube = newCube + cube.substring(67, 70) + cube.charAt(37) + cube.substring(71, 75);
        newCube = newCube + cube.substring(75, 77) + cube.charAt(50) + cube.substring(78, 81);
        return newCube;
    }
    
    static String applyU(String cube) {
        String newCube = "   ";
        newCube = newCube + cube.charAt(17) + cube.charAt(10) + cube.charAt(3) + "\n";
        newCube = newCube + cube.substring(7, 10) + cube.charAt(18) + cube.charAt(11) + cube.charAt(4) + "\n";
        newCube = newCube + cube.substring(14, 17) + cube.charAt(19) + cube.charAt(12) + cube.charAt(5) + "\n";
        newCube = newCube + cube.substring(24, 33) + cube.substring(21, 24) + "\n";
        newCube = newCube + cube.substring(34, 81);
        return newCube;
    }
    
    static String applyD(String cube) {
        String newCube = "";
        newCube = newCube + cube.substring(0, 47);
        newCube = newCube + cube.substring(56, 59) + cube.substring(47, 56) + "\n";
        newCube = newCube + cube.substring(60, 81);
        return newCube;
    }
    
    static class State {
        String state;
        String moves;
        
        State(String state, String moves) {
            this.state = state;
            this.moves = moves;
        }
    }
}