import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class ConnectingPoints {
    private static class Point implements Comparable<Point> {
        int index;  // index of current point
        double dist;  // distance to the previous point
        int parent;  // previous point
        int x;
        int y;

        public Point(int index, int x, int y) {
            this.index = index;
            dist = Double.MAX_VALUE;
            parent = -1;
            this.x = x;
            this.y = y;
        }

        // points are compared according to their dist values
        public int compareTo(Point b) {
            double diff = this.dist - b.dist;
            if (diff > 0.) return 1;
            else if (diff < 0.) return -1;
            else return 0;
        }
    }

    // Prim's Algorithm
    private static double minimumDistance(List<Point> points) {
        points.get(0).dist = 0.;  // set point 0 as starting point

        PriorityQueue<Point> pq = new PriorityQueue<Point>(points);
        double[][] weight = new double[points.size()][points.size()];

        while (!pq.isEmpty()) {
            Point v = pq.poll();
            for (int z = 0; z < points.size(); z++) {
                if (z == v.index) continue;
                if (weight[v.index][z] == 0.) { // length of {v, z} hasn't been calculated
                    weight[v.index][z] = Math.sqrt(Math.pow(v.x - points.get(z).x, 2)
                                                 + Math.pow(v.y - points.get(z).y, 2));
                    weight[z][v.index] = weight[v.index][z]; // graph is undirected
                }

                if (pq.contains(points.get(z)) && points.get(z).dist > weight[v.index][z]) {
                    points.get(z).dist = weight[v.index][z];
                    points.get(z).parent = v.index;
                    // change the priority of the new point z
                    pq.remove(points.get(z));
                    pq.add(points.get(z));
                }
            }
        }

        double result = 0.;
        for (Point v : points) {
            result += v.dist;
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Point> points = new ArrayList<Point>();
        for (int i = 0; i < n; i++) {
            points.add(new Point(i, scanner.nextInt(), scanner.nextInt()));
        }
        System.out.println(minimumDistance(points));
    }
}
