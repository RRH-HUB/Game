package graphics;

public final class Screen {
	private final int ancho;
	private final int alto;

	public final int[] pixeles;

	private final static int LADO_SPRITE = 32;// sprite size
	private final static int MASCARA_SPRITE = LADO_SPRITE - 1;// para no salirnos del sprite

	// constructor de clase
	public Screen(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		pixeles = new int[ancho * alto];

	}

	// -----------------metodos-----------------
	// limpia la pantalla del frame anterior recorre el array de pixels poniendolos
	// en negro
	// Antialiasing????
	public void limpiar() {
		for (int i = 0; i < pixeles.length; i++) {
			pixeles[i] = 0;// negro en hexa
		}
	}

	// dibuja la pantalla
	public void draw(final int compY, final int compX) {
		for (int y = 0; y < alto; y++) {
			int posY = y + compY;
			// para no dibujar fuera de la pantalla evitamos excepciones
			if (posY < 0 || posY >= alto) {
				continue;
			}
			for (int x = 0; x < ancho; x++) {
				int posX = x + compX;
				if (posX < 0 || posX >= ancho) {
					continue;
				}
				// drawing a sprite 'greenGrass'
				pixeles[posX + posY * ancho] = Sprite.water.pixeles[(x & MASCARA_SPRITE)
						+ (y & MASCARA_SPRITE) * LADO_SPRITE];
			}
		}

	}

}
