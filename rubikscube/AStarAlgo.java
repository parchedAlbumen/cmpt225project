package rubikscube;

import java.util.ArrayList;

public class AStarAlgo {
    //f = g + h 
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
        //build this later or tomorrow
        return 1; 
    }

    public static void aStarSolver() {
        // do magic here
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
