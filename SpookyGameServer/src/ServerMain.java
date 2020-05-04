import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

	private static Socket socket;
	private static ServerSocket serverSocket;
	
	ArrayList<Entity> stuff = new ArrayList<Entity>();
	ArrayList<Integer> players = new ArrayList<Integer>(); //indices in the stuff ArrayList (players are entities)
	
	private String fancyString = "";
	private boolean changed = false;
	
	public static void main() {
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
	}
	
	private void onMessage() {
		try {
			
			String returnMessage = "";
			socket = serverSocket.accept();
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String temp = br.readLine();
			System.out.println("Inputted Message: " + temp);
			
			if (temp.substring(0, 9).contentEquals("newPlayer")) { //gets called when a player joins, and it will return the player's index when that happens
				
				if (!playerExists(temp.substring(9))) { //if the player already exists, don't reset them, substring(9) is their username
					stuff.add(new Player(0, 0, 30, 40, 0, 5, temp.substring(9))); // a new player is added
					players.add(stuff.size()-1);
					returnMessage = "p"+(stuff.size()-1);
				} else
				returnMessage = "p"+getPlayerIndex(temp.substring(9));
			}
			else
				returnMessage = stringifyStuff();
			
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
	
	private String stringifyStuff() {
		String result = "";
		if (!changed)
			return fancyString;
		
		for (Entity e : stuff) {
			result += e.x+","+e.y+","+e.vx+","+e.vy+","+e.w+","+e.h+","+e.icon;
			if (e instanceof Player) result += ","+e.getName();
			result+="/";
		}
		
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
	
}
