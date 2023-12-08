package Grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Grafo<T> {

    private boolean direcionado;
    private boolean ponderado;
    private MatrizAdjacencia matrizAdjacencia = new MatrizAdjacencia();
    private ListaAdjacencia listaAdjacencia = new ListaAdjacencia();

    private ArrayList<NodoGrafo<T>> elementos = new ArrayList<NodoGrafo<T>>();
    
    public Grafo(boolean direcionado, boolean ponderado) {
        this.direcionado = direcionado;
        this.ponderado = ponderado;
    }

    public int inserir(T dado) {
        int indice = this.elementos.size();

        this.elementos.add(new NodoGrafo<T>(dado));

        return indice;
    }

    public void inserirAresta(int origem, int destino) {
        inserirAresta(origem, destino, 1);
    }

    public void inserirAresta(int origem, int destino, int peso) {
        inserirListaAdjacencia(origem, destino, peso);
        inserirMatrizAdjacencia(origem, destino, peso);
    }

    public void removerAresta(int origem, int destino) {
        removerListaAdjacencia(origem, destino);
        removerMatrizAdjacencia(origem, destino);
    }

    public void inserirListaAdjacencia(int origem, int destino) {
        inserirListaAdjacencia(origem, destino, 1);
    }

    public void inserirListaAdjacencia(int origem, int destino, int peso) {
        peso = this.ponderado ? peso : 1;

        listaAdjacencia.inserir(origem, destino, peso);

        if(this.direcionado || origem == destino) {
            return;
        }

        listaAdjacencia.inserir(destino, origem, peso);
    }

    public void removerListaAdjacencia(int origem, int destino) {
        listaAdjacencia.remover(origem, destino);

        if(this.direcionado || origem == destino) {
            return;
        }

        listaAdjacencia.remover(destino, origem);
    }

    public void inserirMatrizAdjacencia(int origem, int destino) {
        inserirMatrizAdjacencia(origem, destino, 1);
    }

    public void inserirMatrizAdjacencia(int origem, int destino, int peso) {
        peso = this.ponderado ? peso : 1;

        matrizAdjacencia.inserir(origem, destino, peso);

        if(this.direcionado || origem == destino) {
            return;
        }

        matrizAdjacencia.inserir(destino, origem, peso);
    }

    public void removerMatrizAdjacencia(int origem, int destino) {
        matrizAdjacencia.remover(origem, destino);
        
        if(this.direcionado || origem == destino) {
            return;
        }

        matrizAdjacencia.remover(destino, origem);
    }

    public LinkedList<Adjacencia> getListaAdjacenciaVertice(T dado) {
        return listaAdjacencia.getListaAdjacencia(this.getIndiceDado(dado));
    }

    public boolean listaAdjacenciaVerticeVazia(T dado) {
        return getListaAdjacenciaVertice(dado) == null 
            || getListaAdjacenciaVertice(dado).isEmpty();
    }

    private int getIndiceDado(T dado) {
        int indice = 0;

        for (NodoGrafo<T> nodoGrafo : elementos) {
            if(nodoGrafo.getDado() == dado) {
                return indice;
            }

            indice++;
        }

        return indice;
    }

    public ArrayList<NodoGrafo<T>> getElementos() {
        return this.elementos;
    }
    
    public MatrizAdjacencia getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public ListaAdjacencia getListaAdjacencia() {
        return listaAdjacencia;
    }

    public ArrayList<HashMap<String, Integer>> getMatrizIncidencia() {
        ArrayList<HashMap<String, Integer>> matrizIncidencia = 
            new ArrayList<HashMap<String, Integer>>();

        for (int linha = 0; linha < elementos.size(); linha++) {
            matrizIncidencia.add(new HashMap<String, Integer>());
            
            for (Integer origemAdjacencia = 0; 
                origemAdjacencia < getListaAdjacencia().getLista().size(); 
                origemAdjacencia++
            ) {
                LinkedList<Adjacencia> adjacencias = getListaAdjacencia()
                    .getLista()
                    .get(origemAdjacencia);

                for (Adjacencia adjacencia : adjacencias) {

                    String chave = 
                        "{" + origemAdjacencia + "-" + adjacencia.getIndiceNodoDestino() + "}";

                    if(
                        linha == origemAdjacencia 
                        || (!direcionado && linha == adjacencia.getIndiceNodoDestino())
                    ) {
                        matrizIncidencia.get(linha).put(chave, adjacencia.getPeso());
                    }
                    else {
                        matrizIncidencia.get(linha).put(chave, 0);
                    }
                }
            }

        }
        return matrizIncidencia;
    }

    // Método que verifica se o grafo é completo
    public boolean isCompleto() {
        for (int origem = 0; origem < elementos.size(); origem++) {
            for (int destino = 0; destino < elementos.size(); destino++) {
                if(origem == destino) {
                    continue;
                }
                
                // Se não existir nenhuma aresta entre os vértices, não está completo
                if(!this.getListaAdjacencia().existeAresta(origem, destino)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean buscaLargura(int origem, T dado) {
        ArrayList<Integer> elementosMapeados = new ArrayList<Integer>();
        
        ArrayList<Integer> proximosElentos = new ArrayList<Integer>(); 

        proximosElentos.add(origem);

        while(!proximosElentos.isEmpty()) {
            ArrayList<Integer> elementosAtuais = proximosElentos;
            proximosElentos = new ArrayList<Integer>();

            for (Integer elemento : elementosAtuais) {
                if(elementosMapeados.indexOf(elemento) != -1) {
                    continue;
                }

                if(elementos.get(elemento).getDado() == dado) {
                    return true;
                }

                for (Adjacencia adjacencia : getListaAdjacencia().getLista().get(elemento)) {
                    proximosElentos.add(adjacencia.getIndiceNodoDestino());
                }

                elementosMapeados.add(elemento);
            }
        }

        return false;
    } 

    // Verifica se o grafo é conexo
    public boolean isConexo() {
        if (elementos.isEmpty()) {
            return true; // Um grafo vazio é considerado conexo
        }

        for (int i = 0; i < elementos.size(); i++) {
            boolean[] visitados = new boolean[elementos.size()];
            if (!buscaProfundidade(i, visitados)) {
                return false; // Se algum vértice não for alcançável, o grafo não é conexo
            }
        }

        return true; // Todos os vértices são alcançáveis, o grafo é conexo
    }
    
    // Realiza uma busca por profundidade no grafo
    private boolean buscaProfundidade(int vertice, boolean[] visitados) {
        visitados[vertice] = true;
        LinkedList<Adjacencia> adjacencias = listaAdjacencia.getLista().get(vertice);

        if (adjacencias != null) {
            for (Adjacencia adjacencia : adjacencias) {
                int indiceDestino = adjacencia.getIndiceNodoDestino();
                if (!visitados[indiceDestino]) {
                    buscaProfundidade(indiceDestino, visitados);
                }
            }
        }

        // Verifica se todos os vértices foram visitados
        for (boolean visitado : visitados) {
            if (!visitado) {
                return false; // Se algum vértice não foi visitado, não é conexo
            }
        }

        return true; // Todos os vértices foram visitados, é conexo
    }


    public static Grafo<Integer> criarGrafo(boolean direcionado, boolean ponderado) {
        Scanner entrada  = new Scanner(System.in);

        Grafo<Integer> grafo = new Grafo<Integer>(direcionado, ponderado);

        System.out.println("Número de Vertices: ");

        int numeroElementos = entrada.nextInt();

        for (int i = 1; i <= numeroElementos; i++) {
            System.out.println("Informe o " + i + "º nó: ");
            grafo.inserir(entrada.nextInt());
            entrada.nextLine();
        }

        for (int indicePrimeiroElemento = 0; indicePrimeiroElemento < numeroElementos; indicePrimeiroElemento++) {
            for (int indiceSegundoElemento = 0; indiceSegundoElemento < numeroElementos; indiceSegundoElemento++) {
                if(!direcionado && indiceSegundoElemento  < indicePrimeiroElemento) {
                    continue;
                }

                int indiceTextualPrimeiro = indicePrimeiroElemento+ 1;
                int indiceTextualSegundo = indiceSegundoElemento + 1;

                System.out.println("O " + indiceTextualPrimeiro + "º nó tem ligação com o " + indiceTextualSegundo + "º (Y|N): ");

                if(!entrada.nextLine().toUpperCase().equals("Y")) {
                    continue;
                }

                if(!ponderado) {
                    grafo.inserirAresta(indicePrimeiroElemento, indiceSegundoElemento);
                    continue;
                }

                System.out.println("Valor da aresta: ");
                grafo.inserirAresta(indicePrimeiroElemento, indiceSegundoElemento, entrada.nextInt());
                entrada.nextLine();
            }
        }

        entrada.close();

        return grafo;
    }
    
    // Remove o vertice do indice passado por parâmentro
    public void removerVertice(int indice) {
        if (indice < 0 || indice >= elementos.size()) {
            System.out.println("Índice inválido, não foi possível remover o vértice.");
            return;
        }

        // Remove todas as arestas conectadas ao vértice
        for (int i = 0; i < elementos.size(); i++) {
            if (i == indice) {
                listaAdjacencia.remover(i, i); // Remove loopbacks se existirem
            } else {
                listaAdjacencia.remover(i, indice);
                listaAdjacencia.remover(indice, i);
            }
        }

        // Remove o vértice da lista de elementos
        elementos.remove(indice);
    }
    
    // Verifica se o grafo é Euleriano, Semi-Euleriano ou Não Euleriano
    public String isEuleriano() {
        int contadorVerticesImpares = 0;

        // Itera sobre cada vértice no grafo para contar quantos possuem grau ímpar
        for (int i = 0; i < elementos.size(); i++) {
            int grau = listaAdjacencia.getListaAdjacencia(i).size();

            // Se o grau do vértice for ímpar, incrementa o contador de vértices ímpares
            if (grau % 2 != 0) {
                contadorVerticesImpares++;
            }
        }

        // Verifica o número de vértices ímpares e determina o tipo do grafo com base nesse número
        if (contadorVerticesImpares == 0) {
            return "Grafo Euleriano";
        } else if (contadorVerticesImpares == 2) {
            return "Grafo Semi-Euleriano";
        } else {
            return "Grafo Não Euleriano";
        }
    }
    
 // Verifica se o grafo é Hamiltoniano, Semi-Hamiltoniano ou Não Hamiltoniano
    public String isHamiltoniano() {
        int numVertices = elementos.size();

        // Verifica se todos os vértices possuem grau >= (numVertices / 2)
        for (int i = 0; i < numVertices; i++) {
            int grau = listaAdjacencia.getListaAdjacencia(i).size();

            if (grau < (numVertices / 2)) {
                return "Grafo Não Hamiltoniano";
            }
        }

        // Verifica se o grafo possui um ciclo Hamiltoniano
        if (verificarCicloHamiltoniano()) {
            return "Grafo Hamiltoniano";
        }

        // Verifica se o grafo possui um caminho Hamiltoniano
        if (verificarCaminhoHamiltoniano()) {
            return "Grafo Semi-Hamiltoniano";
        }

        return "Grafo Não Hamiltoniano";
    }

    private boolean verificarCicloHamiltoniano() {
        int numVertices = elementos.size();

        boolean[] visitados = new boolean[numVertices];

        // Para cada vértice, verifica se existe um ciclo Hamiltoniano iniciando nesse vértice
        for (int i = 0; i < numVertices; i++) {
            // Inicia a busca em profundidade para encontrar um ciclo Hamiltoniano a partir do vértice i
            if (buscaCicloHamiltoniano(i, i, visitados, 1)) {
                return true;
            }
        }

        return false;
    }

    private boolean buscaCicloHamiltoniano(int inicio, int verticeAtual, boolean[] visitados, int contadorVertices) {
        int numVertices = elementos.size();

        // Se todos os vértices foram visitados e o vértice atual é o mesmo que o vértice de início, retorna true
        if (contadorVertices == numVertices && verticeAtual == inicio) {
            return true;
        }

        // Marca o vértice atual como visitado
        visitados[verticeAtual] = true;

        // Explora todos os vértices adjacentes ao vértice atual
        LinkedList<Adjacencia> adjacencias = listaAdjacencia.getListaAdjacencia(verticeAtual);
        if (adjacencias != null) {
            for (Adjacencia adjacencia : adjacencias) {
                int proximoVertice = adjacencia.getIndiceNodoDestino();
                if (!visitados[proximoVertice]) {
                    if (buscaCicloHamiltoniano(inicio, proximoVertice, visitados, contadorVertices + 1)) {
                        return true;
                    }
                }
            }
        }

        // Desmarca o vértice atual após a busca
        visitados[verticeAtual] = false;

        return false;
    }

    private boolean verificarCaminhoHamiltoniano() {
        int numVertices = elementos.size();

        boolean[] visitados = new boolean[numVertices];

        // Para cada vértice, verifica se existe um caminho Hamiltoniano iniciando nesse vértice
        for (int i = 0; i < numVertices; i++) {
            // Inicia a busca em profundidade para encontrar um caminho Hamiltoniano a partir do vértice i
            if (buscaCaminhoHamiltoniano(i, visitados, 1)) {
                return true;
            }
        }

        return false;
    }

    private boolean buscaCaminhoHamiltoniano(int verticeAtual, boolean[] visitados, int contadorVertices) {
        int numVertices = elementos.size();

        // Se todos os vértices foram visitados, retorna true
        if (contadorVertices == numVertices) {
            return true;
        }

        // Marca o vértice atual como visitado
        visitados[verticeAtual] = true;

        // Explora todos os vértices adjacentes ao vértice atual
        LinkedList<Adjacencia> adjacencias = listaAdjacencia.getListaAdjacencia(verticeAtual);
        if (adjacencias != null) {
            for (Adjacencia adjacencia : adjacencias) {
                int proximoVertice = adjacencia.getIndiceNodoDestino();
                if (!visitados[proximoVertice]) {
                    if (buscaCaminhoHamiltoniano(proximoVertice, visitados, contadorVertices + 1)) {
                        return true;
                    }
                }
            }
        }

        // Desmarca o vértice atual após a busca
        visitados[verticeAtual] = false;

        return false;
    }

    
    public void imprimirGrafo() {
        for (int i = 0; i < elementos.size(); i++) {
            System.out.print(elementos.get(i).getDado() + " -> ");
            LinkedList<Adjacencia> adjacencias = listaAdjacencia.getLista().get(i);
            
            if (adjacencias != null) {
                for (Adjacencia adjacencia : adjacencias) {
                    int indiceDestino = adjacencia.getIndiceNodoDestino();
                    if (indiceDestino < elementos.size()) {
                        System.out.print(elementos.get(indiceDestino).getDado());
                        if (ponderado) {
                            System.out.print("(" + adjacencia.getPeso() + ")");
                        }
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }
}
