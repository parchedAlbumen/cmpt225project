package src.rubikscube;

public class Movements {
    public static void doMoves(Cubie cubie, char movement) {
        //FURDLB | do magic here
        if (movement == 'F') {
            //corner permutation part 
            int tempCornerP = cubie.getCornerP(0); //URF becomes temp
            cubie.setCornerP(0, cubie.getCornerP(1)); //URF becomes UFL (clockwise motion)
            cubie.setCornerP(1, cubie.getCornerP(5)); //UFL -> DLF
            cubie.setCornerP(5, cubie.getCornerP(4)); //DLF -> DFR
            cubie.setCornerP(4, tempCornerP); //DFR -> temp

            //corner orientation part 
            int tempCornerO = cubie.getCornerO(0);
            cubie.setCornerO(0, (cubie.getCornerO(1) + 2) % 3);
            cubie.setCornerO(1, (cubie.getCornerO(5) + 1) % 3);
            cubie.setCornerO(5, (cubie.getCornerO(4) + 2) % 3);
            cubie.setCornerO(4, (tempCornerO + 1) % 3);

            //edge permutation part  UF->FR->DF->FL->UF
            int tempEdgeP = cubie.getEdgeP(1); //UF
            cubie.setEdgeP(1, cubie.getEdgeP(9)); //UF -> FL
            cubie.setEdgeP(9, cubie.getEdgeP(5));
            cubie.setEdgeP(5, cubie.getEdgeP(8));
            cubie.setEdgeP(8, tempEdgeP);

            //edge orientation part 
            int tempEdgeO = cubie.getEdgeO(1);
            cubie.setEdgeO(1, cubie.getEdgeO(9) ^ 1); //what 
            cubie.setEdgeO(9, cubie.getEdgeO(5) ^ 1); 
            cubie.setEdgeO(5, cubie.getEdgeO(8) ^ 1); 
            cubie.setEdgeO(8, tempEdgeO ^ 1);

        } else if (movement == 'U') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(0);
            cubie.setCornerP(0, cubie.getCornerP(3));
            cubie.setCornerP(3, cubie.getCornerP(2));
            cubie.setCornerP(2, cubie.getCornerP(1));
            cubie.setCornerP(1, tempCornerP);

            //corner orientation
            int tempCornerO = cubie.getCornerO(0);
            cubie.setCornerO(0 , cubie.getCornerO(3));
            cubie.setCornerO(3 , cubie.getCornerO(2));
            cubie.setCornerO(2 , cubie.getCornerO(1));
            cubie.setCornerO(1 , tempCornerO);

            //edge permutation
            int tempEdgeP = cubie.getEdgeP(0);
            cubie.setEdgeP(0, cubie.getEdgeP(3));
            cubie.setEdgeP(3, cubie.getEdgeP(2));
            cubie.setEdgeP(2, cubie.getEdgeP(1));
            cubie.setEdgeP(1, tempEdgeP);

            //edge orientation
            int tempEdgeO = cubie.getEdgeO(0);
            cubie.setEdgeO(0, cubie.getEdgeO(3));
            cubie.setEdgeO(3, cubie.getEdgeO(2));
            cubie.setEdgeO(2, cubie.getEdgeO(1));
            cubie.setEdgeO(1, tempEdgeO);

        } else if (movement == 'R') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(0);
            cubie.setCornerP(0, cubie.getCornerP(4));
            cubie.setCornerP(4, cubie.getCornerP(7));
            cubie.setCornerP(7, cubie.getCornerP(3));
            cubie.setCornerP(3, tempCornerP);

            //corner orientation
            int tempCornerO = cubie.getCornerO(0);
            cubie.setCornerO(0, (cubie.getCornerO(4) + 1) % 3);
            cubie.setCornerO(4, (cubie.getCornerO(7) + 2) % 3);
            cubie.setCornerO(7, (cubie.getCornerO(3) + 1) % 3);
            cubie.setCornerO(3, (tempCornerO + 2) % 3);

            //edge permutation
            int tempEdgeP = cubie.getEdgeP(0);
            cubie.setEdgeP(0, cubie.getEdgeP(8));
            cubie.setEdgeP(8, cubie.getEdgeP(4));
            cubie.setEdgeP(4, cubie.getEdgeP(11));
            cubie.setEdgeP(11, tempEdgeP);

            //edge orientation
            int tempEdgeO = cubie.getEdgeO(0);
            cubie.setEdgeO(0, cubie.getEdgeO(8));
            cubie.setEdgeO(8, cubie.getEdgeO(4));
            cubie.setEdgeO(4, cubie.getEdgeO(11));
            cubie.setEdgeO(11, tempEdgeO);

        } else if (movement == 'D') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(4);
            cubie.setCornerP(4, cubie.getCornerP(5));
            cubie.setCornerP(5, cubie.getCornerP(6));
            cubie.setCornerP(6, cubie.getCornerP(7));
            cubie.setCornerP(7, tempCornerP);

            //corner orientation
            int tempCornerO = cubie.getCornerO(4);
            cubie.setCornerO(4, cubie.getCornerO(5));
            cubie.setCornerO(5, cubie.getCornerO(6));
            cubie.setCornerO(6, cubie.getCornerO(7));
            cubie.setCornerO(7, tempCornerO);

            //edge permutation
            int tempEdgeP = cubie.getEdgeP(4);
            cubie.setEdgeP(4, cubie.getEdgeP(5));
            cubie.setEdgeP(5, cubie.getEdgeP(6));
            cubie.setEdgeP(6, cubie.getEdgeP(7));
            cubie.setEdgeP(7, tempEdgeP);

            //edge orientation
            int tempEdgeO = cubie.getEdgeO(4);
            cubie.setEdgeO(4, cubie.getEdgeO(5));
            cubie.setEdgeO(5, cubie.getEdgeO(6));
            cubie.setEdgeO(6, cubie.getEdgeO(7));
            cubie.setEdgeO(7, tempEdgeO);

        } else if (movement == 'L') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(1);
            cubie.setCornerP(1, cubie.getCornerP(2));
            cubie.setCornerP(2, cubie.getCornerP(6));
            cubie.setCornerP(6, cubie.getCornerP(5));
            cubie.setCornerP(5, tempCornerP);

            //corner orientation
            int tempCornerO = cubie.getCornerO(1);
            cubie.setCornerO(1, (cubie.getCornerO(2) + 2) % 3);
            cubie.setCornerO(2, (cubie.getCornerO(6) + 1) % 3);
            cubie.setCornerO(6, (cubie.getCornerO(5) + 2) % 3);
            cubie.setCornerO(5, (tempCornerO + 1) % 3);

            //edge permutation
            int tempEdgeP = cubie.getEdgeP(2);
            cubie.setEdgeP(2, cubie.getEdgeP(10));
            cubie.setEdgeP(10, cubie.getEdgeP(6));
            cubie.setEdgeP(6, cubie.getEdgeP(9));
            cubie.setEdgeP(9, tempEdgeP);  

            //edge orientation
            int tempEdgeO = cubie.getEdgeO(2);
            cubie.setEdgeO(2, cubie.getEdgeO(10));
            cubie.setEdgeO(10, cubie.getEdgeO(6));
            cubie.setEdgeO(6, cubie.getEdgeO(9));
            cubie.setEdgeO(9, tempEdgeO);

        } else if (movement == 'B') {
            //corner permutation 
            int tempCornerP = cubie.getCornerP(3); 
            cubie.setCornerP(3, cubie.getCornerP(7));
            cubie.setCornerP(7, cubie.getCornerP(6));
            cubie.setCornerP(6, cubie.getCornerP(2));
            cubie.setCornerP(2, tempCornerP);

            //corner orientation
            int tempCornerO = cubie.getCornerO(3);
            cubie.setCornerO(3, (cubie.getCornerO(7) + 2) % 3);
            cubie.setCornerO(7, (cubie.getCornerO(6) + 1) % 3);
            cubie.setCornerO(6, (cubie.getCornerO(2) + 2) % 3);
            cubie.setCornerO(2, (tempCornerO + 1) % 3);

            //edge permutation
            int tempEdgeP = cubie.getEdgeP(3);
            cubie.setEdgeP(3, cubie.getEdgeP(10));
            cubie.setEdgeP(10, cubie.getEdgeP(7));
            cubie.setEdgeP(7, cubie.getEdgeP(11));
            cubie.setEdgeP(11, tempEdgeP);  

            //edge orientation
            int tempEdgeO = cubie.getEdgeO(3);
            cubie.setEdgeO(3, cubie.getEdgeO(10) ^ 1);
            cubie.setEdgeO(10, cubie.getEdgeO(7) ^ 1);
            cubie.setEdgeO(7, cubie.getEdgeO(11) ^ 1);
            cubie.setEdgeO(11, tempEdgeO ^ 1);
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
