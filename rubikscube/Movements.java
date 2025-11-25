package rubikscube;

public class Movements {
    public static void doMoves(Cubie cubie, char movement) {
        //FURDLB | do magic here
        if (movement == 'F') {
            //corner permutation part 
            int tempOrieP = cubie.getCornerP(0); //DLF becomes temp
            cubie.setCornerP(0, cubie.getCornerP(1)); //URF becomes UFL (clockwise motion)
            cubie.setCornerP(1, cubie.getCornerP(5)); //UFL -> DLF
            cubie.setCornerP(5, cubie.getCornerP(4)); //DLF -> DFR
            cubie.setCornerP(4, tempOrieP); //DFR -> temp

            //corner orientation part (idk how to do this)

            //edge permutation part  UF->FR->DF->FL->UF
            int tempEdgeP = cubie.getEdgeP(1); //UF
            cubie.setEdgeP(1, cubie.getEdgeP(9)); //UF -> FL
            cubie.setEdgeP(9, cubie.getEdgeP(5));
            cubie.setEdgeP(5, cubie.getEdgeP(8));
            cubie.setEdgeP(8, tempEdgeP);

            //edge orientation part 
            //do magic 
        } else if (movement == 'U') {
            //do magic
        } else if (movement == 'R') {
            //do magic
        } else if (movement == 'D') {
            //do magic
        } else if (movement == 'L') {
            //do magic 
        } else if (movement == 'B') {
            // do magic
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
