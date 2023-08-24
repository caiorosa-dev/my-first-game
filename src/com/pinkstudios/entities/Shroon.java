package com.pinkstudios.entities;

import com.pinkstudios.main.Game;
import com.pinkstudios.main.GamePhases;
import com.pinkstudios.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shroon extends Entity{

	private BufferedImage[] animation;
	private BufferedImage[] animationDamaged;
	
	private boolean isDamaged = false;
	private int DamageFrames = 0,maxDamageFrames = 15;
	private boolean shooted;
	
	private int index = 0,maxIndex=3;
	public int frames=0,maxFrames=30;
	private int life = 2;
	
	public Shroon(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth = 0;
		
		this.setMask(0, 0, 28, 31);
		
		animation = new BufferedImage[4];
		animationDamaged = new BufferedImage[4];
		for(int i =0; i < maxIndex; i++) {
			animation[i] = Game.spritesheet.getSprite(64 + (i*32), 352 , 32, 32);
			animationDamaged[i] = Game.spritesheet.getSprite(64 + (i*32), 384 , 32, 32);
		}
	}

	public void tick() {
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index == maxIndex) {
				index = 0;
			}
		}
		
		if(index == 2) {
			if(!shooted) {
				int px = 10+maskx;
				int py = 18;
				Game.enemyBullets.add(new EnemyBulletShoot(this.getX()+px,this.getY()+py,3,3,null,0,1,10));
				Game.enemyBullets.add(new EnemyBulletShoot(this.getX()+px,this.getY()+py,3,3,null,0,-1,10));
				Game.enemyBullets.add(new EnemyBulletShoot(this.getX()+px,this.getY()+py,3,3,null,1,0,10));
				Game.enemyBullets.add(new EnemyBulletShoot(this.getX()+px,this.getY()+py,3,3,null,-1,0,10));
				shooted = true;
			}

		}else {
			shooted = false;
		}
		
		testDamage();
		coliddingBoom();
		coliddingBullet();
		
		if(life <= 0) {
			destroySelf();
			return;
		}
		
	}
	
	public void testDamage() {
		if(isDamaged) {
			DamageFrames ++;
			if(DamageFrames == maxDamageFrames) {
				DamageFrames = 0;
				isDamaged = false;
			}
		}
	}
	
	public void coliddingBoom() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof BoomerangShoot) {
				if(Entity.isColidding(this, atual)) {
					isDamaged = true;
					if(Game.rand.nextInt(100) < 15) {
						life-=0.5;
					}
				}
			}
		}
	}
	
	public void coliddingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			BulletShoot e = Game.bullets.get(i);
				if(Entity.isColidding(this, e)) {
					isDamaged = true;
					life-=e.damage;
					Game.bullets.remove(i);
					return;
				}
			}
		}
	
	public void destroySelf() {
		GamePhases.kills+=1;
		Game.entities.remove(this);
		Game.enemies3.remove(this);
		System.out.println(Game.enemies3.size());
	}
	
	public void render(Graphics g) {
		if(isDamaged == false) {
			g.drawImage(animation[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else
			g.drawImage(animationDamaged[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
	}
	
}
