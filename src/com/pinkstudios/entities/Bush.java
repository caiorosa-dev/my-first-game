package com.pinkstudios.entities;

import java.awt.image.BufferedImage;

public class Bush extends Entity{

	public Bush(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.setMask(2, 3,27,28);
	}

}
