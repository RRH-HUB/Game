package graphics;

public final class Sprite {
	// usamos hoja de Sprites de 320x320
	private final int lado;
	private int x;
	private int y;

	public int[] pixeles;
	private final SpriteSheet hoja;

	// -------------------------sprite collection
	public static Sprite greenGrass = new Sprite(32, 0, 0, SpriteSheet.terrain_5);

	public Sprite(final int lado, final int columna, final int fila, final SpriteSheet hoja) {
		this.lado = lado;

		pixeles = new int[this.lado * this.lado];

		this.x = columna * this.lado;
		this.y = fila * this.lado;
		this.hoja = hoja;

		for (int y = 0; y < lado; y++) {
			for (int x = 0; x < lado; x++) {
				pixeles[x + y * lado] = hoja.pixeles[(x + this.x) + (y + this.y) * hoja.getAncho()];
			}
		}

	}

}
