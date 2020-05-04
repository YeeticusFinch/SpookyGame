
public class Person extends Entity {

	int speed;
	
	public Person(int x, int y, int w, int h, int icon, int speed) {
		super(x, y, w, h, icon);
	}
	
	public void move(short dx, short dy) { // dx = 1 to move right, dx = -1 to move left, dx = 0 to not move in x direction....
		x += dx * speed;
		vx = dx*speed;
		y += dy * speed;
		vy = dy*speed;
	}
	
}
