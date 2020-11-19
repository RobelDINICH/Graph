package com.Robel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws RobelEmptyStackException {


        Scanner reader = new Scanner(System.in);

        int numNodes = 0, stages = 0, outEdges = 0, edgesInCounter = 0, stageColumn = 0;
        boolean feasiblity = false;
        int[][] adjacencyM;
        int[] stageValue;
        int[] edgesIn;
        int[] topologicalArr;
        
        int[] earlyActivity;
        int[] est;
        int[] lateActivity;
        int[] lst;
        

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
        
        //Setting Late Stage and Early Stage
        est = graph.EarlyStager(topologicalArr, stageValue);
        lst = graph.LateStager(est[est.length-1], topologicalArr, stageValue);

        //Checking Graph Visibility
        feasiblity = graph.feasibility(topologicalArr, stageValue);

        earlyActivity = graph.earlyActivity(7,5,est);
        lateActivity = graph.lateActivity(lst,5,7);
        String critical = graph.criticalActivity(lateActivity, earlyActivity);

        //Console Displayer

        if(feasiblity)
            System.out.println("The project is Feasible");
        else{
            System.out.println("The project is not feasible");
            System.exit(0);
        }

        System.out.println("Ordering");
        for (int j : topologicalArr) System.out.println(j + " ");


        System.out.println("\n\nStage     Early     Late");
        for (int i = 0; i < lst.length; i++) {
            if(i+1 == topologicalArr[i])
                System.out.println(i+1 + "     " + est[topologicalArr[i]-1] + "     " + lst[topologicalArr[i]-1]);
            else
                System.out.println(i+1 + "     " + est[i] + "     " + lst[i]);
        }

        System.out.println("\nTotal Projection Time: " + est[est.length-1] + "\n");
        System.out.println("Activity     Early     Late");

        for (int i = 0; i < earlyActivity.length; i++)
            System.out.println(i+1 + "     " + earlyActivity[i] + "     " + lateActivity[i]);


        System.out.println("\nCritical Activities: " + critical);

    }
}
