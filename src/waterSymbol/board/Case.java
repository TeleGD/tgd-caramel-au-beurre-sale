package waterSymbol.board;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Case {
	private int x;
	private int y;
	private Image sprite;
	
	public Case(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(sprite, x, y);
	}
}
