package waterSymbol.board;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import waterSymbol.board.cases.*;

public class Board {
	private List<Case> cases;
	private float ratio;
	
	public Board(int width, int height) {
		this.ratio = (width/1920f)<(height/1080f)?(width/1920f):(height/1080f);
		
		this.cases = new ArrayList<Case>();
	}
	
	public void addCase(Case c) {
		this.cases.add(c);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for(Case c : cases) {
			c.update(container, game, delta);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		for(Case c : cases) {
			c.render(container, game, context, ratio);
		}
	}
}
