package com.pinkstudios.entities;

import java.awt.image.BufferedImage;

public class Weapon extends Entity{

	public Weapon(int x, int y, int width, int heith, BufferedImage sprite) {
		super(x, y, width, heith, sprite);
		setMask(2, 4, 29, 25);
	}
	
}
