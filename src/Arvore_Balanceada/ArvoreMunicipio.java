package Arvore_Balanceada;

class Municipio {
    String nome;
    double areaTotalKm2;
    int populacao;

    public Municipio(String nome, double areaTotalKm2, int populacao) {
        this.nome = nome;
        this.areaTotalKm2 = areaTotalKm2;
        this.populacao = populacao;
    }
}

class Nodo {
    Municipio municipio;
    Nodo esq, dir;
    int countMunicipios;

    public Nodo(Municipio municipio) {
        this.municipio = municipio;
        dir = esq = null;
        countMunicipios = 0;
    }
}

public class ArvoreMunicipio {
    private Nodo raiz;

    public ArvoreMunicipio() {
        raiz = null;
    }

    public void inserir(Municipio municipio) {
        raiz = inserir(raiz, municipio);
    }

    private Nodo inserir(Nodo nodo, Municipio municipio) {
        if (nodo == null) {
            return new Nodo(municipio);
        }

        int comparacao = municipio.nome.compareTo(nodo.municipio.nome);

        if (comparacao < 0) {
            nodo.esq = inserir(nodo.esq, municipio);
        } else if (comparacao > 0) {
            nodo.dir = inserir(nodo.dir, municipio);
        }

        return nodo;
    }
    
	public void mostrarOrdem() {
		mostrandoOrdenado(raiz);
	}
	
	private void mostrandoOrdenado(Nodo raiz) {
		if(raiz != null) {
			mostrandoOrdenado(raiz.esq);
			System.out.println(raiz.municipio.nome + " - " + raiz.municipio.areaTotalKm2 + " - " + raiz.municipio.populacao);
			mostrandoOrdenado(raiz.dir);
		}
	}
	
	public int contarMunicipios() {
	   return contarMunicipios(raiz);
	}

	private int contarMunicipios(Nodo raiz) {
	    if (raiz == null) {
	        return 0;
	    }
	    int esq = contarMunicipios(raiz.esq);
	    int dir = contarMunicipios(raiz.dir);

	    return 1 + esq + dir;
	}
	
	public void mostrarDensidade() {
		mostrarDensidade(raiz);
	}
	
	private void mostrarDensidade(Nodo raiz) {
		float densidade = 0;
		if(raiz != null) {
			mostrarDensidade(raiz.esq);
			densidade = (float) (raiz.municipio.populacao / raiz.municipio.areaTotalKm2);
			System.out.println("Densidade de " + raiz.municipio.nome + ": " + densidade);
			mostrarDensidade(raiz.dir);
		}
		
	}
	
	public void mostrarMaiorPopulacaoQue(float valor) {
		mostrarMaiorPopulacaoQue(raiz, valor);
	}
	
	private void mostrarMaiorPopulacaoQue(Nodo raiz, float valor) {
		if(raiz != null) {
			mostrarMaiorPopulacaoQue(raiz.esq, valor);
			if(raiz.municipio.populacao >= valor) {
				System.out.println(raiz.municipio.nome);
			}
			mostrarMaiorPopulacaoQue(raiz.dir, valor);
		}
	}
	
	public class MaiorPopulacaoResultado {
	    public float maiorPopulacao = 0;
	    public String cidade = "";
	}

	public void mostrarMaiorPopulacao() {
	    MaiorPopulacaoResultado resultado = new MaiorPopulacaoResultado();
	    mostrarMaiorPopulacao(raiz, resultado);
	    System.out.println(resultado.cidade);
	}

	private void mostrarMaiorPopulacao(Nodo raiz, MaiorPopulacaoResultado resultado) {
	    if (raiz == null) {
	        return;
	    }

	    if (raiz.municipio.populacao > resultado.maiorPopulacao) {
	        resultado.maiorPopulacao = raiz.municipio.populacao;
	        resultado.cidade = raiz.municipio.nome;
	    }

	    mostrarMaiorPopulacao(raiz.esq, resultado);
	    mostrarMaiorPopulacao(raiz.dir, resultado);
	}
	
	public class SomatorioAreaKm2 {
		public float total = 0;
		public float porcNacional = 0;
		public float territorioNacional = 8510000;
	}
	
	public void MostrarPorcEmRelacaoTerritNacional() {
		SomatorioAreaKm2 resultado = new SomatorioAreaKm2();
		MostrarPorcEmRelacaoTerritNacional(raiz, resultado);
		System.out.println("Porcentagem em relação ao território nacional:  " + resultado.porcNacional + "%");
	}
	
	private void MostrarPorcEmRelacaoTerritNacional(Nodo raiz, SomatorioAreaKm2 resultado) {
		if (raiz == null) {
			return;
		}
		
		resultado.total += raiz.municipio.areaTotalKm2;
		
		MostrarPorcEmRelacaoTerritNacional(raiz.esq, resultado);
		MostrarPorcEmRelacaoTerritNacional(raiz.dir, resultado);
		
		resultado.porcNacional = (resultado.total / resultado.territorioNacional) * 100;
	}

}