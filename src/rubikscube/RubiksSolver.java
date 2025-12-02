package src.rubikscube;

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

    // public static int heuristic(Cubie currCubie, Cubie goalCubie) {
    //     int estimation = 0;

    //     for (int i = 0; i < 8; i++)  {
    //         if (currCubie.getCornerP(i) != goalCubie.getCornerP(i)) { //they don't equal so BAD! 
    //             estimation++;
    //         }
    //         if (currCubie.getCornerO(i) != 0) {
    //             estimation++;
    //         }
    //     }

    //     for (int i = 0 ; i < 12; i++) {
    //         if (currCubie.getEdgeP(i) != i) {
    //             estimation++;
    //         }
    //         if (currCubie.getEdgeO(i) != 0) {
    //             estimation++;
    //         }
    //     }
    //     return (int) estimation/4;
    // }
    public static int heuristic(Cubie currCubie, Cubie goalCubie) {
    int misplacedCorners = 0;
    int cornerOriSum = 0;

    for (int i = 0; i < 8; i++) {
        if (currCubie.getCornerP(i) != goalCubie.getCornerP(i)) {
            misplacedCorners++;
        }
        cornerOriSum += currCubie.getCornerO(i); // 0,1,2
    }

    int misplacedEdges = 0;
    int edgeOriSum = 0;
    for (int i = 0; i < 12; i++) {
        if (currCubie.getEdgeP(i) != goalCubie.getEdgeP(i)) {
            misplacedEdges++;
        }
        edgeOriSum += currCubie.getEdgeO(i); // 0 or 1
    }

    // ceil(x/4) = (x + 3) / 4 for ints
    int hPosCorners = (misplacedCorners + 3) / 4;
    int hPosEdges   = (misplacedEdges   + 3) / 4;
    int hOriCorners = (cornerOriSum     + 3) / 4;
    int hOriEdges   = (edgeOriSum       + 3) / 4;

    return Math.max(
        Math.max(hPosCorners, hPosEdges),
        Math.max(hOriCorners, hOriEdges)
    );
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

        //for debugging 
        int explored = 0;
        int skipped = 0;
        
        while(!queue.isEmpty()) {
            Node currNode = queue.poll();
            explored++;

            if (System.currentTimeMillis() - start > limit) {
                System.out.println("explored: " + explored);
                System.out.println("skipped: " + skipped);
                return "TIMEOUT";
            }

            if (currNode.g >= 25) { //depth limit,, 
                continue;
            }

            if (currNode.cube.isSolved()) {
                return createMoveSets(currNode); //WE FOUND SOMETHING so backtrack here
            }

            for (char move: moves) {                
                Cubie copy = Movements.movesGo(currNode.cube, move);                 
                if (theVisiteds.contains(copy)) {
                    skipped++;
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
