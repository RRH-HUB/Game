package controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Keyboard implements KeyListener {

	private final static int numeroTeclas = 1000;
	private final boolean[] keys = new boolean[numeroTeclas];

	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;

	public void actualizar() {
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;

	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;

	}

	public void keyTyped(KeyEvent e) {

	}

}
