import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {
    private static boolean[] visited;

    private static void explore(ArrayList<Integer>[] adj, int x, int y) {
        visited[x] = true;
        if (x == y) return;
        for (int next : adj[x]) {
            if (!visited[next])
                explore(adj, next, y);
        }
    }

    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        visited = new boolean[adj.length];  // all initialized to false
        explore(adj, x, y);
        if (visited[y])
            return 1;
        else
            return 0;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();  // vertices #
        int m = scanner.nextInt();  // edges #
        //List<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
        // the above expression preferred by Java; see:
        // http://docs.oracle.com/javase/tutorial/extra/generics/fineprint.html
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }
}

