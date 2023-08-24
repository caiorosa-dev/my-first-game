package com.pinkstudios.entities;

import com.pinkstudios.main.Game;
import com.pinkstudios.world.Camera;
import com.pinkstudios.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BulletShoot extends Entity{

	public double damage;
	public double dx;
	public double dy;
	private double speed = 8;
	public boolean isPoison;
	
	private int life = 60,curLife = 0;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite,int dx,int dy,double damage) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		this.damage = damage;
		this.setMask(10, 10, 3, 3);
	}
	
	public void checkCollisionBush() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bush) {
				if(Entity.isColidding(this, atual)) {
					isPoison = true;
					life = 100;
				}
			}
		}
	}
	
	public void tick() {
		
		checkCollisionBush();
		
		if(!(World.isFreeDynamic((int)(x+(dx*speed)), (int)(y+(dy*speed)), width, height))) {
			Game.bullets.remove(this);
		}
		
		x+=dx*speed;
		y+=dy*speed;
		curLife++;
		if(curLife >= life) {
			Game.bullets.remove(this);
		}
	}
	
	public void render(Graphics g) {
		
		if(!isPoison)
			g.setColor(Color.YELLOW);
		else
			g.setColor(new Color(0x8A2BFF));
		g.fillRect(this.getX() - Camera.x,this.getY() - Camera.y,width,height);
	}
	
}
