package waterSymbol.board.cases;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public abstract class Case {
	private int x, y;
	private float width, height;
	private Image sprite;
	
	public Case(int x, int y, String type, float ratio) {
		this.x = x;
		this.y = y;
		
		setSprite(AppLoader.loadPicture ("/images/"+ type+ ".png"));
		
		this.width = 1920f/35f * ratio;
		this.height = 1080f/20f * ratio;
	}
	
	public void setSprite(Image sprite) {
		this.sprite = sprite.copy();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawImage(sprite, x*width, y*height, width*(x+1), (y+1)*height, 0, 0, sprite.getWidth(), sprite.getHeight());
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
}
