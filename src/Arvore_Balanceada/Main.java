package Arvore_Balanceada;

public class Main {

	public static void main(String[] args) {
		
		ArvoreMunicipio municipio = new ArvoreMunicipio();
		
		Municipio municipio1 = new Municipio("Sao Paulo", 1521111, 1200000);
		Municipio municipio2 = new Municipio("Rio do Sul", 260750, 70000);
		Municipio municipio3 = new Municipio("Florianopolis", 675000, 537000);
		
		municipio.inserir(municipio1);
		municipio.inserir(municipio2);
		municipio.inserir(municipio3);
		
		municipio.mostrarOrdem();
		System.out.println("-------------");
		System.out.println("Número de Município(s): " + municipio.contarMunicipios());
		System.out.println("-------------");
		municipio.mostrarMaiorPopulacaoQue(536000);
		System.out.println("-------------");
		municipio.mostrarDensidade();
		System.out.println("-------------");
		municipio.MostrarPorcEmRelacaoTerritNacional();
		System.out.println("-------------");
		municipio.mostrarMaiorPopulacao();
	}

}
