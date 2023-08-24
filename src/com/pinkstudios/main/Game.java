package com.pinkstudios.main;

import com.pinkstudios.entities.*;
import com.pinkstudios.graficos.Achivements;
import com.pinkstudios.graficos.Inventory;
import com.pinkstudios.graficos.Spritesheet;
import com.pinkstudios.graficos.UI;
import com.pinkstudios.world.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable,KeyListener{
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	private BufferedImage image;

	public static boolean hasGun = false, hasBoom = false;
	
	private boolean cheats = false;
	public final static int WIDTH = 480;
	public final static int HEIGHT = 240;
	public final static int SCALE = 2;
	
	public static int CUR_LEVEL = 1,MAX_LEVEL = 10;
	
	public static List<Entity> entities;
	public static List<Blob> enemies;
	public static List<Stalk> enemies2;
	public static List<Shroon> enemies3;
	public static List<BulletShoot> bullets;
	public static List<EnemyBulletShoot> enemyBullets;
	public static Spritesheet spritesheet;
	public static Spritesheet backmenu;
	public static Spritesheet title;
	
	public static World world;
	
	public static Player player;
	
	public static boolean enterP = false;
	
	public static String gameState = "MENU";
	
	InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("font.ttf");
	InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("font.ttf");
	InputStream stream3 = ClassLoader.getSystemClassLoader().getResourceAsStream("font.ttf");
	InputStream stream4 = ClassLoader.getSystemClassLoader().getResourceAsStream("font.ttf");
	InputStream stream5 = ClassLoader.getSystemClassLoader().getResourceAsStream("font.ttf");
	public static Font font8;
	public static Font font18;
	public static Font font25;
	public static Font font30;
	public static Font font40;
	
	public static UI UI;
	public static Menu menu;
	public static GamePhases phases;
	
	public int[] pixels;
	
	public static Random rand;
	
	public void mensagem() {
		System.out.println();
	}
	
	public Game(){
		Sound.musicBackground.loop();
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_BGR);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		//Iniciar obj.
		UI = new UI();
		title = new Spritesheet("/title.png");
		backmenu = new Spritesheet("/title-screen.png");
		phases = new GamePhases();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Blob>();
		enemies2 = new ArrayList<Stalk>();
		enemies3 = new ArrayList<Shroon>();
		bullets = new ArrayList<BulletShoot>();
		enemyBullets = new ArrayList<EnemyBulletShoot>();
		spritesheet = new Spritesheet("/Spritesheet.png");
		player = new Player(0,0,32,32,spritesheet.getSprite(0, 64, 32, 32));	
		entities.add(player);
		world = new World("/levels/level1.png");
		menu = new Menu();
		
		try {
			font8 = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(9f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			font18 = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(15f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			font25 = Font.createFont(Font.TRUETYPE_FONT, stream5).deriveFont(20f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			font30 = Font.createFont(Font.TRUETYPE_FONT, stream3).deriveFont(30f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		try {
			font40 = Font.createFont(Font.TRUETYPE_FONT, stream4).deriveFont(40f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void initFrame() {
		frame = new JFrame("Pink Dungeon 1.0");
		frame.add(this);
		//Icone janela
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/window_icon.png"));
		}catch(IOException e) {
		}
		frame.setIconImage(img);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Game game = new Game();
		game.start();
	}
	
	public static void updateGame(int level) {
		World.restartGame("level"+level+".png");
		Game.gameState = "NORMAL";
	}
	
	
	public void tick(){
		
		if(menu.pause) {
			gameState = "PAUSE";
		}
		
		if(gameState == "GAME_WIN") {
			if(menu.enter) {
				Achivements.reset();
				gameState = "MENU";
			}
		}
		
		if(gameState == "NORMAL") {
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).tick();
			}
			for(int i = 0; i < enemyBullets.size(); i++) {
				enemyBullets.get(i).tick();
			}
			
		}else if(gameState == "MENU" || gameState == "PAUSE") {
			menu.tick();
		}else if(gameState == "PHASE_FINAL") {
			phases.tick();
		}
		
	}
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(100,100,100));
		g.fillRect(0,0,WIDTH,HEIGHT);
		/*
		 * Renderizacao Jogo
		 */
		
			world.render(g);
			Collections.sort(entities,Entity.nodeSorter);
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).render(g);
			}
			for(int i = 0; i < enemyBullets.size(); i++) {
				enemyBullets.get(i).render(g);
			}
			 /*
			 * 
			 */
			UI.render(g);
			Inventory.render(g);
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE, null);
			g.setColor(Color.black);
			g.setFont(font18);
			g.drawString(""+player.ammo, 17*2, 19*3);
			g.drawString(""+CUR_LEVEL, 19*2, 29*3);
			g.drawString(""+(enemies.size()+enemies2.size()+enemies3.size()), 19*2, 39*3);
			if(gameState == "GAME_OVER") {
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(new Color(0,0,0,100));
					g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
					g.setFont(font40);
					g.setColor(Color.white);
					g.drawString("GAME OVER!",((WIDTH/2)*SCALE)-60*SCALE, (HEIGHT/2)*SCALE);
					g.setFont(font25);
					g.drawString("Pressione ENTER para reiniciar a fase",((WIDTH/2)*SCALE)-90*SCALE, ((HEIGHT/2)*SCALE) + 50);
			}else if(gameState == "GAME_WIN") {
					Graphics2D g2 = (Graphics2D) g;
					g2.setColor(new Color(0,0,0,100));
					g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
					g.setFont(new Font("Arial",Font.BOLD,40));
					g.setColor(Color.white);
					g.drawString("PARABENS! Voce ganhou o jogo!",((WIDTH/2)*SCALE)-160*SCALE, (HEIGHT/2)*SCALE);
					g.setFont(new Font("Arial",Font.BOLD,30));
					g.drawString("-> Pressione ENTER para voltar ao menu <-",((WIDTH/2)*SCALE)-155*SCALE, ((HEIGHT/2)*SCALE) + 50);
			}else if(gameState == "MENU" || gameState == "PAUSE") {
				menu.render(g);
			}else if(gameState == "PHASE_FINAL") {
				phases.render(g);
		}
		if(cheats) {
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.drawString("MODO CHEAT", 0, 480-40);
		}

		bs.show();
	}
	
	public void run(){
		requestFocus();
		long lastTime = System.nanoTime();
		double frameAmount = 60.0;
		double ns = 1000000000 / frameAmount;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta>= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+frames);
				frames = 0;
				timer+= 1000;
			}
		}
		stop();
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			if(gameState == "MENU" || Menu.pauseMenu) 
				menu.up = true;
			else
				player.up = true;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
				if(gameState == "MENU" || Menu.pauseMenu)
					menu.down = true;
				else
					player.down = true;
			}
		if(e.getKeyCode() == KeyEvent.VK_X ||
				e.getKeyCode() == KeyEvent.VK_SPACE){
			player.shoot = true;
			}
		
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			player.up = false;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
			}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(gameState == "GAME_OVER") {
				GamePhases.deaths++;
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
				gameState = "NORMAL";
			}else{
				menu.enter = true;
				enterP = true;
			}
			}
		if(e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_Z) {
			player.shift = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(!(gameState == "MENU" && gameState == "")) {
				menu.pause = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_1) {
			player.selected = "GUN";
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			player.selected = "BOOMERANG";
		}
		if(e.getKeyCode() == KeyEvent.VK_3) {
			player.selected = "";
		}
		if(e.getKeyCode() == KeyEvent.VK_4) {
			player.selected = "";
		}
		if(e.getKeyCode() == KeyEvent.VK_5) {
			player.selected = "";
		}
		if(e.getKeyCode() == KeyEvent.VK_F1) {
			if(cheats) {
				World.restartGame("level1.png");
				Game.hasBoom = false;
				Game.hasGun = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_F12) {
			if(cheats) {
				cheats = false;
			}else {
				cheats = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_F2) {
			if(cheats)
				World.restartGame("level2.png");
		}
		if(e.getKeyCode() == KeyEvent.VK_F3) {
			if(cheats)
				World.restartGame("level3.png");
		}
		if(e.getKeyCode() == KeyEvent.VK_F4) {
			if(cheats)
				World.restartGame("level4.png");
		}
		if(e.getKeyCode() == KeyEvent.VK_F5) {
			if(cheats)
				World.restartGame("level5.png");
		}
		if(e.getKeyCode() == KeyEvent.VK_F6) {
			if(cheats)
				World.restartGame("level6.png");
		}
		if(e.getKeyCode() == KeyEvent.VK_F7) {
			if(cheats)
				World.restartGame("level7.png");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
