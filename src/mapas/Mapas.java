package mapas;

import graphics.Screen;

public abstract class Mapas {
	private int alto;
	private int ancho;

	private int[] tiles;

	public Mapas(int ancho, int alto) {
		this.ancho = ancho;
		this.alto = alto;

		tiles = new int[ancho * alto];
		generarMapa();
	}

	public Mapas(String ruta) {

		cargarMapa(ruta);
	}

	private void generarMapa() {

	}

	private void cargarMapa(String ruta) {

	}

	public void actualizar() {

	}

	public void mostrar(int compX, int compY, Screen pantalla) {

	}

}
