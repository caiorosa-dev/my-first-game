package com.pinkstudios.graficos;

import com.pinkstudios.main.Game;

import java.awt.*;

public class Inventory {
	
	public static boolean gun;
	public static boolean boom;
	
	
	
	public static void render(Graphics g) {
		
		g.drawImage(Game.spritesheet.getSprite(224, 369,32, 15), 3, 240-17, null);
		
		if(Game.hasGun) {
			g.drawImage(Game.spritesheet.getSprite(226, 354,13, 13), 4, 240-16, null);
		}
		
		if(Game.hasBoom) {
			g.drawImage(Game.spritesheet.getSprite(241, 354,13, 13), 21, 240-16, null);
		}
		
		if(Game.player.selected == "GUN") {
			if(Game.hasGun)
				g.drawImage(Game.spritesheet.getSprite(198, 358,19, 19), 1, 240-19, null);
		}else if(Game.player.selected == "BOOMERANG") {
			if(Game.hasBoom)
				g.drawImage(Game.spritesheet.getSprite(198, 358,19, 19), 18, 240-19, null);
		}
		
	}
	
	
	
}
