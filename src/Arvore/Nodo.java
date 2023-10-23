package Arvore;

public class Nodo{
	private int order; //order of the node
	private int n; //number of chaves currently in the node
	private int[] chaves; //chaves in the node
	private boolean isLeaf; //says is is a Leaf or not
	private Nodo[] filhos; //pointers to filhos
	
	public Nodo(int order, boolean isLeaf){
		this.order = order;
		this.n = 0;
		this.chaves = new int[2*order];
		this.filhos = new Nodo[2*order + 1];
		this.isLeaf = isLeaf;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[] getChaves() {
		return chaves;
	}

	public void setChaves(int[] chaves) {
		this.chaves = chaves;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Nodo[] getFilhos() {
		return filhos;
	}

	public void setFilhos(Nodo[] filhos) {
		this.filhos = filhos;
	}
	
	public boolean isFull(){
		return this.n == 2*this.order;
	}
	
	public boolean isEmpty(){
		return this.n==0;
	}
	
	public void print(){
		System.out.println("Chaves:");
		for(int i = 0; i<this.n - 1; i++){
			System.out.print(this.chaves[i] + " - ");
		}
		if(this.n >= 1){
			System.out.println(this.chaves[n-1] + "  (" + this.getN() + " chaves)");
		}
		if(! this.isLeaf){
			System.out.println("Filhos"
					+ ":");
			for(int i=0; i<this.n+1; i++){
				this.filhos[i].print();
			}
		}
	}
	
	// Implementação das funções
	
	public int[] search(int x){
		int i = 0;
		while(i < this.n && x > this.chaves[i]){
			i++;
		}
		
		if(i < this.n && x == this.chaves[i]){
			return this.chaves;
		}else if(this.isLeaf){
			return null;
		}else{
			return this.filhos[i].search(x);
		}
		
	}
	
	public void dividirFilho(int i){
		Nodo y = this.filhos[i];
		Nodo z = new Nodo(this.order, true);
		z.isLeaf = y.isLeaf;
		z.n = this.order-1;
		
		for(int j = 1; j < this.order; j++){
			z.chaves[j-1] = y.chaves[j+this.order];
		}
		
		if(! y.isLeaf){
			for(int j = 0; j<= this.order; j++){
				z.filhos[j] = y.filhos[j + this.order];
			}
		}
		
		y.n = this.order;
		
		for(int j = this.n; j>=i+1; j--){
			this.filhos[j+1] = this.filhos[j];
		}
		
		this.filhos[i+1] = z;
		
		for(int j = this.n -1; j>=i; j--){
			this.chaves[j+1] = this.chaves[j];
		}
		
		this.chaves[i] = y.chaves[this.order];
		this.n++;
		
	}
	
	public void insertNonFull(int x){
		int i = this.n -1;
		if(this.isLeaf){
			
			while(i >= 0 && x<this.chaves[i]){
				this.chaves[i+1] = this.chaves[i];
				i--;
			}
			
			this.chaves[i+1] = x;
			this.n++;
		}else{
			
			while(i >= 0 && x<this.chaves[i]){
				i--;
			}
			i++;
			if(this.filhos[i].isFull()){
				this.dividirFilho(i);
				
				if(x > this.chaves[i]){
					i++;
				}
			}
			
			this.filhos[i].insertNonFull(x);
		}
	}
	
}