package Arvore_Binaria;

public class Main {

	public static void main(String[] args) {
		
		ArvoreBinaria arvore = new ArvoreBinaria();
		
		arvore.inserir(50);
		arvore.inserir(90);
		arvore.inserir(38);
		arvore.inserir(40);
		arvore.inserir(15);
		arvore.inserir(10);
		arvore.inserir(12);
		arvore.inserir(13);
		
		arvore.mostrarPorNivel();
		System.out.println("-------------");
		arvore.mostraArvoreOrdemCrescente();
	}

}
