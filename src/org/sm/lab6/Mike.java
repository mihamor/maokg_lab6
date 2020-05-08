package org.sm.lab6;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Map;

public class Mike {
	private TransformUtility body;
	private TransformUtility leftLeg;
	private TransformUtility rightLeg;
	private TransformUtility rightHand;
	private TransformUtility leftHand;
	private TransformUtility mainMove1;
	private TransformUtility mainMove2;
	private TransformUtility mainModel1;
	private TransformUtility mainModel2;

	public Mike(Canvas canvas) throws FileNotFoundException {
		TransformUtility[] transfoms = loadObject("mike.obj",
				"monstr", "left_leg", "right_leg", "right_hand", "left_hand");

		body = transfoms[0];
		leftLeg = transfoms[1];
		rightLeg = transfoms[2];
		rightHand = transfoms[3];
		leftHand = transfoms[4];

		Material material = new Material();
		material.setAmbientColor (new Color3f(1, 1, 1));
		material.setDiffuseColor (new Color3f(1f, 1f, 1f));
		material.setSpecularColor(new Color3f(0.1f, 0.1f, 0.1f));
		material.setShininess(1f);
		material.setLightingEnable(true);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.COMBINE);

		TextureLoader textureLoader = new TextureLoader("ground.jpg", "RGB", canvas);
		Appearance ap = new Appearance();
		ap.setTexCoordGeneration(new TexCoordGeneration(
				TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_2));
		ap.setMaterial(material);
		ap.setTextureAttributes(texAttr);
		ap.setTexture(textureLoader.getTexture());

		TransformUtility ground = new TransformUtility(new Box(1000, 1000, 0.1f, ap));
		ground.translate(0, 0, -1);

		mainMove1 = new TransformUtility(
				body.asNode(), leftLeg.asNode(), rightLeg.asNode(), rightHand.asNode(), leftHand.asNode());
		mainMove2 = new TransformUtility(mainMove1.asNode());
		mainModel1 = new TransformUtility(mainMove2.asNode(), ground.asNode());
		mainModel2 = new TransformUtility(mainModel1.asNode());

		initRotation(-Math.PI/1.8, Math.PI, 0);
	}

	private static TransformUtility[] loadObject(String filename, String... groupNames) throws FileNotFoundException {
		Scene scene = new ObjectFile(ObjectFile.RESIZE).load(filename);
		BranchGroup root = scene.getSceneGroup();

		Map<String, Shape3D> nameMap = scene.getNamedObjects();

		root.removeAllChildren();

		TransformUtility[] ret = new TransformUtility[groupNames.length];

		for (int i = 0; i < groupNames.length; ++i) {
			ret[i] = new TransformUtility(nameMap.get(groupNames[i]));
		}

		return ret;
	}

	double legRotateDX = 0.03, bodyRotateDy = 0.01, handRotateDX = 0.08;

	public void update() {
		leftLeg.rotate(legRotateDX, 0, 0);
		rightLeg.rotate(-legRotateDX, 0, 0);
		rightHand.rotate(handRotateDX, 0, 0);
		leftHand.rotate(-handRotateDX, 0, 0);

		if(Math.abs(leftLeg.rotX) > 0.3) {
			legRotateDX *= -1;
			handRotateDX *= -1;
		}

		body.rotate(0, bodyRotateDy, 0);

		if(Math.abs(body.rotY) > 0.1) {
			bodyRotateDy *= -1;
		}

		double speed = 0.03;

		mainMove2.translate(speed * Math.sin(mainMove1.rotZ), -speed * Math.cos(mainMove1.rotZ), 0);
	}

	public void turnLeft() {
		mainMove1.rotateMul(0, 0, -0.05 );
	}

	public void turnRight() {
		mainMove1.rotateMul(0, 0, 0.05);
	}

	public void initRotation(double rotX, double rotY, double rotZ) {
		mainMove1.setRotation(-rotX, 0, 0);
		mainModel1.setRotation(rotX, 0, 0);
		mainModel2.setRotation(0, rotY, 0);
	}

	public void rotateModel(double rotX, double rotY, double rotZ) {
		mainMove1.rotate(-rotX, 0, 0);
		mainModel1.rotate(rotX, 0, 0);
		mainModel2.rotate(0, rotY, 0);
	}

	public Node asNode() {
		return mainModel2.asNode();
	}
}
