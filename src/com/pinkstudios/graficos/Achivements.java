package com.pinkstudios.graficos;

import com.pinkstudios.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Achivements {

	public static boolean ach1 = false,ach2 = false,ach3 = false,ach4 = false,ach5 = false,ach6 = false,ach7 = false,ach8 = false,ach9 = false,ach10 = false;
	
	public static BufferedImage achivement1 = Game.spritesheet.getSprite(97, 202, 10, 10);
	public static BufferedImage achivement2 = Game.spritesheet.getSprite(111, 202, 10, 10);
	public static BufferedImage achivement3 = Game.spritesheet.getSprite(125, 202, 10, 10);
	public static BufferedImage achivement4 = Game.spritesheet.getSprite(97, 215, 10, 10);
	public static BufferedImage achivement5 = Game.spritesheet.getSprite(101, 215, 10, 10);
	public static BufferedImage achivement6 = Game.spritesheet.getSprite(125, 215, 10, 10);
	public static BufferedImage achivement7 = Game.spritesheet.getSprite(97, 228, 10, 10);
	public static BufferedImage achivement8 = Game.spritesheet.getSprite(111, 228, 10, 10);
	public static BufferedImage achivement9 = Game.spritesheet.getSprite(125, 228, 10, 10);
	public static BufferedImage achivement10 = Game.spritesheet.getSprite(97, 241, 10, 10);
	
	public static void reset() {
		ach1 = false;
		ach2 = false;
		ach3 = false;
		ach4 = false;
		ach5 = false;
		ach6 = false;
		ach7 = false;
		ach8 = false;
		ach9 = false;
		ach10 = false;
	}
	
	public void render(Graphics g) {
		
		if(ach1) {
			g.drawImage(achivement1, 64, 1, null);
		}
		if(ach2) {
			g.drawImage(achivement2, 64, 1+13, null);
		}
		if(ach3) {
			g.drawImage(achivement3, 64, 1+13+13, null);
		}
		if(ach4) {
			g.drawImage(achivement4, 64, 1+13+13+13, null);
		}
		if(ach5) {
			g.drawImage(achivement5, 64, 1+13+13+13+13, null);
		}
		if(ach6) {
			g.drawImage(achivement6, 78, 1, null);
		}
		if(ach7) {
			g.drawImage(achivement7, 78, 1+13, null);
		}
		if(ach8) {
			g.drawImage(achivement8, 78, 1+13+13, null);
		}
		if(ach9) {
			g.drawImage(achivement9, 78, 1+13+13+13, null);
		}
		if(ach10) {
			g.drawImage(achivement10, 78, 1+13+13+13+13, null);
		}
	}
}
