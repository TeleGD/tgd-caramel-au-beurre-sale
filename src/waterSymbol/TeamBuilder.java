package waterSymbol;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class TeamBuilder {

	private ArrayList<Character> choices;
	private static Image image = AppLoader.loadPicture("/images/teamBuilder.png");
	
	private float charSize;
	private float ratioChar;
	private float xmin;
	private float xmax;
	private float ymin;
	private float ymax;
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	
	
	public TeamBuilder(int width, int height) {
		
		adpatSize(width, height);
		
		choices = new ArrayList<Character>();
		choices.add(new Character("a","a"));
		choices.add(new Character("a","a"));
		choices.add(new Character("a","a"));
	}
	
	private void adpatSize(int width, int height) {
		int a = (width * image.getHeight());
		int b = (image.getWidth() * height);
		int heightS = height;
		int widthS = width;
		if (a > b) {
			heightS = (image.getHeight() * width / image.getWidth());
		} else if (b > a) {
			widthS = (image.getWidth() * height / image.getHeight());
		}
		xmin = (width - widthS) / 2;
		ymin = (height - heightS) / 2;
		xmax = xmin + widthS;
		ymax = ymin + heightS;
		
		ratioChar = (width/1920f)>=(height/1080f)?(width/1920f):(height/1080f);
		charSize = 200*ratioChar;
		
		x1 = (int) (680*ratioChar+xmin);
		x2 = (int) (1050*ratioChar+xmin);
		y1 = (int) (160*ratioChar+ymin);
		y2 = (int) (520*ratioChar+ymin);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.black);
		context.drawImage(image, xmin, ymin, xmax, ymax, 0, 0, image.getWidth(), image.getHeight());
		context.drawRect(x1, y1, charSize, charSize);
		context.drawRect(x2, y1, charSize, charSize);
		context.drawRect(x1, y2, charSize, charSize);
		context.drawRect(x2, y2, charSize, charSize);
	}
	
}
