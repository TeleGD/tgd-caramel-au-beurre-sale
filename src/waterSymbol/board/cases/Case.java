package waterSymbol.board.cases;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import waterSymbol.Character;

public abstract class Case {
	private int j, i;
	private float width, height;
	private Image sprite;
	private Character character;
	private Color filter;
	
	public Case(int j, int i, String type, float ratio) {
	
		this.j = j;
		this.i = i;

		setSprite(AppLoader.loadPicture ("/images/"+ type+ ".png"));
		
		this.width = 1920f/35f * ratio;
		this.height = 1080f/20f * ratio;
		
		filter = new Color(255,255,255,255);
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite.copy();
	}

	public int [] getPos () {
		return new int [] {this.i, this.j};
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
		g.drawImage(sprite, j *width, i *height, width*(j +1), (i +1)*height, 0, 0, sprite.getWidth(), sprite.getHeight(),filter);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getJ() {
		return j;
	}

	public int getI() {
		return i;
	}

	public void highlight(boolean b) {
		if (b)
			filter = new Color(187, 210, 225, 255);
		else
			filter = new Color(247, 180, 150, 255);
	}
	
	public void outlight() {
		filter = new Color(255,255,255,255);
	}

	public boolean isAccessible() {
		return (character==null && !(this instanceof Wall));
	}
}
