package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import controls.Keyboard;

/*creamos la ventana del juego
 * clase canvas para convertir la ventana en en una zona de dibujo 
 * añadimos un identificador por defecto de serio para en el futurio saber si hay modificaciones en la clase
 * usamos Jframe que es la ventana q usa java
 * creamos el constructor de juego
 * definimos las variables de tamaño y nombre de la ventana si las definimos final el compilador actuara mucho mas rapido
 * fijamos el tamamño de nuestra venta en el cosntructo con setPreferredSize
 * implementamos la interfaz runnable para ejecutar diferentes threads
 */
public class Juego extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private final int ANCHO = 920;
	private final int ALTO = 620;
	private final String NOMBRE = "Rol del Güeno";

	private static int aps2 = 0;
	private static int fps = 0;
	// evita problemas al existir la posibilidad de que varios metodos usen esta
	// variable
	private static volatile boolean encendido = false;

	private static JFrame ventana;
	private static Thread thread;
	private static Keyboard keyboard;

	private Juego() {
		setPreferredSize(new Dimension(ANCHO, ALTO));

		ventana = new JFrame(NOMBRE);
		// cierra la ventana en la x y no quede ejen¡cutando el loop en segundo plano
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// no dejamos que varien el tamaño de la ventana
		ventana.setResizable(false);
		// añadiremos un diseño de ventana
		ventana.setLayout(new BorderLayout());
		// añadimos a nuestra ventana nuestra clase
		ventana.add(this, BorderLayout.CENTER);
		// ajustamos el contenido de la ventana al tamaño asigano por setpreferedsize
		ventana.pack();
		// fijamos la ventana en el centro del escritorio
		ventana.setLocationRelativeTo(null);
		// para que la ventana sea visible
		ventana.setVisible(true);
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
			// problemas con cantidades pequeñas de tiempo <1seg

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

		if (keyboard.down) {
			System.out.println("abajo");
		}
		if (keyboard.left) {
			System.out.println("izquierda");
		}
		if (keyboard.right) {
			System.out.println("derecha");
		}
		if (keyboard.up) {
			System.out.println("arriba");
		}
		aps2++;

	}

	private void mostrar() {
		fps++;

	}
}
