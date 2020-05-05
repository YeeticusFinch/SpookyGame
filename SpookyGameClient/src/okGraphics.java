import java.util.ArrayList;

import processing.core.PApplet;

public class okGraphics extends PApplet {
	
	ArrayList<Sprite> stuff = new ArrayList<Sprite>();
	String name = "";
	int playerIndex;
	int updateCount = 0;
	long lastUpdate = 0;
	
	boolean[] keys = new boolean[200];
	
	public okGraphics() { //Constructor
		name = "Fred";
		playerIndex = Integer.parseInt(Communicator.transmit("newPlayer"+name).substring(1));
		System.out.println("Your player index is " + playerIndex);
	}
	
	public void setup() { //Just like arduino, setup code runs once
		
	}
	
	public void draw() { //Just like the loop function in arduino, except this one can also draw stuff
		background(255);
		
		for (Sprite e : stuff)
			e.draw(this);
		
		if (millis()-lastUpdate>50)
			update();
		
		System.out.println("drawed");
	}
	
	public void update() {
		parseMessage(Communicator.transmit(controllerMessage()));
		for (Sprite e : stuff)
			e.update();
		System.out.println("updated");
	}
	
	public String controllerMessage() {
		String r = ""+playerIndex+":"+name+"&";
		
		if (keys[87])
			r += "w";
		if (keys[65])
			r += "d";
		if (keys[83])
			r += "s";
		if (keys[68])
			r += "a";
		
		return r;
	}
	
	// message = x,y,vx,vy,w,h,icon/&;
	// message = x,y,vx,vy,w,h,icon,name/&;
	//edit(int x, int y, int vx, int vy, int w, int h, int icon, PApplet g, String name)
	public void parseMessage(String m) {
		int si = 0; //Sprite index
		
		int pID = Integer.parseInt(m.substring(0, m.indexOf(':')));
		if (pID != playerIndex) {
			playerIndex = pID;
		}
		
		m = m.substring(m.indexOf(':')+1);
		
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
	
	public void keyPressed() {
		keys[keyCode] = true;
		/*if (keyCode == 87)
			keyCode = this.UP;
		else if (keyCode == 65)
			keyCode = this.LEFT;
		else if (keyCode == 83)
			keyCode = this.DOWN;
		else if (keyCode == 68)
			keyCode = this.RIGHT;*/
	}
	
	public void keyReleased() {
		keys[keyCode] = false;
	}
	
}
