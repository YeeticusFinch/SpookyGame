import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ServerMain extends JPanel implements ActionListener {

	private static Socket socket;
	private static ServerSocket serverSocket;
	
	ArrayList<Entity> stuff = new ArrayList<Entity>();
	ArrayList<Integer> players = new ArrayList<Integer>(); //indices in the stuff ArrayList (players are entities)
	
	private String fancyString = "";
	private boolean changed = false;
	
	Timer fancyClock;
	
	public static void main(String args[]) {
		try {
			
			int port = 25000;
			serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port " + port);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
		
		ServerMain fancyServer = new ServerMain();

	}
	
	public ServerMain() { // constructor runs once on startup
		fancyClock = new Timer(50, (ActionListener) this); //Timer fires every 50 milliseconds
		//fancyClock.setInitialDelay(500); //Wait half a second before starting
		fancyClock.setRepeats(true);
		fancyClock.start();
		while (true) {
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("REEEEE");
			}
		}
	}
	
	private void onMessage() {
		try {
			
			String returnMessage = "";
			socket = serverSocket.accept();
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String temp = br.readLine();
			int tempID;
			System.out.println("Inputted Message: " + temp);
			
			if (temp.length() >8 && temp.substring(0, 9).contentEquals("newPlayer")) { //gets called when a player joins, and it will return the player's index when that happens
				
				if (!playerExists(temp.substring(9))) { //if the player already exists, don't reset them, substring(9) is their username
					stuff.add(new Player(0, 0, 30, 40, 0, 5, temp.substring(9))); // a new player is added
					players.add(stuff.size()-1);
					returnMessage = "p"+(stuff.size()-1);
					changed = true;
				} else
				returnMessage = "p"+getPlayerIndex(temp.substring(9));
			}
			else {
				
				tempID = Integer.parseInt(temp.substring(0, temp.indexOf(':'))); //Player index
				Player p = (Player)stuff.get(players.get(tempID));
				if (!p.username.equalsIgnoreCase(temp.substring(temp.indexOf(':')+1, temp.indexOf('&')))) {
					tempID = getPlayerIndex(temp.substring(temp.indexOf(':')+1, temp.indexOf('&')));
					p = (Player)stuff.get(players.get(tempID));
				}
				temp = temp.substring(temp.indexOf('&')+1);
				System.out.println("keyPresses = " + temp);
				p.vx = 0;
				p.vy = 0;
				for (int i = 0; i < temp.length(); i++) {
					switch (temp.charAt(i)) {
						case 'w':
							p.vy = -p.speed;
							break;
						case 'd':
							p.vx = -p.speed;
							break;
						case 's':
							p.vy = p.speed;
							break;
						case 'a':
							p.vx = p.speed;
							break;
					}
				}
				
				if (changed)
					returnMessage = stringifyStuff(tempID);
				else
					returnMessage = "";
			}
				
			
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(returnMessage + "\n");
			System.out.println("Message sent to the client is " + returnMessage);
			bw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
	}
	
	private String stringifyStuff(int pID) {
		
		String result = pID + ":";
		if (!changed)
			return fancyString;
		System.out.print("Stringify: ");
		for (Entity e : stuff) {
			result += e.x+","+e.y+","+e.vx+","+e.vy+","+e.w+","+e.h+","+e.icon;
			if (e instanceof Player) result += ","+e.getName();
			result+="/";
		}
		result += "&";
		fancyString = result;
		System.out.println(result);
		changed = false;
		return result;
	}
	
	private boolean playerExists(String name) {
		if (players.size() > 0) {
			for (int e : players)
				if (stuff.get(e).getName().equalsIgnoreCase(name))
					return true;
		}
		return false;
	}
	
	private int getPlayerIndex(String name) { // Returns -1 if no player exists by that name
		if (players.size() > 0) {
			for (int e : players)
				if (stuff.get(e).getName().equalsIgnoreCase(name))
					return e;
		}
		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		onMessage();
		System.out.println("3");
		for (int i = 0; i < stuff.size(); i++) {
			stuff.get(i).update();
			if (stuff.get(i).vx != 0 || stuff.get(i).vy != 0)
				changed = true;
			if (stuff.get(i).dead) {
				stuff.remove(i);
				i--;
			}
		}
	}
	
}
