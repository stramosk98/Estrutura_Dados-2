package Arvore;

import java.util.List;

public class ArvoreB{
	private int order;
	private Nodo raiz;
	
	public ArvoreB(int order){
		this.order = order;
		this.raiz = new Nodo(order, true);
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}
	
	public void print(){
		System.out.println("Arvore de ordem " + this.order);
		this.raiz.print();
	}
	
	public int[] search(int x){
		return this.raiz.search(x);
	}
	
	public boolean busca(int x) {
		long tInicial = System.currentTimeMillis();
		if(this.search(x) != null) {
			return true;
		}
		System.out.println("Tempo árvore-B para buscar: " + (System.currentTimeMillis() - tInicial) + " ms");
		return false;
	}
	
	private Nodo dividiRaiz(){
		Nodo s = new Nodo(this.order, false);
		s.getFilhos()[0] = this.raiz;
		this.raiz = s;
		s.dividirFilho(0);
		return s;
	}
	
	public void insert(int x){
		Nodo r = this.raiz;
		if(r.search(x) != null) 
			return;
		
		if(r.isFull()){
			Nodo s = this.dividiRaiz();
			s.insertNonFull(x);
		}else{
			r.insertNonFull(x);
		}
	}
	
	public void deletar(int chave) {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        boolean chaveDeletada = delete(raiz, chave);

        // Se o nó raiz ficar vazio após a operação de exclusão, atualiza o nó raiz
        if (raiz.isEmpty()) {
            Nodo newRaiz = raiz.isLeaf() ? null : raiz.getFilhos()[0];
            raiz = newRaiz;
        }

        // Verifica se a chave está na árvore
        if (!chaveDeletada) {
            System.out.println("Chave não está na árvore.");
        }
    }
	
	private int findPredecessor(Nodo nodo, int index) {
        //  Procura a chave mais a direita na subárvore com nodo.getFilhos()[index]
        Nodo atual = nodo.getFilhos()[index];
        while (!atual.isLeaf()) {
            atual = atual.getFilhos()[atual.getN()];
        }
        return atual.getChaves()[atual.getN() - 1];
    }
	
	
	private boolean delete(Nodo nodo, int chave) {
        // Procura o indice da primeira chave maior ou igual a chave passada
        int index = 0;
        while (index < nodo.getN() && chave > nodo.getChaves()[index]) {
            index++;
        }

        // Se a chave for encontrada neste nó
        if (index < nodo.getN() && chave == nodo.getChaves()[index]) {
            if (nodo.isLeaf()) {
                // A chave é uma folha, deleta diretamente
                for (int i = index; i < nodo.getN() - 1; i++) {
                    nodo.getChaves()[i] = nodo.getChaves()[i + 1];
                }
                nodo.setN(nodo.getN() - 1);
                return true;
            } else {
                int chaveToReplace = findPredecessor(nodo, index);
                nodo.getChaves()[index] = chaveToReplace;
                return delete(nodo.getFilhos()[index], chaveToReplace);
            }
        } else {
            // Chave não encontrada neste nó, continua procurando
            if (nodo.isLeaf()) {
               // Chave não está na árvore
                return false;
            } else {
                // Procura recursivamente no filho
                boolean chaveDeletada = delete(nodo.getFilhos()[index], chave);

                // Corrige o nó filho caso não esteja na ordem correta
                if (nodo.getFilhos()[index].getN() < this.order) {
                    noFilhoDesordenado(nodo, index);
                }

                return chaveDeletada;
            }
        }
    }
	
	private void noFilhoDesordenado(Nodo parent, int filhoIndex) {
        if (filhoIndex > 0 && parent.getFilhos()[filhoIndex - 1].getN() > this.order) {
            // Pega uma chave do irmao esquerdo
            pegarNodoEsquerdo(parent, filhoIndex);
        } else if (filhoIndex < parent.getN() && parent.getFilhos()[filhoIndex + 1].getN() > this.order) {
            // Pega uma chave do irmao direito
            pegarNodoDireito(parent, filhoIndex);
        } else {
            // Mescla o nó filho com o irmao
            mesclaIrmaos(parent, filhoIndex);
        }
    }
	
	private void pegarNodoEsquerdo(Nodo parent, int filhoIndex){
		Nodo filho = parent.getFilhos()[filhoIndex];
		Nodo irmao = parent.getFilhos()[filhoIndex - 1];
		
		filho.insertNonFull(parent.getChaves()[filhoIndex - 1]);
		parent.getChaves()[filhoIndex - 1] = irmao.getChaves()[irmao.getN() - 1];
		irmao.setN(irmao.getN() - 1);
	}
	
	private void pegarNodoDireito(Nodo parent, int filhoIndex){
		Nodo filho = parent.getFilhos()[filhoIndex];
		Nodo irmao = parent.getFilhos()[filhoIndex+1];
		
		filho.insertNonFull(parent.getChaves()[filhoIndex]);
		parent.getChaves()[filhoIndex] = irmao.getChaves()[0];

		for(int i=0; i<irmao.getN() - 1; i++){
			irmao.getChaves()[i] = irmao.getChaves()[i+1];
		}
		irmao.setN(irmao.getN()-1);
	}
	
	private void mesclaIrmaos(Nodo parent, int filhoIndex) {
	    Nodo filho = parent.getFilhos()[filhoIndex];

	    if (filhoIndex == 0) {
	        // Mescla com o nó irmao da direita
	        Nodo dirIrmao = parent.getFilhos()[1];

	        filho.getChaves()[filho.getN()] = parent.getChaves()[0];
	        filho.setN(filho.getN() + 1);

	        // Copia as chaves do irmão da direita para o nó filho
	        for (int i = 0; i < dirIrmao.getN()-1; i++) {
	            filho.getChaves()[filho.getN()] = dirIrmao.getChaves()[i];
	            filho.setN(filho.getN() + 1);
	        }
	        
	        
	        parent.getChaves()[0] = dirIrmao.getChaves()[dirIrmao.getN() - 1];

	        // Aloca os ponteiros dos nós filhos para o nó pai
	        for (int i = 1; i < parent.getN(); i++) {
	        	if(parent.getChaves().length > i && parent.getFilhos().length > i) {
	        		parent.getChaves()[i] = parent.getChaves()[i + 1];
	            	parent.getFilhos()[i] = parent.getFilhos()[i + 1];
	        	}
	        }
	        
	        parent.setN(parent.getN() - 1);
	        
	    } else {
	        // Mescla com o nó irmao da esquerda
	        Nodo esqIrmao = parent.getFilhos()[filhoIndex - 1];

	        esqIrmao.getChaves()[esqIrmao.getN()] = parent.getChaves()[filhoIndex - 1];
	        esqIrmao.setN(esqIrmao.getN() + 1);

	        // Copia as chaves do irmão da esquerda para o nó filho
	        for (int i = 0; i < filho.getN(); i++) {
	        	if(esqIrmao.getChaves().length >= esqIrmao.getN() && filho.getChaves().length >= i) {
	        		esqIrmao.getChaves()[esqIrmao.getN()] = filho.getChaves()[i];
	            	esqIrmao.setN(esqIrmao.getN() + 1);
	        	}
	        }
	        
	        parent.getChaves()[filhoIndex - 1] = filho.getChaves()[filho.getN()-1];

	        // Aloca os ponteiros dos nós filhos para o nó pai
	        for (int i = filhoIndex - 1; i < parent.getN() - 1; i++) {
	            parent.getChaves()[i] = parent.getChaves()[i + 1];
	            parent.getFilhos()[i + 1] = parent.getFilhos()[i + 2];
	        }

	        parent.setN(parent.getN() - 1);
	    }
	}
	
	public void executaPerformanceInserir(int n, List<Integer> lista) {
		long tInicial = System.currentTimeMillis();
		for(int i = 0; i < lista.size(); i++) {
			this.insert(lista.get(i));
		}
		System.out.println("Tempo árvore-B para inserir: " + (System.currentTimeMillis() - tInicial) + " ms");
	}
	
	public void executaPerformanceRemover(int n, List<Integer> lista) {
		long tInicial = System.currentTimeMillis();
		for(int j = 0; j < n; j++) {
			this.deletar(lista.get(j));
		}
		System.out.println("Tempo árvore-B para remover: " + (System.currentTimeMillis() - tInicial) + " ms");
	}


}