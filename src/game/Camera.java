package game;

public class Camera {
	
	public static int x = 0;
	public static int y = 0;
	public static int z = 0;
	
	public static int angle = 0;
	
	public static void rotateLeftBy(int degrees) {
		angle += degrees;
	}	
	
	public static void rotateRigthBy(int degrees) {
		angle -= degrees;
	}
	
	public static void moveLeftBy(int dx) {
		x -= dx;
	}
	
	public static void moveRightBy(int dx) {
		x += dx;
	}
	
	public static void moveUpBy(int dy) {
		y += dy;
	}
	
	public static void moveDownBy(int dy) {
		y -= dy;
	}
	
	public static void moveInBy(int dz) {
		z -= dz;
	}
	
	public static void moveOutBy(int dz) {
		z += dz;
	}
}
