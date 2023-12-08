package Grafos;

public class Main {

	public static void main(String[] args) {
		
		
		boolean direcionado = false; 
		boolean ponderado = false;
		
		Grafo<Integer> grafo = Grafo.criarGrafo(direcionado, ponderado);
		
		System.out.println("\nGrafo é conexo? " + grafo.isConexo());
		System.out.println("\n" + grafo.isEuleriano());
		System.out.println("\n" + grafo.isHamiltoniano());
		
		System.out.println();
        grafo.imprimirGrafo();
        
        System.out.println();
        grafo.removerVertice(1);
        grafo.imprimirGrafo();
        
        System.out.println(grafo.isCompleto());

        
        int vertices = 5;
		int[][] graph = {{0, 1, 0, 3, 10}, {0, 0, 5, 0, 0}, {0, 0, 0, 0, 1}, 
						 {0, 0, 2, 0, 6 }, {0, 0, 0, 0, 0 }};
		
		AlgoritmoDijkstra dijkstra = new AlgoritmoDijkstra(vertices);
		
		dijkstra.setGraph(graph);
		
		dijkstra.visualizeGraph();
		
		dijkstra.dijkstra(0, 4);
	}

}
