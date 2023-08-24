package com.pinkstudios.entities;

import java.awt.image.BufferedImage;

public class FinalEn extends Entity{

	public FinalEn(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		depth = 1;
		this.setMask(12, 12,10,10);
	}
	
}
