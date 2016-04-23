package game;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class GameCore implements Runnable,
										  KeyListener,
						   				  MouseListener,
										  MouseMotionListener,
										  MouseWheelListener {
	
	private static final DisplayMode[] DISPLAY_MODES = {
		new DisplayMode(1280, 800, 32, 0),
		new DisplayMode(1280, 800, 24, 0),
		new DisplayMode(1280, 800, 16, 0),
		new DisplayMode( 800, 600, 32, 0),
		new DisplayMode( 800, 600, 24, 0),
		new DisplayMode( 800, 600, 16, 0)
	};
	
	protected ScreenManager screen;
	
	private boolean running;
	
	//JLabel scoreText, score;
	
	public final void stop() {
		System.exit(0);
		//running = false;
	}
	
	public final void run() {
		try {
			initialize();
			gameLoop();
		}
		finally{
			screen.restoreScreen();
		}
	}
	
	private void initialize() {
		screen = new ScreenManager();
		
		DisplayMode displayMode = screen.findFirstCompatibleMode(DISPLAY_MODES);
		screen.setFullScreen(displayMode);
		
		running = true;
		
		JFrame frame = screen.getFullScreenWindow();
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		
		//scoreText.setText("Score: ");
		//score.setText("0");
		
		init();
	}
	
	public abstract void init();
	
	public final Image loadImg(URL url) {
		try {
			return ImageIO.read(url);
		} 
		catch (IOException e) {}
		
		return null;
	}
	
	public abstract void loadImgs();
	
	private final void gameLoop() {
		int frameTime = 16;
		int sleepTime;
		
		long startTime = System.currentTimeMillis();
		
		while (running) {
			gameUpdate();
			
			drawScreen();
			
			sleepTime = frameTime - (int)(System.currentTimeMillis() - startTime);
			
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				}
				catch(InterruptedException e) {
				}
			}
			
			startTime = System.currentTimeMillis();
		}
	}
	
	public abstract void gameUpdate();
	
	public void drawScreen() {
		Graphics2D g = screen.getGraphics();
		
		g.setColor(Color.blue);
		g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		
        draw(g);
        g.dispose();
        
        screen.update();
	}
	
	public abstract void draw(Graphics g);
	
}
