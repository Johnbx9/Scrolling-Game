package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Button extends AARect
{
	private BufferedImage buttonImg;
	private ImageIcon buttonImgIcon;
	private boolean showButton;
	
	public Button(int x, int y, int w, int h, String name) 
	{
		super(x, y, w, h);
		try {
			this.buttonImg = ImageIO.read(new File("./src/resources/Menu/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		showButton = true;
		
		this.buttonImgIcon = new ImageIcon();
		this.buttonImgIcon.setImage(this.buttonImg);
	}
	
	public void draw(Graphics g)
	{
		buttonImgIcon.paintIcon(null,  g, x, y);
	}
	
	public void showButton()
	{
		showButton = true;
	}
	
	public void hideButton()
	{
		showButton = false;
	}
	
	
}
