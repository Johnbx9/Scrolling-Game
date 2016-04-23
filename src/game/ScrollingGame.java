package game;

import game.SoundLibrary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ScrollingGame extends GameCore {
	
	private static final int NUMTREASURES = 5, TREASURESREQUIRED = 5, SCROLLTIME = 15000;
	
	public static void main(String[] args) {
		ScrollingGame game = new ScrollingGame();
		game.run();
	}
	
	public static SoundLibrary soundLibrary = new SoundLibrary();
	
	private static int width;
	private static int height;
	
	private boolean gameOver, paused, doublePoints, gameStarted;
	
	private int scrollSpeed, treasureGood, treasureBad, treasureStreak;
	private int score;
	private long gameStartTimer, streakTimer, scrollTimer, deathTimer;
	
	private Background bg;
	private Player player;
	private Treasure[] treasure;
	
	private int[] treasureNormal, treasureDouble; 
	
	private Menu startMenu, endMenu, scoreMenu;
	private Button startButton, quitButton;
//	private Block startingBlock;
	
	private Block[] blocks = new Block[100];
	private RandomBlockGenerator blockGenerator;
	
	private BufferedImage streakImage;
	private ImageIcon streakIcon;
	
	private Image bgImg;
	
	public void init() {
		Window window = screen.getFullScreenWindow();
		
		window.setFocusTraversalKeysEnabled(false);
		
		width  = screen.getWidth();
		height = screen.getHeight();
		
		startMenu= new Menu("TreasureHunter");
		endMenu = new Menu("GAMEOVER");
		scoreMenu = new Menu("GAMEOVERSCORE");
		startButton = new Button(265, 475, 115, 39, "Play");
		quitButton = new Button(420, 475, 115, 39, "Quit");
		
		gameStarted = false;
		gameOver = false;
		paused = true;
		score = 0;
		
		scrollTimer = 
		scrollSpeed = 2;
		
	//	loadImgs();
		try {
			this.streakImage = ImageIO.read(new File("./src/resources/treasure/TREASURESTREAK.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		streakIcon = new ImageIcon();
		this.streakIcon.setImage(this.streakImage);
		
		bg = new Background(bgImg);
		blockGenerator = new RandomBlockGenerator(blocks);

		blocks[0] = new Block(width/2 - 500, 20, 2220);
		blocks[0].makeStartingTile();
		player = new Player(width/2, 0, 53, 64);
		
		treasureGood = 0;
		treasureBad = 0;
		treasureStreak = 0;
		doublePoints = false;
		treasure = new Treasure[NUMTREASURES];
		treasureNormal = new int[10];
		treasureDouble = new int[10];
		
		for(int i = 0; i < NUMTREASURES; i++)
			treasure[i] = new Treasure(i * 100, 5, 40, 40);
		
		player.handleCollision(blocks[0]);
		ScrollingGame.soundLibrary.playAudio("music");
		initializeKeyEvents();
	}
	
	private boolean exit_pressed;
	private boolean pause_pressed;
	
	private boolean UP_pressed;
	private boolean DN_pressed;
	private boolean LT_pressed;
	private boolean RT_pressed;
	
	private void initializeKeyEvents() {
		exit_pressed = false;
		
		UP_pressed = false;
		DN_pressed = false;
		LT_pressed = false;
		RT_pressed = false;
	}

//	
	
	public void loadImgs() {
		bgImg = loadImg(ScrollingGame.class.getResource("/resources/texture_dirt_1600x1200.jpg"));
	}

	public void gameUpdate() {
		checkSystemInput();
		if(gameStarted)
		{
			if(System.currentTimeMillis() - scrollTimer > SCROLLTIME)
			{
				scrollTimer = System.currentTimeMillis();
				scrollSpeed++;
			}
			if (!paused) 
			{
				checkGameInput();
			
				player.update();
				player.moveDownBy(scrollSpeed);
				if (player.isOutOfScreen()) {
					gameOver = true;
					gameStarted = false;
					player.jump();
					player.update();
					ScrollingGame.soundLibrary.stopAudio("music");
					ScrollingGame.soundLibrary.playAudio("death");
					deathTimer = System.currentTimeMillis();
				}
				if((System.currentTimeMillis() - streakTimer > 3000) && doublePoints)
				{
					treasureStreak = 0;
					doublePoints = false;
					
				}
				for(int i = 0; i < NUMTREASURES; i++)
				{
					treasure[i].fallDown();
					if(player.hasCollectedTreasure(treasure[i]))
					{
						if(treasure[i].getFallSpeed() == 9 )
							{
								ScrollingGame.soundLibrary.playAudio("badcollect");
								treasureBad++;
								treasureStreak = 0;
								if(doublePoints)
							{
								doublePoints = false;
								streakTimer = 10000;
							}
						}
						else
						{
							ScrollingGame.soundLibrary.playAudio("collect");
							treasureGood++;
							treasureStreak++;
							
							if(treasureStreak >= TREASURESREQUIRED && !doublePoints)
							{
								streakTimer = System.currentTimeMillis();
								doublePoints = true;
							}
						}
						if(doublePoints)
						{
							increaseScore(2 * treasure[i].getWorth());
							treasureDouble[treasure[i].getFallSpeed()]++;
						}
						else
						{
							increaseScore(treasure[i].getWorth());
							treasureNormal[treasure[i].getFallSpeed()]++;
						}
						player.handleTreasureCollection(treasure[i]);
					
					}
				}

				bg.scrollDown(scrollSpeed);
				blockGenerator.update();
			
//			startingBlock.update();
//			startingBlock.moveDownBy(scrollSpeed);
			
			// blocks update
				for (Block block: blocks) 
				{
					if (block.isMoving()) 
					{
						block.update();
						block.moveDownBy(scrollSpeed);

						if (block.y > player.y + player.h) {
						block.setCollidable(true);
						}
					}
				}
			
				handleCollisions();
			}
		}
		else if (!gameStarted)
			player.update();
	}
	
	private void checkSystemInput() {
		if (exit_pressed) {
			stop();
		}
		
		if (pause_pressed) {
			paused = !paused;
			pause_pressed = false;
		}
	}
	
	private void checkGameInput() {
		if (!player.isInAir()) {
			if (UP_pressed) {
				player.jump();
				ScrollingGame.soundLibrary.playAudio("jump");
			}
		}
		else if(player.getDoubleJumpReady() && player.getUpReleasedInAir())
		{
			if (UP_pressed) {
				player.jump();
				player.setDoubleJumpReady(false);
				ScrollingGame.soundLibrary.playAudio("jump");
			}
		}
//		if (DN_pressed) {
//			player.moveDownBy(4);
//		}
		if (LT_pressed) {
			player.moveLeftBy(4);
		}
		if (RT_pressed) {
			player.moveRightBy(4);
		}
	}
	
	private void handleCollisions() {
		if (player.isInAir()) {
			for (Block block: blocks) {
				if (block.isCollidable() ) {
					if (player.hasCollidedWith(block)) {
						player.handleCollision(block);
						player.setUpReleasedInAir(false);
						player.setDoubleJumpReady(true);
						
					}
				}
			}			
		}
	}

	public void increaseScore(int score)
	{
		this.score += score;
	}
	
	public void draw(Graphics g) {
		bg.draw(g);	
//		startingBlock.draw(g);
		
		for (Block block: blocks) {
			if (block.isMoving()) {
				block.draw(g);				
			}
		}
		
		for(int i = 0; i < NUMTREASURES; i++)
			treasure[i].draw(g);
		if(player.y < height)
			player.draw(g);
		if(doublePoints)
		{
			if(System.currentTimeMillis() - streakTimer < 1000) 
			streakIcon.paintIcon(null, g, player.x - 25, player.y - 55);
		}
		
		g.setColor(Color.BLACK);
		g.drawString("Score:  " + score, 10, 25);
		g.drawString("Good Treasures:  " + treasureGood, 10, 35);
		g.drawString("Bad Treasures:  " + treasureBad, 10, 45);
		g.drawString("Streak Timer: " + treasureStreak + "  " + doublePoints + "  " + streakTimer, 10, 55);
		
		if(!gameStarted && !gameOver)
		{
			startMenu.draw(g);
			startButton.draw(g);
			quitButton.draw(g);
		}
		

		if ((System.currentTimeMillis() - deathTimer > 2500) && gameOver) 
		{
			endMenu.draw(g);
			scoreMenu.draw(g);
			for(int i = 3; i < 10; i++)
			{
				if(i == 9)
				{
					g.drawString("Normal x " + treasureNormal[i] + "   " + (treasureNormal[i] * i * 100), 265, (i - 3) * 45 + 218);
				}
				else
				{
					g.drawString("Normal x " + treasureNormal[i] + "   " + (treasureNormal[i] * i * 100), 265, (i - 3) * 45 + 210);
					g.drawString("Double x " + treasureDouble[i] + "   " + (treasureDouble[i] * i * 100), 265, (i - 3) * 45 + 223);
				}
			}
			g.setFont(new Font("Arial", Font.BOLD, 48));
			g.drawString("" + score, 470, 380);
			//Font font = new Font("arial", Font.BOLD, 20);
			//g.setFont(font);
			//g.setColor(Color.CYAN);
			//g.drawString("Game Over", width/2, height/2);
		}//
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_ESCAPE) exit_pressed = true;
		if (keyCode == KeyEvent.VK_P) pause_pressed = true;
		
		if (keyCode == KeyEvent.VK_SPACE) UP_pressed = true;
		if (keyCode == KeyEvent.VK_S) DN_pressed = true;
		if (keyCode == KeyEvent.VK_A) LT_pressed = true;
		if (keyCode == KeyEvent.VK_D) RT_pressed = true;
	}
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_SPACE) 
		{
			UP_pressed = false;
			player.setUpReleasedInAir(true);
		}	
		if (keyCode == KeyEvent.VK_S) 
			{
				DN_pressed = false;
			}
		if (keyCode == KeyEvent.VK_A) 
			{
				LT_pressed = false;
				player.notMoving();
			}
		if (keyCode == KeyEvent.VK_D) 
			{
				RT_pressed = false;
				player.notMoving();
			}
	}
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
	    int my = e.getY();
	    if(startButton.contains(mx, my))
	    {
	    	paused = false;
	    	gameStarted = true;
	    	scrollTimer = System.currentTimeMillis();
	    }
	    
	    if(quitButton.contains(mx,my))
	    {
	    	System.exit(0);
	    }
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseWheelMoved(MouseWheelEvent e) {}

}
