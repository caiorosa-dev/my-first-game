package com.pinkstudios.entities;

import java.awt.image.BufferedImage;

public class Ammo extends Entity {

	public Ammo(int x, int y, int width, int heith, BufferedImage sprite) {
		super(x, y, width, heith, sprite);
		this.setMask(9, 5, 14, 24);
	}

}
