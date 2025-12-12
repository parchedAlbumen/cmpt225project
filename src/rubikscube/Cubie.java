package src.rubikscube;

import java.util.*;

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
    
    //default constructor (basically just show the solved cube)
    public Cubie() {
        cornerP = new int[8];
        cornerO = new int[8];
        edgeP = new int[12];
        edgeO = new int[12];

        for (int i = 0; i < 8; i++) {
            cornerO[i] = 0;
            cornerP[i] = i;
        }
        
        for (int i = 0; i < 12; i++) {
            edgeO[i] = 0;
            edgeP[i] = i;
        }
    }

    //copy constructor
    public Cubie(Cubie theCubie) {
        cornerO = theCubie.cornerO.clone();
        cornerP = theCubie.cornerP.clone();
        edgeO = theCubie.edgeO.clone();
        edgeP = theCubie.edgeP.clone();
    }

    //transform facelets into cubies
    public Cubie(char[] facelets) {
        this(); //start with default

        if (facelets == null || facelets.length < 54) {
            System.out.print("BAD FACELETS!");
            return; //we dont want it to continue
        }

        updateCorners(facelets);
        updateEdges(facelets);
        doOrientationValidation(); //we want it so that orientations are within bounds

        // //20 in total 8 corners, 12 edges
        // cubies = new Cubes[20];

        // cornerP = new int[8];
        // cornerO = new int[8];
        // edgeP = new int[12];
        // edgeO = new int[12];

        // //first 8: corners  (BRUTE FORCE SET UP idk how to automate this lol) 
        // //base of the corners order:  U R F D B L
        // cubies[0] =  new Cubes(facelets[8], facelets[15], facelets[14]); //kociemba ordering starts with URF
        // cubies[1] =  new Cubes(facelets[6], facelets[12], facelets[11]); //UFL 
        // cubies[2] =  new Cubes(facelets[0], facelets[9], facelets[20]); //ULB
        // cubies[3] =  new Cubes(facelets[2], facelets[18], facelets[17]); //UBR
        // cubies[4] =  new Cubes(facelets[47], facelets[38], facelets[39]); //DFR
        // cubies[5] =  new Cubes(facelets[45], facelets[35], facelets[36]); //DLF
        // cubies[6] =  new Cubes(facelets[51], facelets[44], facelets[33]); //DBL
        // cubies[7] =  new Cubes(facelets[53], facelets[41], facelets[42]); //DRB
        
        // //edges 
        // cubies[8] =  new Cubes(facelets[5], facelets[16]); //kociemba ordering starts with UR
        // cubies[9] =  new Cubes(facelets[7], facelets[13]); //UF
        // cubies[10] =  new Cubes(facelets[3], facelets[10]); //UL
        // cubies[11] =  new Cubes(facelets[1], facelets[19]); //UB
        // cubies[12] =  new Cubes(facelets[50], facelets[40]); //DR
        // cubies[13] =  new Cubes(facelets[46], facelets[37]); //DF
        // cubies[14] =  new Cubes(facelets[48], facelets[34]); //DL
        // cubies[15] =  new Cubes(facelets[52], facelets[43]); //DB
        // cubies[16] =  new Cubes(facelets[26], facelets[27]); //FR
        // cubies[17] =  new Cubes(facelets[24], facelets[23]); //FL
        // cubies[18] =  new Cubes(facelets[32], facelets[21]); //BL
        // cubies[19] =  new Cubes(facelets[30], facelets[29]); //BR

        // updateCornersAndEdges();
    }

    private void updateCorners(char[] facelets) {
        char[][] solvedCorners = {
            {'O', 'B', 'W'}, //based on getPermCornerOrder for URF,, the colours are OBW
            {'O', 'W', 'G'}, //follows the getPermCornerORder pattern
            {'O', 'G', 'Y'},
            {'O', 'Y', 'B'},
            {'R', 'W', 'B'},
            {'R', 'G', 'W'},
            {'R', 'Y', 'G'},
            {'R', 'B', 'Y'},
        };

        for (int i = 0; i < 8; i++) {
            char[] faceletsColours = getCornerColours(i, facelets); //just grabs the real colours from the facelet

            //same thing with what we did in getPermCorner, but more simplified
            for (int j = 0; j < 8; j++) {
                if (isCornerMatching(faceletsColours, solvedCorners[j])) { //if we found the right thing
                    cornerP[i] = j;
                    cornerO[i] = getCornerOrientation(faceletsColours, solvedCorners[j]);
                    break; //end it here
                }
            }
        }
    }

    private boolean isCornerMatching(char[] scrambled, char[] solved) {
        char[] scrambledClone = scrambled.clone();
        char[] solvedClone = solved.clone();
        Arrays.sort(scrambledClone);  //sort them because we want it so that they are in proper order when comparing
        Arrays.sort(solvedClone);
        return Arrays.equals(scrambledClone, solvedClone);
    }

    private char[] getCornerColours(int index , char[] facelets) {
        if (index == 0) {
            return new char[]{facelets[8], facelets[15], facelets[14]};
        } else if (index == 1) {
            return new char[]{facelets[6], facelets[12], facelets[11]};
        } else if (index == 2) {
            return new char[]{facelets[0], facelets[9], facelets[20]};
        } else if (index == 3) {
            return new char[]{facelets[2], facelets[18], facelets[17]};
        } else if (index == 4) {
            return new char[]{facelets[47], facelets[38], facelets[39]};
        } else if (index == 5) {
            return new char[]{facelets[45], facelets[35], facelets[36]};
        } else if (index == 6) {
            return new char[]{facelets[51], facelets[44], facelets[33]};
        } else if (index == 7) {
            return new char[]{facelets[53], facelets[41], facelets[42]};
        } else {
            return new char[3]; //because bad index value
        }
    }

    //what we basically did for updating the corner orientation before,,, its commented out
    private int getCornerOrientation(char[] scrambled, char[] solved) {
        char realColour = solved[0];

        for (int i = 0; i < 3; i++) {
            if (scrambled[i] == realColour) {
                if (i == 0) return 0; //GOOD cuz right orientation
                if (i == 1) return 2; //counter clockwise
                if (i == 2) return 1; //because clockwise 
            }
        }

        return 0; //default
    }

    private void updateEdges(char[] facelets) {
        //same thing that we did for getting the permutation orders for edges originally, but much cleaner
        char[][] solvedEdges = {
            {'O', 'B'},  //for UR its orange and blue 
            {'O', 'W'}, 
            {'O', 'G'}, 
            {'O', 'Y'}, 
            {'R', 'B'}, 
            {'R', 'W'}, 
            {'R', 'G'}, 
            {'R', 'Y'}, 
            {'W', 'B'}, 
            {'W', 'G'}, 
            {'Y', 'G'}, 
            {'Y', 'B'}, 
        }; 

        // do something similar just like updateCorners
        for (int i = 0; i < 12; i++) {
            char[] actualColours = getEdgeColours(i , facelets); //get right colours from the facelets

            for (int j = 0; j < 12; j++) {
                if (isEdgeMatching(actualColours, solvedEdges[j])) {
                    edgeP[i] = j;
                    edgeO[i] = getEdgeOrientation(actualColours, solvedEdges[j]);
                    break;
                }
            }
        }
    }

    private boolean isEdgeMatching(char[] scrambled, char[] solved) {
        //can only be flipped so
        if ((scrambled[0] == solved[0] && scrambled[1] == solved[1]) || (scrambled[0] == solved[1] && scrambled[1] == solved[0])) {
            return true;
        }
        return false; //not found
    }

    private char[] getEdgeColours(int index, char[] facelets) {
        if (index == 0) {
            return new char[]{facelets[5], facelets[16]}; //following the same pattern so this would be UR for the real colour
        } else if (index == 1) {
            return new char[]{facelets[7], facelets[13]};
        } else if (index == 2) {
            return new char[]{facelets[3], facelets[10]};
        } else if (index == 3) {
            return new char[]{facelets[1], facelets[19]};
        } else if (index == 4) {
            return new char[]{facelets[50], facelets[40]};
        } else if (index == 5) {
            return new char[]{facelets[46], facelets[37]};
        } else if (index == 6) {
            return new char[]{facelets[48], facelets[34]};
        } else if (index == 7) {
            return new char[]{facelets[52], facelets[43]};
        } else if (index == 8) {
            return new char[]{facelets[26], facelets[27]};
        } else if (index == 9) {
            return new char[]{facelets[24], facelets[23]};
        } else if (index == 10) {
            return new char[]{facelets[32], facelets[21]};
        } else if (index == 11) {
            return new char[]{facelets[30], facelets[29]};
        } else {
            return new char[2]; //because bad index 
        }
    }

    private int getEdgeOrientation(char[] scrambled, char[] solved) {
        if (scrambled[0] == solved[0] && scrambled[1] == solved[1]) {
            return 0; //clearly because right orientation
        }
        return 1;
    }

    private void doOrientationValidation() {
        for (int i = 0; i < 8; i++) {
            cornerO[i] = Math.abs(cornerO[i] % 3); //makes sure it stays in 0 - 2 strictly
        }

        for (int i = 0; i < 12; i++) {
            edgeO[i] = Math.abs(edgeO[i] % 2);
        }
    }

    public Cubes[] getCubies() {
        return this.cubies;
    }

    public int getCornerP(int index) {
        return cornerP[index];
    }

    public void setCornerP(int index, int value) {
        cornerP[index] = value;
    }

    public int getCornerO(int index) {
        return cornerO[index];
    }

    public void setCornerO(int index, int value) {
        cornerO[index] = value % 3; // we want to make sure orientation is only from 0 to 2 
        // if (cornerO[index] < 0) {
        //     cornerO[index] = 2; //WATCH 
        // }
    }

    public int getEdgeP(int index) {
        return edgeP[index];
    }

    public void setEdgeP(int index, int value) {
        edgeP[index] = value;
    }

    public int getEdgeO(int index) {
        return edgeO[index];
    }

    public void setEdgeO(int index, int value) {
        edgeO[index] = value % 2; //we want to make sure it edges orientation is only 0 to 1
        // if (edgeO[index] < 0) {
        //     edgeO[index] = 1 //WATCH
        // }
    }

    private void updateCornersAndEdges() {
        //corner perm and corner orientation magic here
        for (int i = 0; i < 8; i++) {
            fillCorner(i); //fills in the corner permutation
        }
        
        for (int i = 8; i < 20; i++) {
            fillEdges(i); //fills in the edge permutaiton
        }
    }

    private void fillCorner(int index) {
        //permutation set up
        char[] stuff = cubies[index].colours;
        int permOrder = getPermCornerOrder(stuff); //
        cornerP[index] = permOrder;
        
        if (permOrder == -1) {
            System.out.println("doesnt match anything here");
            throw new IllegalStateException("bad");
        }

        //orientation set up
        updateCornerOrientation(cubies[index].colours, index);
    }
    
    private int getPermCornerOrder(char[] stuff) {
        //  0 - 1   - 2   -  3  - 4   - 5   - 6   - 7
        //URF - UFL - ULB - UBR - DFR - DLF - DBL - DRB
        int num = -1;
        if ((stuff[0] == 'W' || stuff[1] == 'W' || stuff[2] == 'W') && (stuff[0] == 'O' || stuff[1] == 'O' || stuff[2] == 'O') && (stuff[0] == 'B' || stuff[1] == 'B' || stuff[2] == 'B')) num = 0; //URF
        if ((stuff[0] == 'O' || stuff[1] == 'O' || stuff[2] == 'O') && (stuff[0] == 'W' || stuff[1] == 'W' || stuff[2] == 'W') && (stuff[0] == 'G' || stuff[1] == 'G' || stuff[2] == 'G')) num = 1; //UFL
        if ((stuff[0] == 'O' || stuff[1] == 'O' || stuff[2] == 'O') && (stuff[0] == 'G' || stuff[1] == 'G' || stuff[2] == 'G') && (stuff[0] == 'Y' || stuff[1] == 'Y' || stuff[2] == 'Y')) num = 2; //ULB
        if ((stuff[0] == 'O' || stuff[1] == 'O' || stuff[2] == 'O') && (stuff[0] == 'Y' || stuff[1] == 'Y' || stuff[2] == 'Y') && (stuff[0] == 'B' || stuff[1] == 'B' || stuff[2] == 'B')) num = 3; //UBR
        if ((stuff[0] == 'R' || stuff[1] == 'R' || stuff[2] == 'R') && (stuff[0] == 'W' || stuff[1] == 'W' || stuff[2] == 'W') && (stuff[0] == 'B' || stuff[1] == 'B' || stuff[2] == 'B')) num = 4; //DFR
        if ((stuff[0] == 'R' || stuff[1] == 'R' || stuff[2] == 'R') && (stuff[0] == 'G' || stuff[1] == 'G' || stuff[2] == 'G') && (stuff[0] == 'W' || stuff[1] == 'W' || stuff[2] == 'W')) num = 5; //DLF
		if ((stuff[0] == 'R' || stuff[1] == 'R' || stuff[2] == 'R') && (stuff[0] == 'G' || stuff[1] == 'G' || stuff[2] == 'G') && (stuff[0] == 'Y' || stuff[1] == 'Y' || stuff[2] == 'Y')) num = 6; //DBL 
		if ((stuff[0] == 'R' || stuff[1] == 'R' || stuff[2] == 'R') && (stuff[0] == 'Y' || stuff[1] == 'Y' || stuff[2] == 'Y') && (stuff[0] == 'B' || stuff[1] == 'B' || stuff[2] == 'B')) num = 7; //DRB
        return num;
    }

    private void fillEdges(int index) {
        //permutation set up
        char[] stuff = cubies[index].colours;
        int edgeOrder = getPermEdgeOrder(stuff);
        edgeP[index - 8] = edgeOrder;

        if (edgeOrder == -1) {
            System.out.println("BAD");
            throw new IllegalStateException("BAD BAD");
        }

        //orientation set up
        //edge array starts at 8, soo 
        updateEdgeOrientation(cubies[index].colours, index - 8); //NOT SURE ABOUT THE first PARAMETER I DID THE MATH IN MY HEAD
    }

    private int getPermEdgeOrder(char[] stuff) {
        //same idea as getPermOrder:
        //order to follow: 
        //0 to 11 
        //UR - UF - UL - UB - DR - DF - DL - DB - FR - FL - BL - BR
        int num = -1;
        if ((stuff[0] == 'O' || stuff[1] == 'O') && (stuff[0] == 'B' || stuff[1] == 'B')) num = 0; //UR
        if ((stuff[0] == 'O' || stuff[1] == 'O') && (stuff[0] == 'W' || stuff[1] == 'W')) num = 1; //UF
		if ((stuff[0] == 'O' || stuff[1] == 'O') && (stuff[0] == 'G' || stuff[1] == 'G')) num = 2; //UL
		if ((stuff[0] == 'O' || stuff[1] == 'O') && (stuff[0] == 'Y' || stuff[1] == 'Y')) num = 3; //UB
		if ((stuff[0] == 'R' || stuff[1] == 'R') && (stuff[0] == 'B' || stuff[1] == 'B')) num = 4; //DR
		if ((stuff[0] == 'R' || stuff[1] == 'R') && (stuff[0] == 'W' || stuff[1] == 'W')) num = 5; //DF
		if ((stuff[0] == 'R' || stuff[1] == 'R') && (stuff[0] == 'G' || stuff[1] == 'G')) num = 6; //DL
		if ((stuff[0] == 'R' || stuff[1] == 'R') && (stuff[0] == 'Y' || stuff[1] == 'Y')) num = 7; //DB
		if ((stuff[0] == 'W' || stuff[1] == 'W') && (stuff[0] == 'B' || stuff[1] == 'B')) num = 8; //FR
		if ((stuff[0] == 'W' || stuff[1] == 'W') && (stuff[0] == 'G' || stuff[1] == 'G')) num = 9; //FL
		if ((stuff[0] == 'Y' || stuff[1] == 'Y') && (stuff[0] == 'G' || stuff[1] == 'G')) num = 10; //BL
		if ((stuff[0] == 'Y' || stuff[1] == 'Y') && (stuff[0] == 'B' || stuff[1] == 'B')) num = 11; //BR
        return num;
    }

    private void updateCornerOrientation(char[] array, int index) {
        //right now this the actual array of URF, index is current pos in cornerO array

                //URF - UFL - ULB - UBR - DFR - DLF - DBL - DRB  ((( FOLLOW THIS ORDER )))

        int num = 0;  
        if (index == 0 || index == 1 || index == 2 || index == 3) { //facing the up face
            while (num < 3 && array[num] != 'O' ) {
                num++;
            }
        } else {
            while (num < 3 && array[num] != 'R') { //facing the bottom face
                num++;
            }
        }
        cornerO[index] = num;

        // if (index == 0) { // we want to look at URF orientation 
        //     while (num < 3) { //URF
        //         if (array[num] != 'O') { //WE WANT TO SEE THE FIRST CHAR TO BE 'O' CUZ OBW order (URF)
        //             num++;
        //         }
        //     }
        //     cornerO[index] = num;  //index currently represents the index at cornerO array and num represents the orientation
        // }

        // if (index == 1) { //UFL
        //     while (num < 3) {
        //         if (array[num] != 'O') { //WE WANT FIRST CHAR TO BE 'O' CUZ OWG order (UFL)
        //             num++;
        //         }

        //     }
        //     cornerO[index] = num;
        // }

        // if (index == 2) { //ULB
        //     while (num < 3) {
        //         if (array[num] != 'O') {
        //             num++;
        //         }
        //     }
        //     cornerO[index] = num;
        // }

        // if (index == 3) { //UBR
        //     while (num < 3) {
        //         if (array[num] != 'O') {
        //             num++;
        //         }
        //     }
        //     cornerO[index] = num;
        // }

        // if (index == 4) {  //DFR
        //     while (num < 3) {
        //         if (array[num] != 'O') {
        //             num++;
        //         }
        //     }
        //     cornerO[index] = num;
        // }

        // if (index == 5) {  //DLF
        //     while (num < 3) {
        //         if (array[num] != 'O') {
        //             num++;
        //         }
        //     }
        //     cornerO[index] = num;
        // }

        // if (index == 6) { //DBL
        //     while (num < 3) {
        //         if (array[num] != 'O') {
        //             num++;
        //         }
        //     }
        //     cornerO[index] = num;
        // }

        // if (index == 7) { //DRB
        //     while (num < 3) {
        //         if (array[num] != 'O') {
        //             num++;
        //         }
        //     }
        //     cornerO[index] = num;
        // }
    }

    private void updateEdgeOrientation(char[] array, int index) {
        //main problem is the index positioning because i put corner and edge in the same array lol

                //UR - UF - UL - UB - DR - DF - DL - DB - FR - FL - BL - BR  (((FOLLOW THIS ORDER)))

        //do this from 0 - 11 
        int num = 0;
        if (index == 0 || index == 1 || index == 2 || index == 3) {
            while (num < 2 && array[num] != 'O') {
                num++;
            }
        } else if (index == 4 || index == 5 || index == 6 || index == 7) {
            while (num < 2 && array[num] != 'R') {
                num++;
            }
        } else if (index == 8 || index == 9) {
            while (num < 2 && array[num] != 'W') {
                num++;
            }
        } else {
            while (num < 2 && array[num] != 'Y') {
                num++;
            }
        }

        edgeO[index] = num;
    }

    public boolean isSolved() {
        //check corner perm
        for (int i = 0; i < 8; i++) {
            if (this.cornerP[i] != i) {
                return false;
            } 

            if (this.cornerO[i] != 0) {
                return false;
            }
        }

        for (int i = 0; i < 12; i++) {
            if (this.edgeP[i] != i) {
                return false;
            }

            if (this.edgeO[i] != 0) {
                return false;
            }
        }
        return true; //meets everything!
    }

    // private String getCorrectForm() {
    //     String stateInit = //took this from TestRubiksCube.java/i can also just grab it from the file
    //                 "   OOO\n" + 
    //                 "   OOO\n" +
    //                 "   OOO\n" +
    //                 "GGGWWWBBBYYY\n" +
    //                 "GGGWWWBBBYYY\n" +  
    //                 "GGGWWWBBBYYY\n" +
    //                 "   RRR\n" +
    //                 "   RRR\n" +
    //                 "   RRR\n";

    //     return stateInit;
    // }

    public boolean isPhase1Good() {
        //basically only look at the orientations for now:
        for (int i = 0; i < 8; i++) {
            if (this.cornerO[i] != 0) {
                return false;
            }
        }

        for (int i = 0; i < 12; i++) {
            if (this.edgeO[i] != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cubie other)) return false;

        return Arrays.equals(cornerP, other.cornerP) &&
            Arrays.equals(cornerO, other.cornerO) &&
            Arrays.equals(edgeP,   other.edgeP) &&
            Arrays.equals(edgeO,   other.edgeO);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(cornerP);
        result = 31 * result + Arrays.hashCode(cornerO);
        result = 31 * result + Arrays.hashCode(edgeP);
        result = 31 * result + Arrays.hashCode(edgeO);
        return result;
    }
}
