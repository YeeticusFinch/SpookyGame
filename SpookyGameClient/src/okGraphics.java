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
	
	// message = x,y,vx,vy,w,h,icon/&;
	// message = x,y,vx,vy,w,h,icon,name/&;
	//edit(int x, int y, int vx, int vy, int w, int h, int icon, PApplet g, String name)
	public void parseMessage(String m) {
		int si = 0; //Sprite index
		
		while (m.charAt(0) != '&' & m.length() > 2) {
			stuff.get(si).edit( //REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
					Integer.parseInt(m.substring(0, m.indexOf(','))), 
					Integer.parseInt(m.substring(m.indexOf(',')+1, m.indexOf(',', m.indexOf(',')+1))), 
					Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',')+1), m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1))), 
					Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1), m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1))),
					Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1), m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1))),
					Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1), m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1))), 
					Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1), m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1)+1))), 
					this,
					m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1)+1), m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1)+1)+1))
					);
			si++;
			m = m.substring(m.indexOf('/')+1);
		}
		
	}
	
}
