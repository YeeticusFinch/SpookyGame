import processing.core.PApplet;
import processing.core.PImage;

//if something looks like a resource, put it here
public class Resources {

	public static PImage[] pics = new PImage[10000];
	public static String[] picNames = new String[10000];
	public static int loadedPics = 0;
	public static final String fp = "https://fancy.lerdorf.com/images/";
	
	public static PImage getImage(String fp, PApplet g) {
		if (loadedPics > 0 && contains(picNames, fp, loadedPics)) {
			return pics[indexOf(picNames, fp, loadedPics)];
		}
		else {
			picNames[loadedPics] = fp;
			pics[loadedPics] = g.loadImage(fp);
			loadedPics++;
			//System.out.println(fp);
			return pics[loadedPics-1];
		}
	}
	
	public static int indexOf(String[] data, String x, int n) {
		for (int i = 0; i < n; i++)
			if (data[i].equals(x))
				return i;
		return -1;
	}
	
	public static boolean contains(String[] data, String x, int n) {
		for (int i = 0; i < n; i++)
			if (data[i].equals(x))
				return true;
		return false;
	}
	
}
