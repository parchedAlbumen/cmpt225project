package rubikscube;

public class Movements {
    public static void doMoves(Cubie cubie, char movement) {
        //FURDLB | do magic here
        if (movement == 'F') {
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
