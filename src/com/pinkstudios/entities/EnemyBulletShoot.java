package com.pinkstudios.entities;

import com.pinkstudios.main.Game;
import com.pinkstudios.world.Camera;
import com.pinkstudios.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyBulletShoot extends Entity{

	public double damage;
	public double dx;
	public double dy;
	private double speed = 8;
	
	private int life = 90,curLife = 0;
	
	
	public EnemyBulletShoot(int x, int y, int width, int height, BufferedImage sprite,int dx,int dy,double damage) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		this.damage = damage;
		this.setMask(10, 10, 3, 3);
		
	}

	public void tick() {
		if(!(World.isFreeDynamic((int)(x+(dx*speed)), (int)(y+(dy*speed)), width, height))) {
			Game.enemyBullets.remove(this);
		}
		
		x+=dx*speed;
		y+=dy*speed;
		curLife++;
		if(curLife >= life) {
			Game.enemyBullets.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(0x8A2BFF));
		g.fillRect(this.getX() - Camera.x,this.getY() - Camera.y,width,height);
	}
	
}
