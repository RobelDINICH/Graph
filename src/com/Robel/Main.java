/*
File or Class name: Main, Grapher
Program author(s): Robel Tadele
Course number and title: COSC2203, Data Structures Section 1
Assignment number and name: Can it be done? Comp B.
Submission date: Nov 19 2020
 */

package com.Robel;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws RobelEmptyStackException {

        Scanner reader = new Scanner(System.in);

        int numNodes, stages = 0, actCount = 0, outEdges = 0, edgesInCounter = 0, stageColumn = 0;
        int[][] adjacencyM;
        int[] stageValue, edgesIn, topologicalArr;

        //Array for edges going into a given Stage
        int[] earlyActivity, est;
        int[] lateActivity, lst;

        System.out.println("Welcome to the AOE Maker ");
        System.out.println("Enter values \n");
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

        for (stageColumn = 0; stageColumn < numNodes; stageColumn++) {
            edgesInCounter = 0;
            int m = stageColumn;

            //If value in matrix != -1 increase edges
            for (int i = 0; i < numNodes; i++) {
                if (adjacencyM[i][m] != -1) {
                    edgesInCounter++;
                    actCount++;
                }
                edgesIn[m] = edgesInCounter;
            }
        }

        //Grapher Object
        Grapher graph = new Grapher(edgesIn, adjacencyM);

        //Using Grapher Class to do all the calculations
        //Storing Topological Array
        topologicalArr = graph.topologicalOrdering(stageValue);

        //Setting Late Stage and Early Stage
        est = graph.earlyStager(topologicalArr, stageValue);
        lst = graph.lateStager(est[est.length - 1], topologicalArr, stageValue);

        earlyActivity = graph.earlyActivities(actCount, stageValue.length, est);
        lateActivity = graph.lateActivities(actCount, stageValue.length, lst);

        ArrayList<Integer> critical = graph.criticalActivities(lateActivity, earlyActivity);


        //Console Displayer
        if (graph.isFeasible()) {
            System.out.println("\nThe project is Feasible");

        }else {
            System.out.println("\nThe project is not feasible\nProgram Terminated :)");
            System.exit(0);
        }

        //Prints Topological Ordering
        System.out.print("Ordering: ");
        for (int j : topologicalArr) System.out.print(j + " ");

        System.out.println("\n\nStage      Early         Late");
        for (int i = 0; i < lst.length; i++) {
            if (i + 1 == topologicalArr[i])
                System.out.println(i + 1 + "            " + est[topologicalArr[i] - 1] + "            " + lst[topologicalArr[i] - 1]);
            else
                System.out.println(i + 1 + "            " + est[i] + "            " + lst[i]);
        }

        //Prints Total Project Tim
        System.out.println("\nTotal Projection Time: " + est[est.length - 1] + "\n");
        System.out.println("Activity    Early   Late");

        for (int i = 0; i < earlyActivity.length; i++)
            System.out.println(i + 1 + "            " + earlyActivity[i] + "       " + lateActivity[i]);

        //Prints Critical Activities

        System.out.println("\nCritical Activities: " + critical);

    }//End of Main Method
}//End of Main Class