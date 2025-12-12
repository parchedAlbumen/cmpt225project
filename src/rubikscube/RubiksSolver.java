package src.rubikscube;

import java.util.*;

public class RubiksSolver {
    //do the A* magic here with the heuristics here facts 
    public static class Node implements Comparable<Node> { 
        Cubie cube;
        Node parent; 
        int hash;
        int g; //the current cost 
        int h; //heuristic 
        char move;

        Node(Cubie theCube, int g, int h, Node theParent, char theMove) {
            this.cube = new Cubie(cube); // I don't think we want to use the real cube, so copy
            this.g = g;
            this.h = h;
            this.parent = theParent;
            this.move = theMove;
            this.hash = cube.hashCode();
        }

        public int getF() { // get f lol
            return g + h;
        }

        @Override 
        public int compareTo(Node otherNode) {  //we want to use this to determine the prioritized node
            return Integer.compare(this.getF(), otherNode.getF());
        }

        @Override 
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            Node theOther = (Node) other; //explicit define the other object as a node
            return this.hash == theOther.hash && this.cube.equals(theOther.cube);
        }

        @Override 
        public int hashCode() {
            return this.hash;
        }
    }

    public static int strongHeuristic(Cubie cube) { //change name lol
        int cornerDist = 0;
        int edgeDist = 0;

        //for determining edge distance 
        for (int i = 0; i < 8; i++) {
            int currPos = cube.getCornerP(i);
            int targetPos = i;

            if (currPos != targetPos) { //they dont match so do some math
                int difference = Math.abs(currPos - targetPos);
                if (difference >= 4) {
                    cornerDist += 2;
                } else if (difference == 1 || difference == 3) {
                    cornerDist += 1; //they are just adjacent to each other
                } else {
                    cornerDist += 2; 
                }
            }
        }

        // divide by 4, because 4 corners are affected per turn
        cornerDist = (cornerDist + 3) / 4;

        //for determining edge distance (SAME PATTERN AS CORNER POS)
        for (int i = 0; i < 12; i++) {
            int currPos = cube.getEdgeP(i);
            int targetPos = i;

            if (currPos != targetPos) {
                if (Math.abs(currPos - targetPos) == 1) {
                    edgeDist += 1 ;
                } else {
                    edgeDist += 2;
                }
            }

            if (cube.getEdgeO(i) != 0) {
                edgeDist += 1;
            }
        }

        //each move affects 4 edges, so dividing by 4 makes sense
        edgeDist = (edgeDist + 3) / 4;

        return Math.max(edgeDist, cornerDist); //returns whatever was bigger out of the 2 
    }

    public static String bfsSolver(Cubie cube, int max) {
        char[] moves = {'F', 'U', 'R', 'D', 'L', 'B'};
        Queue<Node> queue = new LinkedList<Node>();
        HashSet<Integer> visited = new HashSet<Integer>();

        Node startNode = new Node(cube, 0, 0, null, ' ');
        queue.add(startNode);
        visited.add(cube.hashCode()); 

        // int explored = 0; for debugging and stuff
        int maxNodes = 100000; 

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            //explored++;

            if (curr.cube.isSolved()) {
                System.out.println("YAY! we solved it");
                // System.out.println("explored: " + explored);
                return createMoveSets(curr);
            }

            if (curr.g >= max) {
                continue; // we dont wanna continue at this path so skip
            }

            for (char move: moves) {
                Cubie nextCube = Movements.movesGo(curr.cube, move);
                int nextHash = nextCube.hashCode();

                if (!visited.contains(nextHash)) {
                    visited.add(nextHash);
                    queue.add(new Node(nextCube, curr.g + 1, 0, curr, move));
                }
            }
        }
        //nothing was found
        return null;
    }

    public static String aStarSolver(Cubie cube) {
        char[] moves = {'F', 'U', 'R', 'D', 'L', 'B'};
        PriorityQueue<Node> openQueue = new PriorityQueue<Node>();
        HashSet<Integer> closedSet = new HashSet<Integer>();
        HashMap<Integer, Integer> gValues = new HashMap<Integer, Integer>();

        int startingHeuristic = strongHeuristic(cube);
        Node startNode = new Node(cube, 0, startingHeuristic, null, ' ');
        openQueue.add(startNode);
        gValues.put(cube.hashCode(), 0);

        // int explored = 0; 

        while (!openQueue.isEmpty()) {
            Node curr = openQueue.poll();
            // explored++;

            if (curr.cube.isSolved()) {
                System.out.println("WE FOUND SOMETHING!");
                return createMoveSets(curr);
            }

            //idk about the bestG

            closedSet.add(curr.hashCode());

            for (char move: moves) {
                Cubie nextCube = Movements.movesGo(curr.cube, move);
                int nextHash = nextCube.hashCode();

                if (closedSet.contains(nextHash)) continue;

                int newHeuristic = strongHeuristic(nextCube);
                Node nextNode = new Node(nextCube, curr.g + 1, newHeuristic, curr, move);
                openQueue.add(nextNode);
                gValues.put(nextHash, curr.g+1);
            }
        }
        //couldn't find anything
        return null;
    }

    public static String bruteForcing(Cubie cube, int max) {
        if (cube.isSolved()) {
            return "";
        }

        for (int depth = 1; depth <= max; depth++) {
            String solution = bruteForceHelper(cube, depth, new HashSet<Integer>());
            if (solution != null) {
                return solution;
            }
        }
        return null;
    }
    
    private static String bruteForceHelper(Cubie cube, int depth, HashSet<Integer> visited) {
        char[] moves = {'F', 'U', 'R', 'D', 'L', 'B'};
        int hash = cube.hashCode();
        if (visited.contains(hash)) {
            return null;
        }
        visited.add(hash);
    
        for (char move: moves) {
            Cubie nextCube = Movements.movesGo(cube, move);
            String result = bruteForceHelper(nextCube, depth - 1, visited);
            if (result != null ){
                return move + result; //recusively build the solution;
            }
        }
        
        visited.remove(hash);
        return null;
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

    public static String hello() {
        return "Hi";
    }

    // private static Cubie applyMoves(Cubie cube, String moves) {
    //     Cubie result = new Cubie(cube); //copy cuz we dont want to manipualte the OG
    //     for (char move : moves.toCharArray()) {
    //         result = Movements.movesGo(result, move);
    //     }
    //     return result;
    // }
}

// public static String bfsSolver(Cubie scrambledCube, Cubie goalCube, char[] moves) {
    //     LinkedList<Node> queue = new LinkedList<Node>();
    //     HashSet<Cubie> theVisited = new HashSet<Cubie>(); //we want to see that its visited
    
    //     long start = System.currentTimeMillis();
    //     long limit = 10000;
    
    //     Node phase1Node = RubiksSolver.bfsPhaseOne(scrambledCube, moves);
    //     if (phase1Node == null) {
        //         return "FAILED";
        //     } else {
            //         System.out.println("IT PASSED?");
            //     }
            
            //     Node startNode = new Node(scrambledCube, 0, 0, null, 'q'); // q is a placeholder
            //     queue.add(startNode);
            //     theVisited.add(startNode.cube);
            
            //     //for debugging 
            //     int explored = 0;
            //     int skipped = 0;
            
            //     while (!queue.isEmpty()) { //FIFO
            //         Node currNode = queue.removeFirst();
            //         explored++;
            
            //         if (System.currentTimeMillis() - start > limit) {
                //             System.out.println("explored: " + explored);
                //             System.out.println("skipped: " + skipped);
                //             return "TIMEOUT";
                //         }
                
                //         if (currNode.cube.isSolved()) {
                    //             return createMoveSets(currNode); 
                    //         }
                    
                    //         if (currNode.g >= 15) { //god's number is 20,, so if it tries to go above that, skip it
                    //             continue;
                    //         }
                    
                    //         for (char move: moves) {
                        //             // avoid doing 3 of the same turns
                        //             if (currNode.parent != null && currNode.parent.parent != null && currNode.parent.parent.parent != null) {
                            //                 if (move == currNode.move && move == currNode.parent.move && move == currNode.parent.parent.move) {
                                //                     skipped++;
                                //                     continue;
                                //                 }
                                //             }
                                
                                //             Cubie nextCube = Movements.movesGo(currNode.cube, move);
                                
                                //             if (theVisited.contains(nextCube)) {
                                    //                 skipped++;
                                    //                 continue;
                                    //             }
                                    
                                    //             theVisited.add(nextCube);
                                    //             Node nextNode = new Node(nextCube, currNode.g + 1, 0, currNode, move);
                                    //             queue.add(nextNode);
                                    //         }
                                    //     }
                                    //     return ""; //failed to find any solutionos
                                    // }
                                    
    // public static Node bfsPhaseOne(Cubie scrambled, char[] moves) {
    //     System.out.println("started phase 1");
    //     LinkedList<Node> queue = new LinkedList<Node>();
    //     HashSet<Cubie> theVisited = new HashSet<Cubie>(); //we want to see that its visited
                                            
    //     long start = System.currentTimeMillis();
    //     long limit = 10000;
                                            
    //     Cubie startCubie = new Cubie(scrambled);
    //     Node startNode = new Node(startCubie, 0, 0, null, ' '); // spacae is a placeholder
    //     queue.add(startNode);
    //     theVisited.add(startNode.cube);
                                            
    //     //for debugging
    //     int explored = 0;
    //     int skipped = 0;
                                            
    //     while (!queue.isEmpty()) { //FIFO
    //         Node currNode = queue.removeFirst();
    //         explored++;
                                                
    //         if (currNode.cube.isPhase1Good()) {
    //             return currNode;
    //         }
                                                
    //         if (System.currentTimeMillis() - start > limit) {
    //             System.out.println("explored: " + explored);
    //             System.out.println("skipped: " + skipped);
    //             return null;
    //         }
                                                
    //         for (char move: moves) {
    //             if (currNode.g >= 3) {
    //                 if (move == currNode.move && move == currNode.parent.move && move == currNode.parent.parent.move) {
    //                     skipped++;
    //                     continue;
    //                 }
    //             }
                                                    
    //             Cubie nextCube = Movements.movesGo(currNode.cube, move);
    //             if (theVisited.contains(nextCube)) {
    //                 continue;
    //             } else {
    //                 theVisited.add(nextCube);
    //                 queue.add(new Node(nextCube, currNode.g + 1, 0, currNode, move));
    //             }
    //         }
    //     }
    //     return null; //failed to find any solutions
    // }