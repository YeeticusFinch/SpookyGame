import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import processing.core.PApplet;

public class okGraphics extends PApplet {
	
	ArrayList<Sprite> stuff = new ArrayList<Sprite>();
	String name = "";
	int playerIndex;
	int updateCount = 0;
	long lastUpdate = 0;
	
	JFrame frame = new JFrame("Fancy Window");
	JOptionPane pane;
	String input;
	JDialog d;
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	boolean[] keys = new boolean[200];
	
	public okGraphics() { //Constructor
		
		changeButtons();
		pane = new JOptionPane("Welcome to a Spooky Game, \ntype 'client' if you want to connect to your client (debugging), \nleave blank to just connect to the server");
		d = pane.createDialog(frame, "BONJOUR");
		pane.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
		if (JOptionPane.showInputDialog(pane).equalsIgnoreCase("client"))
			Communicator.host = "localhost";
		else
			Communicator.host = "ovh.lerdorf.com";
		
		changeButtons();
		pane = new JOptionPane("Input your fancy username");
		d = pane.createDialog(frame, "Create Username");
		pane.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
		name = JOptionPane.showInputDialog(pane);
		
		try {
			playerIndex = Integer.parseInt(Communicator.transmit("newPlayer"+name).substring(1));
			System.out.println("Your player index is " + playerIndex);
			
			changeButtons();
			pane = new JOptionPane("Successfully connected to the server\nyour username is " + name + " and your ID is " + playerIndex);
			d = pane.createDialog(frame, "YAAAY");
			d.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
			d.setVisible(true);
		} catch (Exception e) {
			changeButtons();
			pane = new JOptionPane("Error connecting to server\nthe game will not work");
			d = pane.createDialog(frame, "REEEEEE");
			d.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
			d.setVisible(true);
		}
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
			//REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
			int x = Integer.parseInt(m.substring(0, m.indexOf(',')));
			int y = Integer.parseInt(m.substring(m.indexOf(',')+1, m.indexOf(',', m.indexOf(',')+1)));
			int vx = Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',')+1)+1, m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)));
			int vy = Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1, m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)));
			int w = Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1, m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)));
			int h = Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1, m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1)));
			int icon = Integer.parseInt(m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1)+1, m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1)+1)));
			String name = m.substring(m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',', m.indexOf(',')+1)+1)+1)+1)+1)+1)+1, m.indexOf('/'));
					
			if (stuff.size() <= si)
				stuff.add(new Sprite(x, y, vx, vy, w, h, icon, this, name));
			else
				stuff.get(si).edit( 
					x, 
					y, 
					vx, 
					vy,
					w,
					h, 
					icon, 
					this,
					name
					);
			si++;
			m = m.substring(m.indexOf('/')+1);
		}
		
	}
	
	public void keyPressed() {
		System.out.println("Keypress");
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
	
	private void changeButtons() {
		int i = (int)(10*Math.random());
		if (1 == i) {
			UIManager.put("OptionPane.cancelButtonText", "nope");
			UIManager.put("OptionPane.okButtonText", "yup");
		} else if (2 == i) {
			UIManager.put("OptionPane.cancelButtonText", "nah");
			UIManager.put("OptionPane.okButtonText", "yeah");
		} else if (3 == i) {
			UIManager.put("OptionPane.cancelButtonText", "plz no");
			UIManager.put("OptionPane.okButtonText", "sure");
		} else if (4 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Idiotic");
			UIManager.put("OptionPane.okButtonText", "Dank");
		} else if (5 == i) {
			UIManager.put("OptionPane.cancelButtonText", "absolutely not");
			UIManager.put("OptionPane.okButtonText", "absolutely");
		} else if (6 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Nuo");
			UIManager.put("OptionPane.okButtonText", "Yeet");
		} else if (7 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Why");
			UIManager.put("OptionPane.okButtonText", "Why not");
		} else if (7 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Spooky");
			UIManager.put("OptionPane.okButtonText", "Fancy");
		} else if (8 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Not cool");
			UIManager.put("OptionPane.okButtonText", "Guavy");
		} else if (9 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Not fancy");
			UIManager.put("OptionPane.okButtonText", "Extremely Fancy");
		} else if (10 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Terrifying");
			UIManager.put("OptionPane.okButtonText", "Amazing");
		} else {
			UIManager.put("OptionPane.cancelButtonText", "Cancel");
			UIManager.put("OptionPane.okButtonText", "Yoink");
		}
	}
	
}
