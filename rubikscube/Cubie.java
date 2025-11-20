package rubikscube;

public class Cubie {
    private class Cubes {
        char[] colours; 

        Cubes(char first, char second, char third) {
            colours = new char[3];
            colours[0] = first;
            colours[1] = second;
            colours[2] = third;
        }

        Cubes(char first, char second) {
            colours = new char[2];
            colours[0] = first;
            colours[1] = second;
        }
    }

    //variables also should be private becuz oop stuff
    private Cubes[] cubies;  //for now

    private int[] cornerP;
    private int[] cornerO;
    private int[] edgeP;
    private int[] edgeO;

    Cubie(char[] facelets) {
        //20 in total 8 corners, 12 edges
        cubies = new Cubes[20];

        cornerP = new int[8];
        cornerO= new int[8];
        edgeP = new int[12];
        edgeO = new int[12];

        //first 8: corners  (BRUTE FORCE SET UP idk how to automate this lol) 
        //base of the corners order:  U R F D B L
        cubies[0] =  new Cubes(facelets[8], facelets[15], facelets[14]); //kociemba ordering starts with URF
        cubies[1] =  new Cubes(facelets[6], facelets[12], facelets[11]); //UFL 
        cubies[2] =  new Cubes(facelets[0], facelets[9], facelets[20]); //ULB
        cubies[3] =  new Cubes(facelets[2], facelets[18], facelets[17]); //UBR
        cubies[4] =  new Cubes(facelets[47], facelets[38], facelets[39]); //DFR
        cubies[5] =  new Cubes(facelets[45], facelets[35], facelets[36]); //DLF
        cubies[6] =  new Cubes(facelets[51], facelets[44], facelets[33]); //DBL
        cubies[7] =  new Cubes(facelets[53], facelets[41], facelets[42]); //DRB
        
        //edges 
        cubies[8] =  new Cubes(facelets[5], facelets[16]); //kociemba ordering starts with UR
        cubies[9] =  new Cubes(facelets[7], facelets[13]); //UF
        cubies[10] =  new Cubes(facelets[3], facelets[10]); //UL
        cubies[11] =  new Cubes(facelets[1], facelets[19]); //UB
        cubies[12] =  new Cubes(facelets[50], facelets[40]); //DR
        cubies[13] =  new Cubes(facelets[46], facelets[37]); //DF
        cubies[14] =  new Cubes(facelets[48], facelets[34]); //DL
        cubies[15] =  new Cubes(facelets[52], facelets[43]); //DB
        cubies[16] =  new Cubes(facelets[26], facelets[27]); //FR
        cubies[17] =  new Cubes(facelets[24], facelets[23]); //FL
        cubies[18] =  new Cubes(facelets[32], facelets[21]); //BL
        cubies[19] =  new Cubes(facelets[30], facelets[29]); //BR

        updateCornersAndEdges();
    }

    public Cubes[] getCubies() {
        return this.cubies;
    }

    private void updateCornersAndEdges() {
        //corner perm and corner orientation magic here
        for (int i = 0; i < 8; i++) {
            fillCornerP(i); //fills in the corner permutation
        }
        
        for (int i = 8; i < 20; i++) {
            fillEdgesP(i); //fills in the edge permutaiton
        }
    }

    //doing the permutation magic here:
    private void fillCornerP(int index) {
        char[] stuff = cubies[index].colours;
        int permOrder = getPermOrder(stuff);
        cornerP[index] = permOrder;
    }
    
    private int getPermOrder(char[] stuff) {
        // 1  -  2  -  3  - 4   - 5   - 6   - 7   - 8 
        //URF - UFL - ULB - UBR - DFR - DLF - DBL - DRB
        int num = -1;
        if ((stuff[0] == 'W' || stuff[1] == 'W' || stuff[2] == 'W') && (stuff[0] == 'O' || stuff[1] == 'O' || stuff[2] == 'O') && (stuff[0] == 'B' || stuff[1] == 'B' || stuff[2] == 'B')) num = 1;
        if ((stuff[0] == 'W' || stuff[1] == 'W' || stuff[2] == 'W')) num = 2; // continue this please 
        //continue from here
        return num;
    }

    private void fillEdgesP(int index) {
        char[] stuff = cubies[index].colours;
        int edgeOrder = getEdgeOrder(stuff);
        edgeP[index] = edgeOrder;
    }

    private int getEdgeOrder(char[] stuff) {
        //same idea as getPermOrder:
        //order to follow: 
        // 1 -  2 - 3  - 4  - 5  - 6  - 7  - 8  - 9  - 10 - 11 - 12
        //UR - UF - UL - UB - DR - DF - DL - DB - FR - FL - BL - BR
        int num = -1;
        if ((stuff[0] == 'O' || stuff[1] == 'W') && (stuff[0] == 'O' || stuff[1] == 'W')) num = 1;
        //continue from here please
        return num;
    }

    //orientiation magic part here
    









    
    //gonna fix this


    private String getCorrectForm() {
        String stateInit = //took this from TestRubiksCube.java/i can also just grab it from the file
                    "   OOO\n" + 
                    "   OOO\n" +
                    "   OOO\n" +
                    "GGGWWWBBBYYY\n" +
                    "GGGWWWBBBYYY\n" +
                    "GGGWWWBBBYYY\n" +
                    "   RRR\n" +
                    "   RRR\n" +
                    "   RRR\n";

        return stateInit;

    }
}
