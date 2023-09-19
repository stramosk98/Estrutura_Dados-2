package Arvore_Binaria;
import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinaria {
	
	private class Nodo{
		private int chave;
		private Nodo dir,esq;
		
		public Nodo(int item) {
			this.chave = item;
			dir = esq = null;
		}
	}
	
	Nodo raiz = null;
	
	public void inserir(int chave) {
		raiz = inserirDado(raiz,chave);
	}
	
	private Nodo inserirDado(Nodo raiz, int chave) {
		if(raiz == null) {
			raiz = new Nodo(chave);
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

    private Nodo inserirItem(Nodo raiz, int chave) {
        if (raiz == null) {
            raiz = new Nodo(chave);
            return raiz;
        }

        Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);

    	while(!fila.isEmpty()) {
    		int tamanhoNivel = fila.size();
    		for(int i = 0; i < tamanhoNivel; i++) {
    			Nodo nodoAtual = fila.poll();

            if (chave < nodoAtual.chave) {
                if (nodoAtual.esq == null) {
                	nodoAtual.esq = new Nodo(chave);
                    return raiz;
                } else {
                    fila.add(nodoAtual.esq);
                }
            } else if (chave > nodoAtual.chave) {
                if (nodoAtual.dir == null) {
                	nodoAtual.dir = new Nodo(chave);
                    return raiz;
                } else {
                    fila.add(nodoAtual.dir);
                }
            }
        }
    }
    	return raiz;
    }

	public void mostraArvoreOrdemCrescente() {
		mostraArvoreOrdemCrescente(raiz);
	}
	
	private void mostraArvoreOrdemCrescente(Nodo raiz) {
		if(raiz != null) {
			mostraArvoreOrdemCrescente(raiz.esq);
			System.out.println(raiz.chave);
			mostraArvoreOrdemCrescente(raiz.dir);
			
		}
	}
	
	public void mostraArvoreOrdemDecrescente() {
		mostraArvoreOrdemDecrescente(raiz);
	}
	
	private void mostraArvoreOrdemDecrescente(Nodo raiz) {
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
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll(); 
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
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll();
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
	
	public void remover(int chave) {
		raiz = removerItem(raiz,chave);
	}
	
	private Nodo removerItem(Nodo raiz, int chave) {
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
				Nodo sucessor = encontrarSucessor(raiz.dir);
				raiz.chave = sucessor.chave;
				raiz.dir = removerItem(raiz.dir, sucessor.chave);
			}
		}
		return raiz;	
	}
	
	private Nodo encontrarSucessor(Nodo nodo) {
		while(nodo.esq != null) {
			nodo = nodo.esq;
		}
		return nodo;
	}
	
	public void mostraMaior() {
		mostraChaveMaior(raiz);
	}
	
	private void mostraChaveMaior(Nodo raiz) {
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
	
	private void mostraChaveMenor(Nodo raiz) {
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
	
	private void mostraTamanhoArvore(Nodo raiz) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		int tamanho = 0;
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll(); 
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
	
	private void mostraAlturaArvore(Nodo raiz) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		int altura = -1;
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			altura++;
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll();
				if(nodoAtual != null) {
					fila.add(nodoAtual.esq);
					fila.add(nodoAtual.dir);
				}
			}
		}
		System.out.println("A altura da árvore é: " + altura);
	}
	
	public void mostraNivelNodo(int no) {
		mostraNivelNodo(raiz, no);
	}
	
	private void mostraNivelNodo(Nodo raiz, int no) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		int nivel = 0;
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			nivel++;
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll(); 
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
	
	private void mostraNosAncestrais(Nodo raiz, int no) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll();
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
	
	private void mostraNoFolhaDescendente(Nodo raiz, int no) {
		if(raiz == null) {
			System.out.println("Árvore vazia!");
			return;
		}
		Queue<Nodo> fila = new LinkedList<>();
		fila.add(raiz);
		
		while(!fila.isEmpty()) {
			int tamanhoNivel = fila.size();
			for(int i = 0; i < tamanhoNivel; i++) {
				Nodo nodoAtual = fila.poll();
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

    private boolean mostraSubArvoreDireita(Nodo raiz, int no) {
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

    private boolean mostraSubArvoreEsquerda(Nodo raiz, int no) {
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

    private void mostrarEmOrdem(Nodo raiz) {
        if (raiz != null) {
            mostrarEmOrdem(raiz.esq);
            System.out.println(raiz.chave);
            mostrarEmOrdem(raiz.dir);
        }
    }
	
    public void mostraNoFolha() {
    	mostraNoFolha(this.raiz);
    }

    private void mostraNoFolha(Nodo raiz) {
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
	
}