package Grafos;

public class AlgoritmoDijkstra {
    private int V; // N�mero de v�rtices no grafo
    private int[][] graph; // Matriz de adjac�ncia representando o grafo

    /**
     * Construtor para inicializar AlgoritmoDijkstra com o n�mero de v�rtices.
     */
    public AlgoritmoDijkstra(int vertices) {
        V = vertices;
        setGraph(new int[V][V]);
    }

    // Getters e Setters para a matriz do grafo
    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    /**
     * Encontra o v�rtice com o menor valor de dist�ncia do conjunto de v�rtices
     * que ainda n�o foram inclu�dos na �rvore do caminho mais curto.
     */
    private int minimumDistance(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int v = 0; v < V; v++) {
            if (!visited[v] && distances[v] <= minDistance) {
                minDistance = distances[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    /**
     * Imprime o caminho mais curto do n� de origem ao destino, juntamente com seu custo total.
     */
    private void printPath(int[] distances, int[] parent, int source, int destination) {
        System.out.print("Caminho m�nimo entre " + source + " e " + destination + ": ");
        int crawl = destination;
        System.out.print(crawl);
        while (parent[crawl] != -1) {
            System.out.print(" <- " + parent[crawl]);
            crawl = parent[crawl];
        }
        System.out.println("\nCusto total: " + distances[destination]);
    }
    
    /**
     * M�todo para visualizar o grafo.
     * Imprime uma representa��o visual do grafo, mostrando os v�rtices conectados com seus respectivos pesos.
     */
    public void visualizeGraph() {
        System.out.println("Visualiza��o do Grafo:");

        for (int i = 0; i < V; i++) {
            System.out.print(i + " -> ");
            for (int j = 0; j < V; j++) {
                if (getGraph()[i][j] != 0) {
                    System.out.print(j + "(" + getGraph()[i][j] + ") ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Algoritmo de Dijkstra para encontrar o caminho mais curto entre um n� de origem e um destino.
     */
    public void dijkstra(int source, int destination) {
        int[] distances = new int[V]; // Array para armazenar as dist�ncias a partir da origem
        boolean[] visited = new boolean[V]; // Array para marcar v�rtices visitados
        int[] parent = new int[V]; // Array para armazenar o n� pai de cada v�rtice no caminho mais curto

        // Inicializa os arrays
        for (int i = 0; i < V; i++) {
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
            parent[i] = -1;
        }

        distances[source] = 0; // A dist�ncia da origem para ela mesma � 0

        // Encontra o caminho mais curto para todos os v�rtices
        for (int count = 0; count < V - 1; count++) {
            int u = minimumDistance(distances, visited);
            visited[u] = true;

            // Atualiza distances[v] se n�o foi visitado, existe uma aresta de u para v,
            // e o peso total do caminho da origem at� v atrav�s de u � menor que a distances[v] atual
            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE &&
                        distances[u] + graph[u][v] < distances[v]) {
                    distances[v] = distances[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        printPath(distances, parent, source, destination); // Imprime o caminho mais curto
    }
    
}