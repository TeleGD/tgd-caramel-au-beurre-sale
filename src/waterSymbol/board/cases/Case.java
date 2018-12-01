package waterSymbol.board.cases;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import waterSymbol.Character;

public abstract class Case {
	private int x, y;
	private float width, height;
	private Image sprite;
	private Character character;
	private Color filter;
	
	public Case(int x, int y, String type, float ratio) {
	
		this.x = x;
		this.y = y;

		setSprite(AppLoader.loadPicture ("/images/"+ type+ ".png"));
		
		this.width = 1920f/35f * ratio;
		this.height = 1080f/20f * ratio;
		
		filter = new Color(255,255,255,255);
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite.copy();
	}

	public void setCharacter (Character character) {
		this.character = character;
	}

	public Character getCharacter () {
		return this.character;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {

	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawImage(sprite, x*width, y*height, width*(x+1), (y+1)*height, 0, 0, sprite.getWidth(), sprite.getHeight(),filter);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g, float ratio) {
		g.drawImage(sprite, x*width*ratio, y*height*ratio, width*ratio*(x+1), (y+1)*height*ratio, 0, 0, sprite.getWidth(), sprite.getHeight());
	}

	public void highlight(boolean b) {
		if (b)
			filter = new Color(49, 140, 231, 254);
		else
			filter = new Color(247, 35, 12, 254);
	}
	
	public void outlight() {
		filter = new Color(255,255,255,255);
	}

	public boolean isAccessible() {
		return (character==null && !(this instanceof Wall));
	}
}
