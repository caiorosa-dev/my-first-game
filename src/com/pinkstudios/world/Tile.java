package com.pinkstudios.world;

import com.pinkstudios.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(32, 0, 32, 32);
	public static BufferedImage TILE_NEW = Game.spritesheet.getSprite(64, 0, 32, 32);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
	
}
