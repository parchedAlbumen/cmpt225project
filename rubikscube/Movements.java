package rubikscube;

public class Movements {
    public static void doMoves(Cubie cubie, char movement) {
        //FURDLB | do magic here
        if (movement == 'F') {
            //corner permutation part 
            int tempCornerP = cubie.getCornerP(0); //DLF becomes temp
            cubie.setCornerP(0, cubie.getCornerP(1)); //URF becomes UFL (clockwise motion)
            cubie.setCornerP(1, cubie.getCornerP(5)); //UFL -> DLF
            cubie.setCornerP(5, cubie.getCornerP(4)); //DLF -> DFR
            cubie.setCornerP(4, tempCornerP); //DFR -> temp

            //corner orientation part (idk how to do this)

            //edge permutation part  UF->FR->DF->FL->UF
            int tempEdgeP = cubie.getEdgeP(1); //UF
            cubie.setEdgeP(1, cubie.getEdgeP(9)); //UF -> FL
            cubie.setEdgeP(9, cubie.getEdgeP(5));
            cubie.setEdgeP(5, cubie.getEdgeP(8));
            cubie.setEdgeP(8, tempEdgeP);

            //edge orientation part 
        } else if (movement == 'U') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(0);
            cubie.setCornerP(0, cubie.getCornerP(3));
            cubie.setCornerP(3, cubie.getCornerP(2));
            cubie.setCornerP(2, cubie.getCornerP(1));
            cubie.setCornerP(1, tempCornerP);
            //corner orientation
            //edge permutation
            int tempEdgeP = cubie.getEdgeP(0);
            cubie.setEdgeP(0, cubie.getEdgeP(3));
            cubie.setEdgeP(3, cubie.getEdgeP(2));
            cubie.setEdgeP(2, cubie.getEdgeP(1));
            cubie.setEdgeP(1, tempEdgeP);
            //edge orientation
        } else if (movement == 'R') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(0);
            cubie.setCornerP(0, cubie.getCornerP(4));
            cubie.setCornerP(4, cubie.getCornerP(7));
            cubie.setCornerP(7, cubie.getCornerP(3));
            cubie.setCornerP(3, tempCornerP);
            //corner orientation
            //edge permutation
            int tempEdgeP = cubie.getEdgeP(0);
            cubie.setEdgeP(0, cubie.getEdgeP(8));
            cubie.setEdgeP(8, cubie.getEdgeP(4));
            cubie.setEdgeP(4, cubie.getEdgeP(11));
            cubie.setEdgeP(11, tempEdgeP);
            //edge orientation
        } else if (movement == 'D') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(4);
            cubie.setCornerP(4, cubie.getCornerP(5));
            cubie.setCornerP(5, cubie.getCornerP(6));
            cubie.setCornerP(6, cubie.getCornerP(7));
            cubie.setCornerP(7, tempCornerP);
            //corner orientation
            //edge permutation
            int tempEdgeP = cubie.getEdgeP(4);
            cubie.setEdgeP(4, cubie.getEdgeP(5));
            cubie.setEdgeP(5, cubie.getEdgeP(6));
            cubie.setEdgeP(6, cubie.getEdgeP(7));
            cubie.setEdgeP(7, tempEdgeP);
            //edge orientation
        } else if (movement == 'L') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(1);
            cubie.setCornerP(1, cubie.getCornerP(2));
            cubie.setCornerP(2, cubie.getCornerP(6));
            cubie.setCornerP(6, cubie.getCornerP(5));
            cubie.setCornerP(5, tempCornerP);
            //corner orientation
            //edge permutation
            int tempEdgeP = cubie.getEdgeP(2);
            cubie.setEdgeP(2, cubie.getEdgeP(10));
            cubie.setEdgeP(10, cubie.getEdgeP(6));
            cubie.setEdgeP(6, cubie.getEdgeP(9));
            cubie.setEdgeP(9, tempEdgeP);            
            //edge orientation
        } else if (movement == 'B') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(3); 
            cubie.setCornerP(3, cubie.getCornerP(7));
            cubie.setCornerP(7, cubie.getCornerP(6));
            cubie.setCornerP(6, cubie.getCornerP(2));
            cubie.setCornerP(2, tempCornerP);

            //corner orientation
            //edge permutation
            int tempEdgeP = cubie.getEdgeP(3);
            cubie.setEdgeP(3, cubie.getEdgeP(11));
            cubie.setEdgeP(11, cubie.getEdgeP(7));
            cubie.setEdgeP(7, cubie.getEdgeP(10));
            cubie.setEdgeP(10, tempEdgeP);        
            //edge orientation
        } else {
            System.out.println("invalid character");
        }
    }

    public static Cubie movesGo(Cubie cubie, char movement) {
        Cubie newCube = new Cubie(cubie); //copy the new cube
        doMoves(newCube, movement); 
        return newCube; //return the copy becuz we are gonna use this to do comparisons for the actual graph traversals
    }
}
