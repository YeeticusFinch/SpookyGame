
public class Player extends Person {

	String username;
	
	public Player(int x, int y, int w, int h, int icon, int speed, String username) {
		super(x, y, w, h, icon, speed);
		this.username = username;
		name = username;
	}
	
	public String getName() {
		return username;
	}

}
