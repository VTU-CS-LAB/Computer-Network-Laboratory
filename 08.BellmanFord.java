
/*
8. Write a program to find the shortest path between vertices using bellman-ford
algorithm.
*/

// Temporary Code

import java.util.Scanner;

class BellmanFord {

    static int n, dest;
    static double[] prevDistanceVector, distanceVector;
    static double[][] adjacencyMatrix;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of nodes");
        n = scanner.nextInt();
        adjacencyMatrix = new double[n][n];

        System.out.println("Enter Adjacency Matrix (Use 'Infinity' for No Link)");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                adjacencyMatrix[i][j] = scanner.nextDouble();

        System.out.println("Enter destination vertex");
        dest = scanner.nextInt();

        distanceVector = new double[n];
        for (int i = 0; i < n; i++)
            distanceVector[i] = Double.POSITIVE_INFINITY;
        distanceVector[dest - 1] = 0;

        bellmanFordAlgorithm();

        System.out.println("Distance Vector");
        for (int i = 0; i < n; i++)
            System.out.print(distanceVector[i] + " ");
        System.out.println();
    }

    static void bellmanFordAlgorithm() {
        for (int i = 0; i < n - 1; i++) {
            prevDistanceVector = distanceVector.clone();
            for (int j = 0; j < n; j++) {
                double min = Double.POSITIVE_INFINITY;
                for (int k = 0; k < n; k++) {
                    if (min > adjacencyMatrix[j][k] + prevDistanceVector[k]) {
                        min = adjacencyMatrix[j][k] + prevDistanceVector[k];
                    }
                }
                distanceVector[j] = min;
            }
        }
    }
}

// ///////
// Output:
//
// Enter number of nodes
// 6
// Enter Adjacency Matrix (Use 'Infinity' for No Link)
// 0 3 2 5 Infinity Infinity
// 3 0 Infinity 1 4 Infinity
// 2 Infinity 0 2 Infinity 1
// 5 1 2 0 3 Infinity
// Infinity 4 Infinity 3 0 2
// Infinity Infinity 1 Infinity 2 0
// Enter destination vertex
// 6
// Distance Vector
// 3.0 4.0 1.0 3.0 2.0 0.0