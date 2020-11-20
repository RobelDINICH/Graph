package com.Robel;

import java.util.ArrayList;

class Grapher {

    RobelStack<Integer> myStack = new RobelStack<>();
    boolean feasibility;
    int[] inCount;
    int[][] adjMatrix;

    //Constructor
    public Grapher(int[] inCount, int[][] adjMatrix) {
        this.inCount = inCount;
        this.adjMatrix = adjMatrix;
        feasibility = false;
    }

    //Finds the Topological Ordering of the Graph
    public int[] topologicalOrdering(int[] points) throws RobelEmptyStackException {

        int top = 0;
        int counter = 0;
        int[] topological = new int[points.length];

        for(int i = 0; i < inCount.length; i++) {
            if(inCount[i] == 0)
                myStack.push(i);
        }

        while(!myStack.isEmpty()) {

            top = myStack.pop();
            topological[counter] = points[top];

            for(int j = 0; j < adjMatrix[top].length; j++) {
                if(adjMatrix[top][j] > 0) {
                    inCount[j]--;
                    if(inCount[j] == 0)
                        myStack.push(j);
                }
            }
            counter++;
            //Project is Feasible
            feasibility = true;
        }
        //feasibility = true;
        return topological;
    }

    //Early Stage Finder
    public int[] earlyStager(int[] point , int[] topological) {

        //EST array
        int[] est = new int[point.length];

        //Possible paths Holder
        int[] paths = new int[point.length];

        int index = 0;
        int max = 0;

        //For Columns
        for(int j = 0; j < point.length; j++) {
            //For rows
            for(int i = 0; i < point.length; i++) {
                if(adjMatrix[i][topological[j] - 1] > 0) {
                    paths[index] = est[i] + adjMatrix[i][topological[j] - 1];
                    index++;
                }
            }

            for(int k = 0; k < index; k++) {
                if(paths[k] > max)
                    max = paths[k];
            }
            est[topological[j] - 1] = max;

            //resetting for new column
            index = 0;
            max = 0;
        }
        return est;
    }

    //Finds Early Activity Time
    public int[] earlyActivities(int activityCount , int nodeCount , int[] est) {

        int[] earlyA = new int[activityCount];
        int counter = 0;

        for(int i = 0; i < nodeCount; i++) {
            for(int j = 0; j < nodeCount; j++) {
                if(adjMatrix[i][j] > 0) {
                    earlyA[counter] = est[i];
                    counter++;
                }
            }
        }
        return earlyA;
    }

    //Late Stage  finder
    public int[] lateStager(int totalProjectTime, int[] nodes, int[] topological) {

        int[] lst = new int[nodes.length];

        //Reversing Topological
        reverser(topological);

        //Possible Paths holder array
        int[] paths = new int[nodes.length];

        int index = 0;
        int min = totalProjectTime;

        //For Rows of adjMatrix
        for(int i = 0; i < nodes.length; i++) {

            //For Column of adjMatrix
            for(int j = 0; j < nodes.length; j++) {

                if(adjMatrix[topological[i] - 1][j] > 0) {
                    paths[index] = lst[j] - adjMatrix[topological[i] - 1][j];
                    index++;
                }
            }

            //determining the minimum cost path:
            for(int k = 0; k < index; k++) {
                if(paths[k] < min && paths[k] >= 0) {
                    min = paths[k];
                }
            }

            lst[topological[i] - 1] = min;

            //reset index and min to 0 for next column:
            index = 0;
            min = totalProjectTime;
        }
        return lst;
    }

    //Finds Late Activity Time
    public int[] lateActivities(int activityCount, int nodeCount, int[] LST) {

        int[] LA = new int[activityCount];
        int counter = 0;

        for(int i = 0; i < nodeCount; i++) {
            for(int j = 0; j < nodeCount; j++) {
                if(adjMatrix[i][j] > 0) {
                    LA[counter] = LST[j] - adjMatrix[i][j];
                    counter++;
                }
            }
        }
        return LA;
    }

    //Critical Activities Finder
    public ArrayList<Integer> criticalActivities(int[] EAT , int[] LAT) {

        ArrayList<Integer> crit = new ArrayList<>();
        for(int i = 0; i < EAT.length; i++) {
            if(LAT[i] - EAT[i] == 0)
                crit.add(i+1);
        }
        return crit;
    }

    //Reverses Array
    public void reverser(int[] array) {
        for(int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    //Feasibility Checker
    boolean isFeasible() {
        return feasibility;
    }
} //End of Grapher