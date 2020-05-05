import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {

	int x, y, w, h, vx, vy, iconID;
	
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
		this.iconID = icon;
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
		this.iconID = icon;
	}
	
	public void edit(int x, int y, int vx, int vy, int w, int h, int icon, PApplet g, String name) {
		if (iconID != icon) {
			this.icon = Resources.getImage(Resources.fp+icon+".png", g);
			this.icon.resize(w, h);
		} else if (this.w != w || this.h != h) {
			this.icon.resize(w,  h);
		}
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
		x += vx;
		y += vy;
	}
	
}
