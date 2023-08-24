package com.pinkstudios.entities;

import com.pinkstudios.graficos.Achivements;
import com.pinkstudios.main.Game;
import com.pinkstudios.main.GamePhases;
import com.pinkstudios.world.Camera;
import com.pinkstudios.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

	public boolean right,up,left,down;
	public double speed = 1.75;
	
	private int down_dir = 3;
	private int top_dir = 2;
	private int right_dir = 0;
	private int left_dir = 1;
	private int dir = 0;
	private int dirStop = 1;
	
	public boolean shift;
	public boolean dash;
	public int dashFrames=0,maxDashFrames=15;
	
	public String selected = "GUN";
	public String tempSelected = "";
	
	private boolean shootActive = false;
	
	public int framesShoot=0,maxFramesShoot=15;
	private int index = 0,maxIndex=3;
	public int frames=0,maxFrames=5;
	private int damageFrames = 0;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] topPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] rightDamagedPlayer;
	private BufferedImage[] leftDamagedPlayer;
	private BufferedImage[] topDamagedPlayer;
	private BufferedImage[] downDamagedPlayer;
	private BufferedImage dashRightPlayer;
	private BufferedImage dashLeftPlayer;
	private BufferedImage dashTopPlayer;
	private BufferedImage dashDownPlayer;
	private BufferedImage stopPlayer;
	private BufferedImage stopDamagedPlayer;
	
	public double life = 100;
	public double maxLife = 100;
	
	public static int wins;
	
	private int bushFrames = 0;
	
	public boolean shoot = false;
	
	public boolean isDamaged = false,bushDamage = false;
	
	//public boolean Game.hasGun = false, Game.hasBoom = false;
	
	private boolean moved = false;
	
	public int ammo = 0;
	
	public Player(int x, int y, int width, int heith, BufferedImage sprite) {
		super(x, y, width, heith, sprite);
		
		depth = 1;
		
		this.setMask(8, 0, 17, 32);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		topPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		rightDamagedPlayer = new BufferedImage[4];
		leftDamagedPlayer = new BufferedImage[4];
		topDamagedPlayer = new BufferedImage[4];
		downDamagedPlayer = new BufferedImage[4];
		dashRightPlayer = Game.spritesheet.getSprite(192, 192, 32, 32);
		dashLeftPlayer = Game.spritesheet.getSprite(192, 224, 32, 32);
		dashTopPlayer = Game.spritesheet.getSprite(192, 288, 32, 32);
		dashDownPlayer = Game.spritesheet.getSprite(192, 256, 32, 32);
		stopPlayer = Game.spritesheet.getSprite(0, 128, 32, 32);
		stopDamagedPlayer = Game.spritesheet.getSprite(128, 128, 32, 32);
		for(int i =0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 64, 32, 32);
			downPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 4*32, 32, 32);
			leftPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 96, 32, 32);
			topPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 5*32, 32, 32);
		}
		for(int i =0; i < 4; i++) {
			rightDamagedPlayer[i] = Game.spritesheet.getSprite(128 + (i*32), 64, 32, 32);
			downDamagedPlayer[i] = Game.spritesheet.getSprite(128 + (i*32), 4*32, 32, 32);
			leftDamagedPlayer[i] = Game.spritesheet.getSprite(128 + (i*32), 96, 32, 32);
			topDamagedPlayer[i] = Game.spritesheet.getSprite(128 + (i*32), 5*32, 32, 32);
		}
		
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*32 - Game.HEIGHT);
	}
	
	public void checkAll() {
		this.checkCollisionAmmo();
		this.checkCollisionLifePack();
		this.checkCollisionGun();
		this.checkCollisionBoom();
		this.checkCollisionBoomShoot();
		this.checkCollisionBush();
		this.checkCollisionFinal();
		this.coliddingEnemyBullet();
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Ammo) {
				if(Entity.isColidding(this, atual)) {
					ammo+=5;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionBoom() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Boomerang) {
				if(Entity.isColidding(this, atual)) {
					Game.hasBoom = true;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionBoomShoot() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof BoomerangShoot) {
				if(Entity.isColidding(this, atual)) {
					if(((BoomerangShoot) atual).isBacking) {
						Game.hasBoom = true;
						Game.entities.remove(atual);
					}
				}
			}
		}
	}
	
	public void checkCollisionGun() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColidding(this, atual)) {
					Game.hasGun = true;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionBush() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bush) {
				if(Entity.isColidding(this, atual)) {
					bushFrames++;
					if((bushFrames == 25)) {
						bushFrames = 0;
						bushDamage = true;
						isDamaged = true;
					}

				}
			}
		}
	}
	public void checkCollisionFinal() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof FinalEn) {
				if(Entity.isColidding(this, atual)) {
					if(Game.enemies.size() <= 0 && Game.enemies2.size() <= 0 
						&& Game.enemies3.size() <= 0) {
							if(!(Game.CUR_LEVEL > Game.MAX_LEVEL)) {
								wins++;
								Game.gameState = "PHASE_FINAL";
								if(wins == 1)
									Achivements.ach1 = true;
								if(wins == 2)
									Achivements.ach2 = true;
								if(wins == 3)
									Achivements.ach3 = true;
								if(wins == 4)
									Achivements.ach4 = true;
								if(wins == 5)
									Achivements.ach5 = true;
								if(wins == 6)
									Achivements.ach5 = true;
								if(wins == 7)
									Achivements.ach5 = true;
								if(wins == 8)
									Achivements.ach5 = true;
								if(wins == 9)
									Achivements.ach5 = true;
							}else {
								Game.gameState = "GAME_WIN";
								Achivements.ach6 = true;
							}
					}
				}
			}
		}
	}
	
	public void checkCollisionLifePack() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColidding(this, atual)) {
					life += 20;
					if(life > 100) {
						life = 100;
					}
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void coliddingEnemyBullet() {
		for(int i = 0; i < Game.enemyBullets.size(); i++) {
			EnemyBulletShoot e = Game.enemyBullets.get(i);
				if(Entity.isColidding(this, e)) {
					life-=e.damage;
					Game.enemyBullets.remove(i);
					return;
				}
		}
	}

	public void tick() {
		dash = false;
		moved = false;
		dir = 4;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			dir = right_dir;
			dirStop = 1;
			x+=speed;
		}else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			dir = left_dir;
			dirStop = 2;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))) {
			moved = true;
			dir = top_dir;
			y-=speed;
			
		}else if(down && World.isFree(this.getX(),(int)(y+speed))){
			moved = true;
			dir = down_dir;
			y+=speed;
			}
	
		if(shift) {
			if(dir != 4) {
				if(dashFrames == 0) {
					tempSelected = selected;
				}
				dash = true;
				dashFrames++;
				selected = "";
				if(dashFrames == maxDashFrames) {
					dashFrames = 0;
					shift = false;
					dash = false;
					selected = tempSelected;
				}
			}
		}
		
		if(dash) {
			if(!(dir == 4)) {
				if(dir == right_dir && World.isFree((int)(x+speed*2),this.getY())) {
					x+=speed*2;
				}else if(dir == left_dir && World.isFree((int)(x-speed*2),this.getY())) {
					x-=speed*2;
				}
				if(dir == top_dir && World.isFree(this.getX(),(int)(y-speed*2))) {
					y-=speed*2;
				}else if(dir == down_dir && World.isFree(this.getX(),(int)(y+speed*2))) {
					y+=speed*2;
				}
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
		}
		
		if(life <= 0) {
			GamePhases.deaths++;
			Game.gameState = "GAME_OVER";
		}

		if(isDamaged) {
			damageFrames++;
			if(!bushDamage) {
				if(this.damageFrames == 8) {
					damageFrames = 0;
					isDamaged = false;
					}
				}else if(this.damageFrames == 20){
					if(life > 5) {
						damageFrames = 0;
						isDamaged = false;
						bushDamage = false;
						life-=0.5;
				}
			}
		}
		
		if(shoot) {
			framesShoot++;
			if(framesShoot > maxFramesShoot) {
				framesShoot = 0;
				shootActive = true;
				shoot = false;
			}else {
				shootActive = false;
			}
		}
		
		
		if(shootActive) {
			shootActive = false;
			int dy = 0;
			int dx = 0;
			int px = 18;
			int py = 14;
			
			if(dir == 4) {
				if(dirStop == 1) {
					px = 28;
					dx = 1;
				}else {
					px = -2;
					dx = -1;
				}
			}
			
			if(dir == right_dir) {
				px = 28;
				dx = 1;
			}else if(dir == left_dir){
				px = -2;
				dx = -1;
			}
			if(dir == top_dir) {
				dy = -1;
				py = 0;
			}else if(dir == down_dir) {
				dy = 1;
			}
				
			if(selected == "GUN") {
				if(Game.hasGun && ammo > 0) {
					ammo--;
					BulletShoot b = new BulletShoot(this.getX()+px,this.getY()+py,3,3,null,dx,dy,1);
					Game.bullets.add(b);
					}
				}else if (selected == "BOOMERANG") {
					if(Game.hasBoom) {
						Game.hasBoom = false;
						BoomerangShoot b = new BoomerangShoot(this.getX(),this.getY(),11,11,null,dx,dy);
						Game.entities.add(b);
				}
			}

		}
		
		
		
		checkAll();
		
		updateCamera();
		
		
	}
		public void render(Graphics g) {
			if(!isDamaged) {
				if(dir == right_dir) {
					if(!dash) {
						if(selected == "GUN") {
							if(Game.hasGun) 
								g.drawImage(Entity.GUN_RIGHT, this.getX()+4 - Camera.x, this.getY()+2 - Camera.y, null);
						}else if(selected == "BOOMERANG") {
							if(Game.hasBoom)
								g.drawImage(Entity.BOOM_RIGHT, this.getX()+5 - Camera.x, this.getY()+5 - Camera.y, null);
						}
						g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						g.drawImage(dashRightPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
					}

				}else if(dir == left_dir) {
					if(!dash) {
						if(selected == "GUN") {
							if(Game.hasGun) 
								g.drawImage(Entity.GUN_LEFT, this.getX() - 4 - Camera.x, this.getY()+2 - Camera.y, null);
						}else if(selected == "BOOMERANG") {
							if(Game.hasBoom)
								g.drawImage(Entity.BOOM_LEFT, this.getX() - 4 - Camera.x, this.getY()+5 - Camera.y, null);
						}
						g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						g.drawImage(dashLeftPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
					}


				}else if(dirStop == 1 && dir == 4) {
					if(!dash) {
						g.drawImage(stopPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
						if(selected == "GUN") {
							if(Game.hasGun) 
								g.drawImage(Entity.GUN_RIGHT, this.getX()+6 - Camera.x, this.getY()+2 - Camera.y, null);
						}else if(selected == "BOOMERANG") {
							if(Game.hasBoom)
								g.drawImage(Entity.BOOM_RIGHT, this.getX()+5 - Camera.x, this.getY()+5 - Camera.y, null);
						}

					}

				}else if(dirStop == 2 && dir == 4) {
					g.drawImage(stopPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
					if(selected == "GUN") {
						if(Game.hasGun) 
							g.drawImage(Entity.GUN_LEFT, this.getX()-4 - Camera.x, this.getY()+2 - Camera.y, null);
					}else if(selected == "BOOMERANG") {
						if(Game.hasBoom)
							g.drawImage(Entity.BOOM_LEFT, this.getX()-4 - Camera.x, this.getY()+5 - Camera.y, null);
					}
				}
				if(dir == top_dir) {
					if(!dash) {
						g.drawImage(topPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						g.drawImage(dashTopPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
					}
					
				}else if(dir == down_dir) {
					if(!dash) {
						g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
					}else {
						g.drawImage(dashDownPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
					}
					
				}
		}else {
			if(dir == right_dir) {
				g.drawImage(rightDamagedPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftDamagedPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == 4) {
				g.drawImage(stopDamagedPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if(dir == top_dir) {
				g.drawImage(topDamagedPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == down_dir) {
				g.drawImage(downDamagedPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
	}
}
