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

    Cubie(Cubie theCubie) {
        cornerO = theCubie.cornerO.clone();
        cornerP = theCubie.cornerP.clone();
        edgeO = theCubie.edgeO.clone();
        edgeP = theCubie.edgeP.clone();
    }

    Cubie(char[] facelets) {
        //20 in total 8 corners, 12 edges
        cubies = new Cubes[20];

        cornerP = new int[8];
        cornerO = new int[8];
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
        cornerO[index] = value;
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
        edgeO[index] = value;
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

        //orientation set up
        updateCornerOrientation(cubies[permOrder].colours, index);
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
		if ((stuff[0] == 'R' || stuff[1] == 'R' || stuff[2] == 'R') && (stuff[0] == 'G' || stuff[1] == 'G' || stuff[2] == 'G') && (stuff[0] == 'B' || stuff[1] == 'B' || stuff[2] == 'B')) num = 7; //DRB
        return num;
    }

    private void fillEdges(int index) {
        //permutation set up
        char[] stuff = cubies[index].colours;
        int edgeOrder = getPermEdgeOrder(stuff);
        edgeP[index - 8] = edgeOrder;

        //orientation set up
        //edge array starts at 8, soo 
        updateEdgeOrientation(cubies[edgeOrder + 8].colours, index - 8); //NOT SURE ABOUT THE first PARAMETER I DID THE MATH IN MY HEAD
    }

    private int getPermEdgeOrder(char[] stuff) {
        //same idea as getPermOrder:
        //order to follow: 
        //0 to 11 
        //UR - UF - UL - UB - DR - DF - DL - DB - FR - FL - BL - BR
        int num = -1;
        if ((stuff[0] == 'O' || stuff[1] == 'W') && (stuff[0] == 'O' || stuff[1] == 'W')) num = 0; //UR
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
        if (index == 0) { // we want to look at URF orientation 
            while (num < 3) { //URF
                if (array[num] != 'O') { //WE WANT TO SEE THE FIRST CHAR TO BE 'O' CUZ OBW order (URF)
                    num++;
                }
            }
            cornerO[index] = num;  //index currently represents the index at cornerO array and num represents the orientation
        }

        if (index == 1) { //UFL
            while (num < 3) {
                if (array[num] != 'O') { //WE WANT FIRST CHAR TO BE 'O' CUZ OWG order (UFL)
                    num++;
                }

            }
            cornerO[index] = num;
        }

        if (index == 2) { //ULB
            while (num < 3) {
                if (array[num] != 'O') {
                    num++;
                }
            }
            cornerO[index] = num;
        }

        if (index == 3) { //UBR
            while (num < 3) {
                if (array[num] != 'O') {
                    num++;
                }
            }
            cornerO[index] = num;
        }

        if (index == 4) {  //DFR
            while (num < 3) {
                if (array[num] != 'O') {
                    num++;
                }
            }
            cornerO[index] = num;
        }

        if (index == 5) {  //DLF
            while (num < 3) {
                if (array[num] != 'O') {
                    num++;
                }
            }
            cornerO[index] = num;
        }

        if (index == 6) { //DBL
            while (num < 3) {
                if (array[num] != 'O') {
                    num++;
                }
            }
            cornerO[index] = num;
        }

        if (index == 7) { //DRB
            while (num < 3) {
                if (array[num] != 'O') {
                    num++;
                }
            }
            cornerO[index] = num;
        }
    }

    private void updateEdgeOrientation(char[] array, int index) {
        //main problem is the index positioning because i put corner and edge in the same array lol

                //UR - UF - UL - UB - DR - DF - DL - DB - FR - FL - BL - BR  (((FOLLOW THIS ORDER)))

        //do this from 0 - 11 
        int num = 0;
        if (index == 0) { //UR 
            while (num < 2) { 
                if (array[num] != 'O' ) { //SO WE WANT FIRST CHAR TO BE 'O'
                    num++;
                }
            }
            edgeO[index] = num;
        }

        if (index == 1) { //UF
			while (num < 2) {
				if (array[num] != 'O' ) { //SO WE WANT FIRST CHAR TO BE 'O'
					num++;
				}
			}
			edgeO[index] = num;
		}

		if (index == 2) { //UL
			while (num < 2) {
				if (array[num] != 'O' ) { //SO WE WANT FIRST CHAR TO BE 'O'
					num++;
				}
			}
			edgeO[index] = num;
		}

        if (index == 3) { //UB
			while (num < 2) {
				if (array[num] != 'O' ) { //SO WE WANT FIRST CHAR TO BE 'O'
					num++;
				}
			}
			edgeO[index] = num;
		}

		if (index == 4) { //DR
			while (num < 2) {
				if (array[num] != 'R' ) { //SO WE WANT FIRST CHAR TO BE 'R'
					num++;
				}
			}
			edgeO[index] = num;
		}

		if (index == 5) { //DF
			while (num < 2) {
				if (array[num] != 'R' ) { //SO WE WANT FIRST CHAR TO BE 'R'
					num++;
				}
			}
			edgeO[index] = num;
		}

        if (index == 6) { //DL
			while (num < 2) {
				if (array[num] != 'R' ) { //SO WE WANT FIRST CHAR TO BE 'R'
					num++;
				}
			}
			edgeO[index] = num;
		}

		if (index == 7) { //DB
			while (num < 2) {
				if (array[num] != 'R' ) { //SO WE WANT FIRST CHAR TO BE 'R'
					num++;
				}
			}
			edgeO[index] = num;
		}

		if (index == 8) { //FR
			while (num < 2) {
				if (array[num] != 'W' ) { //SO WE WANT FIRST CHAR TO BE 'W'
					num++;
				}
			}
			edgeO[index] = num;
		}

        if (index == 9) { //FL
			while (num < 2) {
				if (array[num] != 'W' ) { //SO WE WANT FIRST CHAR TO BE 'W'
					num++;
				}
			}
			edgeO[index] = num;
		}

		if (index == 10) { //BL
			while (num < 2) {
				if (array[num] != 'Y' ) { //SO WE WANT FIRST CHAR TO BE 'Y'
					num++;
				}
			}
			edgeO[index] = num;
		}

		if (index == 11) { //BR
			while (num < 2) {
				if (array[num] != 'Y' ) { //SO WE WANT FIRST CHAR TO BE 'Y'
					num++;
				}
			}
			edgeO[index] = num;
		}
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
