package org.sm.lab6;

import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class TransformUtility {
	private TransformGroup translationGroup = new TransformGroup();
	private TransformGroup rotationGroup = new TransformGroup();

	private Transform3D translationTransform = new Transform3D();
	private Transform3D rotationTransform = new Transform3D();

	double x, y, z, rotX, rotY, rotZ;

	public TransformUtility(Node... objects) {
		for(Node n: objects) {
			translationGroup.addChild(n);
		}

		translationGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		translationGroup.setTransform(translationTransform);

		rotationGroup.addChild(translationGroup);
		rotationGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotationGroup.setTransform(rotationTransform);
	}

	public void translate(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;

		translationTransform.setTranslation(new Vector3d(this.x, this.y, this.z));
		translationGroup.setTransform(translationTransform);
	}

	public void rotate(double rotX, double rotY, double rotZ) {
		this.rotX += rotX;
		this.rotY += rotY;
		this.rotZ += rotZ;


		if(this.rotX != 0) rotationTransform.rotX(this.rotX);
		if(this.rotY != 0) rotationTransform.rotY(this.rotY);
		if(this.rotZ != 0) rotationTransform.rotZ(this.rotZ);

		rotationGroup.setTransform(rotationTransform);
	}

	public void rotateMul(double rotX, double rotY, double rotZ) {
		this.rotX += rotX;
		this.rotY += rotY;
		this.rotZ += rotZ;

		Transform3D rotationX = new Transform3D();
		Transform3D rotationY = new Transform3D();
		Transform3D rotationZ = new Transform3D();

		if(this.rotX != 0) rotationX.rotX(this.rotX);
		if(this.rotY != 0) rotationY.rotY(this.rotY);
		if(this.rotZ != 0) rotationZ.rotZ(this.rotZ);

		rotationZ.mul(rotationX);

		rotationGroup.setTransform(rotationZ);
	}


	public void setRotation(double rotX, double rotY, double rotZ) {
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;


		if(this.rotX != 0) rotationTransform.rotX(this.rotX);
		if(this.rotY != 0) rotationTransform.rotY(this.rotY);
		if(this.rotZ != 0) rotationTransform.rotZ(this.rotZ);

		rotationGroup.setTransform(rotationTransform);
	}

	public Node asNode() {
		return rotationGroup;
	}
}
