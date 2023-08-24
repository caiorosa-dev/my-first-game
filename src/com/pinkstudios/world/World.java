package com.pinkstudios.world;

import com.pinkstudios.entities.*;
import com.pinkstudios.graficos.Achivements;
import com.pinkstudios.graficos.Spritesheet;
import com.pinkstudios.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	
	public static final int TILE_SIZE = 32;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0, 0,map.getWidth(),map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getWidth(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						//Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOOR);
					}else if(pixelAtual == 0xFFFFFFFF) {
						//Parede
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF00A3E0) {
						//Secreto
						tiles[xx + (yy*WIDTH)] = new SecretTile(xx*32,yy*32,Tile.TILE_WALL);
					}else if(pixelAtual == 0xFF3C3C3C) {
						//Tile novo
						tiles[xx + (yy*WIDTH)] = new NewTile(xx*32,yy*32,Tile.TILE_NEW);
					}else if(pixelAtual == 0xFF007F00) {
						//Bush
						Game.entities.add(new Bush(xx*32,yy*32,32,32,Entity.BUSH_EN));
					}else if(pixelAtual == 0xFF0014FF) {
						//Player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
					}else if(pixelAtual == 0xFFFFDB00) {
						//Bullet
						Game.entities.add(new Ammo(xx*32,yy*32,32,32,Entity.BULLET_EN));
					}else if(pixelAtual == 0xFFFF00E6) {
						//Weapon
						Game.entities.add(new Weapon(xx*32,yy*32,32,32,Entity.WEAPON_EN));
					}else if(pixelAtual == 0xFFFF3AE9) {
						//Boomerang
						Game.entities.add(new Boomerang(xx*32,yy*32,32,32,Entity.BOOMERANG_EN));
					}else if(pixelAtual == 0xFF00FF00) {
						//Lifepack
						Lifepack pack = new Lifepack(xx*32,yy*32,32,32,Entity.LIFEPACK_EN);
						Game.entities.add(pack);
					}else if(pixelAtual == 0xFFBC89FF) {
						//Final
						if(Game.CUR_LEVEL == 1)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement1));
						if(Game.CUR_LEVEL == 2)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement2));
						if(Game.CUR_LEVEL == 3)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement3));
						if(Game.CUR_LEVEL == 4)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement4));
						if(Game.CUR_LEVEL == 5)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement5));
						if(Game.CUR_LEVEL == 6)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement6));
						if(Game.CUR_LEVEL == 7)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement7));
						if(Game.CUR_LEVEL == 8)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement8));
						if(Game.CUR_LEVEL == 9)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement9));
						if(Game.CUR_LEVEL == 10)
							Game.entities.add(new FinalEn(xx*32+12,yy*32+12,32,32,Achivements.achivement10));
						
					}else if(pixelAtual == 0xFFFF0000) {
						//Enemy
						Blob en = new Blob(xx*32,yy*32,32,32,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}else if(pixelAtual == 0xFFAA4A00) {
						//Enemy2
						Stalk en = new Stalk(xx*32,yy*32,32,32,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies2.add(en);
					}else if(pixelAtual == 0xFFFF5112) {
						//Enemy3
						Shroon en = new Shroon(xx*32,yy*32,32,32,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies3.add(en);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void restartGame(String level) {
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Blob>();
		Game.enemies2 = new ArrayList<Stalk>();
		Game.enemies3 = new ArrayList<Shroon>();
		Game.bullets = new ArrayList<BulletShoot>();
		Game.enemyBullets = new ArrayList<EnemyBulletShoot>();
		Game.spritesheet = new Spritesheet("/Spritesheet.png");
		Game.player = new Player(0,0,32,32,Game.spritesheet.getSprite(0, 64, 32, 32));
		Game.entities.add(Game.player);
		Game.world = new World("/levels/"+level);
		return;
	}
	
	public static boolean isFreeDynamic(int xnext,int ynext,int width,int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + width -1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height -1) / TILE_SIZE;
		
		int x4 = (xnext + width -1) / TILE_SIZE;
		int y4 = (ynext + height -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static boolean isFree(int xnext,int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE -1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE -1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static boolean isFreeBush(int xnext,int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE -1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE -1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof SecretTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof SecretTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof SecretTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof SecretTile));
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x /32;
		int ystart = Camera.y /32;
		
		int xfinal = xstart + (Game.WIDTH / 32) +5;
		int yfinal = ystart + (Game.HEIGHT / 32) +5;
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
			
		}
	}
	
	
}
