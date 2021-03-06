
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
 
public class Communicator {
 
    private static Socket socket;
    public static String host;
    
    public static String transmit(String text)
    {
    	String message = "";
        try
        {
        	//Only uncomment one of the following (DEPRICATED)
            //host = "ovh.lerdorf.com"; //Choose this one to connect to server (server must be running on ovh.lerdorf.com)
        	//host = "localhost"; //Choose this one to run locally (server must also be running locally)
            
            int port = 25000;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
 
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
 
            bw.write(text + "\n");
            bw.flush();
            System.out.println("Message sent: " + text);
            
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            message = br.readLine();
            System.out.println("Message received: " + message);
            
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return message;
    }
}