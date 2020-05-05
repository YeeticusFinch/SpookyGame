
public class Entity {
	int x, y;
	int vx = 0, vy = 0;
	int w, h;
	String name;
	boolean dead = false;
	
	int icon; //each number corresponds to an image (example: 1.png)
	
	public Entity(int x, int y, int w, int h, int icon) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void update() {
		x+=vx;
		y+=vy;
	}
	
}
