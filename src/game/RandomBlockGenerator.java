package game;

import java.awt.Graphics;
import java.util.Random;

public class RandomBlockGenerator {
	
	private Block[] blocks;
	
	private int index;
	
	private int count;
	
	public RandomBlockGenerator(Block[] blocks) {
		this.blocks = blocks;
		initBlocks();
		
		index = 1;
		count = 1;
	}
	
	private void initBlocks() {
		for (int i = 1; i < blocks.length; i ++) {
			blocks[i] = new Block();
		}
	}
	
	public void update() {
		count ++;
		if (count > 40) {
			generateBlock(index);
			count = 0;
		}
		
		index++;
		
		if (index >= blocks.length) {
			index = 1;		
			//generateBlock(index);
		}
	}
 	
	public void generateBlock(int index) {
		int width = ScrollingGame.getWidth();
		
		Random randSize = new Random();
		Random randXLoc = new Random();
		int randTile = randSize.nextInt(3);
		int randX = randXLoc.nextInt(4);
		//switch(randTile)
		if(randTile == 0)
		{
			blocks[index].setTilePicker(0);
			blocks[index].w = 130;
		}
		else if(randTile == 1)
		{
			blocks[index].setTilePicker(1);
			blocks[index].w = 192;
		}
		else if(randTile == 2)
		{
			blocks[index].setTilePicker(2);
			blocks[index].w = 256;
		}
		
//		blocks[index].setW((int)(Math.random() * Block.MAX_SIZE));
//		blocks[index].setH((int)(Math.random() * Block.MAX_SIZE));

//		if (blocks[index].w < Block.MIN_SIZE) {
//			blocks[index].setW(Block.MIN_SIZE);
//		}
//		if (blocks[index].h < Block.MIN_SIZE) {
//			blocks[index].setH(Block.MIN_SIZE);
//		}
//		if(index - 1 == 0 || index - 1 == 1)
//		{
			blocks[index].setX((int)(Math.random() * width));
			if(blocks[index].x > (width - 100))
				blocks[index].x -= 100;
		/*	if ((blocks[index].x + blocks[index].w > (width - 100)) || (blocks[index].x < 100)) 
		 * {
		 
				if(blocks[index].getTilePicker() == 2)
				{
					if(randX == 0)
						blocks[index].setX((width / 6));
					else if(randX == 1)
						blocks[index].setX((width * 3 / 8));
					else if(randX == 2)
						blocks[index].setX((width / 2));
					else if(randX == 3)
						blocks[index].setX((width * 2 / 3));
					else if(randX == 4)
						blocks[index].setX(width / 4);
				}
				else if(randX == 0)
					blocks[index].setX((width / 4));
				else if(randX == 1)
					blocks[index].setX((width * 3 / 8));
				else if(randX == 2)
					blocks[index].setX((width / 2));
				else if(randX == 3)
					blocks[index].setX((width * 2 / 3));
				else if(randX == 4)
					blocks[index].setX(width * 3 / 4);
			}*/
/*		}
//		else 
		{
			while((blocks[index].x + blocks[index].w > (width - 100)) || (blocks[index].x < 100))
			{
				int previousX = blocks[index-1].getX();
				int percentX = (int)(Math.random() * previousX);
				if(percentX > previousX / 2)
					blocks[index].setX(previousX + percentX / 4);
				else
					blocks[index].setX(previousX - percentX / 4);
			}
			
		}*/
			//blocks[index].setX((width - 100) - blocks[index].w);//blocks[index].x - blocks[index].w);
		
	/*	else if (blocks[index].x < 100) {
			if(randX == 0)
			{
				blocks[index].setX(50);
			}
			else if(randX == 1)
			{
				blocks[index].setX(150);
			}
			else if(randX == 3)
			{
				blocks[index].setX(250);
			}
			else if(randX == 4)
			{
				blocks[index].setX(350);
			}
			else if(randX == 5)
			{
				blocks[index].setX(450);
			}
			else if(randX == 6)
			{
				blocks[index].setX(550);
			}
			
			//blocks[index].setX(100);//blocks[index].x + (0 - blocks[index].x));
		}*/
		blocks[index].setY(0 - blocks[index].h);
		
		//blocks[index].setH(50);
		
		blocks[index].move();
		
	/*	if(index == 0)
		{
			randTile = randSize.nextInt(4);
			//switch(randTile)
			if(randTile == 0)
			{
				blocks[index].setTilePicker(0);
				blocks[index].w = 130;
			}
			else if(randTile == 1)
			{
				blocks[index].setTilePicker(1);
				blocks[index].w = 192;
			}
			else if(randTile == 3)
			{
				blocks[index].setTilePicker(2);
				blocks[index].w = 256;
			}
		}*/
	}
}
