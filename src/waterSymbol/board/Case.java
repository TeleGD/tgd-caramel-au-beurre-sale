package waterSymbol.board;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public abstract class Case {
	protected int x;
	protected int y;
	protected Image sprite;
	
	public Case(int x, int y, String type) {
		this.x = x;
		this.y = y;
		
		setSprite(AppLoader.loadPicture ("/images/"+ type+ ".png"));
	}
	
	public void setSprite(Image sprite) {
		this.sprite = sprite.copy();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(sprite, x, y);
	}
}
