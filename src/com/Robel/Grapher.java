package com.Robel;

public class Grapher {

    int[][] adjacencyMatrix; //Adjacency Matrix
    int[] late; //Late Stsge array
    int[] early; //Early Stage Array
    int[] topological;  //Topological order array
    int[] predCounter; //Predecessor Count Array
    int[] numEdges; //NUmber of Edges in vertices


    RobelStack<Integer> stacker = new RobelStack<>();


    //Constructor
    Grapher(int[][] adjacency, int[] numEdges) {
        this.adjacencyMatrix = adjacency;
        this.numEdges = numEdges;
    }

    public int[] topologicalOrder(int[] stages) throws RobelEmptyStackException {

        int[] topArray = new int[stages.length];
        int counter = 0;
        int topValue = 0;

        for (int i = 0; i < numEdges.length; i++) {
            if (numEdges[i] == 0)
                stacker.push(i);
        }

        while (!(stacker.isEmpty())) {
            topValue = stacker.pop();
            topArray[counter] = stages[topValue];

            for (int i = 0; i < stages.length; i++) {
                if (adjacencyMatrix[topValue][i] > 0)
                    numEdges[i]--;

                if (numEdges[i] == 0)
                    stacker.push(i);
            }
            counter++;
        }
        return topArray;
    }

    public int[] EarlyStager(int[] top){
        int[] early = new int[top.length];

        //First V topological value = 0
        early[top[0]] = 0;

        for (int i = 0; i < top.length; i++) {
            int max = 0;
            int temp = 0;
            int loc = top[i];

            for (int j = 0; j < top.length-1; j++) {

                //If Matrix !Empty stores elapsed time in temp
                if(!(adjacencyMatrix[j][loc] == -1)){
                    temp = adjacencyMatrix[j][loc] + early[j];

                    if(max < temp)
                        max = temp;
                }
            }
            early[loc] = max;
        }
        return early;
    }














    public boolean feasibility(int[] top, int[] stages) {
        boolean feasibile = false;

        if (top.length == stages.length)
            feasibile = true;

        return feasibile;
    }






}



