package org.sm.lab6;

import java.awt.*;
import javax.media.j3d.Canvas3D;
import javax.swing.*;

public class Window extends JFrame {
	private Canvas3D canvas;

	public Window(Canvas3D canvas) {
		this.canvas = canvas;

		configureWindow();

		getContentPane().add(canvas, BorderLayout.CENTER);
	}

	private void configureWindow() {
		setTitle("Goose");
		setSize(640, 480);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - getWidth()) / 2;
		int locationY = (screenSize.height - getHeight()) / 2;
		setLocation(locationX,locationY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}



}
