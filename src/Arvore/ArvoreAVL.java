package Arvore;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArvoreAVL {
	
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	private class Nodo {
		private int dado, altd, alte, count;
		private Nodo dir, esq;
		
		public Nodo(int dado) {
			this.dado = dado;
			dir = esq = null;
			altd = alte = 0;
			count = 1;
		}
	}
	
	Nodo raiz;
	
	public ArvoreAVL() {
		raiz = null;
	}
	
	public void inserir(int dado) {
		raiz = inserirDado(raiz, dado);
	}
	
	private Nodo inserirDado(Nodo raiz, int dado) {
		if(raiz == null) {
			raiz = new Nodo(dado);
			return raiz;
		}
		if(dado < raiz.dado) {
			raiz.esq = inserirDado(raiz.esq,dado);
			if(raiz.esq.altd > raiz.esq.alte) {
				raiz.alte = raiz.esq.altd +1;
			} else {
				raiz.alte = raiz.esq.alte +1;
			}
			raiz = balanceamento(raiz);
		} else if (dado > raiz.dado) {
			raiz.dir = inserirDado(raiz.dir, dado);
			if(raiz.dir.altd > raiz.dir.alte) {
				raiz.altd = raiz.dir.altd + 1;
			} else {
				raiz.altd = raiz.dir.alte + 1;
			}
			raiz = balanceamento(raiz);
		} else {
			raiz.count++;
		}
		return raiz;
	}
	
	private Nodo balanceamento(Nodo raiz) {
		int fb = raiz.altd - raiz.alte;
		int fbSubArv;
		if(fb == 2) {
			fbSubArv = raiz.dir.altd - raiz.dir.alte;
			if(fbSubArv >= 0) {
				raiz = rotacao_esquerda(raiz);
			} else {
				raiz.dir = rotacao_direita(raiz.dir);
				raiz = rotacao_esquerda(raiz);
			}
		} else if(fb == -2) {
			fbSubArv = raiz.esq.altd - raiz.esq.alte;
			if(fbSubArv <= 0) {
				raiz = rotacao_direita(raiz);
			} else  {
				raiz.esq = rotacao_esquerda(raiz.esq);
				raiz = rotacao_direita(raiz);
			}
		}
		return raiz;
	}
	
	private Nodo rotacao_esquerda(Nodo raiz) {
		Nodo aux1, aux2;
		aux1 = raiz.dir;
		aux2 = aux1.esq;
		raiz.dir = aux2;
		aux1.esq = raiz;
		if(raiz.dir == null) {
			raiz.altd = 0;
		} else if(raiz.dir.alte > raiz.dir.altd) {
			raiz.altd = raiz.dir.alte + 1;
		} else {
			raiz.altd = raiz.dir.altd + 1;
		}
		if(aux1.esq.alte > aux1.esq.altd) {
			aux1.alte = aux1.esq.alte + 1;
		} else {
			aux1.alte = aux1.esq.altd + 1;
		}
		return aux1;
	}
	
	private Nodo rotacao_direita(Nodo raiz) {
		Nodo aux1, aux2;
		aux1 = raiz.esq;
		aux2 = aux1.dir;
		raiz.esq = aux2;
		aux1.dir = raiz;
		if(raiz.esq == null) {
			raiz.alte = 0;
		} else if(raiz.esq.alte > raiz.esq.altd) {
			raiz.alte = raiz.esq.alte + 1;
		} else {
			raiz.alte = raiz.esq.altd + 1;
		}
		if(aux1.dir.alte > aux1.dir.altd) {
			aux1.altd = aux1.dir.alte +1; 
		} else {
			aux1.altd = aux1.dir.altd +1; 
		}
		return aux1;
	}
	
	public void mostrarPorNivel() {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll(); //poll pega o primeiro elemento da fila
				if(nodoAtual != null) {
					System.out.print(nodoAtual.dado + " ");
					fila.add(nodoAtual.esq);
					fila.add(nodoAtual.dir);
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
	
	public void mostrarOrdem() {
		if(raiz == null) {
			System.out.println("Árvore vazia");
		}
		mostrandoOrdenado(raiz);
	}
	
	private void mostrandoOrdenado(Nodo raiz) {
		if(raiz != null) {
			mostrandoOrdenado(raiz.esq);
			System.out.println(raiz.dado + "[" + raiz.count + "] ");
			mostrandoOrdenado(raiz.dir);
		} 
	}
	
	public Nodo buscar(int chave) {
	    long tInicial = System.currentTimeMillis();
	    Nodo resultado = buscar(raiz, chave, tInicial);
	    if (resultado == null) {
	        System.out.println("Tempo árvore-B para buscar: " + (System.currentTimeMillis() - tInicial) + " ms");
	    }
	    return resultado;
	}

	private Nodo buscar(Nodo raiz, int chave, long tInicial) {
	    if (raiz == null) {
	        return null; // Chave não encontrada
	    }

	    if (chave == raiz.dado) {
	        return raiz; // Chave encontrada na raiz
	    }

	    if (chave < raiz.dado) {
	        return buscar(raiz.esq, chave, tInicial); // Procurar na subárvore esquerda
	    } else {
	        return buscar(raiz.dir, chave, tInicial); // Procurar na subárvore direita
	    }
	}

	
	public void remover(int dado) {
		raiz = removerDado(raiz, dado);
	}

	private Nodo removerDado(Nodo raiz, int dado) {
		Nodo aux1, aux2;
		if(raiz == null) {
			return null;
		}
		if (raiz.dado == dado) {
			if (raiz.esq == null && raiz.dir == null) {
				return null;
			} else if (raiz.esq == null) {
				return raiz.dir;
			} else if (raiz.dir == null) {
				return raiz.esq;
			} else {
				aux1 = raiz.dir;
				aux2 = raiz.esq;
				while (aux1.esq != null) {
					aux1 = aux1.esq;
				}
				aux1.esq = raiz.esq;
				return aux2;
			}
		} else if (raiz.dado < dado)
			raiz.dir = removerDado(raiz.dir, dado);
		else
			raiz.esq = removerDado(raiz.esq, dado);
		return raiz;
	}
	
	public void mostrarApenasPrimos() {
		setCount(0);
		mostrandoNumerosPrimos(raiz);
		System.out.println("Qtd de número(s) primo(s): " + getCount());
	}
		
	private void mostrandoNumerosPrimos(Nodo raiz) {
		if(raiz != null) {
			mostrandoNumerosPrimos(raiz.esq);
			if(raiz.dado > 1) {
				int primo = 0;
				for(int i = 1; i <= raiz.dado; i++) {
					if(raiz.dado % i == 0) {
						primo++;
					}
				}
				if(primo == 2) {
					System.out.println(raiz.dado + " é primo");
					setCount(getCount() + 1);
				}
			}
			mostrandoNumerosPrimos(raiz.dir);
		}
	}
	
	public void mostrarNodosApartirNivel(int nivel) {
		mostraNodosApartirdoNivel(raiz, nivel);
	}
	
	private void mostraNodosApartirdoNivel(Nodo raiz, int nivel){
		if(raiz == null) {
			return;
		}
		
		Queue<Nodo> fila = new LinkedList<>();
        fila.add(raiz);
        int nivelAtual = 1;

        while (!fila.isEmpty() && nivelAtual <= nivel) {
            int nodesNivelAtual = fila.size();
            for (int i = 0; i < nodesNivelAtual; i++) {
                Nodo nodoAtual = fila.poll();
                if (nivelAtual == nivel) {
                    System.out.print(nodoAtual.dado + " ");
                }
                if (nodoAtual.esq != null) {
                	fila.add(nodoAtual.esq);
                }
                if (nodoAtual.dir != null) {
                	fila.add(nodoAtual.dir);
                }
            }
            nivelAtual++;
        }
    }
	
	public void somaNosEmNiveisImpares() {
		setCount(0);
		somaNosEmNiveisImpares(raiz);
		System.out.println("Qtd de nós em níveis ímpares: " + getCount());
	}
	
	private void somaNosEmNiveisImpares(Nodo raiz){
		if(raiz == null) {
			return;
		}
		
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		int nivelAtual = 1;
		
		while (!fila.isEmpty()) {
			int nodesNivelAtual = fila.size();
			for (int i = 0; i < nodesNivelAtual; i++) {
				Nodo nodoAtual = fila.poll();
				if (nivelAtual % 2 == 1) {
					setCount(getCount() + 1);
				}
				if (nodoAtual.esq != null) {
					fila.add(nodoAtual.esq);
				}
				if (nodoAtual.dir != null) {
					fila.add(nodoAtual.dir);
				}
			}
			nivelAtual++;
		}
	}
	
	public boolean isAVL() {
	    return isAVL(raiz);
	}

	private boolean isAVL(Nodo raiz) {
	    if (raiz == null) {
	        return true;
	    }

	    int difAltura = Math.abs(altura(raiz.esq) - altura(raiz.dir));

	    if (difAltura <= 1 && isAVL(raiz.esq) && isAVL(raiz.dir)) {
	        return true;
	    }

	    return false;
	}

	private int altura(Nodo raiz) {
	    if (raiz == null) {
	        return 0;
	    }

	    int alturaEsq = altura(raiz.esq);
	    int alturaDir = altura(raiz.dir);

	    return Math.max(alturaEsq, alturaDir) + 1;
	}
	
	public void executaPerformanceInserir(int n, List<Integer> lista) {
		long tInicial = System.currentTimeMillis();
		for(int i = 0; i < lista.size(); i++) {
			this.inserir(lista.get(i));
		}
		System.out.println("Tempo árvore-Avl para inserir: " + (System.currentTimeMillis() - tInicial) + " ms");
	}
	
	public void executaPerformanceRemover(int n, List<Integer> lista) {
		long tInicial = System.currentTimeMillis();
		for(int j = 0; j < n; j++) {
			this.remover(lista.get(j));
		}
		System.out.println("Tempo árvore-Avl para remover: " + (System.currentTimeMillis() - tInicial) + " ms");
	}

}