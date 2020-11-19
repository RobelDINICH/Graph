package com.Robel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws RobelEmptyStackException {


        Scanner reader = new Scanner(System.in);

        int numNodes = 0, stages = 0, outEdges = 0, edgesInCounter = 0, stageColumn = 0;

        int[][] adjacencyM;
        int[] stageValue;
        int[] edgesIn;
        int[] topologicalArr;
        int[] est;
        boolean feasiblity = false;

        System.out.println("Welcome to the AOE Maker ");
        System.out.println("Enter values ");
        numNodes = reader.nextInt();  //reads in number of Vertices

        //Storing Vertices
        stageValue = new int[numNodes];

        //Adjacency Matrix
        adjacencyM = new int[numNodes][numNodes];
        edgesIn = new int[numNodes];

        //Initializes Adjacency Matrix with -1
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                adjacencyM[i][j] = -1;
            }
        }//End of For

        //For stages
        for (int i = 0; i < numNodes; i++) {
            stageValue[i] = reader.nextInt();
            outEdges = reader.nextInt();

            //For edges
            for (int j = 0; j < outEdges; j++) {
                stageColumn = reader.nextInt();
                adjacencyM[i][stageColumn - 1] = reader.nextInt();
            }//End of edge loop
        }//End of Stage Loop

        for (stageColumn = 0; stageColumn < numNodes ; stageColumn++) {
            edgesInCounter = 0;
            int m = stageColumn;

            //If value in matrix != -1 increase edges
            for (int i = 0; i < numNodes; i++) {
                if(!(adjacencyM[i][m] == -1)){
                    edgesInCounter++;

                }
            }
            edgesIn[m] = edgesInCounter;
        }

        Grapher graph = new Grapher(adjacencyM, edgesIn);
        topologicalArr = graph.topologicalOrder(stageValue);
        est = graph.EarlyStager(topologicalArr);

        //Checking Graph Visibility
        feasiblity = graph.feasibility(topologicalArr, stageValue);


        //Console Displayer

        if(feasiblity)
            System.out.println("The project is Feasible");
        else{
            System.out.println("The project is not feasible");
            System.exit(0);
        }

        System.out.println("Ordering");
        for (int i = 0; i < topologicalArr.length; i++) {
            System.out.println(topologicalArr[i] + est[topologicalArr[i]-1]);
        }

        System.out.println("\n\nStage       Early           Late");
        for (int i = 0; i < est.length; i++) {
            System.out.println(i+1 + " " + est[topologicalArr[i]-1]);
        }

    }
}
