package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;



public class Treasure extends AARect
{
	private int fallSpeed, pointsWorth,
				low = 3, range = 7;
	
	private int width = ScrollingGame.getWidth(), 
				height = ScrollingGame.getWidth();
	private Random randSpeed;
	private boolean collected;
	
	private BufferedImage[] treasureImage;
	private ImageIcon treasureIcon;
	
	
	public Treasure(int x, int y, int w, int h)
	{
		super(x, y, w, h);
		collected = false;
		randSpeed = new Random();
		this.fallSpeed = randSpeed.nextInt(range) + low;
		setWorth();
		setDimensions();
		treasureImage = new BufferedImage[range + low];
		
		for(int i = low; i < range + low; i++)
		{
			try {
				treasureImage[i] = ImageIO.read(new File("./src/resources/treasure/treasure" + (i - low) + ".png"));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		treasureIcon = new ImageIcon();
		treasureIcon.setImage(treasureImage[fallSpeed]);
	}
	
	public void fallDown()
	{
		if((y < ScrollingGame.getHeight()) && !collected)	
			moveDownBy(fallSpeed);
		else
    	{
    		int p = (int)(Math.random()*width);
    		if(p <= (1 / 10 * width))
    			p += 100;
    		else if (p >= (9 / 10 * width))
    			p -= 100;
    		this.x = p;
    		this.y = -50;
    		randSpeed = new Random();
    		this.fallSpeed = randSpeed.nextInt(range) + low;
    		setDimensions();
    		setWorth();
    		collected = false;
    		System.out.println(fallSpeed);
    		treasureIcon.setImage(treasureImage[fallSpeed]);
    		//System.out.println(p);		//checks values
    	}
	}
	
	public void draw(Graphics g)
	{
		//g.paintImage(treasureImage[fallSpeed], x, y, null);
		treasureIcon.paintIcon(null, g, x, y);
		//g.fillRect(x, y, w, h);
	//	super.draw(g);
	}
	
	public void moveDownBy(int dy)
	{
		y += dy;
	}
	
	public void setX(int dx)
	{
		x = dx;
	}
	
	public void setY(int dy)
	{
		y = dy;
	}

	public void setW(int w)
	{
		this.w = w;
	}
	
	public void setH(int h)
	{
		this.h = h;
	}
	
	public void setCollected()
	{
		collected = true;
	}
	
	public int getFallSpeed() 
	{
		return fallSpeed;
	}
	
	public void setWorth()
	{
		for(int i = low; i < range; i++)
		{
			if(i == fallSpeed)
				if(i == 9)
					pointsWorth = -900;
				else
					pointsWorth = i * 100;
		}
	}
	
	public int getWorth()
	{
		return pointsWorth;
	}
	
	public void setDimensions()
	{
		if(fallSpeed == 3)
		{
			setW(40);
			setH(37);
		}
		else if(fallSpeed == 4)
		{
			setW(39);
			setH(39);
		}
		else if(fallSpeed == 5)
		{
			setW(39);
			setH(38);
		}
		else if(fallSpeed == 6)
		{
			setW(38);
			setH(36);
		}
		else if(fallSpeed == 7)
		{
			setW(25);
			setH(39);
		}
		else if(fallSpeed == 8)
		{
			setW(39);
			setH(31);
		}
		else if(fallSpeed == 9)
		{
			setW(33);
			setH(40);
		}
	}
	
}
