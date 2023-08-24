package com.pinkstudios.entities;

import com.pinkstudios.main.Game;
import com.pinkstudios.main.GamePhases;
import com.pinkstudios.world.Camera;
import com.pinkstudios.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Stalk extends Entity {
	
	private BufferedImage[] Enemy;
	private BufferedImage[] damagedEnemy;
	
	private double speed = 2.5;

	private boolean isKnockback;
	private int index = 0,maxIndex=4;
	public int frames=0,maxFrames=4;
	private int life = 2;
	private boolean isDamaged;
	private int DamageFrames = 0,maxDamageFrames = 10;
	
	public Stalk(int x, int y, int width, int heith, BufferedImage sprite) {
		super(x, y, width, heith, sprite);
		setMask(3, 0, 26, 32);
		
		depth = 0;
		
		Enemy = new BufferedImage[4];
		damagedEnemy = new BufferedImage[4];
		
		for(int i =0; i < maxIndex; i++) {
			Enemy[i] = Game.spritesheet.getSprite(0 + (i*32), 320 , 32, 32);
		}
		for(int i =0; i < maxIndex; i++) {
			damagedEnemy[i] = Game.spritesheet.getSprite(128 + (i*32), 320 , 32, 32);
		}

	}

	public void tick() {
			if(Game.rand.nextInt(100) < 30) {
				//if(calcDistance(this.getX(),this.getY(),Game.player.getX(),Game.player.getY()) < 300) {	
					if (this.isColiddingWithPlayer() == false) {
						if(x < Game.player.getX() && !isColidding((int)(x+speed), this.getY()) &&
								World.isFree((int)(x+speed), this.getY()) && World.isFreeBush((int)(x+speed), this.getY()))  {
							x+=speed;	
						}else if(x > Game.player.getX() && !isColidding((int)(x-speed), this.getY()) &&
								World.isFree((int)(x-speed), this.getY()) && World.isFreeBush((int)(x-speed), this.getY())) {
							x-=speed;
		
						}
						if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))&& !isColidding(this.getX(), (int)(y+speed)) &&	
								World.isFreeBush(this.getX(), (int)(y+speed))){
							y+=speed;
								
						}else if(y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))&& !isColidding(this.getX(), (int)(y-speed)) &&
								World.isFreeBush(this.getX(), (int)(y-speed))) {
							y-=speed;
							}
						}else {
							if(Game.rand.nextInt(100) < 50) {
								Game.player.life-=4;
								Game.player.isDamaged = true;
						}
					}
				}
				
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index == maxIndex) {
						index = 0;
					}
				//}
			}
		
		
		testDamage();
		coliddingBoom();
		coliddingBullet();
		
		if(life <= 0) {
			destroySelf();
			return;
	}
}
	public double calcDistance(int x1,int x2,int y1,int y2) {
		return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,mwidth,mheight);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),30,30);
		return enemyCurrent.intersects(player);
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
	
	public void destroySelf() {
		GamePhases.kills++;
		Game.entities.remove(this);
		Game.enemies2.remove(this);
	}
	
	public void coliddingBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			BulletShoot e = Game.bullets.get(i);
				if(Entity.isColidding(this, e)) {
					speed = 3;
					isKnockback = true;
					if(e.dx == 1) {
						this.setX(getX() + 20);
					}else if(e.dx == -1){
						this.setX(getX() - 20);
					}
					if(e.dy == 1) {
						this.setY(getY() + 20);
					}
					if(e.dy == -1) {
						this.setY(getY() - 20);
					}
					isDamaged = true;
					if(e.isPoison){
						life--;
					}
					life-=e.damage;
					Game.bullets.remove(i);
					return;
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
	
	private boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,mwidth,mheight);
		for(int i =0; i < Game.enemies2.size(); i++){
			Stalk e = Game.enemies2.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+ masky,mwidth,mheight);
			if(enemyCurrent.intersects(targetEnemy)){
				if(!isKnockback) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
				g.drawImage(Enemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			}else {
				g.drawImage(damagedEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth,mheight);
	}
}
	
	
	

