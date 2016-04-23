package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Background {
	
	private int y;
	
	private int width;
	private int height;
	
	//private Image img;
	private BufferedImage img;
	private ImageIcon backgroundIcon;
	
	public Background(Image img) {
		y = 0;
		width  = ScrollingGame.getWidth();
		height = ScrollingGame.getHeight();
		
		//this.img = img;
		try {
			this.img = ImageIO.read(new File("./src/resources/Menu/BG.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		backgroundIcon = new ImageIcon();
		this.backgroundIcon.setImage(this.img);
	}
	
	public void draw(Graphics g) {
		//g.drawImage(img, width/2, y - height, width, height, null);
		//g.drawImage(img, width/2,          y, width, height, null);
		//g.drawImage(img, width/2, y + height, width, height, null);
		backgroundIcon.paintIcon(null, g, 0, 0);
	}
	
	public void scrollDown(int dy) {
		y += dy;
		
		if (y > img.getHeight(null)) {
			y -= img.getHeight(null);
		}
	}
}
