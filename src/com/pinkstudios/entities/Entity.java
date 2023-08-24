package com.pinkstudios.entities;

import com.pinkstudios.main.Game;
import com.pinkstudios.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;

public class Entity {

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	public static BufferedImage FINAL_EN = Game.spritesheet.getSprite(96, 0, 32, 32);
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(0, 32, 32, 32);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(32, 32, 32, 32);
	public static BufferedImage BOOMERANG_EN = Game.spritesheet.getSprite(0, 384, 32, 32);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(2*32, 32, 32, 32);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(3*32, 32, 32, 32);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(3*32, 32, 32, 32);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(128, 32, 32, 32);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(0, 0, 32, 32);
	public static BufferedImage BUSH_EN = Game.spritesheet.getSprite(3*32, 0, 32, 32);
	public static BufferedImage BOOM_RIGHT = Game.spritesheet.getSprite(0, 352, 32, 32);
	public static BufferedImage BOOM_LEFT = Game.spritesheet.getSprite(32, 384, 32, 32);
	
	private BufferedImage sprite;
	protected int maskx,masky,mwidth,mheight;
	
	public int depth;
	
	public Entity(int x,int y,int width,int height,BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx,int masky,int mwidth,int mheight){
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getheight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	
}
