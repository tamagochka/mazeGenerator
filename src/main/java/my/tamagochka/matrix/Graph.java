package my.tamagochka.matrix;

public class Graph {

    public static int[][] minPathsBetweenAllVertices(int[][] adj) {
        int[][] D = new int[adj.length][adj.length];
        for(int k = 0; k < adj.length; k++) {
            for(int i = 0; i < adj.length; i++) {
                for(int j = 0; j < adj.length; j++) {
                    if((k == j || adj[k][j] != 0) && (i == k || adj[i][k] != 0))
                        if(i != j && adj[i][j] == 0)
                            D[i][j] = Math.min(adj.length, adj[i][k] + adj[k][j]);
                        else
                            D[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
                }
            }
        }
        return D;
    }

    public static int[] minPathsBetweenVertices(int[][] adj, int toVertex) {
        int[] D = new int[adj.length];
        boolean[] visited = new boolean[adj.length];
        for(int i = 0; i < adj.length; i++)
            D[i] = adj[toVertex][i] == 0 ? adj.length : adj[toVertex][i];
        D[toVertex] = 0;
        int index = 0;
        for(int i = 0; i < adj.length; i++) {
            int min = adj.length;
            for(int j = 0; j < adj.length; j++) {
                if(!visited[j] && D[j] < min) {
                    min = D[j];
                    index = j;
                }
            }
            visited[index] = true;
            for(int j = 0; j < adj.length; j++) {
                if(!visited[j] && adj[index][j] != 0 && D[index] != adj.length && (D[index] + adj[index][j] < D[j]))
                    D[j] = D[index] + adj[index][j];
            }
        }
        return D;
    }

    public static int minPathBetweenTwoVertices(int[][] adj, int fromVertex, int toVertex) {
        return minPathsBetweenVertices(adj, fromVertex)[toVertex];
    }

    public static int[][] minPathsToVertexThroughVertices(int[][] adj, int toVertex) {
        int[][] D = new int[adj.length][adj.length];
        for(int i = 0; i < adj.length; i++) {
            int[] save = new int[adj.length];
            for(int j = 0; j < adj.length; j++) {
                save[j] = adj[i][j];
                adj[i][j] = 0;
                adj[j][i] = 0;
            }
            for(int j = 0; j < adj.length; j++) {
                if(save[j] != 0 && i != j)
                    D[i][j] = minPathBetweenTwoVertices(adj, j, toVertex) + 1;
                else
                    D[i][j] = -1;
                if(D[i][j] == adj.length + 1) D[i][j] = 0;
            }
            for(int j = 0; j < adj.length; j++) {
                adj[i][j] = save[j];
                adj[j][i] = save[j];
            }
        }
        return D;
    }

    public static int maxPathLengthBetweenVertices(int[][] adj, int fromVertex, int toVertex) {
        int[][] D = minPathsToVertexThroughVertices(adj, toVertex);
        int vertex = fromVertex;
        int pathLength = 0;
        boolean[] visited = new boolean[D.length];
        while(vertex != toVertex) {
            int max = 0;
            int index = 0;
            for(int i = 0; i < D.length; i++) {
                if(D[vertex][i] > max && !visited[i]) {
                    max = D[vertex][i];
                    index = i;
                }
            }
            visited[vertex] = true;
            pathLength++;
            vertex = index;
        }
        return pathLength;
    }

}
