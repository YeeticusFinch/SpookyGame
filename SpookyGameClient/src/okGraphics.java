import java.util.ArrayList;

import processing.core.PApplet;

public class okGraphics extends PApplet {
	
	ArrayList<Sprite> stuff = new ArrayList<Sprite>();
	String name = "";
	int playerIndex;
	int updateCount = 0;
	
	public okGraphics() { //Constructor
		
		playerIndex = Integer.parseInt(Communicator.transmit("newPlayer"+name).substring(1));
		System.out.println("Your player index is " + playerIndex);
	}
	
	public void setup() { //Just like arduino, setup code runs once
		
	}
	
	public void draw() { //Just like the loop function in arduino, except this one can also draw stuff
		background(255);
		
		for (Sprite e : stuff)
			e.draw(this);
		
		if (updateCount >= 10)
			parseMessage(Communicator.transmit("update"));
		updateCount++;
	}
	
	public void parseMessage(String message) {
		
	}
	
}
