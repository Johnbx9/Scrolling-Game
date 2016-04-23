package game;

// Axis Aligned Rectangle
public class AARect {

	protected int x;
	protected int y;
	
	protected int w; // width
	protected int h; // height
	
	public AARect(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void moveUpBy(int dy) {
		y -= dy;
	}
	
	public void moveDownBy(int dy) {
		y += dy;
	}
	
	public void moveLeftBy(int dx) {
		x -= dx;
	}
	
	public void moveRightBy(int dx) {
		x += dx;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public boolean contains(int mx, int my)
	   {
	      return (mx > x) && (mx < x + w) && (my > y) && (my < y + h);
	   }
}
