package src.rubikscube;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.*;

public class RubiksSolver {
    //do the A* magic here with the heuristics here facts 
    public static class Node implements Comparable<Node> { 
        Cubie cube;

        int g; //the current cost 
        int h; //heuristic 
        Node parent; 
        char move;

        Node(Cubie theCube, int g, int h, Node theParent, char theMove) {
            this.cube = theCube;
            this.g = g;
            this.h = h;
            this.parent = theParent;
            this.move = theMove;
        }

        public int getF() { // get f lol
            return g + h;
        }

        @Override 
        public int compareTo(Node otherNode) {  //we want to use this to determine the prioritized node
            return Integer.compare(this.getF(), otherNode.getF());
        }
    }

    //i honestly don't think this is needed because the A* algorithm is doing this
    // public static ArrayList<Node> createNewNodes(Node curr, char[] moves, Cubie goal, int max) {
    //     ArrayList<Node> newNodes = new ArrayList<Node>();
    //     if (curr.g >= max) { //if reaches the max limit depth go back (TO AVOID CREATING A LOT)
    //         return newNodes;
    //     }

    //     for (char m : moves) {
    //         Cubie newCubie = Movements.movesGo(curr.cube, m);
    //         int newG = curr.g + 1;
    //         int newH = heuristic(newCubie, goal);  //havent started on the heuristic 
    //         newNodes.add(new Node(newCubie, newG, newH, curr, m));
    //     }
    //     return newNodes;
    // }

    public static int heuristic(Cubie currCubie, Cubie goalCubie) {
        int estimation = 0;

        for (int i = 0; i < 8; i++)  {
            if (currCubie.getCornerP(i) != goalCubie.getCornerP(i)) { //they don't equal so BAD! 
                estimation++;
            }
            if (currCubie.getCornerO(i) != 0) {
                estimation++;
            }
            estimation += currCubie.getCornerO(i);  //watch
        }

        for (int i = 0 ; i < 12; i++) {
            if (currCubie.getEdgeP(i) != i) {
                estimation++;
            }
            if (currCubie.getEdgeO(i) != 0) {
                estimation++;
            }
            estimation += currCubie.getEdgeO(i);  //watch
        }
        return (int) estimation/4;
    }

    public static String aStarSolver(Cubie leCube, Cubie leGoal, char[] moves) {
        // do magic here
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        HashSet<Cubie> theVisiteds= new HashSet<Cubie>(); //we want to see that its visited 

        long start = System.currentTimeMillis();
        long limit = 10000;
        
        Node startNode = new Node(leCube, 0, 0, null, 'Q'); //use 'Q' as a starting character,, just remove later (?) placeholder basically
        queue.add(startNode);
        theVisiteds.add(leCube); //checking cubes ?
        
        while(!queue.isEmpty()) {
            Node currNode = queue.poll();

            if (System.currentTimeMillis() - start > limit) {
                return "TIMEOUT";
            }

            if (currNode.cube.isSolved()) {
                return createMoveSets(currNode); //WE FOUND SOMETHING so backtrack here
            }

            for (char move: moves) {
                //check for redundancy here
                
                Cubie copy = Movements.movesGo(currNode.cube, move);                 
                if (theVisiteds.contains(copy)) {
                    continue; 
                } else {
                    theVisiteds.add(copy);
                    int newHeuristic = heuristic(copy, leGoal);
                    Node nextNode = new Node(copy, currNode.g + 1, newHeuristic, currNode, move);
                    queue.add(nextNode);
                }
            }
        }   
        return ""; //return empty string cuz nothing was found
    }

    public static String createMoveSets(Node node) {
        //back track to give the actual answer finaly
        String solution = "";

        while (node.parent != null) {
            solution = node.move + solution;
            node = node.parent; //the current node moves on to the parent node and so ojn
        }
        return solution;
    }
}
