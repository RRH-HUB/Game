package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private final int ancho;
	private final int alto;
	public final int[] pixeles;

	// ---------------------------------sprite sheet collection
	public static SpriteSheet terrain_5 = new SpriteSheet("/textures/terrain_5.png", 320, 320);

	public SpriteSheet(final String ruta, final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;
		pixeles = new int[ancho * alto];
		// creamos una imagen y le asignamos la ruta donde esta y volcamos la imagen en
		// el array
		BufferedImage imagen;
		try {
			imagen = ImageIO.read(SpriteSheet.class.getResource(ruta));
			imagen.getRGB(0, 0, ancho, ancho, pixeles, 0, ancho);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getAncho() {
		return ancho;
	}

}
