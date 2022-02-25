package mainpackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys;
	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public boolean getKey_Up() {
		return keys[KeyEvent.VK_UP];
	}
	
	public boolean getKey_Down() {
		return keys[KeyEvent.VK_DOWN];
	}
	
	public boolean getKey_Left() {
		return keys[KeyEvent.VK_LEFT];
	}
	
	public boolean getKey_Right() {
		return keys[KeyEvent.VK_RIGHT];
	}
	
	public boolean getKey_LeftShift() {
		return keys[KeyEvent.VK_SHIFT];
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
