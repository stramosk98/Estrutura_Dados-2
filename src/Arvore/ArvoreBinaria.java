package Arvore;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArvoreBinaria {
	
	private class Node{
		private int chave;
		private Node dir,esq;
		
		public Node(int item) {
			this.chave = item;
			dir = esq = null;
		}
	}
	
	Node raiz = null;
	
	public void inserir(int chave) {
		raiz = inserirDado(raiz,chave);
	}
	
	private Node inserirDado(Node raiz, int chave) {
		if(raiz == null) {
			raiz = new Node(chave);
			return raiz;
		}
		if (chave < raiz.chave) {
			raiz.esq = inserirDado(raiz.esq ,chave);
		} else if (chave > raiz.chave){
			raiz.dir = inserirDado(raiz.dir, chave);
		}
		return raiz;
	}
	
	public void inserirItem(int chave) {
        raiz = inserirItem(raiz, chave);
    }
	
	private Node inserirItem(Node raiz, int chave) {
	    if (raiz == null) {
	        raiz = new Node(chave);
	        return raiz;
	    }

	    Node atual = raiz;
	    Node parent = null;

	    while (atual != null) {
	        parent = atual;

	        if (chave < atual.chave) {
	            atual = atual.esq;
	        } else if (chave > atual.chave) {
	            atual = atual.dir;
	        } else {
	            // A chave já existe, portanto, não fazemos nada.
	            return raiz;
	        }
	    }

	    if (chave < parent.chave) {
	        parent.esq = new Node(chave);
	    } else {
	        parent.dir = new Node(chave);
	    }

	    return raiz;
	}

	public void mostraArvoreOrdemCrescente() {
		mostraArvoreOrdemCrescente(raiz);
	}
	
	private void mostraArvoreOrdemCrescente(Node raiz) {
		if(raiz != null) {
			mostraArvoreOrdemCrescente(raiz.esq);
			System.out.println(raiz.chave);
			mostraArvoreOrdemCrescente(raiz.dir);
			
		}
	}
	
	public void mostraArvoreOrdemDecrescente() {
		mostraArvoreOrdemDecrescente(raiz);
	}
	
	private void mostraArvoreOrdemDecrescente(Node raiz) {
		if(raiz != null) {
			mostraArvoreOrdemDecrescente(raiz.dir);
			System.out.println(raiz.chave);
			mostraArvoreOrdemDecrescente(raiz.esq);
		}
	}
	
	public void mostrarPorNivel() {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Node> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Node nodoAtual = fila.poll(); 
				if(nodoAtual != null) {
					System.out.print(nodoAtual.chave + " ");
					fila.add(nodoAtual.esq);
					fila.add(nodoAtual.dir);
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
	
	public void mostrarPares() {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Node> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Node nodoAtual = fila.poll();
				if(nodoAtual != null && nodoAtual.chave % 2 == 0) {
					System.out.print(nodoAtual.chave + " ");
					fila.add(nodoAtual.esq);
					fila.add(nodoAtual.dir);
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
	
	public boolean buscar(int chave) {
		long tInicial = System.currentTimeMillis();
	    Node current = raiz;

	    while (current != null) {
	        if (chave == current.chave) {
	            return true;
	        } else if (chave < current.chave) {
	            current = current.esq;
	        } else {
	            current = current.dir;
	        }
	    }
	    System.out.println("Tempo árvore-Binária para buscar: " + (System.currentTimeMillis() - tInicial) + " ms");
	    return false; 
	}

	public void remover(int chave) {
		raiz = removerItem(raiz,chave);
	}
	
	private Node removerItem(Node raiz, int chave) {
		if(raiz == null) {
			return null;
		}
		if(chave < raiz.chave) {
			raiz.esq = removerItem(raiz.esq, chave);
		} else if(chave > raiz.chave) {
			raiz.dir = removerItem(raiz.dir, chave);
		} else {
			if(raiz.esq == null) {
				return raiz.dir;
			} else if(raiz.dir == null) {
				return raiz.esq;				
			} else {
				Node sucessor = encontrarSucessor(raiz.dir);
				raiz.chave = sucessor.chave;
				raiz.dir = removerItem(raiz.dir, sucessor.chave);
			}
		}
		return raiz;	
	}
	
	public void removeDado(int chave) {
		raiz = removerSemRecursiva(raiz,chave);
	}
	
	private Node removerSemRecursiva(Node raiz, int chave) {
	    Node parent = null;
	    Node current = raiz;

	    while (current != null) {
	        if (chave < current.chave) {
	            parent = current;
	            current = current.esq;
	        } else if (chave > current.chave) {
	            parent = current;
	            current = current.dir;
	        } else {
	            if (current.esq == null) {
	                if (parent == null) {
	                    return current.dir;
	                }

	                if (current == parent.esq) {
	                    parent.esq = current.dir;
	                } else {
	                    parent.dir = current.dir;
	                }
	                return raiz;
	            } else if (current.dir == null) {
	                if (parent == null) {
	                    return current.esq;
	                }

	                if (current == parent.esq) {
	                    parent.esq = current.esq;
	                } else {
	                    parent.dir = current.esq;
	                }
	                return raiz;
	            } else {
	                Node sucessor = encontrarSucessor(current.dir);
	                current.chave = sucessor.chave;
	                current.dir = removerItem(current.dir, sucessor.chave);
	            }
	        }
	    }

	    return raiz;
	}

	
	private Node encontrarSucessor(Node nodo) {
		while(nodo.esq != null) {
			nodo = nodo.esq;
		}
		return nodo;
	}
	
	public void mostraMaior() {
		mostraChaveMaior(raiz);
	}
	
	private void mostraChaveMaior(Node raiz) {
		if(raiz != null) {
			if(raiz.dir == null) {
				System.out.println("Maior: " + raiz.chave);
			} else {
				mostraChaveMaior(raiz.dir);
			}
		}
	}
	
	public void mostraMenor() {
		mostraChaveMenor(raiz);
	}
	
	private void mostraChaveMenor(Node raiz) {
		if(raiz != null) {
			if(raiz.esq == null) {
				System.out.println("Menor: " + raiz.chave);
			} else {
				mostraChaveMenor(raiz.esq);
			}
		} 
	}
	
	public void mostraTamanhoArvore() {
		mostraTamanhoArvore(raiz);
	}
	
	private void mostraTamanhoArvore(Node raiz) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Node> fila = new LinkedList<>();
		fila.add(raiz);
		int tamanho = 0;
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Node nodoAtual = fila.poll(); 
				if(nodoAtual != null) {
					tamanho++;
					fila.add(nodoAtual.esq);
					fila.add(nodoAtual.dir);
				}
			}
		}
		System.out.println("O tamanho da árvore é: " + tamanho);
	}
	
	public void mostraAlturaArvore() {
		mostraAlturaArvore(raiz);
	}
	
	private void mostraAlturaArvore(Node raiz) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Node> fila = new LinkedList<>();
		fila.add(raiz);
		int altura = -1;
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			altura++;
			for(int i = 0; i < tamanhoNivel; i++) {
				Node nodoAtual = fila.poll();
				if(nodoAtual != null) {
					fila.add(nodoAtual.esq);
					fila.add(nodoAtual.dir);
				}
			}
		}
		System.out.println("A altura da árvore é: " + altura);
	}
	
	public void mostraNivelNode(int no) {
		mostraNivelNode(raiz, no);
	}
	
	private void mostraNivelNode(Node raiz, int no) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Node> fila = new LinkedList<>();
		fila.add(raiz);
		int nivel = 0;
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			nivel++;
			for(int i = 0; i < tamanhoNivel; i++) {
				Node nodoAtual = fila.poll(); 
				if(nodoAtual != null) {
					if(nodoAtual.chave == no) {
						System.out.println("O nível do nodo é: " + nivel);
						return;
					}
					else if(no > nodoAtual.chave) {
						fila.add(nodoAtual.dir);
					} 
					else if(no < nodoAtual.chave){
						fila.add(nodoAtual.esq);
						
					}
				}
			}
		}
	}
	
	public void mostraNosAncestrais(int no) {
		mostraNosAncestrais(raiz, no);
	}
	
	private void mostraNosAncestrais(Node raiz, int no) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Node> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Node nodoAtual = fila.poll();
				if(nodoAtual != null) {
					if(nodoAtual.chave == no) {
						return;
					}
					System.out.print(nodoAtual.chave + " ");
					fila.add(nodoAtual.esq);
					fila.add(nodoAtual.dir);
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
	
	public void mostraNosDescendentes(int no) {
		mostraNoFolhaDescendente(raiz, no);
	}
	
	private void mostraNoFolhaDescendente(Node raiz, int no) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Node> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Node nodoAtual = fila.poll();
				if(nodoAtual != null) {
					if(nodoAtual.chave == no) {
							System.out.print(nodoAtual.chave + " ");
							fila.add(nodoAtual.esq);
							fila.add(nodoAtual.dir);
					} else { 
						fila.add(nodoAtual.esq);
						fila.add(nodoAtual.dir);
					}
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
	
    public void mostraSubArvoreDireita(int no) {
    	mostraSubArvoreDireita(raiz, no);
    }

    private boolean mostraSubArvoreDireita(Node raiz, int no) {
        if(raiz == null) {
            return false;
        }
        if(raiz.chave == no) {
            mostrarEmOrdem(raiz.dir);
            return true;
        }

        return mostraSubArvoreDireita(raiz.esq, no) || mostraSubArvoreDireita(raiz.dir, no);
    }
    
    public void mostraSubArvoreEsquerda(int no) {
    	mostraSubArvoreEsquerda(raiz, no);
    }

    private boolean mostraSubArvoreEsquerda(Node raiz, int no) {
        if(raiz == null) {
            return false;
        }
        if(raiz.chave == no) {
            mostrarEmOrdem(raiz.esq);
            return true;
        }

        return mostraSubArvoreEsquerda(raiz.esq, no) || mostraSubArvoreEsquerda(raiz.dir, no);
    }
    
    public void mostrarEmOrdem() {
        mostrarEmOrdem(raiz);
    }

    private void mostrarEmOrdem(Node raiz) {
        if (raiz != null) {
            mostrarEmOrdem(raiz.esq);
            System.out.println(raiz.chave);
            mostrarEmOrdem(raiz.dir);
        }
    }
	
    public void mostraNoFolha() {
    	mostraNoFolha(this.raiz);
    }

    private void mostraNoFolha(Node raiz) {
        if(raiz == null) {
            return;
        }

        if(raiz.esq == null && raiz.dir == null) {
            System.out.println(raiz.chave);
            return;
        }

        mostraNoFolha(raiz.esq);
        mostraNoFolha(raiz.dir);
    }
    
    public void executaPerformanceInserir(int n, List<Integer> lista) {
		long tInicial = System.currentTimeMillis();
		for(int i = 0; i < lista.size(); i++) {
			this.inserirItem(lista.get(i));
		}
		System.out.println("Tempo árvore-Binária para inserir: " + (System.currentTimeMillis() - tInicial) + " ms");
	}
	
	public void executaPerformanceRemover(int n, List<Integer> lista) {
		long tInicial = System.currentTimeMillis();
		for(int j = 0; j < n; j++) {
			this.removeDado(lista.get(j));
		}
		System.out.println("Tempo árvore-Binária para remover: " + (System.currentTimeMillis() - tInicial) + " ms");
	}
	
}