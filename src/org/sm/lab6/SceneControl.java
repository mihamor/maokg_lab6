package org.sm.lab6;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.Time;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SceneControl implements KeyListener, ActionListener {
	private SimpleUniverse universe;

	private BranchGroup root;

	private Canvas3D canvas;

	private Goose object;

	public SceneControl() throws IOException {
		initCanvas();
		initUniverse();
		Bounds influenceRegion = new BoundingSphere();

		object = new Goose(canvas);
		root = new BranchGroup();

		root.addChild(object.asNode());

		addLightsToUniverse(influenceRegion);
		addBackground(influenceRegion);
		root.compile();
		universe.addBranchGraph(root);

		new Timer(10, this).start();
	}

	private void initCanvas() {
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		canvas.setDoubleBufferEnable(true);
		canvas.setFocusable(true);

		canvas.addKeyListener(this);
	}

	private void initUniverse() {
		universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
	}

	private void addLightsToUniverse(Bounds influenceRegion) {
		Color3f lightColor = new Color3f(Color.WHITE);
		Vector3f lightDirection = new Vector3f(-1F, -1F, -1F);
		DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
		light.setInfluencingBounds(influenceRegion);
		root.addChild(light);
	}

	private void addBackground(Bounds influenceRegion) {
		Background background = new Background(new Color3f(Color.BLUE));
		background.setApplicationBounds(influenceRegion);
		root.addChild(background);
	}

	public Canvas3D getCanvas() {
		return canvas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If we move forward - move goose
		if(keyForward) {
			object.update();

			if(keyLeft) {
				object.turnLeft();
			}

			if(keyRight) {
				object.turnRight();
			}
		}

		double rotateX = (keyViewUp ? 1 : 0) - (keyViewDown ? 1 : 0);
		double rotateY = (keyViewLeft ? 1 : 0) - (keyViewRight ? 1 : 0);

		object.rotateModel(rotateX*0.05, rotateY*0.05, 0);
	}

	private boolean keyLeft, keyRight, keyForward, keyViewLeft, keyViewRight, keyViewUp, keyViewDown;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W: keyForward = true; break;
			case KeyEvent.VK_A: keyLeft = true; break;
			case KeyEvent.VK_D: keyRight = true; break;

			case KeyEvent.VK_LEFT: keyViewLeft = true; break;
			case KeyEvent.VK_RIGHT: keyViewRight = true; break;
			case KeyEvent.VK_UP: keyViewUp = true; break;
			case KeyEvent.VK_DOWN: keyViewDown = true; break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W: keyForward = false; break;
			case KeyEvent.VK_A: keyLeft = false; break;
			case KeyEvent.VK_D: keyRight = false; break;

			case KeyEvent.VK_LEFT: keyViewLeft = false; break;
			case KeyEvent.VK_RIGHT: keyViewRight = false; break;
			case KeyEvent.VK_UP: keyViewUp = false; break;
			case KeyEvent.VK_DOWN: keyViewDown = false; break;
		}
	}
}
