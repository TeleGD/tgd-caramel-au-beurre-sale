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
	private float ratio;
	
	public TeamBuilder(int width, int height) {
		
		ratio = (width/1920f)<(height/1080f)?(width/1920f):(height/1080f);
		charSize = 200*ratio;
		
		choices = new ArrayList<Character>();
		choices.add(new Character("PAUL", "0"));
		choices.add(new Character("Jean-Mi", "1"));
		choices.add(new Character("Bob", "2"));
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.black);
		context.drawImage(image, 0, 0, container.getWidth(), container.getHeight(), 0, 0, image.getWidth(), image.getHeight());
		context.drawRect(680*ratio, 160*ratio, charSize, charSize);
		context.drawRect(1050*ratio, 160*ratio, charSize, charSize);
		context.drawRect(680*ratio, 520*ratio, charSize, charSize);
		context.drawRect(1050*ratio, 520*ratio, charSize, charSize);
	}
	
	
	
}
