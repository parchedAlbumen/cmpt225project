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
            if (currCubie.getCornerO(i) != goalCubie.getCornerO(i)) { //they don't equal so BAD! 
                estimation++;
            }
            if (currCubie.getCornerP(i) != goalCubie.getCornerP(i)) {
                estimation++;
            }
        }

        for (int j = 0; j < 12; j++) {
            if (currCubie.getEdgeO(j) != goalCubie.getEdgeO(j)) { //they don't equal so ALSO BAD!
                estimation++;
            } 
            if (currCubie.getEdgeP(j) != goalCubie.getEdgeP(j)) {
                estimation++;
            }
        }
        return (int) Math.ceil(estimation/8);
    }

    public static String aStarSolver(Cubie leCube, Cubie leGoal, char[] moves) {
        // do magic here
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        HashSet<Cubie> theVisiteds= new HashSet<Cubie>(); //we want to see that its visited 
        
        Node startNode = new Node(leCube, 0, 0, null, 'Q'); //use 'Q' as a starting character,, just remove later (?) placeholder basically
        queue.add(startNode);
        theVisiteds.add(leCube); //checking cubes ?
        
        while(!queue.isEmpty()) {
            Node currNode = queue.poll();

            if (currNode.cube.isSolved()) {
                return createMoveSets(currNode); //WE FOUND SOMETHING
            }

            if (currNode.g >= 5) {
                continue; //skip the whole loop becuz no point
            }

            for (char move: moves) {
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
