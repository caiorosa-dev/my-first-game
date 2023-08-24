package com.pinkstudios.entities;

import com.pinkstudios.main.Game;
import com.pinkstudios.world.Camera;
import com.pinkstudios.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BoomerangShoot extends Entity{

	public int dx,dy;
	public int damage = 2;
	private double speed = 5;
	public boolean isBacking = false;
	private int index = 0,maxIndex=2;
	
	private BufferedImage[] animation;
	private int animationFrames = 0,maxAnimationFrames = 4;
	private int frames = 0,maxFrames = 90;
	
	public BoomerangShoot(int x, int y, int width, int height, BufferedImage sprite,int dx,int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		this.setMask(13, 11, width, height);
		this.depth = 2;
		
		animation = new BufferedImage[2];
		
		for(int i =0; i < 2; i++) {
			animation[i] = Game.spritesheet.getSprite(0 + (i*32), 352, 32, 32);
		}
	
	}

	public void tick() {
		
		animationFrames++;
		if(animationFrames == maxAnimationFrames) {
			animationFrames = 0;
			index++;
			if(index == maxIndex) {
				index = 0;
			}
		}
		
		if(Game.player.shoot) {
			isBacking = true;
		}
		
		frames++;
		if(isBacking == false) {
			if(frames == maxFrames) {
				frames = 0;
				isBacking = true;
			}
			if(World.isFreeDynamic((int)(x+dx*speed), (int)(y+dy*speed), 23,24 )) {
				x+=dx*speed;
				y+=dy*speed;
			}else {
				isBacking = true;
			}
			
			
			
		}else {
			if(x < Game.player.getX()) {
				x+=speed;
			}else if(x > Game.player.getX()) {
				x-=speed;
			}
			
			if(y < Game.player.getY()) {
				y+=speed;
			}else if(y > Game.player.getY()){
				y-=speed;
			}
				
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(animation[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
}
