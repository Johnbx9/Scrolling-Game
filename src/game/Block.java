package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Block extends AARect {

	public static final int MIN_SIZE = 100;
	public static final int MAX_SIZE = 300;
	
	private static int height = 50;
	
	private boolean moving, first;
	
	private boolean collidable;
	
	private BufferedImage tile1, tile2, tile3, startingTile;
	private ImageIcon tile1Icon, tile2Icon, tile3Icon, startingTileIcon;	
	private int tilePicker;
	
	protected Animation tiles;
	
	public Block() {
		super(0, 0, 130, 0);
		collidable = false;
		first = false;
		tiles = new Animation("./src/resources/tile", 4, 20);
	}
	
	public Block(int x, int y, int w) {
		super(x, y, w, height);
		collidable = true;
		moving = true;
		first = false;
		tiles = new Animation("./src/resources/tile", 4, 20);
	}
	
	public void update() {
		if (isOffscreen()) {
			stop();
			collidable = false;
		}
	}
	
	public void draw(Graphics g) {
		if(first)
		{
			/*System.out.println("tilePicker = " + this.tilePicker);
			System.out.println("x = " + this.x);
			System.out.println("y = " + this.y);
			System.out.println("w = " + this.w);
			System.out.println("h = " + this.h);
			System.out.println("moving = " + this.moving);
			System.out.println("colliable = " + this.collidable);*/
			g.drawImage(tiles.getImageAt(0), x, y, null);
		}
			//startingTileIcon.paintIcon(null, g, x, y);
		else if(tilePicker == 0)
		{
			/*System.out.println("tilePicker = " + this.tilePicker);
			System.out.println("x = " + this.x);
			System.out.println("y = " + this.y);
			System.out.println("w = " + this.w);
			System.out.println("h = " + this.h);
			System.out.println("moving = " + this.moving);
			System.out.println("colliable = " + this.collidable);*/
			g.drawImage(tiles.getImageAt(1), x, y, null);
		}
			//tile1Icon.paintIcon(null, g, x, y);
		else if(tilePicker == 1)
		{
			/*System.out.println("tilePicker = " + this.tilePicker);
			System.out.println("x = " + this.x);
			System.out.println("y = " + this.y);
			System.out.println("w = " + this.w);
			System.out.println("h = " + this.h);
			System.out.println("moving = " + this.moving);
			System.out.println("colliable = " + this.collidable);*/
			g.drawImage(tiles.getImageAt(2), x, y, null);
		}
			//tile2Icon.paintIcon(null, g, x, y);
		else if(tilePicker == 2)
		{
			/*System.out.println("tilePicker = " + this.tilePicker);
			System.out.println("x = " + this.x);
			System.out.println("y = " + this.y);
			System.out.println("w = " + this.w);
			System.out.println("h = " + this.h);
			System.out.println("moving = " + this.moving);
			System.out.println("colliable = " + this.collidable);*/
			g.drawImage(tiles.getImageAt(3), x, y, null);
		}
		//g.setColor(Color.RED);
		//g.fillRect(x, y, w, height);
	}
	
	public void move() {
		moving = true;
	}
	
	public void stop() {
		moving = false;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public void setTilePicker(int t)
	{
		this.tilePicker = t;
	}
	
	public boolean isOffscreen() {
		return (y > ScrollingGame.getHeight());
	}
	
	public boolean isCollidable() {
		return collidable;
	}
	
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	
	public void makeStartingTile()
	{
		//this.tileIcon.setImage(this.startingTile);
		first = true;
		setW(1157);
		setH(389);
	}

	public int getTilePicker() {
		return tilePicker;
	}
}
