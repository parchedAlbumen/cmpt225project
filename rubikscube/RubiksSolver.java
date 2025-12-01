package rubikscube;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.*;

public class RubiksSolver {
    //do the A* magic here with the heuristics here facts 
    public static class Node { 
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
    }

    public static ArrayList<Node> createNewNodes(Node curr, char[] moves, Cubie goal, int max) {
        ArrayList<Node> newNodes = new ArrayList<Node>();
        if (curr.g >= max) { //if reaches the max limit depth go back (TO AVOID CREATING A LOT)
            return newNodes;
        }

        for (char m : moves) {
            Cubie newCubie = Movements.movesGo(curr.cube, m);
            int newG = curr.g + 1;
            int newH = heuristic(newCubie, goal);  //havent started on the heuristic 
            newNodes.add(new Node(newCubie, newG, newH, curr, m));
        }
        return newNodes;
    }

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

    public static void aStarSolver(Cubie leCube, Cubie leGoal, char[] moves) {
        // do magic here
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        HashSet<Cubie> isVisited = new HashSet<Cubie>(); //we want to see that its visited 
        
        Node startNode = new Node(leCube, 0, 0, null, 'Q'); //use 'Q' as a starting character,, just remove later (?) placeholder basically
        queue.add(startNode);
        isVisited.add(leCube);
        
        while(!queue.isEmpty()) {
            Node currNode = queue.poll();


            if (currNode.cube.isSolved(leCube))
        }   
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
