package game;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends AARect{
		
	private final int GRAVITY = 1;
	private int velocity;
	
	private boolean inAir, moving, doubleJumpReady, upReleasedInAir,
					WL , WR, JL, JR;
	
	private int jumpPower;
	
	private Block blockUnderPlayer;
	
	protected Animation walkLeft, walkRight, jumpLeft, jumpRight;
	
	public Player(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		velocity = 0;
		jumpPower = 18;
		blockUnderPlayer = null;
		
		walkLeft = new Animation("./src/resources/sprites2/cb_lt_", 11, 10);
		walkRight = new Animation("./src/resources/sprites2/cb_rt_", 11, 10);
		jumpLeft = new Animation("./src/resources/sprites2/cb_jl_", 11, 10);
		jumpRight = new Animation("./src/resources/sprites2/cb_jr_", 11, 10);
		
		doubleJumpReady = true;
		upReleasedInAir = false;
		moving = false;
		WL = true;
		WR = false;
		JL = false;
		JR = false;
	}
	
	public void update() {
		if (inAir) {			
			if (velocity > 0) {
				moveUpBy(velocity);
			}
			else if (velocity < 0) {
				moveDownBy(-velocity);
			}
			
			velocity -= GRAVITY;
		}
		else { // not in air
			if ( (x > blockUnderPlayer.x + blockUnderPlayer.w) ||
					(blockUnderPlayer.x > x + h) ) {
				inAir = true;
			}
		}
		
 	}
	
	public void draw(Graphics g) {
		if(moving && WL)
			g.drawImage(walkLeft.getCurrentImage(), x, y, null);
		else if(moving && WR)
			g.drawImage(walkRight.getCurrentImage(), x, y, null);
		else if(inAir && JL)
			g.drawImage(jumpLeft.getCurrentImage(), x, y, null);
		else if(inAir && JR)
			g.drawImage(jumpRight.getCurrentImage(), x, y, null);
		else if(WL)
			g.drawImage(walkLeft.getStaticImage(), x, y, null);
		else if(WR)
			g.drawImage(walkRight.getStaticImage(), x, y, null);
		
	//	g.setColor(Color.BLUE);
		//g.drawRect(x, y, w, h);
	}
	
	public void jump() {
		inAir = true;
		moving = false;
		velocity = jumpPower;
	}
	
	public void doubleJump()
	{
		velocity += jumpPower;
	}
	
	public void landOnBlock(Block block) {
		inAir = false;
		y = block.y - h;
	}
	
	public void notMoving()
	{
		moving = false;
	}
	
	public void moveUpBy(int dy) {
		super.moveUpBy(dy);			
	}
	
	public void moveDownBy(int dy) {
		super.moveDownBy(dy);
	}
	
	public void moveLeftBy(int dx) {
		if (x + dx > 5) {
			moving = true;
			WL = true;
			JL = true;
			WR = false;
			JR = false;
			super.moveLeftBy(dx);
		}
	//	if (x > ScrollingGame.getWidth()/2 + dx) {
	//		super.moveLeftBy(dx);
	//	}
	}
	
	public void moveRightBy(int dx) {
		if (x + w < ScrollingGame.getWidth() - 5) {
			moving = true;
			WR = true;
			JR = true;
			WL = false;
			JL = false;
			super.moveRightBy(dx);
		}
		
		//if (x + w < ScrollingGame.getWidth() + ScrollingGame.getWidth()/2 - dx) {
			//super.moveRightBy(dx);
		//}
	}
	
	public boolean hasCollidedWith(Block target) {
		int x2 = target.x;
		int y2 = target.y;
		int w2 = target.w;
		int h2 = target.h;
		Line line = new Line(x2, y2, x2 + w, y2); 
		
		if(velocity <= 0 && hasCollidedWith(line))
		//return ((x < x2 + w2) && (x2 < x + w) && (y2 < y + h));
//		return ((x >= x2) &&
			//			(x + w <= x2 + w2) &&
			//			(y + h <= y2)) &&
			//			y <= y2 + h2;
			//	return ((x - 35 + w >= x2) &&
			//			(x + 35 <= x2 + w2) &&
			//			(y + h >= y) &&
			//			y + h <= y + h);
			//	return ((x >= x2 - 20) &&
			//			(x + w <= x2 + w2 + 20) &&
			//			(y + h <= y2) &&
			//			(y + h <= y2 + h2));
			//	return ((x + w >= x2) &&
			//			(x <= x2 + w2) &&
			//			(y + h <= y2));
			//	return ((x - 35 + w >= x2) &&
			//			(x + 35 <= x2 + w2) &&
			//			(y + h <= y2) &&
			//			y + h <= y2 + h);
		return ((x <= (x2 + w2)) &&
				((x + w) >= x2 ) &&
				((y + h) <= y2));
		else 
			return false;
	}
	
	public boolean hasCollectedTreasure(Treasure treasure)
	{
		int x2 = treasure.x;
		int y2 = treasure.y;
		int w2 = treasure.w;
		int h2 = treasure.h;
		
		Line left = new Line(x2, y2, x2 + w2, y2 + h2);
		Line top = new Line(x2, y2, x2 + w2, y2);
		Line right = new Line(x2 + w2, y2, x2 + w2, y2 + h2);
		Line bottom = new Line(x2, y2 + h2, x2 + w2, y2 + h2);
		if(hasCollidedWith(left) || hasCollidedWith(top) || 
		   hasCollidedWith(right) || hasCollidedWith(bottom))
		{
			return ((x <= (x2 + (w2 / 2))) &&
					((x + w) >= (x2 + (w2 / 2))) &&
					(y <= (y2 + (h2 / 2))) &&
					((y + h) >= (y2 + (h2 / 2))));
		}
		else
			return false;
//		{
//			treasure.setCollected();
//			return true;
////		}
//		else
//			return false;
	}
	
	public void handleCollision(Block block) {
		blockUnderPlayer = block;
		landOnBlock(block);
	}
	
	public void handleTreasureCollection(Treasure treasure)
	{
		treasure.setCollected();
	}
	
	public boolean isOutOfScreen() {
		return (y + h  > ScrollingGame.getHeight());
	}
	
	public boolean isInAir() {
		return inAir;
	}
	
	public void setUpReleasedInAir(boolean state)
	{
		this.upReleasedInAir = state;
	}
	
	public Boolean hasCollidedWith(Line line)
	{
		int x2 = line.x1;
		int y2 = line.y1;
		int w2 = line.w;
		int h2 = line.h;
		return (line.distanceTo(x + (w / 2), y + h )) < (h / 2);
	}

	public int getVelocty() 
	{
		return velocity;
	}

	public boolean getDoubleJumpReady() 
	{
		return doubleJumpReady;
	}

	public boolean getUpReleasedInAir() 
	{
		return upReleasedInAir;
	}

	public void setDoubleJumpReady(boolean state) 
	{
		doubleJumpReady = state;
	}
}
