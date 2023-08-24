package com.pinkstudios.entities;

import java.awt.image.BufferedImage;

public class Boomerang extends Entity{

	public Boomerang(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.setMask(4, 5, 23, 25);
	}

}
