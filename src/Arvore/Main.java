package Arvore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArvoreB arvoreB = new ArvoreB(5);
		ArvoreAVL arvoreAvl = new ArvoreAVL();
		ArvoreBinaria arvoreBinaria = new ArvoreBinaria();
		
		int n = 50;
		Set<Integer> lista = new HashSet<>();
		Random randomGenerator = new Random();

		while (lista.size() < n) {
		    int random = randomGenerator.nextInt(n + 1);
		    lista.add(random);
		}

		List<Integer> listaEmbaralhada = new ArrayList<>(lista);
		Collections.shuffle(listaEmbaralhada);

		
//		Arvore B
		arvoreB.executaPerformanceInserir(n, listaEmbaralhada);
		arvoreB.print();
//		arvoreB.executaPerformanceRemover(n, listaEmbaralhada);
		
//		
//		Arvore AVL
		arvoreAvl.executaPerformanceInserir(n, listaEmbaralhada);
		arvoreAvl.mostrarOrdem();
//		arvoreAvl.executaPerformanceRemover(n, listaEmbaralhada);
		
//		Arvore Binaria
		arvoreBinaria.executaPerformanceInserir(n, listaEmbaralhada);
		arvoreBinaria.mostrarEmOrdem();
//		arvoreBinaria.executaPerformanceRemover(n, listaEmbaralhada);
		
		
	}
	
	

}
