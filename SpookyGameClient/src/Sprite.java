import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {

	int x, y, w, h, vx, vy;
	
	PImage icon;
	
	String name = null;
	
	public Sprite(int x, int y, int vx, int vy, int w, int h, int icon, PApplet g) {
		this.icon = Resources.getImage(Resources.fp+icon+".png", g);
		this.icon.resize(w, h);
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.w = w;
		this.h = h;
	}
	
	public Sprite(int x, int y, int vx, int vy, int w, int h, int icon, PApplet g, String name) {
		this.icon = Resources.getImage(Resources.fp+icon+".png", g);
		this.icon.resize(w, h);
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.w = w;
		this.h = h;
		this.name = name;
	}
	
	public void draw(PApplet g) {
		g.image(icon, x, y);
		if (name != null)
			g.text(name, x+w/2, y+4*h/3);
	}
	
}
