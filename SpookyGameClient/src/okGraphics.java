import java.util.ArrayList;

import processing.core.PApplet;

public class okGraphics extends PApplet {
	
	ArrayList<Sprite> stuff = new ArrayList<Sprite>();
	
	public okGraphics() { //Constructor
		
	}
	
	public void setup() { //Just like arduino, setup code runs once
		
	}
	
	public void draw() { //Just like the loop function in arduino, except this one can also draw stuff
		background(255);
		for (Sprite e : stuff)
			e.draw(this);
	}
	
}
