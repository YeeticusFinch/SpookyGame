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
	
}
