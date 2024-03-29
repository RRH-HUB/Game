package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controls.Keyboard;
import graphics.Screen;

/*creamos la ventana del juego
 * clase canvas para convertir la ventana en en una zona de dibujo 
 * a�adimos un identificador por defecto de serio para en el futurio saber si hay modificaciones en la clase
 * usamos Jframe que es la ventana q usa java
 * creamos el constructor de juego
 * definimos las variables de tama�o y nombre de la ventana si las definimos final el compilador actuara mucho mas rapido
 * fijamos el tamam�o de nuestra venta en el cosntructo con setPreferredSize
 * implementamos la interfaz runnable para ejecutar diferentes threads
 */
public class Juego extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private static final int ANCHO = 800;
	private static final int ALTO = 600;
	private final String NOMBRE = "Rol del G�eno";

	private static int aps2 = 0;
	private static int fps = 0;
	// evita problemas al existir la posibilidad de que varios metodos usen esta
	// variable
	private static int x = 0;
	private static int y = 0;
	private static Screen pantalla;
	/*
	 * nos devuelve un array de int que son los pixeles de la imagen Raster nos
	 * devuelve la secuencia de pixels y get databuffer nos devuekve la imagen en
	 * tipo buffer que es lo que estamos usando y getdata accedemos a los datos, y
	 * los convertimos a ints de data buffer
	 */
	private static BufferedImage image = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
	private static int[] pixeles = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private static volatile boolean encendido = false;

	private static JFrame ventana;
	private static Thread thread;
	private static Keyboard keyboard;

	private static final ImageIcon icono = new ImageIcon(Juego.class.getResource("/icono/IconoFnatasma.png"));

	private Juego() {
		setPreferredSize(new Dimension(ANCHO, ALTO));

		pantalla = new Screen(ANCHO, ALTO);

		ventana = new JFrame(NOMBRE);
		// cierra la ventana en la x y no quede ejen�cutando el loop en segundo plano
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// no dejamos que varien el tama�o de la ventana
		ventana.setResizable(false);
		// a�adiremos un dise�o de ventana
		ventana.setLayout(new BorderLayout());
		// a�adimos a nuestra ventana nuestra clase
		ventana.add(this, BorderLayout.CENTER);
		// ajustamos el contenido de la ventana al tama�o asigano por setpreferedsize
		ventana.pack();
		// fijamos la ventana en el centro del escritorio
		ventana.setLocationRelativeTo(null);
		// para que la ventana sea visible
		ventana.setVisible(true);
		ventana.setIconImage(icono.getImage());
		// inicamos teclado
		keyboard = new Keyboard();
		addKeyListener(keyboard);

	}

	public static void main(String[] args) {
		Juego Juego = new Juego();
		Juego.empezar();
	}
	// -----------------metodos----------------------------

	// empezar el juego synchronized en los dos metodos se asegura de que no usen
	// la variable a la vez
	private synchronized void empezar() {
		encendido = true;
		thread = new Thread(this, "Graficos");
		thread.start();
	}

	// acabar el juego
	private synchronized void finalizar() {
		encendido = false;

		try {
			thread.join();// cierra el hilo pero esperando a que termine de ejecutar lo que estuviera
							// haciendo
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// heredado de la interfaz Runnable
	public void run() {
		// NPS nanosegundoporactualizacion APS actualizacionporsegundo
		// NSPA nanosegundoporactualizacion
		final int NPS = 1000000000;
		final byte APS = 60;
		final double NSPA = NPS / APS;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0;// tiempo entre actualizaciones

		requestFocus();
		// mientras este encendido el bucle se ejecutara
		while (encendido) {
			// bloque del reloj de refresco de la pantalla
			final long inicioBucle = System.nanoTime();
			// tambien se puede usar system.currentTimeMillis() pero tiene
			// problemas con cantidades peque�as de tiempo <1seg

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NSPA;
			// actualizar se ejecutara mas o menos 60veces por segundo
			while (delta >= 1) {
				actualizar();
				delta--;
			}

			mostrar();
			// contador de aps y fps
			if (System.nanoTime() - referenciaContador > NPS) {
				ventana.setTitle(NOMBRE + " APS=" + aps2 + " FPS=" + fps);
				aps2 = 0;
				fps = 0;
				referenciaContador = System.nanoTime();
			}
		}

	}

	private void actualizar() {
		keyboard.actualizar();
//lo que se mueve es el mapa por debajo del personaje
		if (keyboard.down) {
			y--;
		}
		if (keyboard.left) {
			x++;
		}
		if (keyboard.right) {
			x--;
		}
		if (keyboard.up) {
			y++;
		}
		aps2++;

	}

	private void mostrar() {
		BufferStrategy estrategia = getBufferStrategy();
		if (estrategia == null) {
			createBufferStrategy(3);// es un espacio en memoria enb el que se dibuja antes de mostrarlo con
									// estrategia 3 tenmos 3 dibujos de "cola"
			return;
		}
		pantalla.limpiar();
		pantalla.draw(y, x);
		System.arraycopy(pantalla.pixeles, 0, pixeles, 0, pixeles.length);
		Graphics g = estrategia.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		estrategia.show();

//		for (int i = 0; i < pixeles.length; i++) {
//			pixeles[i] = pantalla.pixeles[i];
//		}
		fps++;

	}
}
