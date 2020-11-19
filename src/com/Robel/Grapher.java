package com.Robel;

public class Grapher {

    int[][] adjacencyMatrix; //Adjacency Matrix
    int[] late; //Late Stsge array
    int[] early; //Early Stage Array
    int[] topological;  //Topological order array
    int[] predCounter; //Predecessor Count Array
    int[] numEdges; //NUmber of Edges in vertices
    int temp;


    RobelStack<Integer> stacker = new RobelStack<>();


    //Constructor
    Grapher(int[][] adjacency, int[] numEdges) {
        this.adjacencyMatrix = adjacency;
        this.numEdges = numEdges;
    }

    public boolean feasibility(int[] top, int[] stages) {
        boolean feasibile = false;

        if (top.length == stages.length)
            feasibile = true;

        return feasibile;
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
                if (adjacencyMatrix[topValue][i] > 0) {
                    numEdges[i]--;

                    if (numEdges[i] == 0)
                        stacker.push(i);
                }
            }
            counter++;
        }
        return topArray;
    }

    public int[] EarlyStager(int[] top, int[] stage){
        int[] early = new int[top.length];

        //First V topological value = 0
        early[top[0]] = 0;

        for (int i = 0; i < stage.length-1; i++) {
            int max = 0;
            int loc = top[i];

            for (int j = 0; j < stage.length; j++) {

                //If Matrix !Empty stores elapsed time in temp
                if(!(adjacencyMatrix[j][loc] == -1)){
                    temp = early[j] + adjacencyMatrix[j][loc];

                    if(max < temp)
                        max = temp;
                }
            }
            early[loc] = max;
        }
        return early;
    }

    //Finds LST: min of outgoing activities end and cost
    public int[] LateStager(int weight, int[] topologicalArr, int[] stage) {

        int[] late = new int[stage.length];
        int[] newTopological = backwards(topologicalArr);
        int min = weight;

        //For row
        for (int i = 0; i < stage.length; i++) {
            //For Column
            for (int j = 0; j < stage.length; j++) {
                if(!(adjacencyMatrix[newTopological[i]-1][j] == -1)) {
                    temp = late[j] - adjacencyMatrix[newTopological[i] - 1][j];

                    if(temp < min)
                        min = temp;


                }
            }//End of J

            late[newTopological[i]-1] = min;
            min = weight;

        }//End of I
        return late;

    }//End of LST


    //Array Reverser
    private int[] backwards(int[] topologicalArr) {
        int[] backwards = topologicalArr.clone();

        for (int i = 0; i < topologicalArr.length/2; i++) {
            int temp = backwards[i];
            backwards[i] = backwards[topologicalArr.length-1-i];
            backwards[topologicalArr.length-1-i] = temp;
        }
        return backwards;
    }

    //Est Finder
    public int[] earlyActivity(int counter, int stageCount, int[] est) {

        int[] earlyActivities = new int[counter];
        int count = 0;

        for (int i = 0; i < stageCount; i++) {
            for (int j = 0; j < stageCount; j++) {
                if(!(adjacencyMatrix[i][j] == -1)){
                    earlyActivities[count] = est[i];
                    count++;
                }
            }
        }
        return earlyActivities;
    }

    //LST Finder
    public int[] lateActivity(int[] lst, int stageCount, int activityCount) {

        int[] late = new int[activityCount];
        int count = 0;

        for (int i = 0; i < stageCount; i++) {
            for (int j = 0; j < stageCount; j++) {
                if(!(adjacencyMatrix[i][j] == -1)){
                    late[count] = lst[j] - adjacencyMatrix[i][j];
                    count++;
                }
            }
        }
        return late;
    }

    public String criticalActivity(int[] lateActivity, int[] earlyActivity) {
        StringBuilder activities = new StringBuilder();

        for (int i = 0; i < earlyActivity.length; i++) {
            if(lateActivity[i] - earlyActivity[i] == 0){
                activities.append(Integer.toString(i + 1)).append(" ");
            }
        }
        return activities.toString();

    }
}



