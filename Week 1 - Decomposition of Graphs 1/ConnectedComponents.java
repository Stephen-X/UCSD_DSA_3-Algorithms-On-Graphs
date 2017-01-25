import java.util.ArrayList;
import java.util.Scanner;

public class ConnectedComponents {
    private static boolean[] visited;

    private static void explore(ArrayList<Integer>[] adj, int v) {
        visited[v] = true;
        for (int next : adj[v]) {
            if (!visited[next])
                explore(adj, next);
        }
    }
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        visited = new boolean[adj.length];
        int cc_cont = 0;  // initialize count for connected components
        for (int i = 0; i < adj.length; i++) {
            if (!visited[i]) {
                explore(adj, i);
                cc_cont++;
            }
        }
        return cc_cont;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
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
        System.out.println(numberOfComponents(adj));
    }
}

