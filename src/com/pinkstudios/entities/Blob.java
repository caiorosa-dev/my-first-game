package com.pinkstudios.entities;

import com.pinkstudios.main.Game;
import com.pinkstudios.main.GamePhases;
import com.pinkstudios.world.Camera;
import com.pinkstudios.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Blob extends Entity {
	
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	private BufferedImage[] rightDamagedEnemy;
	private BufferedImage[] leftDamagedEnemy;
	
	private double speed = 1;

	//private boolean oneDamage;
	
	private boolean isDamaged;
	private boolean moved = false;
	private int index = 0,maxIndex=3;
	public int frames=0,maxFrames=5;
	private int life = 3;
		
	private boolean isKnockback;
	private int DamageFrames = 0,maxDamageFrames = 15;
	private int right_dir = 0,left_dir = 1,dir = 0;
	
	public Blob(int x, int y, int width, int heith, BufferedImage sprite) {
		super(x, y, width, heith, sprite);
		setMask(3, 3, 26, 26);
		
		depth = 0;
		
		rightEnemy = new BufferedImage[4];
		leftEnemy = new BufferedImage[4];
		rightDamagedEnemy = new BufferedImage[4];
		leftDamagedEnemy = new BufferedImage[4];
		
		for(int i =0; i < maxIndex; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(0 + (i*32), 256 , 32, 32);
			leftEnemy[i] = Game.spritesheet.getSprite(0 + (i*32), 288, 32, 32);
			rightDamagedEnemy[i] = Game.spritesheet.getSprite(96 + (i*32), 256 , 32, 32);
			leftDamagedEnemy[i] = Game.spritesheet.getSprite(96 + (i*32), 288, 32, 32);
		}
	}

	public void tick() {
		if(Game.rand.nextInt(100) < 30) {
			//if(calcDistance(this.getX(),this.getY(),Game.player.getX(),Game.player.getY()) < 300) {
				if (this.isColiddingWithPlayer() == false) {
					if(!isColiddingEnemy()) {
						moved = false;
						if(x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) &&
								!isColidding((int)(x+speed), this.getY()) && World.isFreeBush((int)(x+speed), this.getY()))  {
							moved = true;
							dir = right_dir;
							x+=speed;
	
							
						}else if(x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())&&
								!isColidding((int)(x-speed), this.getY())&& World.isFreeBush((int)(x-speed), this.getY())) {
							moved = true;
							dir = left_dir;
							x-=speed;
	
						}
						if(y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))&&
								!isColidding(this.getX(), (int)(y+speed)) && World.isFreeBush(this.getX(), (int)(y+speed))) {
							moved = true;
							y+=speed;
							
						}else if(y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed))&&
								!isColidding(this.getX(), (int)(y-speed))&& World.isFreeBush(this.getX(), (int)(y-speed))) {
							moved = true;
							y-=speed;
						}
					}
				}else {
					//Colidindo com player
					if(Game.rand.nextInt(100) < 40) {
						Game.player.life-=2;
						Game.player.isDamaged = true;
				}
				}
				
				if(moved) {
					frames++;
					if(frames == maxFrames) {
						frames = 0;
						index++;
						if(index == maxIndex) {
							index = 0;
						}
					}
				//}
			}
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
	
	public boolean isColiddingEnemy() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Stalk) {
				if(Entity.isColidding(this, atual)) {
					return true;
				}
			}
		}
		return false;
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
		Game.enemies.remove(this);
	}
	
	public void coliddingBoom() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof BoomerangShoot) {
				if(Entity.isColidding(this, atual)) {
					isDamaged = true;
					if(Game.rand.nextInt(100) < 20) {
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
					isKnockback = true;
					if(e.dx == 1) {
						this.setX(getX() + 15);
					}else if(e.dx == -1){
						this.setX(getX() - 15);
					}
					if(e.dy == 1) {
						this.setY(getY() + 15);
					}
					if(e.dy == -1) {
						this.setY(getY() - 15);
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
	
	
	
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,mwidth,mheight);
		for(int i =0; i < Game.enemies.size(); i++){
			Blob e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+ masky,mwidth,mheight);
			if(enemyCurrent.intersects(targetEnemy)){
				if(!isKnockback) {
					return true;
				}else {
					e.isKnockback = true;
				}
			}
		}
		
		
		return false;
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
					g.drawImage(rightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == left_dir) {
					g.drawImage(leftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}else {
				if(dir == right_dir) {
					g.drawImage(rightDamagedEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == left_dir) {
					g.drawImage(leftDamagedEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
		}
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth,mheight);
	}
}
	
	
	

