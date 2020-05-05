package org.sm.lab6;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			SceneControl control = new SceneControl();
			Window window = new Window(control.getCanvas());
			window.setVisible(true);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
