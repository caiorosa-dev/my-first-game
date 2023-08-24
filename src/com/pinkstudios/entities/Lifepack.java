package com.pinkstudios.entities;

import java.awt.image.BufferedImage;

public class Lifepack extends Entity{

	public Lifepack(int x, int y, int width, int heith, BufferedImage sprite) {
		super(x, y, width, heith, sprite);
		this.setMask(3, 10, 26, 19);
	}

}
