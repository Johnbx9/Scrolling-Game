package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Menu
{
	private BufferedImage menuImg;
	private ImageIcon menuImgIcon;

	private boolean showMenu;
	
	public Menu(String name) 
	{	
		try {
			this.menuImg = ImageIO.read(new File("./src/resources/Menu/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		showMenu = false;
		
		this.menuImgIcon = new ImageIcon();
		this.menuImgIcon.setImage(this.menuImg);
		
	}

	public void draw(Graphics g) 
	{
		menuImgIcon.paintIcon(null,  g, 0, 0);
	
		//g.setColor(Color.GRAY);
		//g.fillRect(x, y, w, h);
		//g.setColor(Color.BLUE);
		//g.drawRect(x, y, w, h);
		
			
	}
	
	public void showMenu()
	{
		showMenu = true;
	}
	
	public void hideMenu()
	{
		showMenu = false;
	}
}
