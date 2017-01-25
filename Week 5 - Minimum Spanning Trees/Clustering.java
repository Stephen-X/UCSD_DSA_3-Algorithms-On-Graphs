import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Collections;

public class Clustering {

    // ----------- The following implements disjoint set ---------------
    private static int n;  // number of points
    private static int[] parent;  // parent of each point, or itself if it's root
    private static int[] rank;
    // height of the subtree rooted at point i; inaccurate (upper-bound) after path compression

    // this creates seperate sets for each point
    private static void makeSet() {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // find operation (returns the root of the set containing point i) with path compression
    private static int find(int i) {
        if (i != parent[i]) {  // point i is not root
            parent[i] = find(parent[i]);  // re-append point i to the root
        }
        return parent[i];
    }

    // this merges 2 sets containing point i and j
    private static void union(int i, int j) {
        int i_id = find(i);
        int j_id = find(j);
        if (i_id == j_id) return;  // i, j are in the same set
        if (rank[i_id] > rank[j_id]) {  // tree i is bigger than tree j
            parent[j_id] = i_id;  // ... append j tree to the bigger i tree
        } else {
            parent[i_id] = j_id;
            if (rank[i_id] == rank[j_id]) {
                rank[j_id]++;
            }
        }
    }

    // -----------------------------------------------------------------
    
    private static class Edge implements Comparable<Edge> {
        int index1;
        int index2;  // indices of two end points of an edge
        double length;

        public Edge(int index1, int index2, int[] x, int[] y) {
            this.index1 = index1;
            this.index2 = index2;
            length = Math.sqrt(Math.pow(x[index1] - x[index2], 2)
                             + Math.pow(y[index1] - y[index2], 2));
        }

        // edges are compared according to their lengths
        public int compareTo(Edge b) {
            double diff = length - b.length;
            if (diff > 0.) return 1;
            else if (diff < 0.) return -1;
            else return 0;
        }
    }

    // Idea: construct the MST using Kruskal's algorithm until there're k connected 
    //       components left, then add 1 more edge to the MST. The length of the 
    //       additional edge is the required shorest possible distance.
    private static double clustering(int[] x, int[] y, int k) {
        makeSet();  // this initializes disjoint sets
        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                edges.add(new Edge(i, j, x, y));
            }
        }

        int numberOfSets = n; // initiallly each point forms its own connected component
        PriorityQueue<Edge> mst = new PriorityQueue<Edge>(Collections.reverseOrder());
        // the minimum spanning tree stored in max-heap
        while (numberOfSets >= k) { // while there're more than k connected components 
            Edge e = edges.poll();
            if (find(e.index1) != find(e.index2)) {
                // the 2 end points are not in the same set: the edge doesn't produce a cycle
                mst.add(e);
                union(e.index1, e.index2);
                numberOfSets--; // connecting 2 points means eliminating 1 component
            }
        }

        return mst.poll().length; // the 1 additionally added edges is the shortest possible distance
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        System.out.println(clustering(x, y, k));
    }
}
