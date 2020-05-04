
public class Entity {
	int x, y;
	float vx, vy;
	int w, h;
	String name;
	
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
	
	
	
}
