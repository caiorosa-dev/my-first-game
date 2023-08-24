package com.pinkstudios.graficos;

import com.pinkstudios.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
	
	public BufferedImage hud;
	
	public Achivements achiv;
	public Inventory inv;
	
	public void render(Graphics g) {
		g.setColor(new Color(0x840000));
		g.fillRect(18, 8, 36, 9);
		g.setColor(new Color(0xcf3636));
		g.fillRect(18, 8, (int)((Game.player.life/Game.player.maxLife)*36), 9);
		achiv = new Achivements();
		inv = new Inventory();
		hud = Game.spritesheet.getSprite(0, 192, 96, 64);
		g.drawImage(hud, 0, 0, null);
		achiv.render(g);
		
	}
	
}
