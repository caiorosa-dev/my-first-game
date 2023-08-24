package com.pinkstudios.main;

import com.pinkstudios.world.World;

import java.awt.*;

public class GamePhases {
	
	public static int kills;
	public static int deaths;
	public static int totalDeaths;
	public static int totalKills;
	
	public void tick() {
		
		if(Game.menu.enter) {
			Game.CUR_LEVEL++;
			Game.menu.enter = false;
			String newWorld = "level"+Game.CUR_LEVEL+".png";
			World.restartGame(newWorld);
			Game.gameState = "NORMAL";
			totalKills+=kills;
			totalDeaths+=deaths;
			kills = 0;
			deaths = 0;
		}
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//Renderizar
		g2.setColor(new Color(0,0,0,230));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setFont(Game.font18);
		g.setColor(new Color(0xFFFFFF));
		g.drawImage(Game.title.getSprite(0, 0, 470, 235), 238, 0, null);
		g.drawString("Voce morreu  |"+deaths+"|  vezes", 350, 260);
		g.drawString("Voce matou  |"+kills+"|  pragas", 350, 280);
		g.setFont(Game.font25);
		g.drawString("VOCE ELIMINOU AS PRAGAS DESTE JARDIM", 190, 230);
		g.setFont(Game.font8);
		g.drawString("Presione  `ENTER`  para ir ao proximo jardim", 330, 300);

	}
}
