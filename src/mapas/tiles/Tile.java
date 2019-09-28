package mapas.tiles;

import graphics.Screen;
import graphics.Sprite;

public abstract class Tile {
	public int x;
	public int y;

	public Sprite sprite;

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void mostrar(int x, int y, Screen pantalla) {

	}

	public boolean solid() {
		return false;
	}

}
