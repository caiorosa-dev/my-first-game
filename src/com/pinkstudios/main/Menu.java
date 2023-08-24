package com.pinkstudios.main;

import com.pinkstudios.graficos.Achivements;
import com.pinkstudios.world.World;

import java.awt.*;
import java.io.*;


public class Menu {
	public String[] options = {"Novo Jogo","Carregar Jogo","Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public static boolean saveExists,saveGame;
	
	public boolean pause;

	public static boolean pauseMenu;
	
	public boolean down,up;

	public boolean enter;
	
	public void tick() {		
		
		checkSave();
		
		if(up) {
			up = false;
			currentOption --;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		
		if(down) {
			down = false;
			currentOption ++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(pause) {
			pause = false;
			pauseMenu = true;
		}
		
		if(enter) {
			enter = false;
			if(options[currentOption] == "Novo Jogo") {
				if(pauseMenu) {
					pauseMenu = false;
					Game.gameState = "NORMAL";
				}else {
					Game.gameState = "NORMAL";
					World.restartGame("level1.png");
					Achivements.reset();
				}
			}else if(options[currentOption] == "Sair"){
				if(!pauseMenu) {
					System.exit(1);
				}else{
					Game.gameState = "NORMAL";
					pauseMenu = false;
					Game.gameState = "MENU";
					currentOption = 0;
				}
					
			}else if(options[currentOption] == "Carregar Jogo"){
				if(pauseMenu) {
					/*Salvar
						String[] opt1 = {"level"};
						int[] opt2 = {Game.CUR_LEVEL};
						saveGame(opt1,opt2,10);
						*/
					}else {
					//Carregar
						Game.gameState = "NORMAL";
				}
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(pauseMenu) {
			g2.setColor(new Color(0,0,0,230));
			g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.drawImage(Game.title.getSprite(0, 0, 470, 235), 238, 0, null);
		}else {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.drawImage(Game.backmenu.getSprite(0, 0, 960, 480), 0, 0, null);
		}
		
		
		//g.setFont(Game.font18);
		//g.drawString("Jogo feito por Caio Rosa e Eduardo Marchi", 230,120*3+115);
		g.setFont(Game.font25);
		//Novo jooj
		if(!pauseMenu) {
			g.setColor(new Color(0x000000));
			if(options[currentOption] == "Novo Jogo") {
				if(pauseMenu) {
					g.drawString("- Continuar Jogo", 350, 220);
				}else {
					g.drawString("- Novo Jogo", 350, 220);
				}
			}else {
				if(pauseMenu) {
					g.drawString("Continuar Jogo", 350, 220);
				}else {
					g.drawString("Novo Jogo", 350, 220);
				}
			}
			//Continuar jooj
			if(options[currentOption] == "Carregar Jogo") {
				if(pauseMenu) {
					g.drawString("- Salvar Jogo (DESATIVADO)", 350, 250);
				}else {
					g.drawString("- Continuar Jogo", 350, 250);
				}
			}else {
				if(pauseMenu) {
					g.drawString("Salvar Jogo (DESATIVADO)", 350, 250);
				}else {
					g.drawString("Continuar Jogo", 350, 250);
				}
			}
			
			//Sair jooj
			if(!pauseMenu) {
				if(options[currentOption] == "Sair") {
					g.drawString("- Sair", 350, 280);
				}else {
					g.drawString("Sair", 350, 280);
				}
			}else {
				if(options[currentOption] == "Sair") {
					g.drawString("- Voltar ao menu", 350, 280);
				}else {
					g.drawString("Voltar ao menu", 350, 280);
				}
			}
	}else{
		g.setColor(new Color(0xFFFFFF));
		if(options[currentOption] == "Novo Jogo") {
			if(pauseMenu) {
				g.drawString("- Continuar Jogo", 350, 220);
			}else {
				g.drawString("- Novo Jogo", 350, 220);
			}
		}else {
			if(pauseMenu) {
				g.drawString("Continuar Jogo", 350, 220);
			}else {
				g.drawString("Novo Jogo", 350, 220);
			}
		}
		//Continuar jooj
		if(options[currentOption] == "Carregar Jogo") {
			if(pauseMenu) {
				g.drawString("- Salvar Jogo (DESATIVADO)", 350, 250);
			}else {
				g.drawString("- Continuar Jogo", 350, 250);
			}
		}else {
			if(pauseMenu) {
				g.drawString("Salvar Jogo (DESATIVADO)", 350, 250);
			}else {
				g.drawString("Continuar Jogo", 350, 250);
			}
		}
		
		//Sair jooj
		if(!pauseMenu) {
			if(options[currentOption] == "Sair") {
				g.drawString("- Sair", 350, 280);
			}else {
				g.drawString("Sair", 350, 280);
			}
		}else {
			if(options[currentOption] == "Sair") {
				g.drawString("- Voltar ao menu", 350, 280);
			}else {
				g.drawString("Voltar ao menu", 350, 280);
			}
		}
	}
	}
	
	public void checkSave() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
			
	}
	

	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = null;
						for(int i = 0; i < val.length;i++) {
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {
					System.out.println(e);
				}
				
			}catch(FileNotFoundException e) {
				System.out.println(e);
			}
		}
		
		return line;
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0;i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			if(saveExists){
				switch(spl2[0])
				{
					case "level":
						int a = Integer.valueOf(spl2[1]);
						Game.updateGame(a);
						pauseMenu = false;
						break;
			}
			}

		}
	}
	
	
	public void saveGame(String[] val1,int [] val2,int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		for(int i = 0;i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				value[n]+=encode;
				current+=value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1) 
					write.newLine();
			} catch (IOException e) {
				e.printStackTrace();
				}
		try {
			write.flush();
			write.close();
		}catch (IOException e) {}
		}
		
		
	}

}
